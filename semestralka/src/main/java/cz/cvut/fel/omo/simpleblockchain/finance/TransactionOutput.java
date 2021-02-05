package cz.cvut.fel.omo.simpleblockchain.finance;

import cz.cvut.fel.omo.simpleblockchain.block.HashUtil;

import java.security.PublicKey;

public class TransactionOutput {
    public String id;
    public PublicKey reciepient;
    public float value;
    public String parentTransactionId;

    public TransactionOutput(PublicKey reciepient, float value, String parentTransactionId) {
        this.reciepient = reciepient;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = HashUtil.applySha256(
                HashUtil.getStringFromKey(reciepient) +
                Float.toString(value) +
                parentTransactionId);
    }

    /*
    check if coin belongs to you
     */
    public boolean isMine(PublicKey publicKey){
        return publicKey == reciepient;
    }
}
