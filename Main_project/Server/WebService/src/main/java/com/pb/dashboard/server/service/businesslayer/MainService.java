package com.pb.dashboard.server.service.businesslayer;

import com.pb.dashboard.server.dao.entity.vitrina.BpInterfaceI;
import com.pb.dashboard.server.dao.service.DaoFactoryI;

import java.util.List;

/**
 * Created by vlad
 * Date: 12.05.15_15:11
 */
public class MainService implements MainServiceI {

    private DaoFactoryI dao;

    @Override
    public List<BpInterfaceI> getBpInterfaces(int complexPkey, int countryPkey) {
        return dao.getVitrina().getInterfaces(complexPkey, countryPkey);
    }

    public void setDao(DaoFactoryI dao) {
        this.dao = dao;
    }
}
