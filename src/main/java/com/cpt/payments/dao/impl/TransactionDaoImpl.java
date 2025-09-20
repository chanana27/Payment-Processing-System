package com.cpt.payments.dao.impl;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.dao.interfaces.TransactionDao;
import com.cpt.payments.entity.TransactionEntity;
import com.cpt.payments.exception.PaymentException;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class TransactionDaoImpl implements TransactionDao {

	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public TransactionDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public boolean createPayment(TransactionEntity transactionEntity) {
		
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
			rowsUpdated = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(transactionEntity));
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
