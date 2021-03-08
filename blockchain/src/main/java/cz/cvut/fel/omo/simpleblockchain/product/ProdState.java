package cz.cvut.fel.omo.simpleblockchain.product;

public enum ProdState {
    BY_FARMER,
    IN_TRANSITION_TO_PROCESSOR,
    BY_PROCESSOR,
    BY_DISTRIBUTOR,
    IN_TRANSACTION_TO_RETAILER,
    BY_RETAILER,
    BY_CUSTOMER,
    IN_TRANSACTION_TO_CUSTOMER
}
