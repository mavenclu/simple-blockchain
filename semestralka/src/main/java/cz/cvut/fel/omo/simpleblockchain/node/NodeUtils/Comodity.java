package cz.cvut.fel.omo.simpleblockchain.node.NodeUtils;

import cz.cvut.fel.omo.simpleblockchain.product.Amount;
import cz.cvut.fel.omo.simpleblockchain.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class Comodity {

    private HashMap<Product, Amount> comodities;
}
