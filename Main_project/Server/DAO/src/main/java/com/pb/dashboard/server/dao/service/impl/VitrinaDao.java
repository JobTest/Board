package com.pb.dashboard.server.dao.service.impl;

import com.pb.dashboard.server.dao.entity.vitrina.BpInterface;
import com.pb.dashboard.server.dao.entity.vitrina.BpInterfaceI;
import com.pb.dashboard.server.dao.service.VitrinaDaoI;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

/**
 * Created by vlad
 * Date: 05.03.15_10:36
 */
@Service
public class VitrinaDao extends DaoAbs implements VitrinaDaoI {

    private static final Logger log = Logger.getLogger(VitrinaDao.class);

    @PersistenceUnit(unitName = "vitrinaUnit")
    private EntityManagerFactory emf;

    @Override
    public List<BpInterfaceI> getInterfaces(int complexId, int countryId) {
        String query = "exec dbo.GetInterfacesForComplex ?, ?";
        return execNativeQueryList(query,
                BpInterface.class, BpInterfaceI.class, log,
                complexId, countryId);
    }

    @Override
    public EntityManagerFactory getEmf() {
        return emf;
    }
}
