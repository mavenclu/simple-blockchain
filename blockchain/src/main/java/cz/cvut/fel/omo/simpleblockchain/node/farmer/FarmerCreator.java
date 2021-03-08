package cz.cvut.fel.omo.simpleblockchain.node.farmer;

import cz.cvut.fel.omo.simpleblockchain.node.NodeUtils.NodeType;

public class FarmerCreator extends FCreator {

    @Override
    public Farmer makeFarmer(String farmerType, String farmername) {
        if (farmerType.equals("small")) {
            return new SmallFarmer(farmername);
        } else if (farmerType.equals("large")) {
            return new LargeFarmer(farmername);
        } else {
            return null;
        }
    }
}
