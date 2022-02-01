package com.github.gosvoh.config;

import java.util.List;

public final class GetExpForEverythingConfig {
    public static int                    blocksNeedToDestroy;
    public static int                    blocksNeedToPlace;
    public static int                    baseExpToGain;
    public static int                    multiplierForLevelStep;
    public static int                    levelStep;
    public static int                    itemsNeedToCraft;
    public static List<? extends String> blackListBlocks;
    public static List<? extends String> blackListCraftedItems;
    public static List<? extends String> blackListBlockTags;
    public static List<? extends String> blackListItemTags;
    public static List<? extends String> blackListBlocksPlace;
    public static List<? extends String> blackListBlockTagsPlace;
    public static boolean                isBlockWhitelistMode;
    public static boolean                isItemWhitelistMode;
    public static boolean                isPlacingEnabled;
    public static boolean                isPlacingWhitelistMode;
}
