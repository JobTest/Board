package com.pb.dashboard.server.service.businesslayer;

import com.pb.dashboard.server.dao.entity.vitrina.BpInterfaceI;
import com.pb.dashboard.server.service.api.CompanyDescription;
import com.pb.dashboard.server.service.api.CompanyItem;
import com.pb.dashboard.server.service.api.RecipientSla;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by vlad
 * Date: 05.03.15_14:01
 */
public interface MonitoringServiceI {

    public List<RecipientSla> getRecipientSla(String interfaceName, LocalDate date, int top);

    public List<CompanyItem> getCompanyItems(String interfaceName, LocalDate date, int companyId);

    public CompanyDescription getCompanyDescription(Integer companyId);
}
