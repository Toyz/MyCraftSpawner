package com.toyz.mcs.Blocks;

import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import com.toyz.mcs.mcs;

public class LoadBlocks {
	public static HashMap<String, SpawnBlock> getBlocks(){
		HashMap<String, SpawnBlock> map = new HashMap<String, SpawnBlock>();
		
		ConfigurationSection cs = mcs._plugin.getConfig().getConfigurationSection("spawners");
		
		for(String key : cs.getKeys(false)){
			ConfigurationSection enity = cs.getConfigurationSection(key);
			
			map.put(EntityType.fromName(key.toUpperCase()).name().toString(), new SpawnBlock(key, EntityType.fromName(key.toUpperCase()), enity.getStringList("recipe"), enity.getStringList("items")));
		}
		
		return map;
	}
}
