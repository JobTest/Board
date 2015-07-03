package com.pb.dashboard.server.service.businesslayer;

import com.pb.dashboard.server.dao.entity.description.DescriptionCompany;
import com.pb.dashboard.server.dao.entity.iqlogrep.CountForCompany;
import com.pb.dashboard.server.dao.entity.iqlogrep.RecipientEntity;
import com.pb.dashboard.server.dao.entity.vitrina.BpInterfaceI;
import com.pb.dashboard.server.dao.service.DaoFactoryI;
import com.pb.dashboard.server.service.api.CompanyDescription;
import com.pb.dashboard.server.service.api.CompanyItem;
import com.pb.dashboard.server.service.api.RecipientSla;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad
 * Date: 05.03.15_14:02
 */
public class MonitoringService implements MonitoringServiceI {

    private DaoFactoryI serviceFactory;

    public void setServiceFactory(DaoFactoryI serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public List<RecipientSla> getRecipientSla(String interfaceName, LocalDate date, int top) {
        List<RecipientSla> list = new ArrayList<>();
        List<RecipientEntity> entity = serviceFactory.getIq().getDebtSlaByCompany(interfaceName, date, top);
        for (RecipientEntity item : entity) {
            DescriptionCompany description = serviceFactory.getDescription().getDescriptionCompany(item.getId());
            list.add(new RecipientSla(item, description));
        }
        return list;
    }

    @Override
    public List<CompanyItem> getCompanyItems(String interfaceName, LocalDate date, int companyId) {
        List<CompanyItem> list = new ArrayList<>();
        List<CountForCompany> entities = serviceFactory.getIq().getDebtErrByCompanyHour(interfaceName, date, companyId);
        for (CountForCompany entity : entities) {
            list.add(new CompanyItem(entity));
        }
        return list;
    }

    @Override
    public CompanyDescription getCompanyDescription(Integer companyId) {
        DescriptionCompany entity = serviceFactory.getDescription().getDescriptionCompany(companyId);
        if (entity == null) {
            return null;
        }
        return new CompanyDescription(entity);
    }
}
