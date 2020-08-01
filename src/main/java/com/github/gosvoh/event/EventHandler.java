package com.github.gosvoh.event;

import com.github.gosvoh.GetExpForEverything;
import com.github.gosvoh.config.GetExpForEverythingConfig;
import com.github.gosvoh.utils.Reference;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
public class EventHandler {
    private static final Logger LOGGER = GetExpForEverything.LOGGER;

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        Block target = event.getState().getBlock();

        if (player.getEntityWorld().isRemote()) return;

        boolean isFoundSomething = false;

        //noinspection ConstantConditions
        if (GetExpForEverythingConfig.blackListBlocks.contains(target.getRegistryName().toString()))
            isFoundSomething = true;
        for (ResourceLocation resourceLocation : target.getTags())
            if (GetExpForEverythingConfig.blackListBlockTags.contains(resourceLocation.toString())) isFoundSomething = true;

            if ((GetExpForEverythingConfig.isBlockWhitelistMode && !isFoundSomething) ||
                (!GetExpForEverythingConfig.isBlockWhitelistMode && isFoundSomething)) return;

        Reference.countOfBrokenBlocks++;

        if (Reference.countOfBrokenBlocks == GetExpForEverythingConfig.blocksNeedToDestroy) {

            if (player.experienceLevel < GetExpForEverythingConfig.levelStep)
                event.setExpToDrop(GetExpForEverythingConfig.baseExpToGain);
            else
                event.setExpToDrop(GetExpForEverythingConfig.baseExpToGain *
                                   (player.experienceLevel / GetExpForEverythingConfig.levelStep * GetExpForEverythingConfig.multiplierForLevelStep));

            Reference.countOfBrokenBlocks = 0;

        }
    }

    @SubscribeEvent
    public static void onCraft(PlayerEvent.ItemCraftedEvent event) {
        PlayerEntity player = event.getPlayer();
        Item target = event.getCrafting().getItem();

        if (player.getEntityWorld().isRemote) return;

        boolean isFoundSomething = false;

        //noinspection ConstantConditions
        if (GetExpForEverythingConfig.blackListCraftedItems.contains(target.getRegistryName().toString()))
            isFoundSomething = true;
        for (ResourceLocation resourceLocation : target.getTags())
            if (GetExpForEverythingConfig.blackListItemTags.contains(resourceLocation.toString())) isFoundSomething = true;

        if ((GetExpForEverythingConfig.isItemWhitelistMode && !isFoundSomething) ||
            (!GetExpForEverythingConfig.isItemWhitelistMode && isFoundSomething)) return;

        Reference.countOfCraftedItems++;

        if (Reference.countOfCraftedItems == GetExpForEverythingConfig.itemsNeedToCraft) {

            if (player.experienceLevel < GetExpForEverythingConfig.levelStep)
                player.giveExperiencePoints(GetExpForEverythingConfig.baseExpToGain);
            else
                player.giveExperiencePoints(GetExpForEverythingConfig.baseExpToGain *
                                            (player.experienceLevel / GetExpForEverythingConfig.levelStep *
                                             GetExpForEverythingConfig.multiplierForLevelStep));

            Reference.countOfCraftedItems = 0;
        }
    }
}
