package cz.cvut.fel.omo.simpleblockchain.node.NodeUtils;

import cz.cvut.fel.omo.simpleblockchain.node.Node;
import cz.cvut.fel.omo.simpleblockchain.product.Amount;
import cz.cvut.fel.omo.simpleblockchain.product.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {
    private Node demander;
    private String supplierType;
    private Product product;
    private Amount amount;
    private double maxPricePerAmount;
    private double priceAcceptanceInterval;
}
