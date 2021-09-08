package com.github.gosvoh;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toSet;

public class ConfigCache {
    private static ConfigCache     INSTANCE;
    private        Set<Block>      blocksBlacklist;
    private        Set<Item>       itemsBlacklist;
    private        Set<Identifier> blockTagsBlacklist;
    private        Set<Identifier> itemTagsBlacklist;

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
        if (isNull(blocksBlacklist)) blocksBlacklist =
                collection.stream().filter(Objects::nonNull).filter(val -> !val.isEmpty())
                          .flatMap(s -> Stream.of(Registry.BLOCK.get(new Identifier(s)))).collect(toSet());
        return blocksBlacklist;
    }

    public Collection<Item> getItemsBlacklist(Collection<String> collection) {
        if (isNull(itemsBlacklist)) itemsBlacklist =
                collection.stream().filter(Objects::nonNull).filter(val -> !val.isEmpty())
                          .flatMap(s -> Stream.of(Registry.ITEM.get(new Identifier(s)))).collect(toSet());
        return itemsBlacklist;
    }

    public Collection<Identifier> getBlockTagsBlacklist(Collection<String> collection) {
        if (isNull(itemsBlacklist)) blockTagsBlacklist =
                collection.stream().filter(Objects::nonNull).filter(val -> !val.isEmpty()).flatMap(s -> {
                    // IDK what happens here, but if replace it with lambda, it returns null
                    return Stream.of(new Identifier(s));
                }).collect(toSet());
        return blockTagsBlacklist;
    }

    public Collection<Identifier> getItemTagsBlacklist(Collection<String> collection) {
        if (isNull(itemsBlacklist))
            collection.forEach(message -> GetExpForEverything.LOGGER.error("str coll " + message));
        itemTagsBlacklist = collection.stream().filter(Objects::nonNull).filter(val -> !val.isEmpty()).flatMap(s -> {
            // Same sh*t here too
            return Stream.of(new Identifier(s));
        }).collect(toSet());
        return itemTagsBlacklist;
    }
}
