package io.github.FiddyPercent.fiddycraft;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
	  public ArrayList<Boolean> TrialStart;
	  public ArrayList<String> defendant;
	  public ArrayList<String> Prosecutor;
	  public ArrayList<String> Defence;
	  public ArrayList<String> Judge;
	  public ArrayList<String> Jury;
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
        getCommand("trial").setExecutor(new FiddyCraftCommands(this));
        getCommand("Lawyer").setExecutor(new FiddyCraftCommands(this));
        getCommand("setdefense").setExecutor(new FiddyCraftCommands(this));
        getCommand("setdefendant").setExecutor(new FiddyCraftCommands(this));
        getCommand("setprosecutor").setExecutor(new FiddyCraftCommands(this));
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
		ScoreboardManager manager = Bukkit.getScoreboardManager();
   		if(manager.getMainScoreboard().getObjective("Jailed") == null){
    	Scoreboard board = manager.getMainScoreboard();
		Objective objective = board.registerNewObjective("Jailed", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		int labor = Integer.parseInt(sentence);
		Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Labor:")); //Get a fake offline player
		score.setScore(labor);
		target.setScoreboard(board);
}
   		if(target.getScoreboard().getObjective("Jailed") == null){
   			Scoreboard board = manager.getMainScoreboard();
   			Objective objective = board.getObjective("Jailed");
   			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
   			int labor =  Integer.parseInt(sentence);
   			Score score = target.getScoreboard().getObjective("Jailed").getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Labor:"));
    		score.setScore(labor);
    		target.setScoreboard(board);
    		
    		
   		}else{
   			target.sendMessage("else");
   			Objective objective = target.getScoreboard().getObjective("Jailed");
   			Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Labor:")); //Get a fake offline player
   			int labor =  Integer.parseInt(sentence);
    		score.setScore(labor);
    		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    		;
   		}
	}
	

    
   public void getJailScoreBoard(Player target){
   	if(this.getConfig().contains("Jailed." + target.getName())){
   		
   			ScoreboardManager manager = Bukkit.getScoreboardManager();
   		if(manager.getMainScoreboard().getObjective("Jailed") == null){
   			Scoreboard board = manager.getMainScoreboard();
   			Objective objective = board.registerNewObjective("Jailed", "dummy");
   			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    		int labor = this.getConfig().getInt("Jailed." + target.getName());
    		Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Labor:")); //Get a fake offline player
    		score.setScore(labor);
    		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    		target.setScoreboard(target.getScoreboard());
   		}else if(target.getScoreboard().getObjective("Jailed") == null){
   			Scoreboard board = manager.getMainScoreboard();
   			Objective objective = board.getObjective("Jailed");
   			Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Labor:")); //Get a fake offline player
   			int labor = this.getConfig().getInt("Jailed." + target.getName());
    		score.setScore(labor);
    		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    		target.setScoreboard(board);
   		}
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
		p.updateInventory();
    	if(p.getInventory().contains(i.getType())){
    		ItemStack[] s = p.getInventory().getContents();
    		p.sendMessage("Yes you have raw fish");
    		p.sendMessage("I have " +s.length + " " + i.getType().toString());
    		
    		
    		
    	}else{
    p.sendMessage("No");
	}
	}
	
	public boolean hasArmor(Player p, Material h, Material c, Material l, Material b){
		ItemStack ht = p.getEquipment().getHelmet();
		ItemStack ct = p.getEquipment().getChestplate();
		ItemStack ls = p.getEquipment().getLeggings();
		ItemStack bs = p.getEquipment().getBoots();
		if(ht != null && ct != null && ls != null && bs != null){
		
		Material helmet = p.getEquipment().getHelmet().getType();
		Material chest = p.getEquipment().getChestplate().getType();
		Material legs = p.getEquipment().getLeggings().getType();
		Material boots = p.getEquipment().getBoots().getType();
		if(helmet == h && chest == c && legs == l && boots == b){
			return true;
		}else{
			return false;
		}
		}else{
			return false;
		}
		
	}
}


