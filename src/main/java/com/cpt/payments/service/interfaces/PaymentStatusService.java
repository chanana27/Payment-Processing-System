package com.cpt.payments.service.interfaces;

import com.cpt.payments.dto.PaymentResponseDTO;
import com.cpt.payments.dto.TransactionDTO;

public interface PaymentStatusService {
	public PaymentResponseDTO insertPayment(TransactionDTO transactionDTO);
}
