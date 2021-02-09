package cz.cvut.fel.omo.simpleblockchain.node;

import cz.cvut.fel.omo.simpleblockchain.finance.Wallet;
import cz.cvut.fel.omo.simpleblockchain.node.NodeUtils.NodeType;
import cz.cvut.fel.omo.simpleblockchain.product.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.UUID;

@Getter
@Setter
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private long id;
    private UUID identification;
    private Wallet wallet;
    private double profit;
    private String name;
    private NodeType type;




    public Node(NodeType type, String name) {
        this.identification = UUID.randomUUID();
        this.type = type;
        this.name = name;
        wallet = new Wallet();
    }


    public void addUnclassifiedProductData(Node sender, Product product, String data) throws SignatureException {
        byte[] msg = data.getBytes(StandardCharsets.UTF_8);
        this.getWallet().getSign().update(msg);
        byte[] signature = this.getWallet().getSign().sign();
        product.addData(this, data,  signature);
    }
    public void addClassifiedProductData(Product product, String data) throws BadPaddingException, IllegalBlockSizeException {
        byte[] input = data.getBytes();
        this.getWallet().getCipher().update(input);
        byte[] cipherText = this.getWallet().getCipher().doFinal();
        product.addData(this, new String(cipherText),null);//todo
    }
    public String readPublicProductData(Product product){
        return ""; //todo
    }

    public String readClassifiedData(Product product) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        String ciphertext = new String(product.getData().get(this.getType()));
        return this.getWallet().readClassifiedData(ciphertext);
    }

    public byte[] signData(String data) throws SignatureException {
        byte[] msg = data.getBytes(StandardCharsets.UTF_8);
        this.getWallet().getSign().update(msg);
        byte[] signature = this.getWallet().getSign().sign();
        return signature;
    }


    public static class Builder {
        private UUID identification;
        private NodeType type;
        private String trademark;

        public Builder(){}


        public Builder withType(NodeType type){
            this.type = type;
            return this;
        }
        public Builder withTrademark(String trademark){
            this.trademark = trademark;
            return this;
        }
        public Node build(){
            return new Node(type, trademark);
        }
    }


}
