package com.github.gosvoh.utils;

import com.github.gosvoh.GetExpForEverything;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("ConstantConditions")
public class SavedInfo {

    private static final Logger LOGGER = GetExpForEverything.LOGGER;

    public static void saveInt(PlayerEntity player, String key, int value) {
        CompoundNBT nbt;

        if (player.getPersistentData().contains(Reference.MOD_ID)) {
            nbt = (CompoundNBT) player.getPersistentData().get(Reference.MOD_ID);
            nbt.putInt(key, value);
        } else {
            nbt = new CompoundNBT();
            nbt.putInt(key, value);
            player.getPersistentData().put(Reference.MOD_ID, nbt);
        }
    }

    public static int loadInt(PlayerEntity player, String key) {
        if (player.getPersistentData().contains(Reference.MOD_ID)) {
            CompoundNBT nbt = (CompoundNBT) player.getPersistentData().get(Reference.MOD_ID);
            return nbt.getInt(key);
        }

        return 0;
    }
}
