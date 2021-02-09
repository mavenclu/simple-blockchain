package cz.cvut.fel.omo.simpleblockchain.finance;

import cz.cvut.fel.omo.simpleblockchain.SimpleBlockchainApplication;
import cz.cvut.fel.omo.simpleblockchain.block.HashUtil;

import java.security.*;
import java.util.ArrayList;

public class Transaction {

    public String transactionId;
    public PublicKey sender;
    public PublicKey reciepient;
    public float value;
    public byte[] signature;

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    private static int sequence = 0; // a rough count of how many transactions have been generated.

    public Transaction(PublicKey from, PublicKey to, float value,
                       ArrayList<TransactionInput> inputs){
        this.sender = from;
        this.reciepient = to;
        this.value = value;
        this.inputs = inputs;
    }

    private String calculateHash(){
        sequence++;
        return HashUtil.applySha256(
                HashUtil.getStringFromKey(sender) +
                        HashUtil.getStringFromKey(reciepient) +
                        value + sequence
        );
    }
    /*
    signs the data
     */
    public void generateSignature(PrivateKey privateKey){
        String data = HashUtil.getStringFromKey(sender) +
                HashUtil.getStringFromKey(reciepient) +
                value;
        signature = HashUtil.applyECDSASig(privateKey, data);

    }
    /*
    Verifies the data we signed hasnt been tampered with
     */
    public boolean verifySignature(){
        String data = HashUtil.getStringFromKey(sender) +
                HashUtil.getStringFromKey(reciepient) +
                value;
        return HashUtil.verifyECDSASig(sender, data, signature);
     }

     /*
     returns true if new transaction could be created
      */
    public boolean processTransaction(){

        if (!verifySignature()){
            System.out.println("#Transaction signature failed to verify");
            return false;
        }

        for (TransactionInput input : inputs){
            input.UTXO = SimpleBlockchainApplication.UTXOs.get(input.transactionOutputId);
        }

        //Checks if transaction is valid:
        if(getInputsValue() < SimpleBlockchainApplication.minimumTransaction) {
            System.out.println("Transaction Inputs too small: " + getInputsValue());
            System.out.println("Please enter the amount greater than " + SimpleBlockchainApplication.minimumTransaction);
            return false;
        }

        //Generate transaction outputs:
        float leftOver = getInputsValue() - value; //get value of inputs then the left over change:
        transactionId = calculateHash();
        outputs.add(new TransactionOutput( this.reciepient, value,transactionId)); //send value to recipient
        outputs.add(new TransactionOutput( this.sender, leftOver,transactionId)); //send the left over 'change' back to sender

        //Add outputs to Unspent list
        for(TransactionOutput o : outputs) {
            SimpleBlockchainApplication.UTXOs.put(o.id , o);
        }

        //Remove transaction inputs from UTXO lists as spent:
        for(TransactionInput i : inputs) {
            if(i.UTXO == null) continue; //if Transaction can't be found skip it
            SimpleBlockchainApplication.UTXOs.remove(i.UTXO.id);
        }

        return true;
    }

    public float getInputsValue(){
        float total = 0;
        for(TransactionInput input : inputs){
            if (input.UTXO == null) continue;
            total += input.UTXO.value;
        }
        return total;
    }

    public float getOutputsValue(){
        float total = 0;
        for(TransactionOutput output : outputs){
            total += output.value;
        }
        return total;
    }
}
