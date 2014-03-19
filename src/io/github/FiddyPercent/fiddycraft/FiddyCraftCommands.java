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
		
		
		if(cmd.getName().equalsIgnoreCase("trial")){
			Player p = (Player) sender;
			if(p.isOp() || plugin.getConfig().contains("Judge." + p.getName())){
				
				if(args.length < 2){
					p.sendMessage(ChatColor.RED +  "Not enough arguments");
					return false;
				}
				String defendant = args[0];
				String Prosecutor = args[1];
				if(!Bukkit.getPlayer(defendant).isOnline()){
					p.sendMessage(ChatColor.RED + "The defendant is not online, make sure you spelled the name correctly");
					return false;
				}
				
				if(!Bukkit.getPlayer(Prosecutor).isOnline()){
					p.sendMessage(ChatColor.RED +  "The Prosecutor is not online, make sure you spelled the name correctly");
					return false;
				}
				
				if(!plugin.getConfig().contains("defense")){
					p.sendMessage(ChatColor.RED + "You need to set the defense location with " + ChatColor.DARK_GREEN + " /setdefense");
					return false;
				}
				
				if(!plugin.getConfig().contains("defendant")){
					p.sendMessage(ChatColor.RED + "You need to set the defendant location with " + ChatColor.DARK_GREEN + " /setdefendant");
					return false;
				}
				
				if(!plugin.getConfig().contains("prosecutor")){
					p.sendMessage(ChatColor.RED + "You need to set the prosecutor location with " + ChatColor.DARK_GREEN + " /setprosecutor");
					return false;
				}
				
				plugin.defendant.add(defendant);
				plugin.Prosecutor.add(Prosecutor);
				plugin.Judge.add(p.getName());
				
				Bukkit.broadcastMessage(ChatColor.GRAY + " The trial for " + defendant + "will start soon");
				Bukkit.broadcastMessage(ChatColor.GRAY + "If you would like to be a jury use "+ ChatColor.AQUA + " /Jury " +ChatColor.GRAY+ "no one who takes place in the trial can hear chat outside of /msg");
				
				
				Player d = Bukkit.getPlayer(defendant);
				Player P = Bukkit.getPlayer(Prosecutor);
				
				double dx = plugin.getConfig().getDouble("defendant.X");
				double dy = plugin.getConfig().getDouble("defendant.Y");
				double dz = plugin.getConfig().getDouble("defendant.Z");
				
				double Px = plugin.getConfig().getDouble("prosecutor.X");
				double Py = plugin.getConfig().getDouble("prosecutor.Y");
				double Pz = plugin.getConfig().getDouble("prosecutor.Z");
				Location dloc = new Location(p.getWorld(), dx, dy, dz);
				Location Ploc = new Location(p.getWorld(), Px, Py, Pz);
				
				d.teleport(dloc);
				P.teleport(Ploc);
				d.sendMessage(ChatColor.GREEN + "You can now select a lawyer by doing " + ChatColor.RED + "/Lawyer <name of Laywer>");
				d.sendMessage(ChatColor.GREEN + "You can get rid of your lawyer by doing " +  ChatColor.RED + "/Remove Laywer");
				P.sendMessage(ChatColor.GREEN + "We are waiting for the Jury and Defence Attorney, if you need help or tips use " + ChatColor.RED + "/Pro tips");
				plugin.TrialStart.add(true);
				
		}else{
			p.sendMessage(ChatColor.RED + "You must be a judge or mod to start a trial");
		}
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("Lawyer")){
			Player p = (Player) sender;
			
			if(plugin.getConfig().contains("TrialPending." + p.getName()) && plugin.TrialStart.contains(true)){
				if(!(args.length == 1)){
					p.sendMessage(ChatColor.RED + "Not enough arguments");
					return false;
				}
				String Lawyer = args[0];
				if(Bukkit.getPlayer(Lawyer) == null){
					p.sendMessage(ChatColor.RED + "The Laywer you selected is not a player please check the spelling");
					return false;
				}
				
				Player L = Bukkit.getPlayer(Lawyer);
				
				if(!L.isOnline()){
					p.sendMessage(ChatColor.RED +  L.getName() + " is not online");
					return false;
				}
				
				plugin.Defence.add(Lawyer);
				p.sendMessage(ChatColor.GREEN + "You have selected " + Lawyer + " as your Laywer");
				p.sendMessage(ChatColor.GREEN + "If you would like to fire your lawyer use " + ChatColor.RED + "/RemoveLaywer");
				String judge = plugin.Judge.get(0);
				if(Bukkit.getPlayer(judge) == null){
					p.sendMessage(ChatColor.RED + "The Judge is not online right now");
				}else{
					Player j = Bukkit.getPlayer(plugin.Judge.get(0));
					j.sendMessage("The Defense has been selected");
					double dx = plugin.getConfig().getDouble("defense.X");
					double dy = plugin.getConfig().getDouble("defense.Y");
					double dz = plugin.getConfig().getDouble("defense.Z");
					Location dloc = new Location(p.getWorld(), dx, dy, dz);
					L.teleport(dloc);
					
					
				}
			}else{
				p.sendMessage(ChatColor.RED + "Either you are not pending trial or the trial hasnt started yet");
				return false;
				
			}
			
			
		return true;
		}
		
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
		
		if(cmd.getName().equalsIgnoreCase("setdefense")){
			Player p = (Player) sender;
			if(p.isOp()){
			double x = p.getLocation().getX();
			double y = p.getLocation().getY();
			double z = p.getLocation().getZ();
			
			plugin.getConfig().set("defense.X", x);
			plugin.getConfig().set("defense.Y", y);
			plugin.getConfig().set("defense.Z", z);
			
			plugin.saveConfig();
			p.sendMessage(ChatColor.GREEN + "You have set the new Defense Attorney location for the conviceted");
			}else{
				p.sendMessage(ChatColor.RED+ "You must be a mod to do this");
			}
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("setdefendant")){
			Player p = (Player) sender;
			if(p.isOp()){
			double x = p.getLocation().getX();
			double y = p.getLocation().getY();
			double z = p.getLocation().getZ();
			
			plugin.getConfig().set("defendant.X", x);
			plugin.getConfig().set("defendant.Y", y);
			plugin.getConfig().set("defendant.Z", z);
			
			plugin.saveConfig();
			p.sendMessage(ChatColor.GREEN + "You have set the new Defendant location for the conviceted");
			}else{
				p.sendMessage(ChatColor.RED+ "You must be a mod to do this");
			}
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("setprosecutor")){
			Player p = (Player) sender;
			if(p.isOp()){
			double x = p.getLocation().getX();
			double y = p.getLocation().getY();
			double z = p.getLocation().getZ();
			
			plugin.getConfig().set("prosecutor.X", x);
			plugin.getConfig().set("prosecutor.Y", y);
			plugin.getConfig().set("prosecutor.Z", z);
			
			plugin.saveConfig();
			p.sendMessage(ChatColor.GREEN + "You have set the new Prosecutor location for the convicted");
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
			//	if(!(target.getScoreboard().getObjective("Jailed") == null)){
			//		plugin.manager.getNewScoreboard();
			//		target.setScoreboard(plugin.manager.getNewScoreboard());
			//	}
				
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
