package com.github.gosvoh.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class SavedInfo {

    public static void saveInt(Player player, String key, int value) {
        CompoundTag nbt;

        if (player.getPersistentData().contains(Reference.MOD_ID)) {
            nbt = player.getPersistentData().getCompound(Reference.MOD_ID);
            nbt.putInt(key, value);
        } else {
            nbt = new CompoundTag();
            nbt.putInt(key, value);
            player.getPersistentData().put(Reference.MOD_ID, nbt);
        }
    }

    public static int loadInt(Player player, String key) {
        if (player.getPersistentData().contains(Reference.MOD_ID)) {
            CompoundTag nbt = player.getPersistentData().getCompound(Reference.MOD_ID);
            return nbt.getInt(key);
        }

        return 0;
    }
}
