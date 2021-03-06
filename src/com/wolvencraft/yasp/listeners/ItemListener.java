/*
 * ItemListener.java
 * 
 * Statistics
 * Copyright (C) 2013 bitWolfy <http://www.wolvencraft.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

package com.wolvencraft.yasp.listeners;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

import com.wolvencraft.yasp.Statistics;
import com.wolvencraft.yasp.db.tables.Normal.MiscInfoPlayersTable;
import com.wolvencraft.yasp.session.OnlineSession;
import com.wolvencraft.yasp.settings.Constants.StatPerms;
import com.wolvencraft.yasp.util.cache.OnlineSessionCache;

/**
 * Listens to any item changes on the server and reports them to the plugin.
 * @author bitWolfy
 *
 */
public class ItemListener implements Listener {
    
    /**
     * <b>Default constructor</b><br />
     * Creates a new instance of the Listener and registers it with the PluginManager
     * @param plugin StatsPlugin instance
     */
    public ItemListener(Statistics plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onItemPickup(PlayerPickupItemEvent event) {
        if(Statistics.getPaused()) return;
        Player player = event.getPlayer();
        if(!StatPerms.ItemPickUp.has(player)) return;
        OnlineSessionCache.fetch(player).itemPickUp(player.getLocation(), event.getItem().getItemStack());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onItemDrop(PlayerDropItemEvent event) {
        if(Statistics.getPaused()) return;
        Player player = event.getPlayer();
        if(!StatPerms.ItemDrop.has(player)) return;
        OnlineSessionCache.fetch(player).itemDrop(player.getLocation(), event.getItemDrop().getItemStack());
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFoodConsume(FoodLevelChangeEvent event) {
        if(Statistics.getPaused()) return;
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if(!StatPerms.ItemUse.has(player)) return;
        OnlineSession session = OnlineSessionCache.fetch(player);
        session.itemUse(player.getLocation(), player.getItemInHand());
        session.addMiscValue(MiscInfoPlayersTable.FoodEaten);
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onItemCraft(CraftItemEvent event) {
        if(Statistics.getPaused()) return;
        Player player = (Player) event.getWhoClicked();
        if(!StatPerms.ItemCraft.has(player)) return;
        OnlineSessionCache.fetch(player).itemCraft(player.getLocation(), event.getCurrentItem());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onItemSmelt(FurnaceExtractEvent event) {
        if(Statistics.getPaused()) return;
        Player player = event.getPlayer();
        if(!StatPerms.ItemMisc.has(player)) return;
        OnlineSessionCache.fetch(player).itemSmelt(player.getLocation(), new ItemStack(event.getItemType()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onToolBreak(PlayerItemBreakEvent event) {
        if(Statistics.getPaused()) return;
        Player player = event.getPlayer();
        if(!StatPerms.ItemBreak.has(player)) return;
        OnlineSessionCache.fetch(player).itemBreak(player.getLocation(), event.getBrokenItem());
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onItemEnchant(EnchantItemEvent event) {
        if(Statistics.getPaused()) return;
        Player player = event.getEnchanter();
        if(!StatPerms.ItemMisc.has(player)) return;
        OnlineSessionCache.fetch(player).itemEnchant(player.getLocation(), new ItemStack(event.getItem().getType()));
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public static void onItemRepair(InventoryClickEvent event){
        HumanEntity entity = event.getWhoClicked();
        
        if(entity instanceof Player) return;
        Player player = (Player) entity;
        if(!StatPerms.ItemAnvil.has(player)) return;
        
        if(!(event.getInventory() instanceof AnvilInventory)) return;
        AnvilInventory anvil = (AnvilInventory) event.getInventory();
        InventoryView view = event.getView();
        int rawSlot = event.getRawSlot();
         
        if(rawSlot != view.convertSlot(rawSlot)) return;
        if(rawSlot != 2) return;
        ItemStack[] items = anvil.getContents();
         
        if(items[0] == null || items[1] == null) return;
        int leftSlot = items[0].getTypeId();
        int rightSlot = items[1].getTypeId();
        if(leftSlot == 0 || leftSlot != rightSlot) return;
        
        ItemStack resultSlot = event.getCurrentItem();
        if(resultSlot == null) return;
            
        ItemMeta meta = resultSlot.getItemMeta();
         
        if(meta == null) return;
        if(!(meta instanceof Repairable)) return;
        Repairable repairable = (Repairable) meta;
        int repairCost = repairable.getRepairCost();
        if(player.getLevel() < repairCost) return;

        OnlineSessionCache.fetch(player).itemEnchant(player.getLocation(), resultSlot);
        com.wolvencraft.yasp.util.Message.log("Item has been repaired for " + repairable.getRepairCost());
    }
    
}
