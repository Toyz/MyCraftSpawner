package com.toyz.mcs;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import net.minecraft.util.org.apache.commons.lang3.text.WordUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.toyz.mcs.BaseCommands.BaseCommand;
import com.toyz.mcs.Blocks.LoadBlocks;
import com.toyz.mcs.Blocks.SpawnBlock;
import com.toyz.mcs.events.BlockPlace;
import com.toyz.mcs.utils.ItemBuilder;
import com.toyz.mcs.utils.MetricsLite;

public class mcs extends JavaPlugin {
	public Logger log = this.getLogger();
	public static mcs _plugin = null;
	public static HashMap<String, SpawnBlock> sBlocks = null;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		try {
			MetricsLite metrics = new MetricsLite(this);
			metrics.start();
		} catch (IOException e) {
			// Failed to submit the stats :-(
		}
		_plugin = this;
		sBlocks = LoadBlocks.getBlocks();
		for (Entry<String, SpawnBlock> sp : sBlocks.entrySet()) {
			ItemStack is = ItemBuilder.CreateItem(
					Material.MOB_SPAWNER.toString(),
					WordUtils.capitalize(sp.getValue().getMonster().name()
							.toLowerCase().replace("_", " ")
							+ " Spawner"),
					Arrays.asList(
							"&1A "
									+ WordUtils.capitalize(sp.getValue()
											.getMonster().name().toLowerCase()
											.replace("_", " ")
											+ " Spawner"), "&8Spawn:"
									+ sp.getValue().getMonster().name()), 1,
					true);
			ShapedRecipe recipe = new ShapedRecipe(is);

			String[] strarray = new String[sp.getValue().getRecipe().size()];
			sp.getValue().getRecipe().toArray(strarray);
			recipe.shape(strarray);
			for (Entry<String, Material> craft : sp.getValue()
					.getCraftingItems().entrySet()) {
				recipe.setIngredient(craft.getKey().charAt(0), craft.getValue());
			}
			getServer().addRecipe(recipe);

			this.getServer()
					.getConsoleSender()
					.sendMessage(
							"[" + ChatColor.BLUE + "MyCraftSpawner"
									+ ChatColor.WHITE + "] Loaded: "
									+ ChatColor.GREEN
									+ sp.getValue().getMonster().name()
									+ ChatColor.WHITE + " Spawner");
		}
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new BlockPlace(), this);

		getCommand("mcs").setExecutor(new BaseCommand());
	}

	public void onDisable() {
		getServer().resetRecipes();
		this._plugin = null;
		this.log = null;
		this.sBlocks = null;
	}

	public void reload() {
		_plugin = this;
		sBlocks = LoadBlocks.getBlocks();
		for (Entry<String, SpawnBlock> sp : sBlocks.entrySet()) {
			ItemStack is = ItemBuilder.CreateItem(
					Material.MOB_SPAWNER.toString(),
					WordUtils.capitalize(sp.getValue().getMonster().name()
							.toLowerCase().replace("_", " ")
							+ " Spawner"),
					Arrays.asList(
							"&1A "
									+ WordUtils.capitalize(sp.getValue()
											.getMonster().name().toLowerCase()
											.replace("_", " ")
											+ " Spawner"), "&8Spawn:"
									+ sp.getValue().getMonster().name()), 1,
					true);
			ShapedRecipe recipe = new ShapedRecipe(is);

			String[] strarray = new String[sp.getValue().getRecipe().size()];
			sp.getValue().getRecipe().toArray(strarray);
			recipe.shape(strarray);
			for (Entry<String, Material> craft : sp.getValue()
					.getCraftingItems().entrySet()) {
				recipe.setIngredient(craft.getKey().charAt(0), craft.getValue());
			}
			getServer().addRecipe(recipe);

			this.getServer()
					.getConsoleSender()
					.sendMessage(
							"[" + ChatColor.BLUE + "MyCraftSpawner"
									+ ChatColor.WHITE + "] Loaded: "
									+ ChatColor.GREEN
									+ sp.getValue().getMonster().name()
									+ ChatColor.WHITE + " Spawner");
		}
	}
}
