package com.github.gosvoh.utils;

import com.github.gosvoh.GetExpForEverything;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("ConstantConditions")
public class SavedInfo {

    private static final Logger LOGGER = GetExpForEverything.LOGGER;

    public static void saveInt(EntityPlayer player, String key, int value) {
        if (player.getEntityData().getCompoundTag(Reference.MOD_ID) != null) {
            NBTTagCompound nbt = player.getEntityData().getCompoundTag(Reference.MOD_ID);
            nbt.setInteger(key, value);
        } else {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger(key, value);
            player.getEntityData().setTag(Reference.MOD_ID, nbt);
        }
    }

    public static int loadInt(EntityPlayer player, String key) {
        if (player.getEntityData().getCompoundTag(Reference.MOD_ID) != null) {
            NBTTagCompound nbt = player.getEntityData().getCompoundTag(Reference.MOD_ID);
            return nbt.getInteger(key);
        }

        return 0;
    }
}
