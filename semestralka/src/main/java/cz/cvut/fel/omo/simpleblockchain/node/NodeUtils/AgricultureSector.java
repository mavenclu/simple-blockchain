package cz.cvut.fel.omo.simpleblockchain.node.NodeUtils;

public enum AgricultureSector {
    CROPS("crops"),
    LIVESTOCK("livestock"),
    DIARY("diary");

    public final String label;

    private AgricultureSector(String label) {
        this.label = label;
    }
}
