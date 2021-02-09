package cz.cvut.fel.omo.simpleblockchain.node.gov;

import cz.cvut.fel.omo.simpleblockchain.node.Node;

public class Government {
    private static Government government;

    private Government(){ }


    public static synchronized Government getGovernment(){
        if (government == null){
            government = new Government();
        }
        return government;
    }

    public double calculateTaxesBasedOnRevenue(Node entity){
        TaxPolicy taxPolicy = new SmallBusinessTaxPolicy();
        TaxPolicy taxPolicyLarge = new LargeBusinessTaxPolicy();
        TaxContext smallBizCntx = new TaxContext(taxPolicy);
        TaxContext largeBizCntx = new TaxContext(taxPolicyLarge);

        switch (entity.getType().name()){
            case "small":
                return smallBizCntx.tax(entity);
            case "larege":
                return largeBizCntx.tax(entity);
            default:
                System.out.println("Failed to get entity's type.");
                return 0;
        }
    }

}
