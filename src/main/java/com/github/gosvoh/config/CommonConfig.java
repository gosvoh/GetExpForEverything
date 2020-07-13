package com.github.gosvoh.config;

import com.github.gosvoh.Reference;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.Collections;

final class CommonConfig {
    final ForgeConfigSpec.IntValue blocksNeedToDestroy;
    final ForgeConfigSpec.IntValue baseExpToGain;
    final ForgeConfigSpec.IntValue multiplierForLevelStep;
    final ForgeConfigSpec.IntValue levelStep;
    final ForgeConfigSpec.IntValue countOfCraftedItems;
    final ForgeConfigSpec.ConfigValue<ArrayList<String>> blackListBlocks;
    final ForgeConfigSpec.ConfigValue<ArrayList<String>> blackListCraftedItems;
    final ForgeConfigSpec.ConfigValue<ArrayList<String>> blackListBlockTags;
    final ForgeConfigSpec.ConfigValue<ArrayList<String>> blackListItemTags;

    CommonConfig(final ForgeConfigSpec.Builder builder) {
        String desc = Reference.MOD_NAME + " mod configuration";
        builder.comment(desc).push(Reference.MOD_ID);

        builder.push("Blocks to destroy");
        desc = "How many blocks you have to destroy to get experience (default: 100)";
        blocksNeedToDestroy = builder.comment(desc)
                .defineInRange("blocks_need_to_destroy", 100, 1, Integer.MAX_VALUE);
        builder.pop();

        builder.push("Exp to gain");
        desc = "How much experience you will get (default: 1)";
        baseExpToGain = builder.comment(desc)
                .defineInRange("base_experience", 1, 1, 1000);
        builder.pop();

        builder.push("Exp multiplier");
        desc = "Multiplier for level step.\n" +
               "For example:\n" +
               "0-4 lvl you will gain 1 exp every 100 destroyed blocks\n" +
               "5-9 lvl you will gain 2 exp every 100 destroyed blocks\n" +
               "10-14 lvl you will gain 4 exp every 100 destroyed blocks\n" +
               "etc.\n" +
               "(default: 2)";
        multiplierForLevelStep = builder.comment(desc)
                .defineInRange("multiplier_for_level_step", 2, 1, 1000);
        builder.pop();

        builder.push("Levels to next multiply");
        desc = "How many levels needs to reach after multiply base exp (default: 5)";
        levelStep = builder.comment(desc)
                .defineInRange("level_step", 5, 1, 1000);
        builder.pop();

        builder.push("Count of crafted items");
        desc = "How much items you have to craft to get experience (default: 100)";
        countOfCraftedItems = builder.comment(desc)
                .defineInRange("items_to_craft", 100, 1, Integer.MAX_VALUE);
        builder.pop();

        builder.push("Blacklisted blocks");
        desc = "Blacklisted blocks, that won't give you experience";
        ArrayList<String> blockArrayList = new ArrayList<>();
        //noinspection ConstantConditions
        Collections.addAll(blockArrayList,
                Blocks.GRASS.getRegistryName().toString(),
                Blocks.VINE.getRegistryName().toString(),
                Blocks.TALL_GRASS.getRegistryName().toString());
        blackListBlocks = builder.comment(desc)
                .defineInList("black_list_blocks", blockArrayList, new ArrayList<>());
        builder.pop();

        builder.push("Blacklisted items");
        desc = "Blacklisted items, that won't give you experience";
        ArrayList<String> itemArrayList = new ArrayList<>();
        //noinspection ConstantConditions
        itemArrayList.add(Items.STICK.getRegistryName().toString());
        blackListCraftedItems = builder.comment(desc)
                .defineInList("black_list_items", itemArrayList, new ArrayList<>());
        builder.pop();

        builder.push("Blacklisted block tags");
        desc = "Blacklisted block tags, that won't give you experience";
        ArrayList<String> blockTagArrayList = new ArrayList<>();
        blockTagArrayList.add("minecraft:flowers");
        blackListBlockTags = builder.comment(desc)
                .defineInList("black_list_item_tags", blockTagArrayList, new ArrayList<>());
        builder.pop();

        builder.push("Blacklisted item tags");
        desc = "Blacklisted item tags, that won't give you experience";
        ArrayList<String> itemTagArrayList = new ArrayList<>();
        itemTagArrayList.add("minecraft:planks");
        blackListItemTags = builder.comment(desc)
                .defineInList("black_list_block_tags", itemTagArrayList, new ArrayList<>());
        builder.pop();

        builder.pop();
    }
}
