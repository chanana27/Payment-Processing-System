package com.cpt.payments.service.interfaces;

import com.cpt.payments.dto.TransactionResponseDTO;
import com.cpt.payments.dto.TransactionDTO;

public interface PaymentStatusService {
	public TransactionResponseDTO insertPayment(TransactionDTO transactionDTO);
}
