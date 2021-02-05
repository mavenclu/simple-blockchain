package cz.cvut.fel.omo.simpleblockchain.node.farmer;

public class FarmerCreator extends FCreator {

    @Override
    protected Farmer makeFarmer(String farmerType) {
        if (farmerType.equals("small")){
            return new SmallFarmer();
        }else if (farmerType.equals("large")){
            return new LargeFarmer();
        }else {
            return null;
        }
    }
}
