package com.cpt.payments.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dao.interfaces.TransactionDao;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.entity.TransactionEntity;
import com.cpt.payments.exception.PaymentException;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class TransactionDaoImpl implements TransactionDao {

	private NamedParameterJdbcTemplate jdbcTemplate;
	private ModelMapper modelMapper;

	public TransactionDaoImpl(NamedParameterJdbcTemplate jdbcTemplate, ModelMapper modelMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.modelMapper = modelMapper;
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

	@Override
	public TransactionDTO findByTxnReference(String txnReference) {
		log.info("Received txnReference  = {} ", txnReference);

		final String sql = "Select * From `Transaction` WHERE txnReference = :txnReference";

		Map<String, Object> params = new HashMap<>();
		params.put("txnReference", txnReference);

		// TODO: handle case when txnEntity is null.
		TransactionEntity entity = jdbcTemplate.queryForObject(
								sql,
								params,
								new BeanPropertyRowMapper<>(TransactionEntity.class));
		
		TransactionDTO txnDTO = modelMapper.map(entity, TransactionDTO.class);
		
		log.info("Transaction fetched from DB txnReference:{}|txnDTO:{}", txnReference, txnDTO);
		return txnDTO;

	}

	@Override
	public boolean updateTransaction(TransactionDTO transactionDTO) {
		log.info("Updating transaction:{}", transactionDTO);
		
		String sql = "UPDATE `Transaction` "
				   + "SET `txnStatusId` = :txnStatusId,"
				   + "`providerReference` = :providerReference "
				   + "WHERE `txnReference` = :txnReference";
		
		int txnStatusId = TransactionStatusEnum.getEnumByName(transactionDTO.getTxnStatus()).getId();
		String txnReference = transactionDTO.getTxnReference();
		String providerReference = transactionDTO.getProviderReference();
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("txnStatusId", txnStatusId);
		parameters.addValue("txnReference", txnReference);
		parameters.addValue("providerReference", providerReference);
		
		int rowsUpdated = jdbcTemplate.update(sql, parameters);
		
		if(rowsUpdated != 1) {
			log.info("Could not initiate transaction in DB!");
//			TODO: throw exception.
		}
		
		log.info("Update Transaction in DB as initiated| rowsUpdated| {}", rowsUpdated);
		return true;
	}
}
