package com.cpt.payments.service.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dao.interfaces.TransactionDao;
import com.cpt.payments.dto.PaymentResponseDTO;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.service.factory.TransactionStatusFactory;
import com.cpt.payments.service.interfaces.PaymentStatusService;
import com.cpt.payments.service.interfaces.TransactionStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentStatusServiceImpl implements PaymentStatusService {

	private TransactionStatusFactory statusFactory;
	private TransactionDao transactionDao;
	private ModelMapper modelMapper;

	public PaymentStatusServiceImpl(TransactionStatusFactory statusFactory,
									TransactionDao transactionDao,
									ModelMapper modelMapper) {
		this.statusFactory = statusFactory;
		this.transactionDao = transactionDao;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public PaymentResponseDTO insertPayment(TransactionDTO transactionDTO) {
		log.info("Received TransactionDTO at service {}", transactionDTO);
		
		transactionDTO.setTxnReference(UUID.randomUUID().toString());
		
		TransactionStatusHandler statusHandler = statusFactory.getStatusHandler(
													TransactionStatusEnum.getEnumByName(
															transactionDTO.getTxnStatus()));
		
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
