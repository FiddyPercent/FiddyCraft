package io.github.FiddyPercent.fiddycraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class FiddyCraftCommands implements CommandExecutor {
	
	private FiddyCraft plugin;
	
	public FiddyCraftCommands(FiddyCraft plugin) {
		this.plugin = plugin;
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		
		
		if(cmd.getName().equalsIgnoreCase("setJail")){
			Player p = (Player) sender;
			if(p.isOp()){
			double x = p.getLocation().getX();
			double y = p.getLocation().getY();
			double z = p.getLocation().getZ();
			
			plugin.getConfig().set("Jail.X", x);
			plugin.getConfig().set("Jail.y", y);
			plugin.getConfig().set("Jail.z", z);
			
			plugin.saveConfig();
			p.sendMessage(ChatColor.GREEN + "You have set the new jail location");
			}else{
				p.sendMessage(ChatColor.RED+ "You must be a mod to do this");
			}
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("setRelease")){
			Player p = (Player) sender;
			if(p.isOp()){
			double x = p.getLocation().getX();
			double y = p.getLocation().getY();
			double z = p.getLocation().getZ();
			
			plugin.getConfig().set("Release.X", x);
			plugin.getConfig().set("Release.Y", y);
			plugin.getConfig().set("Release.Z", z);
			
			plugin.saveConfig();
			p.sendMessage(ChatColor.GREEN + "You have set the new Release location for the conviceted");
			}else{
				p.sendMessage(ChatColor.RED+ "You must be a mod to do this");
			}
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("Jail")){
			Player p = (Player) sender;
			if(!(args.length == 2)){
				return false;
			}

			Player onlinetarget = Bukkit.getServer().getPlayer(args[0]);
			if(!onlinetarget.isOnline()){
				p.sendMessage(ChatColor.RED + "Player not found");
				return false;
			}
			Player target = Bukkit.getPlayer(args[0]);
			
			if(FiddyCraft.isInteger(args[1])== false){
				p.sendMessage(ChatColor.RED + "You need to put down how many labor points the criminal must complete");
				return false;
			}
			
			if(!Bukkit.getPlayer(args[0]).isOnline()){
			p.sendMessage(ChatColor.RED+"Player is not online");	
			}else{
			
			
			if(plugin.getConfig().getString("Jail") == null || !plugin.getConfig().contains("Jail")){
				p.sendMessage(ChatColor.RED + "No jail location is set, use /setJail to set a location for the jail!");
				
			}else{
				double x =  (double) plugin.getConfig().get("Jail.X");
				double y =  (double) plugin.getConfig().get("Jail.y");
				double z =  (double) plugin.getConfig().get("Jail.z");
				 Location loc = new Location(Bukkit.getServer().getWorld("world"),x, y, z);
				Location locT = target.getLocation();
				target.getWorld().strikeLightningEffect(locT);
				target.sendMessage(ChatColor.DARK_RED + "You have been sentenced to " + args[1] + " units of labor in prison you sick scum");
				Bukkit.broadcastMessage(ChatColor.GREEN + "Sent " + target.getName() + " to jail till "  + args[1] + " labor units have been completed.");
				target.teleport(loc);
				ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
				BookMeta bm = (BookMeta) book.getItemMeta();
				bm.setAuthor(ChatColor.RED + "Boone");
				bm.setTitle("Jail Guide");
				bm.addPage(">_>");
				bm.addPage(">_>");
				bm.addPage(">_>");
				bm.addPage("");
				bm.setPage(1,"Welcome to the magical world of jail."
							+" I am your Warden XxBoonexX, This jail has labor based sentences so no afking out. "  + 
							  "The following pages will explain how things work around here so pay attention.");
				bm.setPage(2, "Complete the Following to lower your labor sentence:" + "\n"+ "\n"
							  +"Mining - ores and coal blocks" + "\n" +
						      "Tree cutting - logs only"+ "\n" +
							  "Fishing - raw and cooked" + "\n" + "\n"+ 
						      "Turn in your items to the labor signs");
				bm.setPage(3, "PVP is on in the main area so watch out for other players. No weapons allowed or you may be attacked by our Guards.");
				book.getItemMeta().setDisplayName(ChatColor.RED+ "Jail Guide & Rules");
				book.setItemMeta(bm);
				World world = p.getWorld();
				world.dropItem(loc, book);
				plugin.getConfig().set("Jailed."+ target.getName(), args[1]);
				plugin.saveConfig();
				if(!(target.getScoreboard().getObjective("Jailed") == null)){
					plugin.manager.getNewScoreboard();
				}
				
				plugin.setJailScoreBoard(target, args[1]);
			
			}
		}
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("fcreload")){
			Player p = (Player) sender;
			if(p.isOp()){
				plugin.reloadConfig();
				
				p.sendMessage(ChatColor.GREEN + "You have reloaded the plugin");
			}else{
				p.sendMessage(ChatColor.RED + "You must be OP to reload the plugin.");
			}
		}
		return false;
		
		
	}
}
