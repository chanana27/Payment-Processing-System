package com.cpt.payments.service.interfaces;

import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dto.TransactionDTO;

public interface TransactionStatusHandler {
	
	public boolean processStatus(TransactionDTO transactionDTO);

    public default boolean canUpdate(String fromStatus, String toStatus){
        if(fromStatus == null)
            return true;

        if(fromStatus.equals(toStatus))
            return false;

        // cannot update status to SUCCESS OR FAILED
        return !toStatus.equals(TransactionStatusEnum.SUCCESS.name()) &&
                !toStatus.equals(TransactionStatusEnum.FAILED.name());
    }
}
