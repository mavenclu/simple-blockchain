package cz.cvut.fel.omo.simpleblockchain.finance;

import cz.cvut.fel.omo.simpleblockchain.SimpleBlockchainApplication;
import cz.cvut.fel.omo.simpleblockchain.product.Product;
import lombok.AccessLevel;
import lombok.Getter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Wallet {
    @Getter(AccessLevel.NONE)
    private PrivateKey privateKey;
    public PublicKey publicKey;
    public HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>(); //only UTXOs owned by this wallet.
    private Cipher cipher;
    private Signature sign;


    public Wallet() {
        generateKeyPair();
    }

    /*
    method uses Java.security.KeyPairGenerator to generate an Elliptic Curve KeyPair.
    This methods makes and sets our Public and Private keys.
     */
    public void generateKeyPair() {
        try {
            sign = Signature.getInstance("SHA256withRSA");

//            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
//            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
//            // Initialize the key generator and generate a KeyPair
//            keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
//            KeyPair keyPair = keyGen.generateKeyPair();
//            // Set the public and private keys from the keyPair

            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048);
            KeyPair pair = keyPairGen.generateKeyPair();
            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();

            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            sign.initSign(privateKey);


        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /*
    returns balance and stores the UTXO's owned by this wallet in this.UTXOs
     */
    public float getBalance() {
        float total = 0;
        for (Map.Entry<String, TransactionOutput> item : SimpleBlockchainApplication.UTXOs.entrySet()) {
            TransactionOutput UTXO = item.getValue();
            if (UTXO.isMine(publicKey)) {
                UTXOs.put(UTXO.id, UTXO);
                total += UTXO.value;
            }
        }
        return total;
    }

    /*
    Generates and returns a new transaction from this wallet.
     */
    public Transaction sendFunds(PublicKey recipient, float value) {
        if (getBalance() < value) {
            System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
            return null;
        }
        ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();

        float total = 0;
        for (Map.Entry<String, TransactionOutput> item : UTXOs.entrySet()) {
            TransactionOutput UTXO = item.getValue();
            total += UTXO.value;
            inputs.add(new TransactionInput(UTXO.id));
            if (total > value) break;
        }

        Transaction newTransaction = new Transaction(publicKey, recipient, value, inputs);
        newTransaction.generateSignature(privateKey);

        for (TransactionInput input : inputs) {
            UTXOs.remove(input.transactionOutputId);
        }

        return newTransaction;
    }

    public String readClassifiedData(String cipherText) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        getCipher().init(Cipher.DECRYPT_MODE, this.privateKey);
        byte[] plaintext = getCipher().doFinal(cipherText.getBytes(StandardCharsets.UTF_8));
        return new String(plaintext);

    }
}
