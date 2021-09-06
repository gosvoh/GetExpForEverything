package com.github.gosvoh.mixin;

import com.github.gosvoh.GetExpForEverything;
import com.github.gosvoh.Reference;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
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
        /*for (Tag<Item> itemTag : GetExpForEverything.config.getBlacklistedItemTags())
            if (itemTag.contains(target)) isFoundSomething = true;*/

        Collection<Identifier> coll = ItemTags.getTagGroup().getTagsFor(target);

        //TODO getBlacklistedItemTags returns null
        GetExpForEverything.config.getBlacklistedItemTags().forEach(
                itemTag -> itemTag.values().forEach(item -> GetExpForEverything.LOGGER.info(Registry.ITEM.getId(item).toString())));

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

        //SavedInfo.saveInt(player, "countOfCraftedItems", Reference.countOfCraftedItems);
    }


}
