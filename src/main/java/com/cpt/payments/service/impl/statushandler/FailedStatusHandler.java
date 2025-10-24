package com.cpt.payments.service.impl.statushandler;

import com.cpt.payments.dao.interfaces.TransactionLogDao;
import com.cpt.payments.dto.TransactionLog;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.dao.interfaces.TransactionDao;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.entity.TransactionEntity;
import com.cpt.payments.exception.PaymentException;
import com.cpt.payments.service.interfaces.TransactionStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FailedStatusHandler implements TransactionStatusHandler {

    private TransactionLogDao transactionLogDao;
    private TransactionDao transactionDao;

    public FailedStatusHandler(TransactionLogDao transactionLogDao, TransactionDao transactionDao) {
        this.transactionLogDao = transactionLogDao;
        this.transactionDao = transactionDao;
    }

    @Override
	public boolean processStatus(TransactionDTO transactionDTO) {
        log.info("Processing status for Failed || transaction:{}", transactionDTO);

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
