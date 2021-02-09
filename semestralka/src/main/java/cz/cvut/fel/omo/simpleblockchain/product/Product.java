package cz.cvut.fel.omo.simpleblockchain.product;

import cz.cvut.fel.omo.simpleblockchain.node.Node;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
        ProductData newData = new ProductData(sender, info);
        newData.setSignature(sender.signData(info));
        data.add(newData);
    }

    public void addData(Node sender, String info) throws SignatureException {
        ProductData newData = new ProductData();
        byte[] msg = info.getBytes(StandardCharsets.UTF_8);
        sender.getWallet().getSign().update(msg);
        byte[] signature = sender.getWallet().getSign().sign();
        data.add(this, data,  signature);


    }
}
