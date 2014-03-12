package io.github.FiddyPercent.fiddycraft;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import cosine.boseconomy.BOSEconomy;

public class FiddyCraft extends JavaPlugin {
	  private  FileConfiguration VillagerNames;
	  private File VillagerNameFile;
	  public final FiddyCraftListener fcl = new FiddyCraftListener(this);
	  ScoreboardManager manager = Bukkit.getScoreboardManager();
	  BOSEconomy economy = null;
	  
	  
    public void onDisable() {
        this.getLogger().info("FiddyCraft is Disabled");
        saveConfig();
        saveVillagerNames();
    }
	
    public void onEnable(){
    	loadConfig();
    	reloadVillagerNames();
        this.getLogger().info("FiddyCraft is Enabled");
        getServer().getPluginManager().registerEvents(new FiddyCraftListener(this), this);
        getCommand("vfname").setExecutor(new FiddyCraftCommands(this));
        getCommand("setJail").setExecutor(new FiddyCraftCommands(this));
        getCommand("Jail").setExecutor(new FiddyCraftCommands(this));
        getCommand("fcreload").setExecutor(new FiddyCraftCommands(this));
        this.loadBOSEconomy();
        
        for(Player target: Bukkit.getOnlinePlayers()){
        this.getJailScoreBoard(target);
        }
    	new BukkitRunnable(){
			@Override
			public void run() {
			}
		} .runTaskTimer(this, 0, 200);
			
  
    }
 
    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
       
    }
    
    public void reloadVillagerNames() {
        if (VillagerNames == null) {
        	VillagerNameFile = new File(getDataFolder(), "VillagerNames.yml");
        }
        VillagerNames = YamlConfiguration.loadConfiguration(VillagerNameFile);
        InputStream defConfigStream = this.getResource("VillagerNames.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            VillagerNames.setDefaults(defConfig);
        }
    }
    public FileConfiguration getVillagerNames() {
        if (VillagerNames == null) {
            this.reloadVillagerNames();
        }
        return VillagerNames;
    }
    
    public void saveVillagerNames() {
        if (VillagerNames == null || VillagerNameFile == null) {
        return;
        }
        try {
            getVillagerNames().save(VillagerNameFile);
            Bukkit.broadcastMessage("saving");
        } catch (IOException ex) {
            this.getLogger().log(Level.SEVERE, "Could not save config to " + VillagerNameFile, ex);
        }
    }
    
    public void loadBOSEconomy()
    {
        // Attempt to get the plugin instance for BOSEconomy.
        Plugin temp = this.getServer().getPluginManager().getPlugin("BOSEconomy");
        
        // Check whether BOSEconomy is loaded.
        if(temp == null)
            // BOSEconomy is not loaded on the server.
            economy = null;
        else
            // BOSEconomy is now stored in the "economy" variable.
            economy = (BOSEconomy)temp;
    }
    
    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        // only got here if we didn't return false
        return true;
    }
    
    public void setJailScoreBoard(Player target, String sentence){
    	Scoreboard board = this.manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("Jailed", "trigger");
		objective.setDisplayName("Jailed");
		int labor = Integer.parseInt(sentence);
		Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Labor:")); //Get a fake offline player
		score.setScore(labor);
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		target.setScoreboard(board);
	}
    
    public void getJailScoreBoard(Player target){
    	if(this.getConfig().contains("Jailed." + target.getName())){
    		Scoreboard board = this.manager.getNewScoreboard();
    		Objective objective = board.registerNewObjective("Jailed", "trigger");
    		objective.setDisplayName("Jailed");
    		int labor = this.getConfig().getInt("Jailed." + target.getName());
    		Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Labor:")); //Get a fake offline player
    		score.setScore(labor);
    		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    		target.setScoreboard(board);
    		
    	}
    	
    	
    }
    
}


