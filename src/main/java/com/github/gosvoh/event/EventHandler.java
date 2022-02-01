package com.github.gosvoh.event;

import com.github.gosvoh.config.GetExpForEverythingConfig;
import com.github.gosvoh.utils.Reference;
import com.github.gosvoh.utils.SavedInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
        Reference.countOfBrokenBlocks = SavedInfo.loadInt(event.getPlayer(), "countOfBrokenBlocks");
        Reference.countOfCraftedItems = SavedInfo.loadInt(event.getPlayer(), "countOfCraftedItems");
        Reference.countOfPlacedBlocks = SavedInfo.loadInt(event.getPlayer(), "countOfPlacedBlocks");

        if (Reference.countOfBrokenBlocks >= GetExpForEverythingConfig.blocksNeedToDestroy) Reference.countOfBrokenBlocks = 0;
        if (Reference.countOfCraftedItems >= GetExpForEverythingConfig.itemsNeedToCraft) Reference.countOfCraftedItems = 0;
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Block target = event.getState().getBlock();

        if (event.getPlayer().level.isClientSide) return;

        //noinspection ConstantConditions
        boolean isFoundSomething =
                GetExpForEverythingConfig.blackListBlocks.contains(target.getRegistryName().toString());

        for (ResourceLocation resourceLocation : target.getTags())
            if (GetExpForEverythingConfig.blackListBlockTags.contains(resourceLocation.toString())) {
                isFoundSomething = true;
                break;
            }

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

        SavedInfo.saveInt(player, "countOfBrokenBlocks", Reference.countOfBrokenBlocks);
    }

    @SubscribeEvent
    public static void onCraft(PlayerEvent.ItemCraftedEvent event) {
        Player player = event.getPlayer();
        Item target = event.getCrafting().getItem();

        if (event.getPlayer().level.isClientSide) return;

        //noinspection ConstantConditions
        boolean isFoundSomething =
                GetExpForEverythingConfig.blackListCraftedItems.contains(target.getRegistryName().toString());

        for (ResourceLocation resourceLocation : target.getTags())
            if (GetExpForEverythingConfig.blackListItemTags.contains(resourceLocation.toString())) {
                isFoundSomething = true;
                break;
            }

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

        SavedInfo.saveInt(player, "countOfCraftedItems", Reference.countOfCraftedItems);
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (!GetExpForEverythingConfig.isPlacingEnabled) return;
        if (event.getEntity() instanceof Player player) {
            if (player.level.isClientSide) return;
            Block target = event.getPlacedBlock().getBlock();

            //noinspection ConstantConditions
            boolean isFoundSomething =
                    GetExpForEverythingConfig.blackListBlocksPlace.contains(target.getRegistryName().toString());

            for (ResourceLocation resourceLocation : target.getTags())
                if (GetExpForEverythingConfig.blackListBlockTagsPlace.contains(resourceLocation.toString())) {
                    isFoundSomething = true;
                    break;
                }

            if ((GetExpForEverythingConfig.isPlacingWhitelistMode && !isFoundSomething) ||
                (!GetExpForEverythingConfig.isPlacingWhitelistMode && isFoundSomething)) return;

            Reference.countOfPlacedBlocks++;

            if (Reference.countOfPlacedBlocks == GetExpForEverythingConfig.blocksNeedToPlace) {

                if (player.experienceLevel < GetExpForEverythingConfig.levelStep)
                    player.giveExperiencePoints(GetExpForEverythingConfig.baseExpToGain);
                else
                    player.giveExperiencePoints(GetExpForEverythingConfig.baseExpToGain *
                                                (player.experienceLevel / GetExpForEverythingConfig.levelStep *
                                                 GetExpForEverythingConfig.multiplierForLevelStep));

                Reference.countOfPlacedBlocks = 0;
            }

            SavedInfo.saveInt(player, "countOfPlacedBlocks", Reference.countOfPlacedBlocks);
        }
    }
}
