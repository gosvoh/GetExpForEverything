package com.github.gosvoh.mixin;

import com.github.gosvoh.GetExpForEverything;
import com.github.gosvoh.Reference;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class) public abstract class PlayerLoggedInMixin {
    @Inject(at = @At("TAIL"), method = "onPlayerConnected")
    public void onPlayerConnected(ServerPlayerEntity player, CallbackInfo ci) {
        NbtCompound nbt = new NbtCompound();
        player.writeCustomDataToNbt(nbt);
        if (nbt.contains("countOfBrokenBlocks"))
            Reference.countOfBrokenBlocks = nbt.getInt("countOfBrokenBlocks");
        if (nbt.contains("countOfCraftedItems"))
            Reference.countOfCraftedItems = nbt.getInt("countOfCraftedItems");
        if (Reference.countOfBrokenBlocks >= GetExpForEverything.config.blocks_need_to_destroy) Reference.countOfBrokenBlocks = 0;
        if (Reference.countOfCraftedItems >= GetExpForEverything.config.items_to_craft) Reference.countOfCraftedItems = 0;
    }

    /*@Inject(at = @At("TAIL"), method = "writeCustomDataToNbt")
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("countOfBrokenBlocks", Reference.countOfCraftedItems);
        nbt.putInt("countOfCraftedItems", Reference.countOfCraftedItems);
    }*/
}
