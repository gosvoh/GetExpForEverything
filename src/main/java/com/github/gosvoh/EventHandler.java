package com.github.gosvoh;

import net.minecraft.block.Block;
import net.minecraft.block.BushBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
public class EventHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        Block target = event.getState().getBlock();

        boolean condition = !(target instanceof LeavesBlock) &&
                            !(target instanceof BushBlock);

        LOGGER.debug(condition);

        if (!event.isCanceled())
            Constants.countOfBreakBlocks++;

        if (Constants.countOfBreakBlocks == ConfigHandler.CLIENT.blocksNeedToDestroy.get()) {

            event.setExpToDrop(ConfigHandler.CLIENT.baseExpToGain.get() *
                               ConfigHandler.CLIENT.multiplierForLevelStep.get() *
                               (player.experienceLevel / ConfigHandler.CLIENT.levelStep.get() + 1));

            Constants.countOfBreakBlocks = 0;

        }
    }
}
