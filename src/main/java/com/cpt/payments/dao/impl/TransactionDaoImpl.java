package com.cpt.payments.dao.impl;

import org.springframework.stereotype.Repository;

import com.cpt.payments.dao.interfaces.TransactionDao;
import com.cpt.payments.entity.TransactionEntity;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class TransactionDaoImpl implements TransactionDao {

	
	
	@Override
	public boolean insertPaymentIntoDB(TransactionEntity transactionEntity) {
		
		return true;
	}

}
