package cz.cvut.fel.omo.simpleblockchain.node.gov;

import cz.cvut.fel.omo.simpleblockchain.node.Node;

public interface TaxPolicy {

    public double taxesToPay(Node entity);
}
