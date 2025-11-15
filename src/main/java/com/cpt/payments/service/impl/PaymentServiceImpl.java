package com.cpt.payments.service.impl;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.exception.PaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cpt.payments.constants.ProviderEnum;
import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dao.interfaces.TransactionDao;
import com.cpt.payments.dto.InitiateTxnRequestDTO;
import com.cpt.payments.dto.TransactionResponseDTO;
import com.cpt.payments.dto.TransactionDTO;
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
	public TransactionResponseDTO initiatePayment(InitiateTxnRequestDTO initiateRequestDTO, String txnReference) {
		log.info("Initiating payment for txnReference:{}", txnReference);
		
		TransactionDTO txnDTO= transactionDao.findByTxnReference(txnReference);
		
		txnDTO.setTxnStatus(TransactionStatusEnum.INITIATED.name());
		
		TransactionStatusHandler statusHandler = statusFactory.getStatusHandler(
				TransactionStatusEnum.getEnumByName(txnDTO.getTxnStatus()));
		
		boolean isUpdate = statusHandler.processStatus(txnDTO);
		
		if(!isUpdate) {
			log.info("Txn not updated into DB|| transaction:{}", txnDTO);
			throw new PaymentException(
                    ErrorCodeEnum.Transaction_Already_Initiated.getErrorCode(),
                    ErrorCodeEnum.Transaction_Already_Initiated.getErrorMessage(),
                    HttpStatus.BAD_REQUEST);
		}

        // transaction.getProvider()

		TransactionResponseDTO response = new TransactionResponseDTO();
		response.setTxnReference(txnDTO.getTxnReference());		
		response.setTxnStatus(txnDTO.getTxnStatus());
        response.setRedirectUrl("https://dummy.test.com/redirect");
		
		return response;
	}
}