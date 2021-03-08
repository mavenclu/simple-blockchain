package cz.cvut.fel.omo.simpleblockchain.node.gov;

import cz.cvut.fel.omo.simpleblockchain.node.Node;

public class TaxContext {
    private final TaxPolicy taxPolicy;

    public TaxContext(TaxPolicy taxPolicy) {
        this.taxPolicy = taxPolicy;
    }

    public double tax(Node entity) {
        return taxPolicy.taxesToPay(entity);
    }

}
