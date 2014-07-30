package io.github.FiddyPercent.fiddycraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FcFarmerCommands implements CommandExecutor {
	private FiddyCraft plugin;
	private ChatColor dgreen = ChatColor.DARK_GREEN;
	private ChatColor red = ChatColor.RED;
	private ChatColor gold = ChatColor.GOLD;
	private ChatColor blue = ChatColor.BLUE;
	private ChatColor yellow = ChatColor.YELLOW;
	private ChatColor aqua = ChatColor.AQUA;
	private ChatColor daqua = ChatColor.DARK_AQUA;
	private ChatColor green = ChatColor.GREEN;
	private ChatColor dblue = ChatColor.DARK_BLUE;

	public FcFarmerCommands(FiddyCraft plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable,
			String[] args) {
		
		
		if(cmd.getName().equalsIgnoreCase("setJob")){
			Player p = (Player) sender;
			String Job = args[1];
			String name = args[0];
			
			if(!plugin.isPlayer(name)){
				p.sendMessage(red + name + " is not a player or is not online");
				return true;
			}
			Player target = Bukkit.getPlayer(name);
			
			if(args.length != 2){
				p.sendMessage(red + "not enough arguments!" );
				return true;
			}
			
			if(!Job.equalsIgnoreCase("Farmer")){
				p.sendMessage(red + Job + " is not a job");
				return false;
			}
			FcPlayers fcp = new FcPlayers(plugin, target);
			fcp.setPlayerJob(Job);
			target.sendMessage(dgreen + "You are now a " + gold + Job);
			p.sendMessage(dblue + "You have set "+ dgreen + name + dblue +" job to " + dgreen + Job );
				
			if(!p.isOp()){
				p.sendMessage(red + "You must be Op to do this command!");
				return false;
			}
			
			
			
		}
		
		
		if(cmd.getName().equalsIgnoreCase("farmer")){
			Player p = (Player) sender;
			FcPlayers fcp = new FcPlayers(plugin, p);
			String cmd1 = " info";
			p.sendMessage(dgreen + "farm " + cmd1);
			p.sendMessage(dgreen + "farm " + cmd1);
			p.sendMessage(dgreen + "farm " + cmd1);
			p.sendMessage(dgreen + "farm " + cmd1);
			p.sendMessage(dgreen + "farm " + cmd1);
			
			if(args[0].equalsIgnoreCase(cmd1)){
				
			}
		}
		
		
		return false;
	}

}
