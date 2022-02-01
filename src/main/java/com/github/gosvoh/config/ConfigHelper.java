package com.github.gosvoh.config;

import com.github.gosvoh.utils.Reference;
import net.minecraftforge.fml.config.ModConfig;

public final class ConfigHelper {
    public static void bakeClient(final ModConfig config) {
        String prefix = Reference.MOD_ID + ".";

        GetExpForEverythingConfig.baseExpToGain = config.getConfigData().getInt(prefix + "base_experience");
        GetExpForEverythingConfig.blocksNeedToDestroy =
                config.getConfigData().getInt(prefix + "blocks_need_to_destroy");
        GetExpForEverythingConfig.blocksNeedToPlace = config.getConfigData().getInt(prefix + "blocks_need_to_place");
        GetExpForEverythingConfig.itemsNeedToCraft = config.getConfigData().getInt(prefix + "items_to_craft");
        GetExpForEverythingConfig.levelStep = config.getConfigData().getInt(prefix + "level_step");
        GetExpForEverythingConfig.multiplierForLevelStep =
                config.getConfigData().getInt(prefix + "multiplier_for_level_step");
        GetExpForEverythingConfig.blackListBlocks = config.getConfigData().get(prefix + "black_list_blocks");
        GetExpForEverythingConfig.blackListCraftedItems = config.getConfigData().get(prefix + "black_list_items");
        GetExpForEverythingConfig.blackListBlockTags = config.getConfigData().get(prefix + "black_list_block_tags");
        GetExpForEverythingConfig.blackListItemTags = config.getConfigData().get(prefix + "black_list_item_tags");
        GetExpForEverythingConfig.blackListBlocksPlace = config.getConfigData().get(prefix + "black_list_blocks_place");
        GetExpForEverythingConfig.blackListBlockTagsPlace =
                config.getConfigData().get(prefix + "black_list_block_tags_place");
        GetExpForEverythingConfig.isBlockWhitelistMode = config.getConfigData().get(prefix + "is_block_whitelist_mode");
        GetExpForEverythingConfig.isItemWhitelistMode = config.getConfigData().get(prefix + "is_item_whitelist_mode");
        GetExpForEverythingConfig.isPlacingEnabled = config.getConfigData().get(prefix + "is_placing_enabled");
        GetExpForEverythingConfig.isPlacingWhitelistMode =
                config.getConfigData().get(prefix + "is_placing_whitelist_mode");

        if (Reference.countOfBrokenBlocks >= GetExpForEverythingConfig.blocksNeedToDestroy)
            Reference.countOfBrokenBlocks = 0;
        if (Reference.countOfCraftedItems >= GetExpForEverythingConfig.itemsNeedToCraft)
            Reference.countOfCraftedItems = 0;
    }
}