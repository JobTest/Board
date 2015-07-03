package com.pb.dashboard.external.debtload.repository;

import com.pb.dashboard.core.db.DAOException;
import com.pb.dashboard.core.model.Bank;
import com.pb.dashboard.external.debtload.model.DebtLinesModel;
import com.pb.dashboard.external.debtload.model.DebtLoadModel;
import com.pb.dashboard.external.debtload.model.DebtQueueModel;

import java.util.Collection;

public interface LoadItemsDao {

    Collection<DebtQueueModel> getQueueModelsByBank(Bank bank) throws DAOException;
    Collection<DebtLoadModel> getLoadModelsByBankAndDate(Bank bank, String date, boolean isManualLoad) throws DAOException;
    Collection<DebtLinesModel> getLinesModelByFromDateAndToDate(int bank, String dateFrom, String dateTo);
    String getModifyDate() throws DAOException;

}
