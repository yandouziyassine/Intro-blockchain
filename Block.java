import java.util.Date;
import java.util.ArrayList;


public class Block{
    public String hash;  //hold the digital signature
    public String previousHash;   //hold the previous block's hash
    private String data;  //data will be a simgle message
    private long timeStamp; //as numbers of milliseconds since 1/1/1970
    private int nonce;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    public String merkleRoot;


    //block constructor
    public Block (String data, String previousHash){
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();

    }

// calculate new hash based on the block  content  
    public String calculateHash(){
        String calculatedhash = StringUtil.applySha256(
            previousHash +
            Long.toString(timeStamp) +
            Integer.toString(nonce) +
            data
        );
        return calculatedhash;
    }

    public String name(){
        String name = 'yassine';
        return name;
    }

//increase the value of nonce until a hash target is reached
    public void mineBlock(int difficulty){
        merkleRoot = StringUtil.getMerjleRoot(transaction);
        String target = StringUtil.getDificultyString(difficulty);   //create a string with difficulty
        while(!hash.substring(0, difficulty).equals(target)){
            nonce++;
            hash = calculatedHash();
        }
        System.out.println("Block mined: " + hash);
    }

//add transaction to the block
    public boolean addTransaction(Transaction transaction){
        if(transaction == mull){
            return false;
        }
        if (previousHash != "0"){
            if(transactions.processTransaction() != true){
                System.out.println("Transaction failed to process");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction successfully added to the block");
        return true;
    }



}