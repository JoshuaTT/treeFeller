package me.joshuatoye.treeFeller;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;
import java.util.Set;
import java.util.Stack;

public class BlockBreakListener implements Listener {

    private static final Set<Material> LOGS = EnumSet.of(
           Material.BIRCH_LOG, Material.ACACIA_LOG, Material.DARK_OAK_LOG,
           Material.JUNGLE_LOG, Material.OAK_LOG, Material.SPRUCE_LOG
    );

    private Stack<Block> breakingTree = new Stack<Block>();

    private void breakTree(Block breakingBlock)
    {
        breakingTree.push(breakingBlock);
        Block tempBlock = breakingBlock.getRelative(1,0,0);

        for(int i = -1; i < 2; i++)
            for(int j = -1; j < 2; j++)
                for(int k = -1; k < 2; k++)
                {
                    tempBlock = breakingBlock.getRelative(i, j, k);
                    if(LOGS.contains(tempBlock.getType()) && breakingTree.search(tempBlock) == -1)
                    {
                        breakTree(tempBlock);
                    }
                }

        breakingTree.pop();
        breakingBlock.breakNaturally();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();

        //MainHand mainHand = event.getPlayer().getMainHand();
        Block block = event.getBlock();
        //mainHand.

        if(LOGS.contains(block.getType()) && mainHand.getType() == Material.GOLDEN_AXE)
        {
            breakTree(block);
        }
    }


}
