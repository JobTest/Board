package com.pb.dashboard.server.dao.service;

import com.pb.dashboard.server.dao.entity.description.DescriptionCompany;

/**
 * Created by vlad
 * Date: 05.03.15_13:51
 */
public interface DescriptionDaoI {

    public DescriptionCompany getDescriptionCompany(Integer companyId);
}
