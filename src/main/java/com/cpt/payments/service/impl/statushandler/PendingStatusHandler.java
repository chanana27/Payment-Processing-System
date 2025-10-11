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
public class PendingStatusHandler implements TransactionStatusHandler {
	
	private TransactionDao transactionDao;
	private TransactionLog transactionLog;
	
	public PendingStatusHandler(TransactionDao transactionDao, TransactionLog transactionLog) {
		this.transactionDao = transactionDao;
		this.transactionLog = transactionLog;
	}

	@Override
	public boolean processStatus(TransactionDTO transactionDTO) {
		log.info("Processing status as Pending for txn:{}", transactionDTO);
		
		boolean isTxnUpdated = transactionDao.updateTransaction(transactionDTO);
		
		//TODO: Log entry in transactionlog table
		transactionLog.logEntry(transactionDTO.getId(), TransactionStatusEnum.INITIATED.name(),
				TransactionStatusEnum.getEnumByName(transactionDTO.getTxnStatus()).name());
		
		log.info("Updated status as PENDING for transaction:{}", transactionDTO);
		
		return isTxnUpdated;
	}

}
