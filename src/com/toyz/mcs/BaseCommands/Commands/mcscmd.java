package com.toyz.mcs.BaseCommands.Commands;

import com.toyz.mcs.mcs;
import com.toyz.mcs.BaseCommands.BaseCommand;
import com.toyz.mcs.BaseCommands.Handler.IssueCommands;

public class mcscmd extends BaseCommand{
	private static IssueCommands _cmd = null;
	private static String _Permission = "mcs.admin";
	private static int _minArgs = 0;
	private static String _invaidUsage = "Invalid Args - usage:";
	
	public static Boolean Fire(IssueCommands info){
		_cmd = info;
		Trigger();
		return true;
	}
	
	private static void Trigger(){
		if(_cmd.getArgs().length == 0){
			return;
		}
		if(_cmd.getArg(0).equalsIgnoreCase("reload")){
			if(_cmd.isPlayer()){
				if(!_cmd.getPlayer().hasPermission(_Permission + ".reload")){
					if(!_cmd.getPlayer().isOp()){
						sendMessage("You do not have permission to run this command");
						return;
					}
				}
			}
			sendMessage("Reloading");
			mcs._plugin.reloadConfig();
			mcs._plugin.reload();
			sendMessage("Finished Reloading");
		}
	}
}
