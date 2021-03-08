package cz.cvut.fel.omo.simpleblockchain.product;

import cz.cvut.fel.omo.simpleblockchain.node.Customer;
import cz.cvut.fel.omo.simpleblockchain.node.Distributor;
import cz.cvut.fel.omo.simpleblockchain.node.Node;
import cz.cvut.fel.omo.simpleblockchain.node.NodeUtils.NodeType;
import cz.cvut.fel.omo.simpleblockchain.node.Processor;
import cz.cvut.fel.omo.simpleblockchain.node.farmer.Farmer;
import cz.cvut.fel.omo.simpleblockchain.node.farmer.FarmerCreator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.SignatureException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

class ProductTest {

    public static Product lettuce;
    static FarmerCreator farmerCreator = new FarmerCreator();
    static Node farmer;
    static Processor processor;
    static Customer customer;
    static Distributor distributor;
    private static Farmer competitorFarmer;

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
    @Order(1)
    @Test
    void addPublicDataShouldIncreaseDataSize() throws SignatureException {
        int size = lettuce.getData().size();
        lettuce.addPublicData(farmer, "This lettuce was planted on the synny day of March 3rd" +
                "at 8:30 am at the " + farmer.getName() + " farm with identification: " + farmer.getIdentification() + " . A family type of farm where we palnet all the produce with utmost love" +
                "and dedication to povide the best product. Was watered diligently and harvest on the 19 of May. ");
        assertThat(lettuce.getData()).hasSize(size+1);
    }


    @Order(2)
    @Test
    void readPublicDataShouldBeReadableToAll() {
        assertThat(lettuce.readPublicData().toString()).contains("farm with identification: " + farmer.getIdentification() + " . A family type of farm where we palnet all the produce with utmost love" +
        "and dedication to povide the best product.");
    }

    @Order(3)
    @Test
    void addClassifiedDataShouldIncreaseDataSize() throws BadPaddingException, SignatureException, IllegalBlockSizeException {
        int size = lettuce.getData().size();
        lettuce.addClassifiedData(farmer, distributor, "secret from farmer to distributor");
        assertThat(lettuce.getData()).hasSize(size+1);

    }


    @Order(4)
    @Test
    void readClassifiedDataShouldBeAvailableToRecepient() {
        assertThat(lettuce.readClassifiedData(processor).get(0)).contains("here are confidential data from farmer: ");
    }

    @Test
    void readClassifiedDataToProcessorShouldBeUnavaliableToCompetitorFarmer(){
        assertThat(lettuce.readClassifiedData(competitorFarmer)).hasSize(0);
        lettuce.getData().stream().filter(ProductData::hasClassifiedMsg)
                .forEach(productData -> System.out.println(new String(productData.getClassifiedMsg())));
    }
    @Test
    void readClassifiedDataToProcessorShouldBeUnavaliableToDistributor(){
        assertThat(lettuce.readClassifiedData(distributor)).hasSize(1);
    }
    @Test
    void readClassifiedDataToProcessorShouldBeUnavaliableToCustomer(){
        assertThat(lettuce.readClassifiedData(customer)).hasSize(0);
    }
    @Test
    void competitorFarmerShouldReadHidClassifiedMsg() throws BadPaddingException, SignatureException, IllegalBlockSizeException {
        lettuce.addClassifiedData(processor, competitorFarmer, "here is a secret info from processor to competitor");
        assertThat(lettuce.readClassifiedData(competitorFarmer)).hasSize(1);
        assertThat(lettuce.readClassifiedData(competitorFarmer).get(0)).contains("secret info from processor to competitor");
    }
}