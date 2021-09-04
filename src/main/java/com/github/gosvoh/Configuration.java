package com.github.gosvoh;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.util.ActionResult;

import java.util.ArrayList;
import java.util.List;

@Config(name = Reference.MOD_ID)
public class Configuration implements ConfigData {
    @Comment("How many blocks you have to destroy to get experience")
    public static int                    blocks_need_to_destroy    = 100;
    @Comment("How many items you have to craft to get experience")
    public static int                    items_to_craft       = 100;
    @Comment("How much experience you will gain")
    public static int                    base_experience          = 1;
    @Comment("Multiplier for level step.\n" + "For example:\n" + "0-4 lvl you will gain 1 exp every 100 destroyed blocks\n" + "5-9 lvl you will gain 2 exp every 100 destroyed blocks\n" + "10-14 lvl you will gain 4 exp every 100 destroyed blocks\n" + "etc.")
    public static int                    multiplier_for_level_step = 2;
    @Comment("How many levels needs to reach after multiply base exp")
    public static int                    level_step              = 5;
    public static List<String> black_list_blocks = new ArrayList<>();
    public static List<String> black_list_items = new ArrayList<>();
    public static List<String> black_list_block_tags = new ArrayList<>();
    public static List<String> black_list_item_tags = new ArrayList<>();
    @Comment("This trigger converts your block blacklists to whitelists if true")
    public static boolean                is_block_whitelist_mode = false;
    @Tooltip(count = 2)
    @Comment("This trigger converts your item blacklists to whitelists if true")
    public static boolean                is_item_whitelist_mode = false;

    public static Configuration register() {
        ConfigHolder<Configuration> configHolder = AutoConfig.register(Configuration.class, Toml4jConfigSerializer::new);
        configHolder.registerSaveListener(((configHolder1, configuration) -> {
            ConfigCache.getInstance().invalidate();
            return ActionResult.PASS;
        }));
        configHolder.registerLoadListener(((configHolder1, configuration) -> {
            ConfigCache.getInstance().invalidate();
            return ActionResult.PASS;
        }));

        return configHolder.getConfig();
    }

    @Override
    public void validatePostLoad() throws ValidationException {
        ConfigData.super.validatePostLoad();
    }
}
