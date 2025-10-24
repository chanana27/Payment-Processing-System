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
public class CreatedStatusHandler implements TransactionStatusHandler {
	
	private TransactionDao transactionDao;
	private ModelMapper modelMapper;
    private TransactionLogDao transactionLogDao;
	
	public CreatedStatusHandler(TransactionDao transactionDao,
								ModelMapper modelMapper,
                                TransactionLogDao transactionLogDao) {
		this.transactionDao = transactionDao;
		this.modelMapper = modelMapper;
        this.transactionLogDao = transactionLogDao;
	}

	@Override
	public boolean processStatus(TransactionDTO transactionDTO) {
		log.info("Processing status for Create|transaction:{}", transactionDTO);
//        TransactionDTO txnBeforeUpdate = transactionDao.findByTxnReference(transactionDTO.getTxnReference());

		TransactionEntity transactionEntity = modelMapper.map(transactionDTO, TransactionEntity.class);
		log.info("Converted TransactionDTO to TransactionEntity as {}", transactionEntity);

		Long generatedId = transactionDao.createPayment(transactionEntity);

        if(generatedId == null){
            log.error("Could not create transaction into DB transaction:{}", transactionDTO);
            return false;
        }

//        transactionDTO = transactionDao.findByTxnReference(transactionDTO.getTxnReference());
//        log.info("TransactionDTO after saving into DB| txnDTO:{}", transactionDTO);

        TransactionLog transactionLog = TransactionLog.builder()
                .transactionId(generatedId.intValue())
                .txnFromStatus("-")
                .txnToStatus(transactionDTO.getTxnStatus())
                .build();

        transactionLogDao.createTransactionLog(transactionLog);
        return true;
	}

}
