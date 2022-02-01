package com.github.gosvoh;

import com.github.gosvoh.config.CommonConfig;
import com.github.gosvoh.utils.Reference;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MOD_ID)
public class GetExpForEverything {

    public static final Logger LOGGER = LogManager.getLogger();

    public GetExpForEverything() {
        LOGGER.info(Reference.MOD_NAME + " version " + Reference.VERSION +
                    " by " + Reference.AUTHOR + " started up.");

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.COMMON_SPEC);
    }
}
