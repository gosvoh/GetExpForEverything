package com.github.gosvoh.event;

import com.github.gosvoh.GetExpForEverything;
import com.github.gosvoh.config.GetExpForEverythingConfig;
import com.github.gosvoh.utils.Reference;
import com.github.gosvoh.utils.SavedInfo;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventHandler {

    private static final Logger LOGGER = GetExpForEverything.LOGGER;

    @SubscribeEvent
    public static void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
        Reference.countOfBrokenBlocks = SavedInfo.loadInt(event.player, "countOfBrokenBlocks");
        Reference.countOfCraftedItems = SavedInfo.loadInt(event.player, "countOfCraftedItems");

        if (Reference.countOfBrokenBlocks >= GetExpForEverythingConfig.blocksNeedToDestroy) Reference.countOfBrokenBlocks = 0;
        if (Reference.countOfCraftedItems >= GetExpForEverythingConfig.itemsNeedToCraft) Reference.countOfCraftedItems = 0;
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        Block target = event.getState().getBlock();

        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            return;

        boolean isFoundSomething = false;

        for (String s : GetExpForEverythingConfig.blackListBlocks)
            if (s.equals(target.getRegistryName().toString())) isFoundSomething = true;

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
        EntityPlayer player = event.player;
        Item target = event.crafting.getItem();

        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            return;

        boolean isFoundSomething = false;

        for (String s : GetExpForEverythingConfig.blackListCraftedItems)
            if (s.equals(target.getRegistryName().toString())) isFoundSomething = true;

        if ((GetExpForEverythingConfig.isItemWhitelistMode && !isFoundSomething) ||
            (!GetExpForEverythingConfig.isItemWhitelistMode && isFoundSomething)) return;

        Reference.countOfCraftedItems++;

        if (Reference.countOfCraftedItems == GetExpForEverythingConfig.itemsNeedToCraft) {

            if (player.experienceLevel < GetExpForEverythingConfig.levelStep)
                player.addExperience(GetExpForEverythingConfig.baseExpToGain);
            else
                player.addExperience(GetExpForEverythingConfig.baseExpToGain *
                                     (player.experienceLevel / GetExpForEverythingConfig.levelStep *
                                      GetExpForEverythingConfig.multiplierForLevelStep));

            Reference.countOfCraftedItems = 0;
        }

        SavedInfo.saveInt(player, "countOfCraftedItems", Reference.countOfCraftedItems);
    }
}
