package com.pb.dashboard.monitoring.errors.all.db.mongo;

import com.pb.dashboard.core.config.ConfigManager;
import com.pb.dashboard.core.config.ConfigParams;
import com.pb.dashboard.core.util.IntegerUtil;

/**
 * Created by vlad
 * Date: 05.12.14_10:14
 */
public abstract class ErrorsDBManagerAbs implements ErrorsDBManagerI {

    private static String address;
    private static String port;

    private static ErrorsDBManagerI instance;

    public static ErrorsDBManagerI getInstance() {
        if (instance == null) {
            address = ConfigManager.getInstance().getParam(ConfigParams.MONGO_HOST);
            port = ConfigManager.getInstance().getParam(ConfigParams.MONGO_PORT);
            init();
        }
        return instance;
    }

    private static void init() {
        if (validAddressAndPort()) {
            instance = new ErrorsDashDBManager(address, Integer.parseInt(port));
        } else {
            instance = new ErrorsDBManager();
        }
    }

    private static boolean validAddressAndPort() {
        if (address == null || address.isEmpty()) {
            return false;
        }
        if (port == null || port.isEmpty()) {
            return false;
        }
        if (!IntegerUtil.checkInt(port)) {
            return false;
        }
        return true;
    }
}