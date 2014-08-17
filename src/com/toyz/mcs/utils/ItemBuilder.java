package com.toyz.mcs.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
	public static ItemStack CreateItem(String id, String name, List<String> lore, int amount, boolean glow){
		ItemStack is = new ItemStack(Material.getMaterial(id));
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		
		if(amount > 0){
			is.setAmount(amount);
		}
		
		if(lore != null){
			List<String> Lores = new ArrayList<String>();
			for(String l : lore){
				Lores.add(ChatColor.translateAlternateColorCodes('&', l));
			}
			im.setLore(Lores);
		}
		is.setItemMeta(im);
		
		if(glow)
			NMSUtils.addGlow(is); 
		
		//System.out.println(is);
		return is;
	}
}
