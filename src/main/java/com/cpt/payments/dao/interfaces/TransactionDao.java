package com.cpt.payments.dao.interfaces;

import com.cpt.payments.entity.TransactionEntity;

public interface TransactionDao {
	
	public boolean createPayment(TransactionEntity transactionEntity);
}
