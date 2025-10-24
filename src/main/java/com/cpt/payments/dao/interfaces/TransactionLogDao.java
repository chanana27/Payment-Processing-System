package com.cpt.payments.dao.interfaces;

import com.cpt.payments.dto.TransactionLog;

public interface TransactionLogDao {
	
	public void createTransactionLog(TransactionLog transactionLog);
}
