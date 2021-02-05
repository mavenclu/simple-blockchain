package cz.cvut.fel.omo.simpleblockchain.node.gov;

import cz.cvut.fel.omo.simpleblockchain.node.Node;

public class LargeBusinessTaxPolicy implements TaxPolicy{
    @Override
    public double taxesToPay(Node node) {
        double taxes = node.getProfit() * 0.2;
        return node.getProfit() > 0 ? taxes : 0;
    }
}
