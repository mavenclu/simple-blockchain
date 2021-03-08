package cz.cvut.fel.omo.simpleblockchain.node;

import cz.cvut.fel.omo.simpleblockchain.finance.Wallet;
import cz.cvut.fel.omo.simpleblockchain.node.NodeUtils.NodeType;
import cz.cvut.fel.omo.simpleblockchain.product.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.UUID;

@Getter
@Setter
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private long id;
    private final UUID identification;
    private final Wallet wallet;
    private double profit;
    private String name;


    public Node(String name) {
        this.identification = UUID.randomUUID();
        this.name = name;
        wallet = new Wallet();
    }

    public Node() {
        this.identification = UUID.randomUUID();
        this.wallet = new Wallet();
    }


    public static class Builder {
        private String trademark;

        public Builder() {
        }

        public Builder withTrademark(String trademark) {
            this.trademark = trademark;
            return this;
        }

        public Node build() {
            return new Node(trademark);
        }
    }


}
