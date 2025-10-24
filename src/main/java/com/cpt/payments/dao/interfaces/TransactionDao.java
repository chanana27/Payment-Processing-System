package com.cpt.payments.dao.interfaces;

import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.entity.TransactionEntity;

public interface TransactionDao {
	
	public Long createPayment(TransactionEntity transactionEntity);
	public TransactionDTO findByTxnReference(String txnReference);
	public boolean updateTransaction(TransactionDTO transactionDTO);
}
