import Core.HexUtils;
import Core.Keccak;
import Core.Parameters;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class KeccakExamples {
    private static Keccak Keccak = new Keccak();

    static class Transaction{
        String recipientAddress;
        String senderAddress;
        String objectTransferred;

        Transaction(String recipientAddress,
                    String senderAddress,
                    String objectTransferred){
            this.recipientAddress = recipientAddress;
            this.senderAddress = senderAddress;
            this.objectTransferred = objectTransferred;
        }

        String getInfo(){
            return this.recipientAddress +
                    this.senderAddress +
                    this.objectTransferred;
        }

        String getHash(){
            return Keccak.getHash(HexUtils.getHex(this.getInfo().getBytes()), Parameters.SHAKE256);
        }
    }

    static class Block{
        List<Transaction> transactions;
        String creatorAddress;
        String prevHash;
        Integer nonce;
        String timestamp;

        Block(String creatorAddress, Integer nonce, String prevHash){
            this.transactions = new ArrayList<>();
            this.prevHash = prevHash;
            this.timestamp = new Timestamp(System.currentTimeMillis()) + "";
            this.nonce = nonce;
            this.creatorAddress = creatorAddress;
        }

        void addTransaction(Transaction transaction){
            this.transactions.add(transaction);
        }

        String getInfo(){
            StringBuilder result = new StringBuilder();

            for(Transaction transaction : this.transactions){
                result.append(transaction.getHash());
            }

            result.append(this.creatorAddress)
                    .append(this.nonce)
                    .append(this.timestamp)
                    .append(this.prevHash);

            return result.toString();
        }

        String getHash(){
            return Keccak.getHash(HexUtils.getHex(this.getInfo().getBytes()), Parameters.SHAKE256);
        }
    }

    public static void main(String[] args) {
        /// Creating of Transactions

        Transaction transaction1 = new Transaction("djs8adj98sa98da",
                "dsadsaddsh8ahda", "3 BTC");
        Transaction transaction2 = new Transaction("ewfddfsfdsf345",
                "98y31n3n1k123", "4 BTC");
        Transaction transaction3 = new Transaction("fdsfsf34r345",
                "-plasdlknaoiasd", "5 BTC");
        Transaction transaction4 = new Transaction("fsdf34r434fds",
                "09dsakjadgkmasmdi", "6 BTC");
        Transaction transaction5 = new Transaction("fdsf3443445544",
                "09982381lkadsuaisd", "7 BTC");
        Transaction transaction6 = new Transaction("092dslksdooeewl",
                "ncnsanucoisao8933", "9 BTC");

        /// Creating of Blocks
        Block genesisBlock = new Block("jdsad8s9ad9s0ald", 0, "0");

        Block block1 = new Block("djs8adj98sa98da", 12343, genesisBlock.getHash());
        block1.addTransaction(transaction1);
        block1.addTransaction(transaction2);
        block1.addTransaction(transaction3);

        Block block2 = new Block("98y31n3n1k123", 84387, block1.getHash());
        block2.addTransaction(transaction4);
        block2.addTransaction(transaction5);

        Block block3 = new Block("09dsakjadgkmasmdi", 123892, block2.getHash());
        block3.addTransaction(transaction6);

        /// Output of results
        System.out.println("Genesis block hash: " + genesisBlock.getHash());
        System.out.println("Block 1 hash: " + block1.getHash());
        System.out.println("Block 2 hash: " + block2.getHash());
        System.out.println("Block 3 hash: " + block3.getHash());
    }

}
