package com.github.gosvoh.mixin;

import com.github.gosvoh.GetExpForEverything;
import com.github.gosvoh.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class) public class BlockBreakMixin {
    @Inject(at = @At("TAIL"), method = "onBreak")
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci) {
        if (world.isClient()) return;
        Block target = state.getBlock();

        boolean isFoundSomething = GetExpForEverything.config.getBlacklistedBlocks().contains(target);
        for (Tag<Block> blockTag : GetExpForEverything.config.getBlacklistedBlockTags()) {
            if (blockTag.contains(target)) isFoundSomething = true;
            GetExpForEverything.LOGGER.info(blockTag.values());
        }

        GetExpForEverything.LOGGER.info(GetExpForEverything.config.getBlacklistedBlockTags());

        if ((GetExpForEverything.config.is_block_whitelist_mode && !isFoundSomething) ||
            (!GetExpForEverything.config.is_block_whitelist_mode && isFoundSomething)) return;

        Reference.countOfBrokenBlocks++;

        if (Reference.countOfBrokenBlocks == GetExpForEverything.config.blocks_need_to_destroy) {
            ExperienceOrbEntity exp;
            if (player.experienceLevel < GetExpForEverything.config.level_step) exp =
                    new ExperienceOrbEntity(world, pos.getX(), pos.getY(), pos.getZ(),
                            GetExpForEverything.config.base_experience);
            else exp = new ExperienceOrbEntity(world, pos.getX(), pos.getY(), pos.getZ(),
                    GetExpForEverything.config.base_experience *
                    (player.experienceLevel / GetExpForEverything.config.level_step *
                     GetExpForEverything.config.multiplier_for_level_step));
            world.spawnEntity(exp);
            Reference.countOfBrokenBlocks = 0;
        }

        //SavedInfo.saveInt(player, "countOfBrokenBlocks", Reference.countOfBrokenBlocks);
    }
}
