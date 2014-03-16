package io.github.FiddyPercent.fiddycraft;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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
	  private  FileConfiguration SignLocation;
	  private File SignLocationFile;
	  public final FiddyCraftListener fcl = new FiddyCraftListener(this);
	  ScoreboardManager manager = Bukkit.getScoreboardManager();
	  BOSEconomy economy = null;
	  
	  
    public void onDisable() {
        this.getLogger().info("FiddyCraft is Disabled");
        saveConfig();
        saveSignLocation();
    }
	
    public void onEnable(){
    	loadConfig();
    	reloadSignLocation();
        this.getLogger().info("FiddyCraft is Enabled");
        getServer().getPluginManager().registerEvents(new FiddyCraftListener(this), this);
        getCommand("vfname").setExecutor(new FiddyCraftCommands(this));
        getCommand("setJail").setExecutor(new FiddyCraftCommands(this));
        getCommand("Jail").setExecutor(new FiddyCraftCommands(this));
        getCommand("fcreload").setExecutor(new FiddyCraftCommands(this));
        getCommand("setRelease").setExecutor(new FiddyCraftCommands(this));
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
    
    public void reloadSignLocation() {
        if (SignLocation == null) {
        	SignLocationFile = new File(getDataFolder(), "SignLocation.yml");
        }
        SignLocation = YamlConfiguration.loadConfiguration(SignLocationFile);
        InputStream defConfigStream = this.getResource("SignLocation.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            SignLocation.setDefaults(defConfig);
        }
    }
    public FileConfiguration getSignLocation() {
        if (SignLocation == null) {
            this.reloadSignLocation();
        }
        return SignLocation;
    }
    
    public void saveSignLocation() {
        if (SignLocation == null || SignLocationFile == null) {
        return;
        }
        try {
            getSignLocation().save(SignLocationFile);
            Bukkit.broadcastMessage("saving");
        } catch (IOException ex) {
            this.getLogger().log(Level.SEVERE, "Could not save config to " + SignLocationFile, ex);
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
    	target.setScoreboard(manager.getNewScoreboard());
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
    		target.setScoreboard(manager.getNewScoreboard());
    		Scoreboard board = this.manager.getNewScoreboard();
    		Objective objective = board.registerNewObjective("Jailed", "trigger");
    		objective.setDisplayName("Jailed");
    		int labor = this.getConfig().getInt("Jailed." + target.getName());
    		Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Labor:")); //Get a fake offline player
    		score.setScore(labor);
    		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    		target.setScoreboard(board);
    		
    	}else{
    		Bukkit.getServer().getLogger().info(ChatColor.RED + "get Jailed not have name");
    	}
    }
    	
    public void newJailScore(Player p, int itemvalue){
     	Objective objective = p.getScoreboard().getObjective("Jailed");
		Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Labor:")); //Get a fake offline player	
    	int currentscore = score.getScore();
    	int newscore = currentscore - itemvalue;
    	score.setScore(newscore);
    }
    
    public int getScore(Player p, Objective ob, String sb){
    	Objective objective = ob;
    	Score score = objective.getScore(Bukkit.getOfflinePlayer(sb));
    	int s = score.getScore();
    	return s;
    }
    
    @SuppressWarnings("unused")
    public boolean isSignLocation(Location loc){
    	
    	double lx = loc.getX();
		double ly = loc.getY();
    	double lz = loc.getZ();
    	if(this.getSignLocation().contains("SignLocations" )){
    	
    			double Xlocation = this.getSignLocation().getDouble("SignLocation.X");
    			double Ylocation = this.getSignLocation().getDouble("SignLocation.Y");
    			double Zlocation = this.getSignLocation().getDouble("SignLocation.Z");
    					;
    			if(loc.getX() == Xlocation && loc.getY() == Ylocation && loc.getZ() == Zlocation ){
    				return true;
    			}
    			
    		
    		return true;
    	}else{
    		
    		return false;
    	}
    	
    }
    @SuppressWarnings("deprecation")
	public void takeOne(Player p, ItemStack i){
    	ArrayList<Integer> amount = new ArrayList<Integer>();
    	p.sendMessage(ChatColor.RED + "" + i.getAmount());
    	PlayerInventory stuff = p.getInventory();
    	for(ItemStack pp : stuff){
    		if(pp == i){
    			int d = amount.get(0) + 1;
    			amount.set(0, d);
    		}
    	}
    	
    	
    	  if(amount.get(0) <= 1){
    		  p.sendMessage(i.getAmount()  +"");
    	    p.getInventory().removeItem(i);
    	    p.updateInventory();
    	  }else{
    		  
    	  if(amount.get(0) > 1){
    	    i.setAmount(i.getAmount() - 1);
    	    p.sendMessage(" - 1 :" + i.getAmount());
    	    p.updateInventory();
    	    
    	    //if it doesnt work, try updating the item in the inventory.
    	  }
    	}
     amount.clear();
    }
  
}


