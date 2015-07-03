package com.pb.dashboard.server.service.businesslayer;

import com.pb.dashboard.server.dao.entity.vitrina.BpInterfaceI;

import java.util.List;

/**
 * Created by vlad
 * Date: 12.05.15_15:10
 */
public interface MainServiceI {

    List<BpInterfaceI> getBpInterfaces(int complexPkey, int countryPkey);

}
