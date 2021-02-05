package cz.cvut.fel.omo.simpleblockchain.node.NodeUtils;

public enum BusinessType {
    SMALL_BUSINESS("small"),
    LARGE_BUSINESS("large");

    public final String label;

    private BusinessType(String label) {
        this.label = label;
    }
}
