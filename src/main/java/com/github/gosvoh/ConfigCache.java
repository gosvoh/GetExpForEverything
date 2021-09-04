package com.github.gosvoh;

import static java.util.Objects.isNull;

public class ConfigCache {
    private static ConfigCache INSTANCE;

    public static ConfigCache getInstance(){
        if(isNull(INSTANCE)){
            INSTANCE = new ConfigCache();
        }
        return INSTANCE;
    }

    public void invalidate() {

    }
}
