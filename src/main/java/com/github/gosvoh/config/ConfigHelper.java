package com.github.gosvoh.config;

import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.NoSuchElementException;

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

        // It causes NullPointerException every time on update config file
        /*try {
            GetExpForEverythingConfig.baseExpToGain = clientConfig.getConfigData().get("base_experience");
            GetExpForEverythingConfig.blocksNeedToDestroy = clientConfig.getConfigData().get("blocks_need_to_destroy");
            GetExpForEverythingConfig.countOfCraftedItems = clientConfig.getConfigData().get("items_to_craft");
            GetExpForEverythingConfig.levelStep = clientConfig.getConfigData().get("level_step");
            GetExpForEverythingConfig.multiplierForLevelStep = clientConfig.getConfigData().get("multiplier_for_level_step");
            GetExpForEverythingConfig.blackListBlocks = clientConfig.getConfigData().get("black_list_blocks");
            GetExpForEverythingConfig.blackListCraftedItems = clientConfig.getConfigData().get("black_list_items");
            GetExpForEverythingConfig.blackListBlockTags = clientConfig.getConfigData().get("black_list_block_tags");
            GetExpForEverythingConfig.blackListItemTags = clientConfig.getConfigData().get("black_list_item_tags");
        } catch (NullPointerException | NoSuchElementException ex) {
            LogManager.getLogger().error("ConfigHelper exception: ", ex);
        }*/

    }

    private static void setValueAndSave(final ModConfig modConfig, final String path, final Object newValue) {
        modConfig.getConfigData().set(path, newValue);
        modConfig.save();
    }
}
