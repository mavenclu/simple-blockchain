package cz.cvut.fel.omo.simpleblockchain.node;

import cz.cvut.fel.omo.simpleblockchain.node.NodeUtils.NodeType;
import cz.cvut.fel.omo.simpleblockchain.node.farmer.Farmer;
import cz.cvut.fel.omo.simpleblockchain.node.farmer.FarmerCreator;
import cz.cvut.fel.omo.simpleblockchain.product.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SignatureException;

@SpringBootTest
class NodeTest {
    public Product lettuce;
    FarmerCreator farmerCreator = new FarmerCreator();
    Node farmer;
    Processor processor;
    Customer customer;
    Distributor distributor;
    private Farmer competitorFarmer;

    @BeforeEach
    public void setUp(){
        lettuce = new Product("Lettuce");
        farmer = farmerCreator.makeFarmer("small", "Farma Opocno");
        competitorFarmer = farmerCreator.makeFarmer("large", "AgroForTen");
        processor = new Processor(NodeType.PROCESSOR, "ProccessedFoods Corp.");
        customer = new Customer(NodeType.CUSTOMER, "Jan Novak");
        distributor = new Distributor(NodeType.DISTRIBUTOR, "Fast and Safe Delivery");
    }
    @Order(1)
    @Test
    void addUnclassifiedProductDataShouldBeAbleToReadAll() throws SignatureException {
        farmer.addUnclassifiedProductData(farmer, lettuce, "This lettuce was planted on the synny day of March 3rd" +
                "at 8:30 am at the " + farmer.getName() + " farm. A family type of farm where we palnet all the produce with utmost love" +
                "and dedication to povide the best product. Was watered diligently and harvest on the 19 of May. ");
        System.out.println(competitorFarmer.readPublicProductData(lettuce));
        Assertions.assertThat(competitorFarmer.readPublicProductData(lettuce)).contains("planted on the synny day of March 3rd");
    }

    @Test
    void readPublicProductData() {

    }

    @Test
    void addClassifiedProductData() {
    }

    @Test
    void readClassifiedData() {
    }
}