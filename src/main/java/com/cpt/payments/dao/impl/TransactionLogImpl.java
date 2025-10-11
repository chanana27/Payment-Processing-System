package com.cpt.payments.dao.impl;

import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cpt.payments.dao.interfaces.TransactionLog;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class TransactionLogImpl implements TransactionLog {

	private NamedParameterJdbcTemplate jdbcTemplate;
	private ModelMapper modelMapper;
	
	
	public TransactionLogImpl(NamedParameterJdbcTemplate jdbcTemplate, ModelMapper modelMapper) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.modelMapper = modelMapper;
	}

	

	@Override
	public boolean logEntry(Integer transactionId, String txnFromStatus, String txnToStatus) {
		
		String sql = "INSERT INTO `transaction_log` "
				+ "(transactionId, txnFromStatus, txnToStatus) "
				+ "VALUES (:transactionId, :txnFromStatus, :txnToStatus)";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("transactionId", transactionId);
		params.addValue("txnFromStatus", txnFromStatus);
		params.addValue("txnToStatus", txnToStatus);
		
		int rowsUpdated = jdbcTemplate.update(sql, params);
		
		log.info("Inserted entry in log table|fromStatus:{} |toStatus:{} |transactionId:{}",
				txnFromStatus, txnToStatus, transactionId);
		
		return rowsUpdated == 1;
	}

}
