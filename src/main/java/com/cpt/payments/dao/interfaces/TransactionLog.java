package com.cpt.payments.dao.interfaces;

public interface TransactionLog {
	
	public boolean logEntry(Integer transactionId, String fromStatus, String toStaus);
}
