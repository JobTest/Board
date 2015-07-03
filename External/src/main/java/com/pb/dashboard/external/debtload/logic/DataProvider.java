package com.pb.dashboard.external.debtload.logic;

import com.pb.dashboard.core.db.DAOException;
import com.pb.dashboard.core.model.Bank;
import com.pb.dashboard.core.model.Language;
import com.pb.dashboard.core.util.LogUtil;
import com.pb.dashboard.external.debtload.model.DebtLoadModel;
import com.pb.dashboard.external.debtload.model.DebtQueueModel;
import com.pb.dashboard.external.debtload.repository.LoadItemsDao;
import com.pb.dashboard.external.debtload.repository.jdbc.DebtloadDbManager;
import com.pb.dashboard.external.debtload.view.table.LoadViewDataHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

public class DataProvider {

    private static DataProvider instance = new DataProvider();

    private Logger logger = LoggerFactory.getLogger(getClass());

    private LoadItemsDao dao = new DebtloadDbManager();

    private DataProvider() {}

    public static DataProvider getInstance() {
        return instance;
    }

    public LoadViewDataHolder getLoadTableDataHolder(Language lang, String date, boolean isManualLoad) {
        Bank bank = (lang == Language.RU) ? Bank.RUSSIA : Bank.UKRAINE;
        LoadViewDataHolder dataHolder = new LoadViewDataHolder();
        dataHolder.load(getLoadItemsByBankAndDate(bank, date, isManualLoad), date, getMDate());

        return dataHolder;
    }

    public Collection<DebtQueueModel> getQueueItemsByBank(Bank bank) {
        try {
            return dao.getQueueModelsByBank(bank);
        } catch (DAOException e) {
            logger.error(LogUtil.stackTraceToString(e));
        }
        return new ArrayList<DebtQueueModel>();
    }

    private Collection<DebtLoadModel> getLoadItemsByBankAndDate(Bank bank, String date, boolean isManualLoad) {
        try {
            return dao.getLoadModelsByBankAndDate(bank, date, isManualLoad);
        } catch (DAOException e) {
            logger.error(LogUtil.stackTraceToString(e));
        }
        return new ArrayList<DebtLoadModel>();
    }

    public String getMDate() {
        try {
            return dao.getModifyDate();
        } catch (DAOException e) {
            logger.error(LogUtil.stackTraceToString(e));
        }
        return null;
    }

}
