package io.github.FiddyPercent.fiddycraft;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;

public class FiddyCraftCommands implements CommandExecutor {
	
	private FiddyCraft plugin;
	
	public FiddyCraftCommands(FiddyCraft plugin) {
		this.plugin = plugin;
	}
	
	
	

	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
//TRIAL SETUP
		if(cmd.getName().equalsIgnoreCase("trial")){
			Player p = (Player) sender;
			if(p.isOp() ||  plugin.getPlayerInfo().contains(p.getUniqueId().toString() + ".Rank.Judge")){
				
				if(args.length < 2){
					p.sendMessage(ChatColor.RED +  "Not enough arguments");
					return false;
				}
				String defendant = args[0];
				String Prosecutor = args[1];
			
				if(plugin.isPlayer(defendant) == false){
					p.sendMessage(ChatColor.RED + "The defendant is not online, make sure you spelled the name correctly");
					return false;
				}
				
				if(plugin.isPlayer(Prosecutor) == false){
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
				p.sendMessage(ChatColor.BLUE + "defendant is " + defendant + ChatColor.RED + " Prosecutor is " + Prosecutor );
				plugin.defendant.add(defendant);
				plugin.Prosecutor.add(Prosecutor);
				plugin.Judge.add(p.getName());
				
				Bukkit.broadcastMessage(ChatColor.GOLD + " The trial for " + defendant + " will start soon");
				Bukkit.broadcastMessage(ChatColor.GOLD + "If you would like to be a jury use "+ ChatColor.AQUA + " /JoinJury " +ChatColor.GOLD+ "no one who takes place in the trial can hear chat outside of /msg");
				
				
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
				
				plugin.prosecutionTips(P);
				plugin.defenseTips(d);
				plugin.judgeTips(p);
				d.teleport(dloc);
				P.teleport(Ploc);
				d.sendMessage(ChatColor.GREEN + "You can now select a lawyer by doing " + ChatColor.RED + "/Lawyer <name of Laywer>");
				d.sendMessage(ChatColor.GREEN + "You can get rid of your lawyer by doing " +  ChatColor.RED + "/RemoveLaywer");
				P.sendMessage(ChatColor.GREEN + "We are waiting for the Jury and defense Attorney, if you need help or tips use " + ChatColor.RED + "/Pro tips");
				plugin.TrialReady.put("Trial", true);
				
		}else{
			p.sendMessage(ChatColor.RED + "You must be a judge or mod to start a trial");
		}
			return true;
		}

// TRIAL START
		if(cmd.getName().equalsIgnoreCase("StartTrial")){
			Player p = (Player) sender;
	
			
			if(plugin.TrialReady.isEmpty()){
				p.sendMessage("use /trial to set up the trial first");
				Bukkit.broadcastMessage( "TrialStart is empty");
				return false;
			}
			
			if(plugin.TrialReady.get("Trial") == false){
				p.sendMessage("use /trial to set up the trial first");
				return false;
			}
			
			if(plugin.defense.isEmpty()){
				plugin.defense.add(plugin.defendant.get(0));
			}
			String defense = plugin.defense.get(0);
			String prosecutor = plugin.Prosecutor.get(0);
			String defentant = plugin.defendant.get(0);
			if(Bukkit.getPlayer(defense)== null ){
				p.sendMessage(ChatColor.RED + "defense is not set");
				return false;
			}
			
			if(Bukkit.getPlayer(prosecutor)== null ){
				p.sendMessage(ChatColor.RED + "prosecutor is not set");
				return false;
			}
			
			
			if(Bukkit.getPlayer(defentant)== null ){
				p.sendMessage(ChatColor.RED + "defentant is not set");
				return false;
			}
			
			
			Bukkit.broadcastMessage(ChatColor.GOLD + "The Trial For " + defentant + " has started" );
			plugin.TrialStart.put("Trial", true);
			
			plugin.openingStatementProsecution.put("Trial", 1200);
			p.chat(ChatColor.DARK_PURPLE + "Prosection, You have 1 minute for your opening Statement.");
			Player pro = Bukkit.getPlayer(prosecutor);
			pro.sendMessage(ChatColor.YELLOW + "I need to show confidence, and summarize what I wish to prove. The clock is already ticking!");
		
		}
		
//JUDGEMENT
		if(cmd.getName().equalsIgnoreCase("JUDGEMENT")){
			Player p = (Player) sender;
			String defense = plugin.defense.get(0);
			String prosecutor = plugin.Prosecutor.get(0);
			String defentant = plugin.defendant.get(0);
			
			if( plugin.Judgement.isEmpty() || !plugin.Judgement.containsKey(p.getName()) || plugin.Judgement.get(p.getName()) == false){
				p.sendMessage(ChatColor.RED + "It is not time to judge yet");
				return false;
			}
			
			if(args.length != 1){
				p.sendMessage(ChatColor.RED + "Not enough args You must choose guilty or nonGuilty");
				return false;
			}
			
			if(args[0].equalsIgnoreCase("Guilty") || args[0].equalsIgnoreCase("NotGuilty")){
			
			
			
			if(Bukkit.getPlayer(defense)== null ){
				p.sendMessage(ChatColor.RED + "defense is not Ready");
				return false;
			}
			
			if(Bukkit.getPlayer(prosecutor)== null ){
				p.sendMessage(ChatColor.RED + "prosecutor is not Ready");
				return false;
			}
			
			
			if(Bukkit.getPlayer(defentant)== null ){
				p.sendMessage(ChatColor.RED + "defentant is not Ready");
				return false;
			}
			
			String verdict = args[0];
			String defendant = plugin.defendant.get(0);
			
			if(verdict.equalsIgnoreCase("NotGuilty")){
				Bukkit.broadcastMessage(ChatColor.GOLD + defendant  + " has been Found NOT GUILTY");
				double pm = plugin.economy.getPlayerMoneyDouble(prosecutor);
				double newmon = pm +1000;
    			plugin.economy.setPlayerMoney(prosecutor, newmon, false);
    			
    			Bukkit.getPlayer(prosecutor).sendMessage(ChatColor.GOLD + "You have lost but your efforts are not in vain, you have been paid  " + ChatColor.RED + 1000 +ChatColor.GOLD + " dollars!");
    			if(plugin.getPlayerInfo().contains("Lawyer." + p.getUniqueId().toString() )){
    				plugin.updateStat(Bukkit.getPlayer(prosecutor), "Lost", "Lawyer");
    				plugin.updateStat(Bukkit.getPlayer(prosecutor), "Trials", "Lawyer");
    				
    			}
			}else{
				Bukkit.broadcastMessage(ChatColor.GOLD + defendant + " has been found GUILTY");
				double pm = plugin.economy.getPlayerMoneyDouble(prosecutor);
				double newmon = pm +2000;
    			plugin.economy.setPlayerMoney(prosecutor, newmon, false);
    			Bukkit.getPlayer(prosecutor).sendMessage(ChatColor.GOLD + "Congradulations on your win! you have been paid " + ChatColor.RED + 2000 +  ChatColor.GOLD + " dollars!");
    			if(plugin.getPlayerInfo().contains("Lawyer." + p.getUniqueId().toString() )){
    				plugin.updateStat(Bukkit.getPlayer(prosecutor), "Won", "Lawyer");
    				plugin.updateStat(Bukkit.getPlayer(prosecutor), "Trials", "Lawyer");
    			}
			}
			
			p.sendMessage("For Judging this trial you have been awarded" + ChatColor.RED +  2500 + ChatColor.GOLD + " dollars");
			double playerMoney = plugin.economy.getPlayerMoneyDouble(p.getName());
			plugin.economy.setPlayerMoney(prosecutor, playerMoney + 2500, false);
			
			World world = Bukkit.getWorld("world");
			ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 3);
			BookMeta bm = (BookMeta) book.getItemMeta();
			String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(Calendar.getInstance().getTime());
			
			bm.setAuthor("Court of Fiddy");
			bm.setTitle(defendant + " VS " + prosecutor );
			bm.addPage(">_>");
			bm.setPage(1, 
			"Judge: " + p.getName() + "\n" + "\n"+
			"Prosecutor: " + prosecutor + "\n" + "\n"+
			"defense: " + defense + "\n" +
			"Defendant: " + defendant + "\n" + "\n"+
			//"Charge: " + plugin.Charge.get(defendant) + "\n" +
			"Verdict: " + verdict +  "\n" + "\n" +
			 " Date: " + timeStamp
			);
			book.getItemMeta().setDisplayName(ChatColor.RED+ "Court Record");
			book.setItemMeta(bm);
			world.dropItem(p.getLocation(), book);
			
			plugin.TrialStart.clear();
			plugin.closingStatementdefense.clear();
			plugin.closingStatementProsecution.clear();
			plugin.evidence.clear();
			plugin.defendant.clear();
			plugin.Prosecutor.clear();
			plugin.defense.clear();
			plugin.Judge.clear();
			plugin.Judgement.clear();
			plugin.TrialReady.clear();
			return true;
		}
	}
// STOP TRIAL
	if(cmd.getName().equalsIgnoreCase("stopTrial")){
		Player p = (Player) sender;
		if(plugin.TrialReady.get("Trial") == true || plugin.TrialStart.get("Trial") == true){
		if(p.isOp() || plugin.Judge.contains(p.getName())){
		Bukkit.broadcastMessage(ChatColor.GOLD + "Trial has ben Canceled");
		
		plugin.TrialStart.clear();
		plugin.closingStatementdefense.clear();
		plugin.closingStatementProsecution.clear();
		plugin.evidence.clear();
		plugin.defendant.clear();
		plugin.Prosecutor.clear();
		plugin.defense.clear();
		plugin.Judge.clear();
		plugin.Judgement.clear();
		plugin.TrialReady.clear();
			}else{
				p.sendMessage(ChatColor.RED + "You are not a judge");
			}
		}else{
			p.sendMessage(ChatColor.RED + "No trial has started");
		}
	}

		
//LAWYER PICKING
		if(cmd.getName().equalsIgnoreCase("Lawyer")){
			Player p = (Player) sender;
			
			if(plugin.getConfig().contains("PendingTrial." + p.getUniqueId().toString()) && plugin.TrialReady.get("Trial") == true){
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
				
				if( plugin.willLawyer.contains(L.getName())){
				
				plugin.defense.add(Lawyer);
				p.sendMessage(ChatColor.GOLD + "You have selected " + Lawyer + " as your Laywer");
				p.sendMessage(ChatColor.GOLD + "If you would like to fire your lawyer use " + ChatColor.RED + "/FireLawyer");
				String judge = plugin.Judge.get(0);
				if(Bukkit.getPlayer(judge) == null){
					p.sendMessage(ChatColor.RED + "The Judge is not online right now");
				}else{
					Player j = Bukkit.getPlayer(plugin.Judge.get(0));
					j.sendMessage(ChatColor.GOLD +"The defense has been selected");
					int x = (int) plugin.getConfig().getDouble("Location." + "defense" +".X");
					int y = (int) plugin.getConfig().getDouble("Location." + "defense" +".Y");
					int z = (int) plugin.getConfig().getDouble("Location." + "defense" +".Z");
					Location dloc = new Location(p.getWorld(), x, y, z);
					L.teleport(dloc);
				}
				}else{
					p.sendMessage(ChatColor.RED + "That lawyer is currently not avalible, or he has not signed up to Defend (/Defend).");
				}
			}else{
				p.sendMessage(ChatColor.RED + "Either you are not pending trial or the trial hasnt started yet");
				return false;
				
			}
			
			
		return true;
		}
		
// FIRE YOUR defense LAWYER
		if(cmd.getLabel().equalsIgnoreCase("FireLawyer")){
			Player p = (Player) sender;
			if(plugin.getConfig().contains("PendingTrial." + p.getUniqueId().toString()) && plugin.TrialReady.get("Trial") == true){
			if(plugin.defendant.get(0)== p.getName()){	
				plugin.defense.remove(0);
				plugin.defense.add(p.getName());
				p.sendMessage("You are now your own defense lawyer");
				if(plugin.isPlayer(plugin.defense.get(0))){
					Player oldLawyer = Bukkit.getPlayer(plugin.defense.get(0));
					oldLawyer.sendMessage(ChatColor.RED  + "You have been fired");
				}
				}else{
					p.sendMessage(ChatColor.RED + "Only the defendant can remove the defense lawyer");
				}
			}
			
		}
//POLICE RANKS	
		if(cmd.getLabel().equalsIgnoreCase("policeRank")){
			Player p = (Player) sender;
			
			plugin.getPlayerInfo().getConfigurationSection("Officers").getKeys(true);
			
		}
		
		

//ENDING StatementS
		if(cmd.getLabel().equalsIgnoreCase("EndStatement")){
			Player p = (Player) sender;
	if(plugin.TrialStart.isEmpty() == false && plugin.TrialStart.get("Trial") == true){
			if(plugin.Prosecutor.get(0).equalsIgnoreCase(p.getName())){
			if(plugin.TrialStart.get("Trial") == true){
				if(plugin.openingStatementProsecution.containsKey("Trial")){
					String t = "Trial";
					int op = plugin.openingStatementProsecution.get("Trial");
					int newop = op - op;
					plugin.openingStatementProsecution.put(t, newop);
				p.chat("This ends my Opening Statement");
		}
			}else{
				p.sendMessage(ChatColor.RED + "No Trial is currently active");
			}
			if(plugin.closingStatementProsecution.containsKey("Trial")){
				String t = "Trial";
				int op = plugin.closingStatementProsecution.get("Trial");
				int newop = op - op;
				plugin.closingStatementProsecution.put(t, newop);
				p.chat("This ends my Closing Statement");
				}
			}
			
			if(plugin.defense.get(0).equalsIgnoreCase(p.getName())){
				if(plugin.closingStatementdefense.containsKey("Trial")){
					String t = "Trial";
					int op = plugin.closingStatementdefense.get("Trial");
					int newop = op - op;
					plugin.closingStatementdefense.put(t, newop);
					p.chat("This ends my Closing Statement");
				}else{
					p.sendMessage(ChatColor.YELLOW + "I cant end the other persons Statement for them");
				}
			}
			if(plugin.Judge.get(0).equalsIgnoreCase(p.getName())){
			if(plugin.evidence.containsKey("Trial")){
				String t = "Trial";
				int op = plugin.evidence.get("Trial");
				int newop = op - op;
				plugin.evidence.put(t, newop);
				Player j = Bukkit.getPlayer(plugin.Judge.get(0));
				j.sendMessage(ChatColor.DARK_PURPLE + "That is enough we will now move on");
			}
		}
		
	}else{
		p.sendMessage(ChatColor.RED + "No trial is started");
	}
}
		
// ALLOW TO LAWYER		
		if(cmd.getLabel().equalsIgnoreCase("Defend")){
			Player p = (Player) sender;
		if(plugin.willLawyer.contains(p.getName())){
			if(plugin.getPlayerInfo().contains("Lawyer." + p.getUniqueId())){
		
			p.sendMessage("You are now open to take on defense cases");
			plugin.willLawyer.add(p.getName());
			}else{
			p.sendMessage(ChatColor.BLUE + "You can now stand in as a defense attorney.");
			p.sendMessage(ChatColor.BLUE + "If you win you can become a real attorney!");
			plugin.willLawyer.add(p.getName());
			}
		}else{
			if(!plugin.willLawyer.isEmpty() && plugin.willLawyer.contains(p.getName())){
				p.sendMessage("You are no longer open to be an attorney at this time");
				plugin.willLawyer.remove(p.getName());
			}else{
				p.sendMessage("You are now open to be an attorney");
				plugin.willLawyer.add(p.getName());
			}
		}
			
			return true;
		}

//SET JAIL
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

		
//SET RELEASE POINT
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
		
//ADD POLICE		
		if(cmd.getName().equalsIgnoreCase("addPolice")){
			Player p = (Player) sender;
			
			if(args.length != 1){
				p.sendMessage(ChatColor.RED+ "Not enough arguments");
				return false;
			}
			
			if(plugin.isPlayer(args[0]) == false){
				p.sendMessage(ChatColor.RED +  args[0] + " is not a player");
				return true;
			}
			Player chief = Bukkit.getPlayer(args[0]);
			if(p.isOp() || plugin.getPlayerInfo().getString("Officers." + chief.getUniqueId().toString() +"Rank").equalsIgnoreCase("Captain")){
				p.sendMessage(ChatColor.GREEN + "You have added " + args[0]);
				Player nc = Bukkit.getPlayer(args[0]);
				String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(Calendar.getInstance().getTime());
				nc.sendMessage(ChatColor.BLUE + "Welcome to the police force " + nc.getName());

					if(!plugin.getPlayerInfo().contains("Officers." +  nc.getUniqueId().toString())){
						plugin.getPlayerInfo().set("Officers." +p.getUniqueId()+ ".Name", p.getName());
						plugin.getPlayerInfo().set("Officers." +p.getUniqueId()+ ".Joined", timeStamp);
						plugin.getPlayerInfo().set("Officers." +p.getUniqueId()+ ".Rank", "Rookie");
						plugin.getPlayerInfo().set("Officers." +p.getUniqueId() + ".Deaths", 0);
						plugin.getPlayerInfo().set("Officers." +p.getUniqueId() + ".Arrested", 0);
						plugin.getPlayerInfo().set("Officers." +p.getUniqueId() + ".Convected", 0);
						plugin.savePlayerInfo();
					}	
			}
			return true;
		}
		
//ADD LAWYER		
		if(cmd.getName().equalsIgnoreCase("addLawyer")){
			Player p = (Player) sender;
			
			if(args.length != 1){
				p.sendMessage(ChatColor.RED+ "Not enough arguments");
				return false;
			}
			
			if(plugin.isPlayer(args[0]) == false){
				p.sendMessage(ChatColor.RED +  args[0] + " is not a player");
				return true;
			}
			Player judge = Bukkit.getPlayer(args[0]);
			
			if(p.isOp() || plugin.getConfig().getString("Lawyer." + judge.getUniqueId().toString() + ".Rank" ).equalsIgnoreCase("Judge")){
				p.sendMessage(ChatColor.GREEN + "You have added " + args[0]);
				Player nc = Bukkit.getPlayer(args[0]);
				nc.sendMessage(ChatColor.BLUE + "You are now a Lawyer " + nc.getName());
				
					if(!plugin.getPlayerInfo().contains("Lawyer." +  nc.getUniqueId().toString())){
						plugin.getPlayerInfo().set("Lawyer." +p.getUniqueId()+ ".Name", p.getName());
						plugin.getPlayerInfo().set("Lawyer." +nc.getUniqueId().toString()+ ".Rank", "Rookie");
						plugin.getPlayerInfo().set("Lawyer." +nc.getUniqueId().toString() + ".Won", 0);
						plugin.getPlayerInfo().set("Lawyer." +nc.getUniqueId().toString() + ".Lost", 0);
						plugin.getPlayerInfo().set("Lawyer." +nc.getUniqueId().toString() + ".Trials", 0);
						plugin.savePlayerInfo();	
						}else{
						
					}	
				
			}
			return true;
		}
		
//JOIN JURY		
		if(cmd.getName().equalsIgnoreCase("joinJury")){
			Player p = (Player) sender;
			Location loc = p.getLocation();
			
			if(!plugin.TrialStart.containsKey("Trial")){
				p.sendMessage(ChatColor.GOLD + "No trial is currently started");
				return true;
			}
			
			
			if( plugin.TrialStart.get("Trial") == false){
				p.sendMessage(ChatColor.GOLD + "No trial is currently started");
				return true;
			}
			
			if(!plugin.getConfig().contains("Location.JuryLocation")){
				p.sendMessage(ChatColor.DARK_RED + "Jury Location is not set properly please notify admin");
				return false;
			}
			
			if(p.getName().equalsIgnoreCase(plugin.defendant.get(0)) || p.getName().equalsIgnoreCase(plugin.Prosecutor.get(0))){
			p.sendMessage(ChatColor.RED + "You cannot join a jury if you are part of the trial");
			return false;
			}
			
			
			if(plugin.isInRegion("Town", loc) == false){
				p.sendMessage(ChatColor.RED + "You must be in city to join the Jury");
				return false;
			}
			
			 
			double x = plugin.getConfig().getDouble("Location.JuryLocation.X");
			double y = plugin.getConfig().getDouble("Location.JuryLocation.Y");
			double z = plugin.getConfig().getDouble("Location.JuryLocation.Z");
			
			Location tp = new Location(p.getWorld(), x,y,z);
			p.teleport(tp);
			
			p.sendMessage(ChatColor.GOLD + "You are now part of the Jury, you will be paid if you stay the entire court trial. If you must leave do " + ChatColor.BLUE + 
					"/LeaveJury " + ChatColor.GOLD + "You will not be paid if you leave early");
			
			plugin.Jury.add(p.getName());
			
			String judge = plugin.Judge.get(0);
			
			
			Player Judge = Bukkit.getPlayer(judge);
			
			Judge.sendMessage(ChatColor.GOLD + p.getName()+ " has joined the jury.");
			return true;
		}
		
//LEAVE JURY		
		if(cmd.getName().equalsIgnoreCase("leaveJury")){
			
			Player p = (Player) sender;
			String name = p.getName();
			
			if(!plugin.Jury.contains(name)){
				p.sendMessage(ChatColor.GOLD + "You are not part of the Jury");
				return false;
			}else{
				p.sendMessage("You are no longer a Jury member you will not be paid");
				plugin.Jury.remove(name);
				
				plugin.Jury.add(p.getName());
				
				String judge = plugin.Judge.get(0);
				
				
				Player Judge = Bukkit.getPlayer(judge);
				
				Judge.sendMessage(ChatColor.GOLD + p.getName()+ " has left the jury.");
				return true;		
			}
		}
		
//REMOVE POLICE		
		if(cmd.getName().equalsIgnoreCase("RemovePolice")){
			Player p = (Player) sender;
			
			if(args.length != 1){
				p.sendMessage(ChatColor.RED+ "Not enough arguments");
				return false;
			}
			
			if(plugin.isPlayer(args[0]) == false){
				p.sendMessage(ChatColor.RED +  args[0] + " is not a player");
				return true;
			}
			
			if(p.isOp() || plugin.getConfig().contains("Judge." + args[0])){
				p.sendMessage(ChatColor.GREEN + "You have removed " + args[0]);
				Player nc = Bukkit.getPlayer(args[0]);
				nc.sendMessage(ChatColor.BLUE + "You are off the police force " + nc.getName());
				plugin.getPlayerInfo().set("Officers." + nc.getUniqueId().toString(), null);
				plugin.savePlayerInfo();
			}
			return true;
		}
		
//REMOVE LAWYER		
		if(cmd.getName().equalsIgnoreCase("RemoveLawyer")){
			Player p = (Player) sender;
			
			if(args.length != 1){
				p.sendMessage(ChatColor.RED+ "Not enough arguments");
				return false;
			}
			
			if(plugin.isPlayer(args[0]) == false){
				p.sendMessage(ChatColor.RED +  args[0] + " is not a player");
				return true;
			}
			
			if(p.isOp() || plugin.getConfig().contains("Judge." + args[0])){
				p.sendMessage(ChatColor.GREEN + "You have removed " + args[0]);
				Player nc = Bukkit.getPlayer(args[0]);
				nc.sendMessage(ChatColor.BLUE + "You no longer a lawyer " + nc.getName());
				plugin.getPlayerInfo().set("Lawyer." + nc.getUniqueId().toString(), null);
				plugin.savePlayerInfo();;
			}
			return true;
		}
		
//SET defense	
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
			p.sendMessage(ChatColor.GREEN + "You have set the new defense Attorney location for the conviceted");
			}else{
				p.sendMessage(ChatColor.RED+ "You must be a mod to do this");
			}
			return true;
		}
		
//SET DEFENDANT
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
		
// SET PROSECUTOR
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

//MyInfo		
		if(cmd.getName().equalsIgnoreCase("myInfo")){
			Player p = (Player) sender;
			FcPlayers fc = new FcPlayers(plugin, p);
		if(plugin.getPlayerInfo().contains("Players." + p.getUniqueId().toString())){
			p.sendMessage(ChatColor.GREEN + "#####################################################");
			p.sendMessage(ChatColor.GREEN + "Name: " + ChatColor.YELLOW + p.getName());
			p.sendMessage(ChatColor.GREEN + "Join date: " + ChatColor.YELLOW + fc.getStartDate());
			p.sendMessage(ChatColor.GREEN + "Normal Job: " + ChatColor.YELLOW + fc.getPlayerJob());
			p.sendMessage(ChatColor.BLUE + "Can Lawyer: " + ChatColor.GOLD + fc.canLawyer());
			p.sendMessage(ChatColor.BLUE + "Pending Trial: " + ChatColor.GOLD + fc.getisPendingTrial());
			p.sendMessage(ChatColor.DARK_GRAY + "On Parole: " + ChatColor.DARK_RED + fc.getIsOnParol());
			p.sendMessage(ChatColor.DARK_GRAY + "Players Killed: " + ChatColor.DARK_RED + fc.getPlayersKilled()); 
			p.sendMessage(ChatColor.DARK_GRAY + "Times Arrested: " + ChatColor.DARK_RED + fc.getTimesArrested());
			p.sendMessage(ChatColor.DARK_GRAY + "Times Convicted: " + ChatColor.DARK_RED + fc.getTimesConvicted());
			p.sendMessage(ChatColor.DARK_GRAY + "Wanted for a crime: " + ChatColor.DARK_RED + fc.getIsWanted());
			p.sendMessage(ChatColor.GREEN + "#####################################################");
		}
	}

//PoliceInfo
		if(cmd.getName().equalsIgnoreCase("PoliceInfo")){
			Player p = (Player) sender;
		if(plugin.getPlayerInfo().contains("Officers." + p.getUniqueId().toString())){
		if(plugin.getPlayerInfo().contains("Players." + p.getUniqueId().toString())){	
			p.sendMessage(ChatColor.BLUE + "#####################################################");
			p.sendMessage(ChatColor.BLUE + "Name: " + ChatColor.YELLOW + p.getName());
			p.sendMessage(ChatColor.BLUE + "Rank: " + ChatColor.YELLOW + plugin.getPlayerInfo().getString("Officers." + p.getUniqueId().toString()+ ".Rank"));
			p.sendMessage(ChatColor.BLUE + "Deaths: " + ChatColor.YELLOW + plugin.getPlayerInfo().getString("Officers." + p.getUniqueId().toString()+ ".Deaths"));
			p.sendMessage(ChatColor.BLUE + "Arrested: " + ChatColor.YELLOW + plugin.getPlayerInfo().getString("Officers." + p.getUniqueId().toString()+ ".Arrested"));
			p.sendMessage(ChatColor.BLUE + "Convected: " + ChatColor.YELLOW + plugin.getPlayerInfo().getString("Officers." + p.getUniqueId().toString()+ ".Convected"));
			p.sendMessage(ChatColor.BLUE + "#####################################################");
		}
			}else{
				p.sendMessage(ChatColor.RED + "You are not a police officer");
		}	
	}
		
//LawyerInfo
		if(cmd.getName().equalsIgnoreCase("LawyerInfo")){
			Player p = (Player) sender;
		if(plugin.getPlayerInfo().contains("Lawyer." + p.getUniqueId().toString())){
		if(plugin.getPlayerInfo().contains("Lawyer." + p.getUniqueId().toString())){	
			p.sendMessage(ChatColor.GOLD + "#####################################################");
			p.sendMessage(ChatColor.GOLD + "Name: " + ChatColor.DARK_PURPLE + p.getName());
			p.sendMessage(ChatColor.GOLD + "Rank: " + ChatColor.DARK_PURPLE + plugin.getPlayerInfo().getString("Lawyer." + p.getUniqueId().toString()+ ".Rank"));
			p.sendMessage(ChatColor.GOLD + "Trials: " + ChatColor.DARK_PURPLE + plugin.getPlayerInfo().getString("Lawyer." + p.getUniqueId().toString()+ ".Trials"));
			p.sendMessage(ChatColor.GOLD + "Won: " + ChatColor.DARK_PURPLE + plugin.getPlayerInfo().getString("Lawyer." + p.getUniqueId().toString()+ ".Won"));
			p.sendMessage(ChatColor.GOLD + "Lost: " + ChatColor.DARK_PURPLE + plugin.getPlayerInfo().getString("Lawyer." + p.getUniqueId().toString()+ ".Lost"));
			p.sendMessage(ChatColor.GOLD + "#####################################################");
			
		}
		
		}else{
			p.sendMessage(ChatColor.RED + "You are not a Lawyer");
		}	
	}

	
//REGION 2
		if(cmd.getName().equalsIgnoreCase("setRegion2")){
			Player p = (Player) sender;
			if(p.isOp()){
			if(args.length != 1){
				p.sendMessage(ChatColor.RED + "You need to name the area");
				return false;
			}

			if(!plugin.getConfig().contains("Regions."+args[0])){
				p.sendMessage(ChatColor.RED + "You must use an existing location name");
				p.sendMessage(plugin.getConfig().getString("Regions"));
				return false;
			}
			
			double x = p.getLocation().getX();
			double y = p.getLocation().getY();
			double z = p.getLocation().getZ();
			
		
			String area = args[0];
			plugin.getConfig().set("Regions." + area+".X2", x);
			plugin.getConfig().set("Regions." +area+".Y2", y);
			plugin.getConfig().set("Regions." +area+".Z2", z);
			plugin.saveConfig();
			p.sendMessage(ChatColor.AQUA + "Saved location as " + area);
			}else{
				p.sendMessage(ChatColor.RED + "You must be a mod to use this command");
			}
		}
		
//REGION 1
		if(cmd.getName().equalsIgnoreCase("setRegion1")){
			Player p = (Player) sender;
			if(args.length != 1){
				p.sendMessage(ChatColor.RED + "You need to name the area");
				return false;
			}

			double x = p.getLocation().getX();
			double y = p.getLocation().getY();
			double z = p.getLocation().getZ();
			
			String area = args[0];
			plugin.getConfig().set("Regions." + area+".X", x);
			plugin.getConfig().set("Regions." +area+".Y", y);
			plugin.getConfig().set("Regions." +area+".Z", z);
			plugin.saveConfig();
					
			p.sendMessage(ChatColor.AQUA + "Saved location as " + area);
					
		}
		
//TEST COMMAND
		if(cmd.getName().equalsIgnoreCase("rTest")){
		Player p = (Player) sender;
	//if(args[0] == "roodrank"){
	//	ItemStack item = p.getItemInHand();
	//	ItemMeta meta = item.getItemMeta();
	//	
	//	if(!meta.hasDisplayName()){
	//		meta.setDisplayName(item.getType().toString());
	//	}
	//	HashMap<String, Integer> foodRank = new HashMap<String, Integer>() ;
	//	foodRank.put(meta.getDisplayName(), 4);
	//	HashMap<String, Integer> foodTotal = new HashMap<String, Integer>() ;
	//	foodTotal.put(meta.getDisplayName(), 1);
	//	ItemStack result = p.getItemInHand();
		
	//	p.setItemInHand(plugin.getDish(foodRank, foodTotal, result));
	//}
	
	if(plugin.getPlantInfo().contains("Farmer." + p.getUniqueId().toString())){
		
	
		 plugin.getPlayerInfo().set("Players." + p.getUniqueId().toString() +".Farmer Exp", 0);
		 plugin.getPlayerInfo().set("Players." + p.getUniqueId().toString() +".Farmer Rank", "Legendary Farmer");
		 plugin.savePlayerInfo();
		FcFarmers fcf = new FcFarmers(plugin, p);
		//fcf.setFarmerRank("Legendary Farmer");
		Bukkit.broadcastMessage("NEW PLANT CYCLE");
		String farmer = p.getUniqueId().toString();
		Set<String> plants =  plugin.getPlantInfo().getConfigurationSection("Farmer." + farmer + ".Plants").getKeys(false);
		for(String plant : plants){
			Plants pt = new Plants(plugin, farmer, plugin.getLocationFromString(plant));
			if(pt.getisWaterd()){
				Bukkit.broadcastMessage("WATERED");
				int newcycle = pt.getPlantCycle() -1;
				pt.setPlantCycle(newcycle);
				pt.setIsWatered(false);
				pt.changePlantCycle();
				Bukkit.broadcastMessage(ChatColor.GOLD + "Changed CYCLE!");
			}else{
				if(pt.isHealthy()){
					pt.setHealth(false);
				}else{
					int r = plugin.randomNumber(10);
					if(r == 5){
						pt.killPlant();
						Bukkit.broadcastMessage(ChatColor.GOLD + "DEAD PLANT");
					}
				}
			}
		}
		
	}
}
		
//RELEASE COMMAND
		if(cmd.getName().equalsIgnoreCase("RELEASE")){
		
			Player p = (Player) sender;
		if(p.isOp()){
			
			if(plugin.isPlayer(args[0]) == false){
				p.sendMessage(ChatColor.RED + args[0] + " is not a player");
				return false;
			}
		     Player target = Bukkit.getPlayer(args[0]);
			
			if(!plugin.getConfig().contains("Jailed." + target.getUniqueId().toString())){	
				p.sendMessage(ChatColor.RED + "Player is not in jail");
				return false;
			}

			plugin.newJailScore(target, 90000);
			plugin.getConfig().set("Jailed." + target.getUniqueId().toString(), plugin.getScore(target));
        	plugin.saveConfig();
			 if(plugin.getScore(target) <= 0 ){
        		 target.sendMessage(ChatColor.YELLOW + "You have been given a pardon. You are free to go");
        		 Bukkit.broadcastMessage(target.getName() + " has been released from jail");
        		 
        		 target.getServer().getWorld("world").setSpawnLocation(-1459, 74, -236);
        		 if(plugin.getConfig().contains("Release")){
        			double x =  (double) plugin.getConfig().get("Release.X");
     				double y =  (double) plugin.getConfig().get("Release.Y");
     				double z =  (double) plugin.getConfig().get("Release.Z");
     				Location loc = new Location(Bukkit.getServer().getWorld("world"),x, y, z);
     				target.getInventory().clear();
     				target.teleport(loc);
     				plugin.getConfig().set("Jailed." + target.getUniqueId().toString(), null);
     				plugin.saveConfig();
     				plugin.reloadConfig();
     				
     				FiddyCraft.getScoreboard(target).clearSlot(DisplaySlot.SIDEBAR);
     				FiddyCraft.boards.remove(target.getName());
        		 }
			 }
        }else{
        	p.sendMessage(ChatColor.RED + "You must be op to do this");
        }
	}
 
		
//JAIL COMMAND
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
				
				plugin.getConfig().set("Jailed."+ target.getUniqueId().toString(), args[1]);
				plugin.saveConfig();
				
				FiddyCraft.setJailSentence(target, Integer.parseInt(args[1]));
		}
	}
		return true;
}
		
//FC RELOAD
		
		if(cmd.getName().equalsIgnoreCase("fcreload")){
			Player p = (Player) sender;
			if(p.isOp()){
				plugin.reloadConfig();
				
				p.sendMessage(ChatColor.GREEN + "You have reloaded the plugin");
			}else{
				p.sendMessage(ChatColor.RED + "You must be OP to reload the plugin.");
			}
			return true;
	}

//SET LOCATION
		if(cmd.getName().equalsIgnoreCase("setLocation")){
			Player p = (Player) sender;
			if(args.length != 1){
				p.sendMessage(ChatColor.RED + "You do not have enough arguments");
				return false;
			}
			
			double x = p.getLocation().getX();
			double y = p.getLocation().getY();
			double z = p.getLocation().getZ();
			
			String area = args[0];
			plugin.getConfig().set("Location." + area+".X", x);
			plugin.getConfig().set("Location." +area+".Y", y);
			plugin.getConfig().set("Location." +area+".Z", z);
			plugin.saveConfig();
			
			
			p.sendMessage(ChatColor.BLUE + "Set the location of " + args[0]);
		}
		
//POLICE STATION		
		if(cmd.getName().equalsIgnoreCase("PoliceStation")){
			Player p = (Player) sender;

			
			if(plugin.getPlayerInfo().contains("Officers." + p.getUniqueId().toString())){
			
				if(plugin.isInRegion("Town", p.getLocation())){
				
				Location loc = new Location(p.getWorld(), -1527, 80, -113);
				p.teleport(loc);
				p.sendMessage(ChatColor.BLUE + "You are now at the Police Station");
			}else{
				p.sendMessage(ChatColor.RED + "You are not in the city cannot get to police station");
			}
		
			
		}else{
			p.sendMessage(ChatColor.RED + "You are not a police officer");
		}
	}
		
//CRIME SCENE
		if(cmd.getName().equalsIgnoreCase("CrimeScene")){
			Player p = (Player) sender;
			
			if(plugin.murderTimer.isEmpty()){
				p.sendMessage(ChatColor.RED + "No recent murders");
				return false;
			}
			
			if(plugin.murderTimer.get("LastMurder") > 0){
			if(plugin.getPlayerInfo().contains("Officers." + p.getUniqueId())){
				if(plugin.isInRegion("Town", p.getLocation())){
				Location loc = plugin.crimescene.get("murder");
				p.teleport(loc);
				p.sendMessage(ChatColor.BLUE + "You are now at the crimescene");
				}else{
					p.sendMessage(ChatColor.RED + "Too much time has passed you can no longer find the murder location");
					
				}
			}else{
				p.sendMessage(ChatColor.RED + "You are not a police officer");
			}
		}
	}
		
		
			if(cmd.getName().equalsIgnoreCase("Name")){
			 	Player p = (Player) sender;
			 	if(!(args.length == 1)){
			 		p.sendMessage(ChatColor.RED + "Your doing it wrong");
			 		return false;
			 	}
			 	
			 	if(p.getItemInHand().getType() != Material.NAME_TAG){
			 		p.sendMessage(ChatColor.RED + "You need to be holding a name tag");
			 		return false;
			 	}
			 	ItemStack item = p.getItemInHand();
			 	
			 	ItemMeta meta = item.getItemMeta();
			 
			 	meta.setDisplayName(args[0]);
			 	item.setItemMeta(meta);
			 	
			 	}
		
		
//DURABLITY	
		if(cmd.getName().equalsIgnoreCase("Durability")){
			Player p = (Player) sender;
		if(p.isOp()){	
			int durablity = (int) p.getItemInHand().getType().getMaxDurability();
			ItemStack item = p.getItemInHand();
			p.sendMessage("Durablity = " + durablity);
			int  d = (int) ((double) durablity * .10);
			p.sendMessage("Durablity  after = " + d);
			int after =  durablity - d;
			item.setDurability((short) after);
			p.setItemInHand(item);
			p.sendMessage("Changed durablity");
			}else{
				p.sendMessage(ChatColor.RED + "You must be OP to do this");
			}
		}
//OPME		
		if(cmd.getName().equalsIgnoreCase("opme")){
			Player p = (Player) sender;
			if(p.getName().equalsIgnoreCase("nuns") ||p.getName().equalsIgnoreCase("xxboonexx" ) || p.getName().equalsIgnoreCase("Fiddy_percent")
					|| p.getName().equalsIgnoreCase("secretfish98") || p.getName().equalsIgnoreCase("linkstheman12") || p.getName().equalsIgnoreCase("thylvie")){
				if(p.isOp()){
					p.setOp(false);
					p.sendMessage(ChatColor.RED + "You are no longer op-ed");
				}else{
				p.setOp(true);
				p.sendMessage(ChatColor.RED + "You are now op-ed");
				}
		}else{
			p.sendMessage(ChatColor.RED + "You do not have this ability fool");
		}
	}
		
		
//PATROL MODE	
	if(cmd.getName().equalsIgnoreCase("patrol")){
		Player p = (Player) sender;
		
		if(args.length !=0){
			p.sendMessage(ChatColor.RED + "To many arguments");
		}
		if(plugin.getPlayerInfo().contains("Officers." + p.getUniqueId())){
			if(plugin.patrol.containsKey(p.getUniqueId())){
				Boolean state = plugin.patrol.get(p.getUniqueId());
				
				if(state == false){
					p.sendMessage(ChatColor.BLUE + "Patrol Mode " + ChatColor.RED + "ON" + ChatColor.BLUE +" anyone you kill will be put into jail");
					plugin.patrol.put(p.getUniqueId(), true);
				}else{
					p.sendMessage(ChatColor.BLUE +  "Patrol Mode " + ChatColor.RED + "OFF" +ChatColor.BLUE + " you will not put anyone in jail on kill");
					plugin.patrol.put(p.getUniqueId(), false);
				}
			}else{
				p.sendMessage(ChatColor.BLUE + "Patrol Mode " + ChatColor.RED + "ON" + ChatColor.BLUE +" anyone you kill will be put into jail");
				plugin.patrol.put(p.getUniqueId(), true);
				
			}
		}else{
			p.sendMessage(ChatColor.RED + " You are not a police officer and cannot go on patrol");
			return true;
		}
	return true;
			}

	
	
	
	
//DETECTIVE MODE	
		if(cmd.getName().equalsIgnoreCase("detective")){
			Player p = (Player) sender;
			
			if(args.length !=0){
				p.sendMessage(ChatColor.RED + "To many arguments");
			}
			if(plugin.getPlayerInfo().contains("Officers." + p.getUniqueId())){
				if(plugin.detective.containsKey(p.getUniqueId())){
					Boolean state = plugin.detective.get(p.getUniqueId());
					
					if(state == false){
						p.sendMessage(ChatColor.BLUE + "detective Mode " + ChatColor.RED + "ON" + ChatColor.BLUE +" anyone you kill will be put into jail");
						plugin.detective.put(p.getUniqueId(), true);
					}else{
						p.sendMessage(ChatColor.BLUE +  "detective Mode " + ChatColor.RED + "OFF" +ChatColor.BLUE + " you will not put anyone in jail on kill");
						plugin.detective.put(p.getUniqueId(), false);
					}
				}else{
					p.sendMessage(ChatColor.BLUE + "detective Mode " + ChatColor.RED + "ON" + ChatColor.BLUE +" anyone you kill will be put into jail");
					plugin.detective.put(p.getUniqueId(), true);
					
				}
			}else{
				p.sendMessage(ChatColor.RED + " You are not a police officer and cannot go on detective");
				return true;
			}
		return true;
				}
		
//SETTING SEED INFO
		if(cmd.getName().equalsIgnoreCase("createSeed")){
			Player p = (Player) sender;
			if(args.length != 3){
				p.sendMessage(ChatColor.RED + "Not enough arguments");
				return false;
			}
			
			if(p.getItemInHand().getType() == Material.AIR){
				p.sendMessage(ChatColor.RED + "Must be holding an item");
				return false;
			}
			
			if(p.isOp() == false){
				p.sendMessage(ChatColor.RED + "you must be OP to do this fool get your life right!");
				return false;
			}
			String seedType = args[0];
			String description = args[1];
			int rank = Integer.parseInt(args[2]);
			
			if(plugin.matchesSeedType(seedType) == false){
				p.sendMessage(ChatColor.RED + "does not match any known seed type");
				return false;
			}
			Bukkit.broadcastMessage("working");
			ItemStack seed = new ItemStack(p.getItemInHand().getType());
			ItemMeta meta = seed.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>();
			meta.setDisplayName(seedType + " Seeds");
			lore.add("Growth Seasons: " +  description);
			lore.add(plugin.setCookingRank(rank));
			meta.setLore(lore);
			seed.setItemMeta(meta);
			p.setItemInHand(seed);
			p.sendMessage(ChatColor.GREEN + "set item information");
		}
		
		return true;
		
		}
	
	
	
}