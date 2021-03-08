package cz.cvut.fel.omo.simpleblockchain.block;

import cz.cvut.fel.omo.simpleblockchain.channel.Channel;
import cz.cvut.fel.omo.simpleblockchain.finance.Transaction;
import cz.cvut.fel.omo.simpleblockchain.product.Product;
import cz.cvut.fel.omo.simpleblockchain.product.ProductData;
import lombok.Getter;

import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Getter
public class Block {

    public String hash;
    public String previousHash;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<>();
    public HashMap<Product, List<ProductData>> data;
    public List<Channel> channels;
    private final long timeStamp;
    private int nonce;


    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }


    public String calculateHash() {
        return HashUtil.applySha256(
                previousHash + timeStamp + nonce + data + merkleRoot);
    }

    public void mineBlock(int difficulty) {
        merkleRoot = HashUtil.getMerkleRoot(transactions);
        String target = HashUtil.getDifficultyString(difficulty);
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block mined. " + hash);
    }

    public boolean addTransaction(Transaction transaction) {
        if (transaction == null) {
            return false;
        }
        if (!"0".equals(previousHash)) {
            if (!transaction.processTransaction()) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }

    public boolean addData(Product product, ProductData productData) throws SignatureException, InvalidKeyException {
        if (product == null || productData == null) {
            return false;
        }
        if (!verifyDataSignature(productData)) {
            System.out.println("Failed to verify data. Discarded.");
            return false;
        }
        if (this.data.get(product) != null) {
            this.data.get(product).add(productData);
        } else {
            this.data.put(product, new ArrayList<>());
            this.data.get(product).add(productData);
            System.out.println("Data successfully added.");
            return true;
        }

        return false;
    }

    private boolean verifyDataSignature(ProductData productData) throws InvalidKeyException, SignatureException {
        Signature sign = productData.getSender().getWallet().getSign();
        PublicKey senderPubKey = productData.getSender().getWallet().publicKey;
        sign.initVerify(senderPubKey);
        if (productData.hasClassifiedMsg()) {
            sign.update(productData.getClassifiedMsg());
        } else {
            sign.update(productData.getMsg().getBytes());
        }
        return sign.verify(productData.getSignature());
    }


}
