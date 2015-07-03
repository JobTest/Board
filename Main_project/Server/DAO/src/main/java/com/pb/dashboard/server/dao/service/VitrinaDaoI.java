package com.pb.dashboard.server.dao.service;

import com.pb.dashboard.server.dao.entity.vitrina.BpInterfaceI;

import java.util.List;

/**
 * Created by vlad
 * Date: 05.03.15_10:36
 */
public interface VitrinaDaoI {

    public List<BpInterfaceI> getInterfaces(int complexId, int countryId);
}
