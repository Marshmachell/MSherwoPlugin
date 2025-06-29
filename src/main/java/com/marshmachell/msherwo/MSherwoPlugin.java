package com.marshmachell.msherwo;

import com.marshmachell.msherwo.event.ArmorStandInteractEvent;
import com.marshmachell.msherwo.event.InventoryClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MSherwoPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new InventoryClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ArmorStandInteractEvent(), this);
    }

    @Override
    public void onDisable() {
    }
}
