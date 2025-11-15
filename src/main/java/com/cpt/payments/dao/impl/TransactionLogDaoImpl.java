package com.cpt.payments.dao.impl;

import com.cpt.payments.dto.TransactionLog;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cpt.payments.dao.interfaces.TransactionLogDao;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class TransactionLogDaoImpl implements TransactionLogDao {

	private NamedParameterJdbcTemplate jdbcTemplate;

	public TransactionLogDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

    @Override
    public void createTransactionLog(TransactionLog transactionLog) {
            try{
                jdbcTemplate.update(createTransactionLog(), new BeanPropertySqlParameterSource(transactionLog));
            } catch (Exception e) {
                log.error("Exception occurred while inserting log ", e);
            }
    }

    private String createTransactionLog(){
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO transaction_log ");
        queryBuilder.append("(transactionId, txnFromStatus, txnToStatus) ");
        queryBuilder.append("VALUES(:transactionId, :txnFromStatus, :txnToStatus)");
        log.info("Insert transaction log query: {}", queryBuilder);
        return queryBuilder.toString();
    }



}
