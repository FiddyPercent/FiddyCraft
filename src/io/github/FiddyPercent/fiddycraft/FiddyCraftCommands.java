package io.github.FiddyPercent.fiddycraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FiddyCraftCommands implements CommandExecutor {
	
	private FiddyCraft plugin;
	
	public FiddyCraftCommands(FiddyCraft plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if(cmd.getName().equalsIgnoreCase("Vfname")){

			if(sender instanceof Player){
				Player player = (Player) sender;
				if(args.length == 0){
					player.sendMessage(ChatColor.RED + "not enough arguments");
				}else if( args.length > 1){
					player.sendMessage(ChatColor.RED + "to many arguments");
				}else{
					
				if(!plugin.getVillagerNames().contains("FirstName")){
					plugin.getVillagerNames().set("Firstname", null);
					plugin.saveVillagerNames();
					Bukkit.broadcastMessage("test add firstname first time");
				}
				
				plugin.getVillagerNames().addDefault("FirstName", args[0]);
				plugin.saveVillagerNames();
				player.sendMessage(ChatColor.GREEN + "You have added the name " + args[0] +" to the first name pool");
			}
				
			}else{
				return false;
			}
			return true;
		} 
		return false;
		
		
	}
}
