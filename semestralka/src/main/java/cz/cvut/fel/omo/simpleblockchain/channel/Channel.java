package cz.cvut.fel.omo.simpleblockchain.channel;

import cz.cvut.fel.omo.simpleblockchain.node.Node;
import cz.cvut.fel.omo.simpleblockchain.node.NodeUtils.Request;
import cz.cvut.fel.omo.simpleblockchain.product.Amount;
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
    private List<Request> comodityReqests;

    public Channel() {
        this.participants = new ArrayList<>();
        this.comodityReqests = new ArrayList<>();
    }

    public Channel(List<Node> participants) {
        this.participants = new ArrayList<>();
        this.comodityReqests = new ArrayList<>();
        this.participants = participants;
    }

    public void register(Node node){
        participants.add(node);
    }
    public void unregister(Node node){
        participants.remove(node);
    }

    public void postComodityRequest(Request request){
        comodityReqests.add(request);
    }
    public void respondToComodityRequest(Node supplier, int requestId){
        handleRequest(comodityReqests.get(requestId));
        comodityReqests.remove(requestId);
    }

    private void handleRequest(Request request) {
        double maxPrice = request.getMaxPricePerAmount() + request.getPriceAcceptanceInterval();

    }


}
