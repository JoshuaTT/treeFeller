package me.joshuatoye.treeFeller;

import org.bukkit.plugin.java.JavaPlugin;

public final class TreeFeller extends JavaPlugin {



    @Override
    public void onEnable() {
        // Plugin startup logic
       System.out.println("Starting...");
       getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Ending...");
    }
}
