package com.cpt.payments.service.impl.statushandler;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.exception.PaymentException;
import com.cpt.payments.service.interfaces.TransactionStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CreatedStatusHandler implements TransactionStatusHandler {
	
private final NamedParameterJdbcTemplate jdbcTemplate;
	
	public CreatedStatusHandler(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean processStatus(TransactionDTO transactionDTO) {
		log.info("Received TransactionDTO in CreatedStatusHandler as {}", transactionDTO);
		
		 String sql = "INSERT INTO payments.`Transaction` (" +
	                "userId, paymentMethodId, providerId, paymentTypeId, " +
	                "amount, currency, txnStatusId, " +
	                "merchantTransactionReference, txnReference, " +
	                "providerCode, providerMessage, providerReference, " +
	                "retryCount) " +
	                "VALUES (:userId, :paymentMethodId, :providerId, :paymentTypeId, " +
	                ":amount, :currency, :txnStatusId, " +
	                ":merchantTransactionReference, :txnReference, " +
	                ":providerCode, :providerMessage, :providerReference, " +
	                ":retryCount)";
		
		 int rowsUpdated;
		try {
			rowsUpdated = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(transactionDTO));
			log.info("Insert into DB | rowsUpdated {}", rowsUpdated);
			
		}catch(DuplicateKeyException e) {
			
			log.info("Duplicate Key Exception occurred as {}", e.getMessage());
			throw new PaymentException(
					ErrorCodeEnum.Duplicate_Txn_Reference.getErrorCode(),
					ErrorCodeEnum.Duplicate_Txn_Reference.getErrorMessage(),
					HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			log.info("Exception occurred as {}", e.getMessage());
			throw new PaymentException(
					ErrorCodeEnum.Payment_Not_Saved.getErrorCode(),
					ErrorCodeEnum.Payment_Not_Saved.getErrorMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return rowsUpdated == 1;
		
	}

}
