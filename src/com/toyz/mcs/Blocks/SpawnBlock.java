package com.toyz.mcs.Blocks;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class SpawnBlock {
	private ItemStack Block;
	private String name;
	private EntityType monster;
	private List<String> recipe;
	private List<String> blocks;
	
	public SpawnBlock(String name, EntityType monster, List<String> recipe, List<String> blocks){
		this.Block = new ItemStack(Material.MOB_SPAWNER);
		this.name = name;
		this.monster = monster;
		this.recipe = recipe;
		this.blocks = blocks;
	}
	
	public EntityType getMonster(){
		return this.monster;
	}
	
	public ItemStack getBlock(){
		return this.Block;
	}
	
	public List<String> getRecipe(){
		return this.recipe;
	}
	
	public List<String> getBlocks(){
		return this.blocks;
	}
	
	public HashMap<String, Material> getCraftingItems(){
		HashMap<String, Material> map = new HashMap<String, Material>();
		
		for(String row : this.getBlocks()){
			String[] f = row.split("=");
			Material m = Material.getMaterial(f[1].toUpperCase());
			map.put(f[0], m);
		}
		
		return map;
	}
}
