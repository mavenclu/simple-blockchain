package cz.cvut.fel.omo.simpleblockchain.node;

import cz.cvut.fel.omo.simpleblockchain.finance.Wallet;
import cz.cvut.fel.omo.simpleblockchain.node.NodeUtils.BusinessType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private long id;
    private String identification;
    private Wallet wallet;
    private double profit;
    private BusinessType type;
    private String trademark;

    public Node(String identification, BusinessType type, String trademark) {
        this.identification = identification;
        this.type = type;
        this.trademark = trademark;
        wallet = new Wallet();
    }


    public static class Builder {
        private String identification;
        private BusinessType type;
        private String trademark;

        public Builder(){}

        public Builder withIdentification(String identification){
            this.identification = identification;
            return this;
        }


        public Builder withType(BusinessType type){
            this.type = type;
            return this;
        }
        public Builder withTrademark(String trademark){
            this.trademark = trademark;
            return this;
        }
        public Node build(){
            return new Node(identification, type, trademark);
        }
    }

}
