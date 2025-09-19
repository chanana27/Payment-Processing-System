package com.cpt.payments.service.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cpt.payments.service.impl.statushandler.CreatedStatusHandler;
import com.cpt.payments.service.interfaces.TransactionStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TransactionStatusFactory {
	
	private ApplicationContext applicationContext;
	
	public TransactionStatusFactory(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public TransactionStatusHandler getStatusHandler(int id) {
		if(id == 1) {
			return applicationContext.getBean(
					CreatedStatusHandler.class);
		}
		log.info("No handler found for this status id {}", id);
		return null;
		
			
	}
}
