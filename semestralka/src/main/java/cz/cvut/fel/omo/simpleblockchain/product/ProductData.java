package cz.cvut.fel.omo.simpleblockchain.product;

import cz.cvut.fel.omo.simpleblockchain.node.Node;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
public class ProductData {

    private HashMap<Node, ArrayList<String>> data;

    public ArrayList<String> getProductInfo(Node node){

        return new ArrayList<>();
    }

    public ArrayList<String> getProductInfo(){
        return new ArrayList<>();
    }

    public void addData(Node node, String data){
        this.data.get(node).add(data);

    }

}
