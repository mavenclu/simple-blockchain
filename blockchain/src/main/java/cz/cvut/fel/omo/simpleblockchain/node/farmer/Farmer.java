package cz.cvut.fel.omo.simpleblockchain.node.farmer;

import cz.cvut.fel.omo.simpleblockchain.node.Node;
import cz.cvut.fel.omo.simpleblockchain.node.NodeUtils.AgricultureSector;
import cz.cvut.fel.omo.simpleblockchain.node.NodeUtils.NodeType;
import cz.cvut.fel.omo.simpleblockchain.product.Amount;
import cz.cvut.fel.omo.simpleblockchain.product.Product;
import lombok.Getter;
import lombok.Setter;


import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class Farmer extends Node {

    private List<AgricultureSector> sectors;
    private HashMap<Product, Amount> amountOfAvaliableProducts;

    public Farmer(NodeType type, String name) {
        super(type, name);
    }


    private void setProductPrice(Product product, double price){
        product.setPrice(price);
    }










}
