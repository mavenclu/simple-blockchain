package cz.cvut.fel.omo.simpleblockchain.product;

public enum MeasureKind {
    KG("kg"),
    L("l"),
    PIECE("pieces"),
    TON("ton");

    private final String label;

    private MeasureKind(String label) {
        this.label = label;
    }
}
