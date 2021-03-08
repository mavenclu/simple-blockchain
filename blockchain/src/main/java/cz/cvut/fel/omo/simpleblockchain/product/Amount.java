package cz.cvut.fel.omo.simpleblockchain.product;

import lombok.Getter;
import lombok.Setter;

/*
class to hold info for the amount and measure for the product.
example 10.4 kg
 */
@Getter
@Setter
public class Amount {
    private double amount;
    private MeasureKind measure;

    public Amount(double amount, MeasureKind measure) {
        this.amount = amount;
        this.measure = measure;
    }
}
