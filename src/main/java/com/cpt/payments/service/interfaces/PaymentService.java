package com.cpt.payments.service.interfaces;

import com.cpt.payments.dto.InitiateTxnRequestDTO;
import com.cpt.payments.dto.TransactionResponseDTO;

public interface PaymentService {

	TransactionResponseDTO initiatePayment(InitiateTxnRequestDTO initiateRequestDTO, String txnReference);

}