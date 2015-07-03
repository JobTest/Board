package com.pb.dashboard.monitoring.errors.all.strategy;

import com.pb.dashboard.core.error.DashSQLException;
import com.pb.dashboard.core.util.IntegerUtil;
import com.pb.dashboard.dao.entity.biplanesupport.db.ErrorData;
import com.pb.dashboard.dao.service.ServiceFactory;
import com.pb.dashboard.monitoring.errors.all.db.DescriptionDBManager;
import com.pb.dashboard.monitoring.errors.all.db.container.DescriptionRecipient;
import com.pb.dashboard.monitoring.errors.all.db.pl.PLDBManager;
import com.pb.dashboard.monitoring.errors.all.filter.FilterModelI;
import com.pb.dashboard.monitoring.errors.all.navigator.NavigatorModelAdapter;
import com.pb.dashboard.monitoring.errors.all.table.model.ErrorModel;
import com.pb.dashboard.monitoring.errors.all.table.model.RecipientModel;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by vlad
 * Date: 06.01.15_12:57
 */
public class LoadData {

    public static final String NULL_VALUE = "[null]";
    public static final int CODE_VALUE_DEFAULT = -1;

    private static final Logger log = Logger.getLogger(LoadData.class);
    private FilterModelI filter;
    private NavigatorModelAdapter navigator;

    public LoadData(FilterModelI filterModel, NavigatorModelAdapter navigatorModel) {
        this.filter = filterModel;
        this.navigator = navigatorModel;
    }

    public List<RecipientModel> getRecipients(String codeId) {
        Map<String, Integer> recipientsData = loadRecipientData(codeId);
        List<RecipientModel> recipients = new ArrayList<RecipientModel>();
        for (Map.Entry<String, Integer> recipientData : recipientsData.entrySet()) {
            DescriptionRecipient descriptionRecipient = getDescriptionRecipient(recipientData.getKey());
            RecipientModel recipient = createRecipientModel(recipientData, descriptionRecipient);
            recipients.add(recipient);
        }
        return recipients;
    }

    private Map<String, Integer> loadRecipientData(String codeId) {
        try {
            return ServiceFactory.getMonitoring().getCompanyByErrTotal(
                    navigator.getErrorType(),
                    navigator.getComplex().getId(),
                    navigator.getBpInterface().getPkey(),
                    filter.getDate(),
                    filter.getDate().get(Calendar.DAY_OF_MONTH),
                    filter.getTopItem().getCount(),
                    codeId,
                    navigator.getConsumer().getId()
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyMap();
    }

    private DescriptionRecipient getDescriptionRecipient(String id) {
        try {
            return DescriptionDBManager.getInstance().getDescriptionRecipient(id);
        } catch (DashSQLException e) {
            log.error(e.getMessage(), e);
        }
        return new DescriptionRecipient();
    }

    private RecipientModel createRecipientModel(Map.Entry<String, Integer> recipientData, DescriptionRecipient recipientDes) {
        RecipientModel model = new RecipientModel();
        String key = recipientData.getKey() == null ? NULL_VALUE : recipientData.getKey();
        model.setId(key);
        model.setCount(recipientData.getValue());
        model.setFilial(recipientDes.getFilial());
        model.setName(recipientDes.getCompany());
        return model;
    }


    public List<String> getSessions(String itemId, PageType type, String codeId) {
        try {
            return ServiceFactory.getMonitoring().getSessionByCompanyErrTotal(
                    navigator.getErrorType(),
                    navigator.getComplex().getId(),
                    navigator.getBpInterface().getPkey(),
                    filter.getDate(),
                    filter.getDate().get(Calendar.DAY_OF_MONTH),
                    type == PageType.ERROR ? itemId : codeId,
                    type == PageType.RECIPIENT ? itemId : codeId,
                    navigator.getConsumer().getId()
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }


    public List<ErrorModel> getErrors(PageNumber page, String codeId) {
        List<ErrorData> errTotal = loadErrorData(page, codeId);
        List<ErrorModel> errors = new ArrayList<ErrorModel>();
        for (ErrorData errorData : errTotal) {
            String description = getDescription(errorData.getCode());
            ErrorModel error = new ErrorModel(
                    errorData.getCode(),
                    errorData.getCount(),
                    description
            );
            errors.add(error);
        }
        return errors;
    }

    private List<ErrorData> loadErrorData(PageNumber page, String codeId) {
        try {
            return ServiceFactory.getMonitoring().getErrTotal(
                    navigator.getComplex().getId(),
                    navigator.getBpInterface().getPkey(),
                    filter.getDate(),
                    filter.getDate().get(Calendar.DAY_OF_MONTH),
                    filter.getTopItem().getCount(),
                    navigator.getErrorType(),
                    navigator.getGroup().getId(),
                    navigator.getResponsible().getId(),
                    navigator.getConsumer().getId(),
                    getCodeId(page, codeId)
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    private Integer getCodeId(PageNumber page, String codeId) {
        if (page == PageNumber.SECOND) {
            if (IntegerUtil.checkInt(codeId)) {
                return Integer.valueOf(codeId);
            }
            return null;
        }
        return CODE_VALUE_DEFAULT;
    }

    private String getDescription(String errorCode) {
        return ServiceFactory.getMonitoring().getErrorDescription(errorCode).getTextRu();
    }
}