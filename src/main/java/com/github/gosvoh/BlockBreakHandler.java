package com.github.gosvoh;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBreakHandler implements PlayerBlockBreakEvents.After {
    @Override
    public void afterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (world.isClient()) return;

        Reference.countOfBrokenBlocks++;
        ExperienceOrbEntity exp;
        int levelStep = 5;
        if (Reference.countOfBrokenBlocks == 2) {
            if (player.experienceLevel < 5) exp = new ExperienceOrbEntity(world, pos.getX(), pos.getY(), pos.getZ(), 5);
            else exp = new ExperienceOrbEntity(world, pos.getX(), pos.getY(), pos.getZ(), player.experienceLevel / levelStep * 5);
            Reference.countOfBrokenBlocks = 0;

            NbtCompound nbt;
            world.spawnEntity(exp);
            GetExpForEverything.LOGGER.info("Spawned " + exp.getExperienceAmount() + " exp");
        }


        GetExpForEverything.LOGGER.info("Reference.countOfBrokenBlocks " + Reference.countOfBrokenBlocks);
    }
}

