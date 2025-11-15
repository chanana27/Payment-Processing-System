package com.cpt.payments.service.impl;

import java.util.UUID;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.exception.PaymentException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dao.interfaces.TransactionDao;
import com.cpt.payments.dto.TransactionResponseDTO;
import com.cpt.payments.dto.TransactionDTO;
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
	public TransactionResponseDTO insertPayment(TransactionDTO transactionDTO) {
		log.info("Received TransactionDTO at service {}", transactionDTO);

        // Providing a unique transaction reference for every transaction rq coming from e-commerce.
		transactionDTO.setTxnReference(UUID.randomUUID().toString());
		
		TransactionStatusHandler statusHandler = statusFactory.getStatusHandler(
													TransactionStatusEnum.getEnumByName(
															transactionDTO.getTxnStatus()));

		boolean result = statusHandler.processStatus(transactionDTO);

        if(!result){
            log.info("Transaction not saved into DB| Transaction:{}", transactionDTO);
        }

		TransactionResponseDTO paymentResponse = new TransactionResponseDTO();
		paymentResponse.setTxnReference(transactionDTO.getTxnReference());
		paymentResponse.setTxnStatus(transactionDTO.getTxnStatus());
        paymentResponse.setRedirectUrl("https://redirect.dummy.com");
			
		return paymentResponse;
	}
}
