package com.github.gosvoh;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetExpForEverything implements ModInitializer {
    public static final Logger        LOGGER = LogManager.getLogger(Reference.MOD_ID);
    public static       Configuration config;

    @Override public void onInitialize() {
        config = Configuration.register();

        LOGGER.info(Reference.MOD_NAME + " version " + Reference.VERSION + " by " + Reference.AUTHOR + " started up.");
    }
}
