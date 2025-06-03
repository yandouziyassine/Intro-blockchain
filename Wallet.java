package yandouzi;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.security.*;  //import security protection to the program for finance
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


//preparing the wallet
public class Wallet {
    public PrivateKey privateKey;  //use to sign our transaction so nobody can use those coin
    public PublicKey publicKey;  // act as our adress, also use to verify that the signature is valide and nobody tampered the the coin

    public HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();  //list of unspent transaction

    public Wallet(){
        generateKeyPair();
    }

    public void generateKeyPair(){
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");   

 //initialize the key generator and generate a KeyPair
            keyGen.initialize(ecSpec, random); // 256 byte generate an acceptable security level
            KeyPair keyPair = keyGen.generateKeyPair();
            //set the public and private keys from the KeyPair
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } 
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public float getBalance(){
        float total = 0;
        for (Map.Entry<String, TransactionOutput> item : yandouzi.UTXOs.entrySet()){
            TransactionOutput UTXO = item.getValue();
            if (UTXO.isMine(publicKey)){   //if output ==  my coin
                UTXOs.put(UTXO.id, UTXO);  //add it to our list of unspent transaction
                total += UTXO.value;
            }
        }
        return total;
    }

//generate and return a transaction from this wallet
    public Transaction sendFund(PublicKey _recipient, float value){
        if(getBalance()< value){
            System.out.println("#Not enough fund for the transaction, transaction failed");
            return null;
        }
        ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
        float total = 0;
        for (Map.Entry<String, TransactionOutput> item : UTXOs.entrySet()){
            TransactionOutput UTXO = item.getValue();
            total += UTXO.value;
            inputs.add(new TransactionInput(UTXO.id));
            if (total > value) break;
        }
        Transaction newTransaction = new Transaction(publicKey, _recipient, value, inputs);
        newTransaction.generateSignature(privateKey);

        for (TransactionInput input : inputs){
            UTXOs.remove(input.transactionOutputId);
        }
        return newTransaction;
    }
}
