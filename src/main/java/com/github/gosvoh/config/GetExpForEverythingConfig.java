package com.github.gosvoh.config;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.Tag;

import java.util.ArrayList;

public final class GetExpForEverythingConfig {
    public static int blocksNeedToDestroy;
    public static int baseExpToGain;
    public static int multiplierForLevelStep;
    public static int levelStep;
    public static int countOfCraftedItems;
    public static ArrayList<String> blackListBlocks;
    public static ArrayList<String> blackListCraftedItems;
    public static ArrayList<String> blackListBlockTags;
    public static ArrayList<String> blackListItemTags;
}
