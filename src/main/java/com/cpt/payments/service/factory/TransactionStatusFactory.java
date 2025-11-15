package com.cpt.payments.service.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.service.impl.statushandler.CreatedStatusHandler;
import com.cpt.payments.service.impl.statushandler.FailedStatusHandler;
import com.cpt.payments.service.impl.statushandler.InitiatedStatusHandler;
import com.cpt.payments.service.impl.statushandler.PendingStatusHandler;
import com.cpt.payments.service.impl.statushandler.SuccessStatusHandler;
import com.cpt.payments.service.interfaces.TransactionStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TransactionStatusFactory {
	
	private ApplicationContext applicationContext;
	
	public TransactionStatusFactory(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public TransactionStatusHandler getStatusHandler(TransactionStatusEnum status) {
		
		switch(status) {
		
			case CREATED:
				return applicationContext.getBean(CreatedStatusHandler.class);
			
			case INITIATED:
				return applicationContext.getBean(InitiatedStatusHandler.class);
				
			case PENDING:
				return applicationContext.getBean(PendingStatusHandler.class);
				
			case SUCCESS:
				return applicationContext.getBean(SuccessStatusHandler.class);
				
			case FAILED:
				return applicationContext.getBean(FailedStatusHandler.class);
				
			default:
				log.info("No handler found for status {}", status);
				return null;
		}
	}
}
