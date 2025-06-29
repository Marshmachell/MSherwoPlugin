package com.marshmachell.msherwo.event;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ArmorStandInteractEvent implements Listener {
    private final List<EquipmentSlot> equipmentSlots = List.of(EquipmentSlot.values());
    private final List<ArmorStand.LockType> lockTypes = List.of(ArmorStand.LockType.values());
    private final List<Material> mozhno = List.of(Material.HONEYCOMB, Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLDEN_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE);

    @EventHandler
    public void onClick(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked() instanceof ArmorStand stand) {
            Player player = e.getPlayer();
            ItemStack handItem = player.getInventory().getItemInMainHand();
            if (handItem.getType().isAir() | !(mozhno.contains(handItem.getType()))) {return;}
            World world = stand.getWorld();

            switch (handItem.getType()) {
                case HONEYCOMB -> {if (!isLocked(stand)) {
                    world.spawnParticle(Particle.WAX_ON, stand.getLocation(), 45, 0.25, 1, 0.25, 0.001);
                    world.playSound(stand.getLocation(), Sound.ITEM_HONEYCOMB_WAX_ON, 1.0f, 1.0f);
                    player.swingMainHand();
                    equipmentLock(stand);
                    decrement(player, handItem);
                }}
                default -> {if (isLocked(stand)) {
                    world.spawnParticle(Particle.WAX_OFF, stand.getLocation(), 45, 0.25, 1, 0.25, 0.001);
                    world.playSound(stand.getLocation(), Sound.ITEM_AXE_WAX_OFF, 1.0f, 1.0f);
                    player.swingMainHand();
                    removeEquipmentLock(stand);
                }}
            }
        }
    }

    private void decrement(Player player, ItemStack item) {
        if (!List.of(GameMode.ADVENTURE, GameMode.SURVIVAL).contains(player.getGameMode())) {return;}
        ItemStack cItem = item.clone();
        cItem.setAmount(1);
        player.getInventory().removeItem(cItem);
    }

    private boolean isLocked(ArmorStand stand) {
        for (EquipmentSlot slot : equipmentSlots) {
            for (ArmorStand.LockType lockType: lockTypes) {
                if (!stand.hasEquipmentLock(slot, lockType)) {
                    return false;
                }
            }
        }
        return true;
    }
    private void equipmentLock(ArmorStand stand) {
        for (EquipmentSlot slot : equipmentSlots) {
            for (ArmorStand.LockType lockType : ArmorStand.LockType.values()) {
                stand.addEquipmentLock(slot, lockType);
            }
        }
    }
    private void removeEquipmentLock(ArmorStand stand) {
        for (EquipmentSlot slot : equipmentSlots) {
            for (ArmorStand.LockType lockType : ArmorStand.LockType.values()) {
                stand.removeEquipmentLock(slot, lockType);
            }
        }
    }
}

