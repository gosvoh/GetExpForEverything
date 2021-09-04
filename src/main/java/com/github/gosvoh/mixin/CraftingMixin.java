package com.github.gosvoh.mixin;

import com.github.gosvoh.GetExpForEverything;
import com.github.gosvoh.Reference;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class CraftingMixin {
    @Inject(at = @At("HEAD"), method = "onCraft")
    public void onCraft(World world, PlayerEntity player, int amount, CallbackInfo ci) {
        if (world.isClient()) return;

        Reference.countOfCraftedItems++;
        int levelStep = 5;
        if (Reference.countOfCraftedItems == 2) {
            if (player.experienceLevel < 5) player.addExperience(5);
            else player.addExperience(player.experienceLevel / levelStep * 5);
            Reference.countOfCraftedItems = 0;
        }

        GetExpForEverything.LOGGER.info("Reference.countOfCraftedItems " + Reference.countOfCraftedItems);
    }


}
