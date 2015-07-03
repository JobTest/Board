package com.pb.dashboard.server.service.businesslayer;

import com.pb.dashboard.server.service.api.AuthPay;
import com.pb.dashboard.server.service.api.SmsPay;

import java.util.List;

/**
 * Created by vlad
 * Date: 18.03.15_14:53
 */
public interface MassPaysServiceI {

    List<AuthPay> getAuthPays();

    List<SmsPay> getSmsPays();
}
