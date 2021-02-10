package cz.cvut.fel.omo.simpleblockchain.product;

import cz.cvut.fel.omo.simpleblockchain.node.Customer;
import cz.cvut.fel.omo.simpleblockchain.node.Node;
import cz.cvut.fel.omo.simpleblockchain.node.NodeUtils.NodeType;
import lombok.Getter;
import lombok.Setter;

import java.security.Signature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
class to hold info about the product
values are stored in a hashmap where key is a node - type of the actor of the chain,
and an array of strings - infor about the product.
through the process each actor will insert info to array accordign to authorization -
info about product for customers differ from info for suppliers, distributors, governmet.
each node can access  only info of its type, and all can access info for customers
for example Distributor will insert info for Retailer , [.., shippingVehicleNumber 123456, drivesPhoneNumber 1340988745, ...]
such info is of no value for the rest of the nodes and may contain confidential information, shared only between the Distributor and the Retailer,
that is why other nodes have no access to this info

 */
@Getter
@Setter
public class ProductData {

    private Node sender;
    private Node recepient;
    private String claimsToBe;
    private byte[] signature;
    private String msg;
    private byte[] classifiedMsg;

    public ProductData(){}
    public ProductData(String msg, byte[] signature){
        this.msg = msg;
        this.signature = signature;
    }
    public ProductData(String data){
        this.msg = data;
    }
    public ProductData(Node sender, String data){
        this.msg = data;
        this.sender = sender;
    }
    public ProductData(Node sender, String info, byte [] signature){
        this.sender = sender;
        this.msg = info;
        this.signature = signature;
    }
    public ProductData(Node sender, Node recepient, byte[] info, byte [] signature){
        this.sender = sender;
        this.recepient = recepient;
        this.classifiedMsg = info;
        this.signature = signature;
    }
    public boolean hasClassifiedMsg(){
        return this.getClassifiedMsg() != null;
    }

}
