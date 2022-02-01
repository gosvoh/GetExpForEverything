package com.github.gosvoh.config;

import com.github.gosvoh.utils.Reference;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommonConfig {
    public static final CommonConfig    COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> commonSpecPair =
                new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }

    ForgeConfigSpec.IntValue                            blocksNeedToDestroy;
    ForgeConfigSpec.IntValue                            baseExpToGain;
    ForgeConfigSpec.IntValue                            multiplierForLevelStep;
    ForgeConfigSpec.IntValue                            levelStep;
    ForgeConfigSpec.IntValue                            itemsNeedToCraft;
    ForgeConfigSpec.ConfigValue<List<? extends String>> blackListBlocks;
    ForgeConfigSpec.ConfigValue<List<? extends String>> blackListCraftedItems;
    ForgeConfigSpec.ConfigValue<List<? extends String>> blackListBlockTags;
    ForgeConfigSpec.ConfigValue<List<? extends String>> blackListItemTags;
    ForgeConfigSpec.BooleanValue                        isBlockWhitelistMode;
    ForgeConfigSpec.BooleanValue                        isItemWhitelistMode;

    CommonConfig(ForgeConfigSpec.Builder builder) {
        String desc = Reference.MOD_NAME + " mod configuration";
        ArrayList<String> namesArrayList;
        builder.comment(desc).push(Reference.MOD_ID);

        desc = "How many blocks you have to destroy to get experience (default: 100)";
        blocksNeedToDestroy = builder.comment(desc).defineInRange("blocks_need_to_destroy", 100, 1, Integer.MAX_VALUE);

        desc = "How much experience you will get (default: 1)";
        baseExpToGain = builder.comment(desc).defineInRange("base_experience", 1, 1, 1000);

        desc = """
                Multiplier for level step.
                For example:
                0-4 lvl you will gain 1 exp every 100 destroyed blocks
                5-9 lvl you will gain 2 exp every 100 destroyed blocks
                10-14 lvl you will gain 4 exp every 100 destroyed blocks
                etc.
                (default: 2)""";
        multiplierForLevelStep = builder.comment(desc).defineInRange("multiplier_for_level_step", 2, 1, 1000);

        desc = "How many levels needs to reach after multiply base exp (default: 5)";
        levelStep = builder.comment(desc).defineInRange("level_step", 5, 1, 1000);

        desc = "How much items you have to craft to get experience (default: 100)";
        itemsNeedToCraft = builder.comment(desc).defineInRange("items_to_craft", 100, 1, Integer.MAX_VALUE);

        desc = "Blacklisted blocks, that won't give you experience";
        namesArrayList = new ArrayList<>();
        //noinspection ConstantConditions
        Collections.addAll(namesArrayList, Blocks.GRASS.getRegistryName().toString(),
                Blocks.VINE.getRegistryName().toString(), Blocks.TALL_GRASS.getRegistryName().toString(),
                Blocks.LARGE_FERN.getRegistryName().toString(), Blocks.FERN.getRegistryName().toString());
        desc = desc + "\n(default: " + namesArrayList + ")";
        blackListBlocks =
                builder.comment(desc).defineList("black_list_blocks", namesArrayList, o -> o instanceof String);

        desc = "Blacklisted items, that won't give you experience";
        namesArrayList = new ArrayList<>();
        //noinspection ConstantConditions
        namesArrayList.add(Items.STICK.getRegistryName().toString());
        desc = desc + "\n(default: " + namesArrayList + ")";
        blackListCraftedItems =
                builder.comment(desc).defineList("black_list_items", namesArrayList, o -> o instanceof String);

        desc = "Blacklisted block tags, that won't give you experience";
        namesArrayList = new ArrayList<>();
        namesArrayList.add("minecraft:flowers");
        desc = desc + "\n(default: " + namesArrayList + ")";
        blackListBlockTags =
                builder.comment(desc).defineList("black_list_item_tags", namesArrayList, o -> o instanceof String);

        desc = "Blacklisted item tags, that won't give you experience";
        namesArrayList = new ArrayList<>();
        namesArrayList.add("minecraft:planks");
        desc = desc + "\n(default: " + namesArrayList + ")";
        blackListItemTags =
                builder.comment(desc).defineList("black_list_block_tags", namesArrayList, o -> o instanceof String);

        desc = "This trigger converts your block blacklists to whitelists if true (default: false)";
        isBlockWhitelistMode = builder.comment(desc).define("is_block_whitelist_mode", false);

        desc = "This trigger converts your item blacklists to whitelists if true (default: false)";
        isItemWhitelistMode = builder.comment(desc).define("is_item_whitelist_mode", false);

        builder.pop();
    }
}
