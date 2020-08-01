package com.github.gosvoh.config;

import com.github.gosvoh.GetExpForEverything;
import com.github.gosvoh.utils.Reference;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.Logger;

public final class ConfigHelper {
    private static final Logger LOGGER = GetExpForEverything.LOGGER;

    public static void bakeClient(final ModConfig config) {
        String prefix = Reference.MOD_ID + ".";

        GetExpForEverythingConfig.baseExpToGain = config.getConfigData().getInt(prefix + "base_experience");
        GetExpForEverythingConfig.blocksNeedToDestroy = config.getConfigData().getInt(prefix + "blocks_need_to_destroy");
        GetExpForEverythingConfig.itemsNeedToCraft = config.getConfigData().getInt(prefix + "items_to_craft");
        GetExpForEverythingConfig.levelStep = config.getConfigData().getInt(prefix + "level_step");
        GetExpForEverythingConfig.multiplierForLevelStep = config.getConfigData().getInt(prefix + "multiplier_for_level_step");
        GetExpForEverythingConfig.blackListBlocks = config.getConfigData().get(prefix + "black_list_blocks");
        GetExpForEverythingConfig.blackListCraftedItems = config.getConfigData().get(prefix + "black_list_items");
        GetExpForEverythingConfig.blackListBlockTags = config.getConfigData().get(prefix + "black_list_block_tags");
        GetExpForEverythingConfig.blackListItemTags = config.getConfigData().get(prefix + "black_list_item_tags");
        GetExpForEverythingConfig.isBlockWhitelistMode = config.getConfigData().get(prefix + "is_block_whitelist_mode");
        GetExpForEverythingConfig.isItemWhitelistMode = config.getConfigData().get(prefix + "is_item_whitelist_mode");

        Reference.countOfBrokenBlocks = 0;
        Reference.countOfCraftedItems = 0;

        LOGGER.debug(Reference.MOD_NAME + " configuration file was changed!");
    }

    private static void setValueAndSave(final ModConfig modConfig, final String path, final Object newValue) {
        modConfig.getConfigData().set(path, newValue);
        modConfig.save();
    }
}
