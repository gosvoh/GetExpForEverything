package com.github.gosvoh.utils;

import com.github.gosvoh.config.ConfigHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModEventSubscriber {

    @SubscribeEvent public static void onModConfigEvent(final ModConfig.ModConfigEvent event) {
        ConfigHelper.bakeClient(event.getConfig());
    }
}
