package cz.cvut.fel.omo.simpleblockchain.product;

import cz.cvut.fel.omo.simpleblockchain.node.Node;
import lombok.Getter;
import lombok.Setter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class Product {

    private String name;
    private double price;
    private List<ProductData> data;

    public Product(){
        this.data = new ArrayList<>();

    }
    public Product(String name){
        this.name = name;
        this.data = new ArrayList<>();
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void addPublicData(Node sender, String info) throws SignatureException {
        sender.getWallet().getSign().update(info.getBytes());
        byte[] signature = sender.getWallet().getSign().sign();
        ProductData newData = new ProductData(sender, info);
        newData.setSignature(signature);
        data.add(newData);
    }

    public void addClassifiedData(Node sender, Node recepient, String info) throws SignatureException, BadPaddingException, IllegalBlockSizeException {
        byte[] msg = info.getBytes();
        recepient.getWallet().getCipher().update(msg);
        byte[] encryptedMsg  = recepient.getWallet().getCipher().doFinal();
        sender.getWallet().getSign().update(encryptedMsg);
        byte[] signature = sender.getWallet().getSign().sign();
        ProductData newData = new ProductData(sender, recepient, encryptedMsg, signature);
        data.add(newData);
    }

    public List<String> readPublicData() {
        List<String> productData;
        productData = this.getData()
                .stream()
                .filter(data -> data.getClassifiedMsg() == null)
                .map(ProductData::getMsg)
                .collect(Collectors.toList());
        return productData;

    }
    public List<String> readClassifiedData(Node reader){
        List<String> classifiedData ;
        classifiedData = this.getData()
                .stream()
                .filter(ProductData::hasClassifiedMsg)
                .filter(data -> data.getRecepient() == reader)
                .map(data -> {
                    try {
                        return decipherUtil(reader, data.getClassifiedMsg());
                    } catch (BadPaddingException | InvalidKeyException | IllegalBlockSizeException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
        return classifiedData;
    }
    public void readUtil(Node reader, ProductData data){

    }
    public String decipherUtil(Node reader, byte[] ciphertext) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        return reader.getWallet().readClassifiedData(ciphertext);
    }

}
