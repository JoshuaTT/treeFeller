package me.joshuatoye.treeFeller;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.server.BroadcastMessageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.EnumSet;
import java.util.Set;
import java.util.Stack;

public class BlockBreakListener implements Listener {

    private static final int GOLDEN_AXE_DURABILITY = 32;

    private static final Set<Material> LOGS = EnumSet.of(
           Material.BIRCH_LOG, Material.ACACIA_LOG, Material.DARK_OAK_LOG,
           Material.JUNGLE_LOG, Material.OAK_LOG, Material.SPRUCE_LOG
    );

    private Stack<Block> breakingTree = new Stack<Block>();

    private int breakTree(Block breakingBlock, int damageDone)
    {
        breakingTree.push(breakingBlock);
        Block tempBlock = breakingBlock.getRelative(1,0,0);

        for(int i = -1; i < 2; i++)
            for(int j = -1; j < 2; j++)
                for(int k = -1; k < 2; k++) {
                    tempBlock = breakingBlock.getRelative(i, j, k);
                    if (LOGS.contains(tempBlock.getType()) && breakingTree.search(tempBlock) == -1) {
                        damageDone += breakTree(tempBlock, damageDone);
                    }
                }

        breakingTree.pop();
        breakingBlock.breakNaturally();
        damageDone++;
        return damageDone;
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
            int damage = 0;
            damage = breakTree(block, damage);

            ItemMeta axeMeta = mainHand.getItemMeta();
            int damageDoneAlready = ((Damageable)axeMeta).getDamage();
            System.out.println(damage + damageDoneAlready);
            ((Damageable)axeMeta).setDamage(damageDoneAlready + damage);
            mainHand.setItemMeta(axeMeta);

            if(((Damageable) mainHand.getItemMeta()).getDamage() >= GOLDEN_AXE_DURABILITY)
            {
                mainHand.setAmount(0);
            }
        }
    }


}
