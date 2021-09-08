package com.github.gosvoh.mixin;

import com.github.gosvoh.GetExpForEverything;
import com.github.gosvoh.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(Block.class) public class BlockBreakMixin {
    @Inject(at = @At("TAIL"), method = "onBreak")
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci) {
        if (world.isClient()) return;
        Block target = state.getBlock();

        Collection<Identifier> coll = BlockTags.getTagGroup().getTagsFor(target);
        boolean isFoundSomething = GetExpForEverything.config.getBlacklistedBlocks().contains(target);
        for (Identifier identifier : GetExpForEverything.config.getBlacklistedBlockTags()) {
            if (coll.contains(identifier)) {
                isFoundSomething = true;
                break;
            }
        }

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

        // TODO Saving data to NBT
        //SavedInfo.saveInt(player, "countOfBrokenBlocks", Reference.countOfBrokenBlocks);
    }
}
