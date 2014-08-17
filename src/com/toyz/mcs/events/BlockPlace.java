package com.toyz.mcs.events;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.toyz.mcs.mcs;

public class BlockPlace implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();

		if (p.getItemInHand() != null) {
			Block block = e.getBlock();

			if (CheckIfCrafted(p)) {
				EntityType monster = this.GetMonster(this.ItemKeyName(p));
				if (block.getType() == Material.MOB_SPAWNER) {
					CreatureSpawner cs = (CreatureSpawner) block.getState();
					cs.setSpawnedType(monster);
					cs.update();
				}
			}
		}
	}

	private Boolean CheckIfCrafted(Player player) {
		ItemStack i = player.getItemInHand();
		if (i == null)
			return false;
		if (!i.hasItemMeta())
			return false;

		// Meta stuff
		ItemMeta im = i.getItemMeta();
		if (!im.hasDisplayName())
			return false;
		String dn = im.getDisplayName();
		List<String> lore = im.getLore();

		if (lore.size() == 2) {
			String checking = ChatColor.stripColor(lore.get(1).toLowerCase());
			System.out.println(checking);
			if (checking.startsWith("spawn:")) {
				return true;
			}
		}
		return false;
	}

	private String ItemKeyName(Player player) {
		ItemStack i = player.getItemInHand();
		ItemMeta im = i.getItemMeta();
		String dn = im.getDisplayName();
		List<String> lore = im.getLore();

		if (lore.size() == 2) {
			String checking = lore.get(1).toLowerCase();
			return checking.split(":")[1];
		}
		
		return "";
	}

	private EntityType GetMonster(String key) {
		return EntityType.fromName(key);
	}
}
