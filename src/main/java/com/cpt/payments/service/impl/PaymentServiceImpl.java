package com.cpt.payments.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cpt.payments.constants.ProviderEnum;
import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dao.interfaces.TransactionDao;
import com.cpt.payments.dao.interfaces.TransactionLog;
import com.cpt.payments.dto.InitiateRequestDTO;
import com.cpt.payments.dto.PaymentResponseDTO;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.entity.TransactionEntity;
import com.cpt.payments.service.factory.TransactionStatusFactory;
import com.cpt.payments.service.interfaces.PaymentService;
import com.cpt.payments.service.interfaces.TransactionStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
	
	private TransactionStatusFactory statusFactory;
	private TransactionDao transactionDao;
	
	public PaymentServiceImpl(TransactionStatusFactory statusFactory, TransactionDao transactionDao) {
		super();
		this.statusFactory = statusFactory;
		this.transactionDao = transactionDao;
	}

	@Override
	public PaymentResponseDTO initiatePayment(InitiateRequestDTO initiateRequestDTO, String txnReference) {
		log.info("Received TransactionDTO to initiate payment {}", initiateRequestDTO);
		
		TransactionDTO txnDTO= transactionDao.findByTxnReference(txnReference);
		
		txnDTO.setTxnStatus(TransactionStatusEnum.INITIATED.name());
		
		TransactionStatusHandler statusHandler = statusFactory.getStatusHandler(
				TransactionStatusEnum.getEnumByName(txnDTO.getTxnStatus()));
		
		boolean isUpdate = statusHandler.processStatus(txnDTO);
		
		if(!isUpdate) {
			log.info("Txn not updated into DB|| transaction:{}", txnDTO);
			//TODO: THROW EXCEPTION
		}
		
		
		
		log.info("Calling third party");
		// Identify third party provider.
		String provider = txnDTO.getProvider();
		if(provider.equals(ProviderEnum.TRUSTLY.name())) {
			// call trustly api
			// success response got provider reference and redirectURL from trustly
			String providerReference = "Dummy reference";
			
			txnDTO.setTxnStatus(TransactionStatusEnum.PENDING.name());
			txnDTO.setProviderReference(providerReference);
			
			statusHandler = statusFactory.getStatusHandler(
					TransactionStatusEnum.getEnumByName(txnDTO.getTxnStatus()));
			
			isUpdate = statusHandler.processStatus(txnDTO);
			
			if(!isUpdate) {
				log.info("Cannot update transaction as pending!");
			}
			
			
			
			// failed response from trustly
			
			
		}
		
		
		PaymentResponseDTO response = new PaymentResponseDTO();
		response.setTxnReference(txnDTO.getTxnReference());		
		response.setTxnStatus(txnDTO.getTxnStatus());
		
		return response;
	}
}