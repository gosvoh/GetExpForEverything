package com.github.gosvoh.mixin;

import com.github.gosvoh.GetExpForEverything;
import com.github.gosvoh.Reference;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(ItemStack.class) public abstract class CraftingMixin {
    @Shadow public abstract Item getItem();

    @Inject(at = @At("TAIL"), method = "onCraft")
    public void onCraft(World world, PlayerEntity player, int amount, CallbackInfo ci) {
        if (world.isClient()) return;
        Item target = this.getItem();

        boolean isFoundSomething = GetExpForEverything.config.getBlacklistedItems().contains(target);

        Collection<Identifier> coll = ItemTags.getTagGroup().getTagsFor(target);
        for (Identifier identifier : GetExpForEverything.config.getBlacklistedItemTags()) {
            if (coll.contains(identifier)) {
                isFoundSomething = true;
                break;
            }
        }

        if ((GetExpForEverything.config.is_item_whitelist_mode && !isFoundSomething) ||
            (!GetExpForEverything.config.is_item_whitelist_mode && isFoundSomething)) return;

        Reference.countOfCraftedItems++;

        if (Reference.countOfCraftedItems == GetExpForEverything.config.items_to_craft) {

            if (player.experienceLevel < GetExpForEverything.config.level_step)
                player.addExperience(GetExpForEverything.config.base_experience);
            else player.addExperience(GetExpForEverything.config.base_experience *
                                      (player.experienceLevel / GetExpForEverything.config.level_step *
                                       GetExpForEverything.config.multiplier_for_level_step));

            Reference.countOfCraftedItems = 0;
        }

        ServerPlayerEntity serverPlayerEntity = world.getServer().getPlayerManager().getPlayer(player.getUuid());
        NbtCompound nbt = new NbtCompound();

        serverPlayerEntity.writeCustomDataToNbt(nbt);
        nbt.putInt("countOfCraftedItems", Reference.countOfCraftedItems);
        serverPlayerEntity.readCustomDataFromNbt(nbt);
    }


}
