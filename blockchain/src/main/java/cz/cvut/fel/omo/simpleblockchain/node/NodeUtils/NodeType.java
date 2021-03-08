package cz.cvut.fel.omo.simpleblockchain.node.NodeUtils;

public enum NodeType {
    FARMER("farmer"),
    CUSTOMER("customer"),
    DISTRIBUTOR("distributor"),
    PROCESSOR("processor"),
    RETAILER("retailer"),
    SUPPLIER("supplier"),
    GOVERNMENT("government");

    private final String label;

    private NodeType(String label) {
        this.label = label;
    }


}
