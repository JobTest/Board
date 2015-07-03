package com.pb.dashboard.itdashbord.errors.db;

import com.pb.dashboard.itdashbord.errors.db.container.ErrByCompany;
import com.pb.dashboard.itdashbord.errors.db.container.ErrorsData;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ErrorsDBManager {

    private static final Logger LOG = Logger.getLogger(ErrorsDBManager.class);
    private static ErrorsDBManager instance;

    public static ErrorsDBManager getInstance() {
        if (instance == null) {
            instance = new ErrorsDBManager();
        }
        return instance;
    }

    public List<ErrorsData> getErrorsDataList() {
        List<ErrorsData> datas = new ArrayList<ErrorsData>();

        for (int i = 0; i < 1378; i++) {
            ErrorsData data = new ErrorsData();
            data.setId("40127"+i);
            data.setName("Бездоговорные платежи физических лиц");
            data.setBranch("Днепропетровское РУ");
            data.setOkpo("000000");
            data.setView("Бездоговорные");
            data.setRecipientType("Бездоговорной платеж");
            data.setGroupType("Служебные");
            data.setCountError(String.valueOf(new Random().nextInt(100000)));
            data.setType("Online");
            datas.add(data);
        }
        return datas;
    }

    public List<ErrByCompany> getErrorsByCompany(String dateFrom, String dateTo, int companyId){
        List<ErrByCompany> data = new ArrayList<ErrByCompany>();
        for (int i = 0; i < 77 ; i++) {
            ErrByCompany err = new ErrByCompany();
            err.setDecode("Ошибка соединения с сервером задолжености");
            err.setErrorCode("en_"+i);
            err.setErrorType("Системная");
            err.setNumberOfErrors(String.valueOf((i+1)*20-8));
            data.add(err);
        }
        return data;
    }
}
