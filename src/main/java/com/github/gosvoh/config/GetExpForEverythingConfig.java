package com.github.gosvoh.config;

import com.github.gosvoh.utils.Reference;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("ConstantConditions")
@Config(modid = Reference.MOD_ID)
public class GetExpForEverythingConfig {

    @Config.Comment("How many blocks you have to destroy to get experience (default: 100)")
    @Config.RangeInt(min = 1)
    @Config.Name("Blocks need to destroy")
    public static int blocksNeedToDestroy = 100;

    @Config.Comment("How much items you have to craft to get experience (default: 100)")
    @Config.RangeInt(min = 1)
    @Config.Name("Items need to craft")
    public static int itemsNeedToCraft = 100;

    @Config.Comment("How much base experience you will get (default: 1)")
    @Config.RangeInt(min = 1, max = 1000)
    @Config.Name("Base experience")
    public static int baseExpToGain = 1;

    @Config.Comment({"Multiplier for level step.",
            "For example:",
            "0-4 lvl you will gain 1 exp every 100 destroyed blocks",
            "5-9 lvl you will gain 2 exp every 100 destroyed blocks",
            "10-14 lvl you will gain 4 exp every 100 destroyed blocks",
            "etc.",
            "(default: 2)"})
    @Config.RangeInt(min = 1, max = 1000)
    @Config.Name("Multiplier for level step")
    public static int multiplierForLevelStep = 2;

    @Config.Comment("How many levels needs to reach after multiply base exp (default: 5)")
    @Config.RangeInt(min = 1, max = 1000)
    @Config.Name("Level step")
    public static int levelStep = 5;

    @Config.Comment("Blacklisted blocks, that won't give you experience")
    @Config.Name("Blacklisted blocks")
    public static String[] blackListBlocks = {
            Blocks.DOUBLE_PLANT.getRegistryName().toString(),
            Blocks.VINE.getRegistryName().toString(),
            Blocks.TALLGRASS.getRegistryName().toString(),
            Blocks.RED_FLOWER.getRegistryName().toString(),
            Blocks.YELLOW_FLOWER.getRegistryName().toString()
    };

    @Config.Comment("Blacklisted items, that won't give you experience")
    @Config.Name("Blacklisted items")
    public static String[] blackListCraftedItems = {Items.STICK.getRegistryName().toString()};

    @Config.Comment("This trigger converts your block blacklists to whitelists if true (default: false)")
    @Config.Name("Is block whitelist mode")
    public static boolean isBlockWhitelistMode = false;

    @Config.Comment("This trigger converts your item blacklists to whitelists if true (default: false)")
    @Config.Name("Is item whitelist mode")
    public static boolean isItemWhitelistMode = false;

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    private static class ConfigChangedEventHandler {
        @SubscribeEvent
        public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Reference.MOD_ID)) {
                ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);

                if (Reference.countOfBrokenBlocks >= blocksNeedToDestroy) Reference.countOfBrokenBlocks = 0;
                if (Reference.countOfCraftedItems >= itemsNeedToCraft) Reference.countOfCraftedItems = 0;
            }
        }
    }
}
