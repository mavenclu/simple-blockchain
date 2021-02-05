package cz.cvut.fel.omo.simpleblockchain.node;

import cz.cvut.fel.omo.simpleblockchain.node.NodeUtils.BusinessType;

public class Customer extends Node {
    public Customer(String identification, BusinessType type, String trademark) {
        super(identification, type, trademark);
    }
}
