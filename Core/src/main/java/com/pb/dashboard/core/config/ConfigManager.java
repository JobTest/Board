package com.pb.dashboard.core.config;

import org.boon.json.ObjectMapper;
import org.boon.json.ObjectMapperFactory;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by vlad
 * Date: 09.02.15_14:55
 */
public final class ConfigManager {

    private static ConfigManager INSTANCE;
    private static final String CATALINA_HOME = "catalina.home";
    private static final String CONFIG_FILE_PATH = "/conf/board_config.json";
    private Map<ConfigParams, String> params = new LinkedHashMap<>();

    public static ConfigManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConfigManager();
        }
        return INSTANCE;
    }

    private ConfigManager() {
        init();
    }

    private void init() {
        ObjectMapper mapper = ObjectMapperFactory.create();
        String path = System.getProperty(CATALINA_HOME) + CONFIG_FILE_PATH;
        Map<String, String> map = mapper.readValue(new File(path), Map.class);
        for (ConfigParams config : ConfigParams.values()) {
            params.put(config, map.get(config.getName()));
        }
    }

    public String getParam(ConfigParams param) {
        return params.get(param);
    }
}
