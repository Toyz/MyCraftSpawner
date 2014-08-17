package com.toyz.mcs.BaseCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.toyz.mcs.BaseCommands.Commands.mcscmd;
import com.toyz.mcs.BaseCommands.Handler.IssueCommands;

public class BaseCommand implements CommandExecutor {
	static IssueCommands Info;
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Info = new IssueCommands(sender, cmd.getName(), args, cmd);
		
		if(cmd.getName().equalsIgnoreCase("mcs")){
			mcscmd.Fire(Info);
		}
		return false;
	}
	
	protected static void sendMessage(String Message){
		if(Info.isPlayer()){
			Info.getPlayer().sendMessage("[" + ChatColor.BLUE + "MyCraftSpawner" +ChatColor.WHITE + "] " + Message);
		}
		if(Info.isConsole()){
			Info.getConsole().sendMessage("[" + ChatColor.BLUE + "MyCraftSpawner" +ChatColor.WHITE + "] " + Message);
		}
	}
}
