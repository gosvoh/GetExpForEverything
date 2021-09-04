package com.github.gosvoh;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetExpForEverything implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);
    public static Configuration config;

    @Override
    public void onInitialize() {
        AutoConfig.register(Configuration.class, Toml4jConfigSerializer::new);
        PlayerBlockBreakEvents.AFTER.register(new BlockBreakHandler());
        config = AutoConfig.getConfigHolder(Configuration.class).getConfig();

        LOGGER.info(Reference.MOD_NAME + " version " + Reference.VERSION +
                    " by " + Reference.AUTHOR + " started up.");
    }
}
