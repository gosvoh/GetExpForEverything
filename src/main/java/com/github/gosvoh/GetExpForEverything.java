package com.github.gosvoh;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(GetExpForEverything.ID)
public class GetExpForEverything {

    public static final String ID = "getexpforeverything";
    public static final Logger LOGGER = LogManager.getLogger();

    public GetExpForEverything() {
        LOGGER.info(Constants.MOD_NAME + " Version " + Constants.VERSION +
                    " by " + Constants.AUTHOR + " started up.");

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,
                ConfigHandler.CLIENT_SPEC, Constants.MOD_ID + "-common.toml");
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.debug("Finished Get experience for everything! setup");
    }
}
