public class TransactionImput{
    public String transactionOutputId; //reference to Transaction Outputs --> transactionId
    public TransactionOutput UTXO;  //contain unspend transaction output -->bitcoin convention

    public TransactionImput(String transactionOutputId) {
        this.transactionOutputId = transactionOutputId;
    }

}