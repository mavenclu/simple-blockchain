package cz.cvut.fel.omo.simpleblockchain.channel;

import cz.cvut.fel.omo.simpleblockchain.node.Node;
import cz.cvut.fel.omo.simpleblockchain.product.MeasureKind;
import cz.cvut.fel.omo.simpleblockchain.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Channel {

    private List<Node> participants;

    public Channel() {
        this.participants = new ArrayList<>();
    }

    public Channel(List<Node> participants) {
        this.participants = participants;
    }

    public void register(Node node){

    }
    public void unregister(){

    }

    public void postComodityRequest(Node node, Product product, double amount, MeasureKind kind){

    }


}
