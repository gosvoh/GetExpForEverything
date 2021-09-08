package com.github.gosvoh;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Config(name = Reference.MOD_ID) public class Configuration implements ConfigData {
    @Comment("How many blocks you have to destroy to get experience") public               int          blocks_need_to_destroy    =
            100;
    @Comment("How many items you have to craft to get experience") public                  int          items_to_craft            =
            100;
    @Comment("How much experience you will gain") public                                   int
                                                                                                        base_experience           =
            1;
    @Comment("Multiplier for level step.\n" + "For example:\n" +
             "0-4 lvl you will gain 1 exp every 100 destroyed blocks\n" +
             "5-9 lvl you will gain 2 exp every 100 destroyed blocks\n" +
             "10-14 lvl you will gain 4 exp every 100 destroyed blocks\n" + "etc.") public int
                                                                                                        multiplier_for_level_step =
            2;
    @Comment("How many levels needs to reach after multiply base exp") public              int          level_step                =
            5;
    @Comment("Blacklisted blocks, that won't give you experience") public                  List<String>
                                                                                                        black_list_blocks         =
            new ArrayList<>();
    @Comment("Blacklisted items, that won't give you experience") public                   List<String>
                                                                                                        black_list_items          =
            new ArrayList<>();
    @Comment("Blacklisted block tags, that won't give you experience") public              List<String>
                                                                                                        black_list_block_tags     =
            new ArrayList<>();
    @Comment("Blacklisted item tags, that won't give you experience") public               List<String>
                                                                                                        black_list_item_tags      =
            new ArrayList<>();
    @Comment("This trigger converts your block blacklists to whitelists if true") public   boolean
                                                                                                        is_block_whitelist_mode   =
            false;
    @Comment("This trigger converts your item blacklists to whitelists if true") public    boolean
                                                                                                        is_item_whitelist_mode    =
            false;

    {
        Collections.addAll(black_list_blocks, Registry.BLOCK.getId(Blocks.GRASS).toString(),
                Registry.BLOCK.getId(Blocks.VINE).toString(), Registry.BLOCK.getId(Blocks.TALL_GRASS).toString(),
                Registry.BLOCK.getId(Blocks.LARGE_FERN).toString(), Registry.BLOCK.getId(Blocks.FERN).toString());
        black_list_items.add(Registry.ITEM.getId(Items.STICK).toString());
        black_list_block_tags.add(BlockTags.FLOWERS.getId().toString());
        black_list_item_tags.add(ItemTags.PLANKS.getId().toString());

    }

    public static Configuration register() {
        ConfigHolder<Configuration> configHolder =
                AutoConfig.register(Configuration.class, Toml4jConfigSerializer::new);
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

    public Collection<Block> getBlacklistedBlocks() {
        return ConfigCache.getInstance().getBlocksBlacklist(black_list_blocks);
    }

    public Collection<Item> getBlacklistedItems() {
        return ConfigCache.getInstance().getItemsBlacklist(black_list_items);
    }

    public Collection<Identifier> getBlacklistedBlockTags() {
        return ConfigCache.getInstance().getBlockTagsBlacklist(black_list_block_tags);
    }

    public Collection<Identifier> getBlacklistedItemTags() {
        return ConfigCache.getInstance().getItemTagsBlacklist(black_list_item_tags);
    }

    @Override public void validatePostLoad() throws ValidationException {
        ConfigData.super.validatePostLoad();
    }
}
