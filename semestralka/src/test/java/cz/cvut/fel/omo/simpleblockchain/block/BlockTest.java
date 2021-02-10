package cz.cvut.fel.omo.simpleblockchain.block;

import cz.cvut.fel.omo.simpleblockchain.node.Customer;
import cz.cvut.fel.omo.simpleblockchain.node.Distributor;
import cz.cvut.fel.omo.simpleblockchain.node.Node;
import cz.cvut.fel.omo.simpleblockchain.node.NodeUtils.NodeType;
import cz.cvut.fel.omo.simpleblockchain.node.Processor;
import cz.cvut.fel.omo.simpleblockchain.node.farmer.Farmer;
import cz.cvut.fel.omo.simpleblockchain.node.farmer.FarmerCreator;
import cz.cvut.fel.omo.simpleblockchain.product.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.SignatureException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

        public static Product lettuce;
        static FarmerCreator farmerCreator = new FarmerCreator();
        static Node farmer;
        static Processor processor;
        static Customer customer;
        static Distributor distributor;
        private static Farmer competitorFarmer;
        public static ArrayList<Block> blockchain = new ArrayList<Block>();


    @BeforeAll
        static void setUp() throws BadPaddingException, SignatureException, IllegalBlockSizeException {
            lettuce = new Product("Lettuce");
            farmer = farmerCreator.makeFarmer("small", "Farma Opocno");
            competitorFarmer = farmerCreator.makeFarmer("large", "AgroForTen");
            processor = new Processor(NodeType.PROCESSOR, "ProccessedFoods Corp.");
            customer = new Customer(NodeType.CUSTOMER, "Jan Novak");
            distributor = new Distributor(NodeType.DISTRIBUTOR, "Fast and Safe Delivery");

            lettuce.addClassifiedData(farmer, processor, "here are confidential data from farmer: "
                    + farmer.getName() + " to processor: " + processor.getName());
        }
    @Test
    void addTransaction() {

    }

    @Test
    void addData() {
    }
}