package com.pb.dashboard.server.service.businesslayer;

import com.pb.dashboard.server.dao.entity.dashboard.MassPaysAuthEntity;
import com.pb.dashboard.server.dao.service.DaoFactoryI;
import com.pb.dashboard.server.service.api.AuthPay;
import com.pb.dashboard.server.service.api.SmsPay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad
 * Date: 18.03.15_14:53
 */

public class MassPaysService implements MassPaysServiceI {

    private DaoFactoryI serviceFactory;

    public void setServiceFactory(DaoFactoryI serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public List<AuthPay> getAuthPays() {
        List<AuthPay> res = new ArrayList<>();
        List<MassPaysAuthEntity> massPaysAuth = serviceFactory.getDashboard().getMassPaysAuth();
        for (MassPaysAuthEntity item : massPaysAuth) {
            res.add(new AuthPay(item));
        }
        return res;
    }

    @Override
    public List<SmsPay> getSmsPays() {
        List<SmsPay> res = new ArrayList<>();
        List<MassPaysAuthEntity> massPaysAuth = serviceFactory.getDashboard().getMassPaysAuth();
        for (MassPaysAuthEntity item : massPaysAuth) {
            res.add(new SmsPay(item));
        }
        return res;
    }
}
