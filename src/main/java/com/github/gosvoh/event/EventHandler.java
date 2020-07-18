package com.github.gosvoh.event;

import com.github.gosvoh.utils.Reference;
import com.github.gosvoh.config.GetExpForEverythingConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventHandler {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        Block target = event.getState().getBlock();

        for (String s : GetExpForEverythingConfig.blackListBlocks)
            //noinspection ConstantConditions
            if (s.equals(target.getRegistryName().toString())) return;

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
        EntityPlayer player = event.player;
        Item target = event.crafting.getItem();

        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            return;

        for (String s : GetExpForEverythingConfig.blackListCraftedItems)
            //noinspection ConstantConditions
            if (s.equals(target.getRegistryName().toString())) return;

        Reference.countOfCraftedItems++;

        if (Reference.countOfCraftedItems == GetExpForEverythingConfig.countOfCraftedItems) {

            if (player.experienceLevel < GetExpForEverythingConfig.levelStep)
                player.addExperience(GetExpForEverythingConfig.baseExpToGain);
            else
                player.addExperience(GetExpForEverythingConfig.baseExpToGain *
                                     (player.experienceLevel / GetExpForEverythingConfig.levelStep *
                                      GetExpForEverythingConfig.multiplierForLevelStep));

            Reference.countOfCraftedItems = 0;
        }
    }
}
