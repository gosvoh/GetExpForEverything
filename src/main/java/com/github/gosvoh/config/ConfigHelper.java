package com.github.gosvoh.config;

import net.minecraftforge.fml.config.ModConfig;

public final class ConfigHelper {
    private static ModConfig clientConfig;

    public static void bakeClient(final ModConfig config) {
        clientConfig = config;

        GetExpForEverythingConfig.baseExpToGain = ConfigHolder.COMMON.baseExpToGain.get();
        GetExpForEverythingConfig.blocksNeedToDestroy = ConfigHolder.COMMON.blocksNeedToDestroy.get();
        GetExpForEverythingConfig.countOfCraftedItems = ConfigHolder.COMMON.countOfCraftedItems.get();
        GetExpForEverythingConfig.levelStep = ConfigHolder.COMMON.levelStep.get();
        GetExpForEverythingConfig.multiplierForLevelStep = ConfigHolder.COMMON.multiplierForLevelStep.get();
        GetExpForEverythingConfig.blackListBlocks = ConfigHolder.COMMON.blackListBlocks.get();
        GetExpForEverythingConfig.blackListCraftedItems = ConfigHolder.COMMON.blackListCraftedItems.get();
        GetExpForEverythingConfig.blackListBlockTags = ConfigHolder.COMMON.blackListBlockTags.get();
        GetExpForEverythingConfig.blackListItemTags = ConfigHolder.COMMON.blackListItemTags.get();

    }

    private static void setValueAndSave(final ModConfig modConfig, final String path, final Object newValue) {
        modConfig.getConfigData().set(path, newValue);
        modConfig.save();
    }
}
