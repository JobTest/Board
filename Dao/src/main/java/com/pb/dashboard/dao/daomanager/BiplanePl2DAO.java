package com.pb.dashboard.dao.daomanager;


import com.pb.dashboard.dao.entity.biplanepl.ErrorDescription;
import com.pb.dashboard.dao.entity.biplanepl.ErrorDescriptionI;
import com.pb.dashboard.dao.entity.biplanepl.ErrorDetail;
import com.pb.dashboard.dao.entity.biplanepl.ErrorDetailI;

/**
 * Created by vlad
 * Date: 01.04.15_12:38
 */
public class BiplanePl2DAO extends DashEntityManager implements BiplanePl2DAOI {

    public static final String UNIT_NAME = "biplanePlUnit";

    protected BiplanePl2DAO() {
        super(UNIT_NAME);
    }

    @Override
    public ErrorDetailI getErrorDetail(String systemCode, String errorCode) {
        return execNativeQueryOne("exec dbo.GetErrDetail ?,?", ErrorDetail.class, systemCode, errorCode);
    }

    @Override
    public ErrorDescriptionI getErrorDescription(String systemCode, String errorCode) {
        return execNativeQueryOne("exec dbo.GetErrText ?,?", ErrorDescription.class, systemCode, errorCode);
    }
}
