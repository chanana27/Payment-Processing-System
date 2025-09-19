package com.cpt.payments.service.interfaces;

import com.cpt.payments.dto.TransactionDTO;

public interface TransactionStatusHandler {
	
	public boolean processStatus(TransactionDTO transactionDTO);
}
