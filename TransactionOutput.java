import java.security.PublicKey;

public class TransactionOutput{
    public String id;
    public PublicKey reciepient; //new owner of the coin
    public float value;  //amount of coin in possession
    public String parentTransactionId; //id of the transaction'
//constructor
    public TransactionOutput(PublicKey reciepient, float value, String parentTransactionId){
        this.reciepient = reciepient;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = StringUtil.applySha256(StringUtil.getStringFromKey(reciepient)+Float.toString(value)+parentTransactionId);
    }

    //check whose coin it belong to
    public boolean isMine(PublicKey publicKey){
        return (publicKey == reciepient);
    }
}

