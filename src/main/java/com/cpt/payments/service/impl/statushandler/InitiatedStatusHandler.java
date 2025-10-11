package com.cpt.payments.service.impl.statushandler;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dao.interfaces.TransactionDao;
import com.cpt.payments.dao.interfaces.TransactionLog;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.entity.TransactionEntity;
import com.cpt.payments.exception.PaymentException;
import com.cpt.payments.service.interfaces.TransactionStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InitiatedStatusHandler implements TransactionStatusHandler {
	
	private TransactionDao transactionDao;
	private ModelMapper modelMapper;
	private TransactionLog transactionLog;
	
	public InitiatedStatusHandler(TransactionDao transactionDao,
								ModelMapper modelMapper, TransactionLog transactionLog) {
		this.transactionDao = transactionDao;
		this.modelMapper = modelMapper;
		this.transactionLog = transactionLog;
	}

	@Override
	public boolean processStatus(TransactionDTO transactionDTO) {
		log.info("Processing status for Initiated|| transaction:{}", transactionDTO);
		
		boolean isTxnSaved = transactionDao.updateTransaction(transactionDTO);
		
		//TODO: Logic for update in transaction log
		
		transactionLog.logEntry(transactionDTO.getId(), TransactionStatusEnum.CREATED.name(),
				TransactionStatusEnum.getEnumByName(transactionDTO.getTxnStatus()).name());
		
		return isTxnSaved;
	}

}
