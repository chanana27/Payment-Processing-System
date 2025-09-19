package com.cpt.payments.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cpt.payments.dao.interfaces.TransactionDao;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.entity.TransactionEntity;
import com.cpt.payments.service.factory.TransactionStatusFactory;
import com.cpt.payments.service.interfaces.PaymentStatusService;
import com.cpt.payments.service.interfaces.TransactionStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentStatusServiceImpl implements PaymentStatusService {

	private ModelMapper modelMapper;
	private TransactionStatusFactory statusFactory;
	
	public PaymentStatusServiceImpl(ModelMapper modelMapper, TransactionStatusFactory statusFactory) {
		this.modelMapper = modelMapper;
		this.statusFactory = statusFactory;
	}
	
	@Override
	public String insertPayment(TransactionDTO transactionDTO) {
		log.info("Received TransactionDTO at service {}", transactionDTO);
//		String a=null;
//		a.length();
		
		TransactionEntity entity = modelMapper.map(transactionDTO, TransactionEntity.class);
		log.info("Converted TransactionDTO to TransactionEntity as {}", entity);
		
		TransactionStatusHandler statusHandler = statusFactory.getStatusHandler(transactionDTO.getTxnStatusId());
		boolean result = statusHandler.processStatus(transactionDTO);
		
		return "Payment created into DB "+ result;
	}

}
