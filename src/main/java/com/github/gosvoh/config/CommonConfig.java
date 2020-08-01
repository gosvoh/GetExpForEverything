package com.github.gosvoh.config;

import com.github.gosvoh.utils.Reference;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class CommonConfig {
    final ForgeConfigSpec.IntValue blocksNeedToDestroy;
    final ForgeConfigSpec.IntValue baseExpToGain;
    final ForgeConfigSpec.IntValue multiplierForLevelStep;
    final ForgeConfigSpec.IntValue levelStep;
    final ForgeConfigSpec.IntValue itemsNeedToCraft;
    final ForgeConfigSpec.ConfigValue<List<? extends String>> blackListBlocks;
    final ForgeConfigSpec.ConfigValue<List<? extends String>> blackListCraftedItems;
    final ForgeConfigSpec.ConfigValue<List<? extends String>> blackListBlockTags;
    final ForgeConfigSpec.ConfigValue<List<? extends String>> blackListItemTags;

    CommonConfig(final ForgeConfigSpec.Builder builder) {
        String desc = Reference.MOD_NAME + " mod configuration";
        builder.comment(desc).push(Reference.MOD_ID);

        desc = "How many blocks you have to destroy to get experience (default: 100)";
        blocksNeedToDestroy = builder.comment(desc)
                .defineInRange("blocks_need_to_destroy", 100, 1, Integer.MAX_VALUE);

        desc = "How much experience you will get (default: 1)";
        baseExpToGain = builder.comment(desc)
                .defineInRange("base_experience", 1, 1, 1000);

        desc = "Multiplier for level step.\n" +
               "For example:\n" +
               "0-4 lvl you will gain 1 exp every 100 destroyed blocks\n" +
               "5-9 lvl you will gain 2 exp every 100 destroyed blocks\n" +
               "10-14 lvl you will gain 4 exp every 100 destroyed blocks\n" +
               "etc.\n" +
               "(default: 2)";
        multiplierForLevelStep = builder.comment(desc)
                .defineInRange("multiplier_for_level_step", 2, 1, 1000);

        desc = "How many levels needs to reach after multiply base exp (default: 5)";
        levelStep = builder.comment(desc)
                .defineInRange("level_step", 5, 1, 1000);

        desc = "How much items you have to craft to get experience (default: 100)";
        itemsNeedToCraft = builder.comment(desc)
                .defineInRange("items_to_craft", 100, 1, Integer.MAX_VALUE);

        desc = "Blacklisted blocks, that won't give you experience";
        ArrayList<String> blockArrayList = new ArrayList<>();
        //noinspection ConstantConditions
        Collections.addAll(blockArrayList,
                Blocks.GRASS.getRegistryName().toString(),
                Blocks.VINE.getRegistryName().toString(),
                Blocks.TALL_GRASS.getRegistryName().toString());
        blackListBlocks = builder.comment(desc)
                .defineList("black_list_blocks", blockArrayList, o -> o instanceof String);

        desc = "Blacklisted items, that won't give you experience";
        ArrayList<String> itemArrayList = new ArrayList<>();
        //noinspection ConstantConditions
        itemArrayList.add(Items.STICK.getRegistryName().toString());
        blackListCraftedItems = builder.comment(desc)
                .defineList("black_list_items", itemArrayList, o -> o instanceof String);

        desc = "Blacklisted block tags, that won't give you experience";
        ArrayList<String> blockTagArrayList = new ArrayList<>();
        blockTagArrayList.add("minecraft:flowers");
        blackListBlockTags = builder.comment(desc)
                .defineList("black_list_item_tags", blockTagArrayList, o -> o instanceof String);

        desc = "Blacklisted item tags, that won't give you experience";
        ArrayList<String> itemTagArrayList = new ArrayList<>();
        itemTagArrayList.add("minecraft:planks");
        blackListItemTags = builder.comment(desc)
                .defineList("black_list_block_tags", itemTagArrayList, o -> o instanceof String);

        builder.pop();
    }
}
