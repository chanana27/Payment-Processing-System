package com.cpt.payments.service.impl.statushandler;

import com.cpt.payments.dto.TransactionLog;
import org.springframework.stereotype.Service;

import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dao.interfaces.TransactionDao;
import com.cpt.payments.dao.interfaces.TransactionLogDao;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.service.interfaces.TransactionStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PendingStatusHandler implements TransactionStatusHandler {
	
	private TransactionDao transactionDao;
	private TransactionLogDao transactionLogDao;
	
	public PendingStatusHandler(TransactionDao transactionDao, TransactionLogDao transactionLogDao) {
		this.transactionDao = transactionDao;
		this.transactionLogDao = transactionLogDao;
	}

	@Override
	public boolean processStatus(TransactionDTO transactionDTO) {
        log.info("Processing status for Pending|| transaction:{}", transactionDTO);

        TransactionDTO txnBeforeUpdate = transactionDao.findByTxnReference(transactionDTO.getTxnReference());

        if(!canUpdate(txnBeforeUpdate.getTxnStatus(), transactionDTO.getTxnStatus())){
            log.error("Cannot Update transaction fromStatus:{}|toStatus:{} for txnReference:{}",
                    txnBeforeUpdate.getTxnStatus(),
                    transactionDTO.getTxnStatus(),
                    transactionDTO.getTxnReference());
            return false;
        }

        boolean isTxnSaved = transactionDao.updateTransaction(transactionDTO);

        log.info("Transaction updated successfully||isTxnUpdated:{}", isTxnSaved);

        TransactionLog transactionLog = TransactionLog.builder().transactionId(transactionDTO.getId())
                .txnFromStatus(txnBeforeUpdate.getTxnStatus())
                .txnToStatus(transactionDTO.getTxnStatus())
                .build();

        transactionLogDao.createTransactionLog(transactionLog);
        return isTxnSaved;
	}

}
