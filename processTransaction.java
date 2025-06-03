public boolean processTransaction(){

    if (verifiySignature() == false){
        System.out.println("#Transaction Signature failed to verify");
        return false;
    }

    // gather transaction inputs 
    for (TransactionInput i : inputs){
        i.UTXO = yandouzi.UTXOs.get(i.transactionOutputId);
    }

    //check the validity of the transaction
    if (getOutputsValue() < yandouzi.minimumTransaction){
        System.out.println("#Transaction Inputs to small: " + getInputsValue());
        return false;
    }

    //generate transaction output
    float leftOver = getInputsValue() - value;
    transactionId = calculateHash();
    outputs.add(new TransactionOutput(this.recipient, value, transactionId));
    outputs.add(new TransactionOutput(this.sender, leftOver, transactionId));


    //add outputs to unspend list
    for (TransactionOutput o : outputs){
        yandouzi.UTXOs.put(o.id, o);
    }
    //remove transaction input from UTXO list
    for(TransactionInput i : inputs){
        if (i.UTXO == null) continue;  //transaction = not found --> skip it 
        yandouzi.UTXOs.remove(i.UTXO.id);
    }
    return true;
    
    //return sum of UTXO values
    public float getInputsValue(){
        float total = 0;
        for (TransactionInput i : inputs){
            if (i.UTXO == null) continue;
            total += i.UTXO.value;
        }
        return total;
    }

    
    
    
}