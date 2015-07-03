package com.pb.dashboard.dao.daomanager;

import com.pb.dashboard.dao.entity.biplanepl.ErrorDescriptionI;
import com.pb.dashboard.dao.entity.biplanepl.ErrorDetailI;

/**
 * Created by vlad
 * Date: 01.04.15_12:34
 */
public interface BiplanePl2DAOI {

    public ErrorDetailI getErrorDetail(String systemCode, String errorCode);

    public ErrorDescriptionI getErrorDescription(String systemCode, String errorCode);

}
