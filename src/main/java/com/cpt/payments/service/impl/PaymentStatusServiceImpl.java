package com.cpt.payments.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dto.PaymentResponseDTO;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.service.factory.TransactionStatusFactory;
import com.cpt.payments.service.interfaces.PaymentStatusService;
import com.cpt.payments.service.interfaces.TransactionStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentStatusServiceImpl implements PaymentStatusService {

	private TransactionStatusFactory statusFactory;
	
	public PaymentStatusServiceImpl(TransactionStatusFactory statusFactory) {
		this.statusFactory = statusFactory;
	}
	
	@Override
	public PaymentResponseDTO insertPayment(TransactionDTO transactionDTO) {
		log.info("Received TransactionDTO at service {}", transactionDTO);
		
		transactionDTO.setTxnReference(UUID.randomUUID().toString());
		
		TransactionStatusHandler statusHandler = statusFactory.getStatusHandler(
													TransactionStatusEnum.getEnumByName(
															transactionDTO.getTxnStatus()
															).getId());
		
		PaymentResponseDTO paymentResponse = new PaymentResponseDTO();
		paymentResponse.setTxnReference(transactionDTO.getTxnReference());
		
		boolean result = statusHandler.processStatus(transactionDTO);
		if(result == true)
			paymentResponse.setTxnStatus("CREATED");
		else
			paymentResponse.setTxnStatus("FAILED");
			
		return paymentResponse;
	}

}
