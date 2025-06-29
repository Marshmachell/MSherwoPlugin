package com.marshmachell.msherwo.event;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class InventoryClickEvent implements Listener {
    private final List<Material> HELMETS = List.of(
            Material.LEATHER_HELMET,
            Material.CHAINMAIL_HELMET,
            Material.IRON_HELMET,
            Material.GOLDEN_HELMET,
            Material.DIAMOND_HELMET,
            Material.NETHERITE_HELMET,
            Material.TURTLE_HELMET
    );

    @EventHandler
    public void onClick(org.bukkit.event.inventory.InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCursor();
        ItemStack hat = player.getInventory().getItem(EquipmentSlot.HEAD);
        if (item == null || hat == null) {return;}

        if (e.getRawSlot() != 5 || HELMETS.contains(item.getType())) {return;}
        InventoryAction action = e.getAction();
        if (action != InventoryAction.PLACE_ALL && action != InventoryAction.PLACE_ONE) {return;}

        if (hat.getType() != Material.AIR) {return;}

        ItemStack itemToEquip = item.clone();
        itemToEquip.setAmount(1);

        ItemStack cursorItem = item.clone();
        cursorItem.setAmount(cursorItem.getAmount() - 1);

        player.getInventory().setHelmet(itemToEquip);
        player.setItemOnCursor(cursorItem.getAmount() > 0 ? cursorItem : null);
        player.updateInventory();
        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1.0f, 1.0f);

        e.setCancelled(true);
    }
}
