package com.cpt.payments.service.interfaces;

import com.cpt.payments.dto.InitiateRequestDTO;
import com.cpt.payments.dto.PaymentResponseDTO;

public interface PaymentService {

	PaymentResponseDTO initiatePayment(InitiateRequestDTO initiateRequestDTO, String txnReference);

}