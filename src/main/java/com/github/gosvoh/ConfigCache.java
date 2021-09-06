package com.github.gosvoh;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toSet;

public class ConfigCache {
    private static ConfigCache    INSTANCE;
    private        Set<Block>     blocksBlacklist;
    private        Set<Item>      itemsBlacklist;
    private        Set<Tag<Block>> blockTagsBlacklist;
    private        Set<Tag<Item>>  itemTagsBlacklist;

    public static ConfigCache getInstance() {
        if (isNull(INSTANCE)) {
            INSTANCE = new ConfigCache();
        }
        return INSTANCE;
    }

    public void invalidate() {
        blocksBlacklist = null;
        itemsBlacklist = null;
        blockTagsBlacklist = null;
        itemTagsBlacklist = null;
    }

    public Collection<Block> getBlocksBlacklist(Collection<String> collection) {
        if (isNull(blocksBlacklist))
            blocksBlacklist = collection.stream().filter(Objects::nonNull).filter(val -> !val.isEmpty())
                                                .flatMap(s -> Stream.of(Registry.BLOCK.get(new Identifier(s))))
                                                .collect(toSet());
        return blocksBlacklist;
    }

    public Collection<Item> getItemsBlacklist(Collection<String> collection) {
        if (isNull(itemsBlacklist))
            itemsBlacklist = collection.stream().filter(Objects::nonNull).filter(val -> !val.isEmpty())
                                               .flatMap(s -> Stream.of(Registry.ITEM.get(new Identifier(s))))
                                               .collect(toSet());
        return itemsBlacklist;
    }

    public Collection<Tag<Block>> getBlockTagsBlacklist(Collection<String> collection) {if (isNull(itemsBlacklist))
        blockTagsBlacklist = collection.stream().filter(Objects::nonNull).filter(val -> !val.isEmpty())
                                           .flatMap(s -> Stream.of(BlockTags.getTagGroup().getTag(new Identifier(s))))
                                           .collect(toSet());
        return blockTagsBlacklist;
    }

    public Collection<Tag<Item>> getItemTagsBlacklist(Collection<String> collection) {if (isNull(itemsBlacklist))
        itemTagsBlacklist = collection.stream().filter(Objects::nonNull).filter(val -> !val.isEmpty())
                                           .flatMap(s -> Stream.of(ItemTags.getTagGroup().getTag(new Identifier(s))))
                                           .collect(toSet());
        return itemTagsBlacklist;
    }
}
