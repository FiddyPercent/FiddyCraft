package io.github.FiddyPercent.fiddycraft;

import io.github.FiddyPercent.fiddycraft.Animal.Animals;
import io.github.FiddyPercent.fiddycraft.Animal.animalUtility;
import io.github.FiddyPercent.fiddycraft.Jobs.FcFarmers;
import io.github.FiddyPercent.fiddycraft.Plant.PlantUtil;
import io.github.FiddyPercent.fiddycraft.Plant.Plants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.Mushroom;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import cosine.boseconomy.BOSEconomy;

public class FiddyCraft extends JavaPlugin {
	  private  FileConfiguration PlayerInfo;
	  private File PlayerInfoFile;
	  private  FileConfiguration AnimalData;
	  private File AnimalDataFile;
	  private  FileConfiguration PlantInfo;
	  private File PlantInfoFile;
	  public final FiddyCraftListener fcl = new FiddyCraftListener(this);
	  public HashMap<String, Boolean> TrialStart = new HashMap<String, Boolean>();
	  public HashMap<String, Boolean> TrialReady = new HashMap<String, Boolean>();
	  public HashMap<String, Integer> openingStatementProsecution = new HashMap<String, Integer>();
	  public HashMap<String, Integer> testimony = new HashMap<String, Integer>();
	  public HashMap<String, Integer> closingStatementProsecution = new HashMap<String, Integer>();
	  public HashMap<String, Integer> closingStatementdefense= new HashMap<String, Integer>();
	  public HashMap<String, Integer> evidence = new HashMap<String, Integer>();
	  public HashMap<String, Integer> TrialTime = new HashMap<String, Integer>();
	  public HashMap<UUID, Integer> animalProduce = new HashMap<UUID, Integer>();
	  public HashMap<UUID, Integer> pvpLoggers = new HashMap<UUID, Integer>();
	  public HashMap<String, String> Charge = new HashMap<String, String>();
	  public ArrayList<String> defendant = new ArrayList<String>();
	  public ArrayList<String> Prosecutor = new ArrayList<String>();
	  public ArrayList<String> defense = new ArrayList<String>();
	  public ArrayList<String> Judge = new ArrayList<String>();
	  public ArrayList<String> willLawyer = new ArrayList<String>();
	  public ArrayList<String> Jury = new ArrayList<String>();
	  public ArrayList<String> items = new ArrayList<String>();
	  public HashMap<String, String> verdict  = new HashMap<String, String>();
	  public HashMap<UUID, UUID> attackedLast  = new HashMap<UUID, UUID>();
	  public HashMap<String, Integer> drunkLvl = new HashMap<String, Integer>();
	  public HashMap<UUID, Boolean> patrol = new HashMap<UUID, Boolean>();
	  public HashMap<UUID, Boolean> detective = new HashMap<UUID, Boolean>();
	  public HashMap<String, Boolean> Judgement = new HashMap<String, Boolean>();
	  public HashMap<String, Integer> murderTimer = new HashMap<String, Integer>(); 
	  public ArrayList<String > potlore = new ArrayList<String>();
	  public HashMap<String, Location> crimescene = new HashMap<String, Location>();
	  private static ScoreboardManager manager;
	  static HashMap<String, Scoreboard> boards = new HashMap<String, Scoreboard>();
	  private static HashMap<String, Integer> sentences = new HashMap<String, Integer>();
	  private static final String OBJ_FORMAT = "§a%s";
	  private static final String SCORE_NAME = "§bLabor";
	  public HashMap<String, Boolean> newday = new HashMap<String, Boolean>();
	  FcPerm fp = new FcPerm(this);
	 
	  BOSEconomy economy = null;
	  
	  
    public void onDisable() {
        this.getLogger().info("FiddyCraft is Disabled");
        saveConfig();
        savePlayerInfo();
        savePlantInfo();
        saveAnimalData();
    	this.TrialStart.clear();
    	this.closingStatementdefense.clear();
    	this.closingStatementProsecution.clear();
    	this.evidence.clear();
    	this.defendant.clear();
    	this.Prosecutor.clear();
    	this.defense.clear();
    	this.Judge.clear();
    	this.Judgement.clear();
    }
    public void onEnable(){
    	loadConfig();
    	reloadPlayerInfo();
    	reloadAnimalData();
    	this.addefault();
    	reloadPlantInfo();
    	this.Recipes();
        this.getLogger().info("FiddyCraft is Enabled");
        getServer().getPluginManager().registerEvents(new FiddyCraftListener(this), this);
        getCommand("vfname").setExecutor(new FiddyCraftCommands(this));
        getCommand("setJob").setExecutor(new FcFarmerCommands(this));
        getCommand("setJail").setExecutor(new FiddyCraftCommands(this));
        getCommand("Jail").setExecutor(new FiddyCraftCommands(this));
        getCommand("fcreload").setExecutor(new FiddyCraftCommands(this));
        getCommand("trial").setExecutor(new FiddyCraftCommands(this));
        getCommand("Lawyer").setExecutor(new FiddyCraftCommands(this));
        getCommand("setdefense").setExecutor(new FiddyCraftCommands(this));
        getCommand("setdefendant").setExecutor(new FiddyCraftCommands(this));
        getCommand("setRelease").setExecutor(new FiddyCraftCommands(this));
        getCommand("setprosecutor").setExecutor(new FiddyCraftCommands(this));
        getCommand("addPolice").setExecutor(new FiddyCraftCommands(this));
        getCommand("RemovePolice").setExecutor(new FiddyCraftCommands(this));
        getCommand("patrol").setExecutor(new FiddyCraftCommands(this));
        getCommand("rTest").setExecutor(new FiddyCraftCommands(this));
        getCommand("Name").setExecutor(new FiddyCraftCommands(this));
        getCommand("setRegion2").setExecutor(new FiddyCraftCommands(this));
        getCommand("setRegion1").setExecutor(new FiddyCraftCommands(this));
        getCommand("joinJury").setExecutor(new FiddyCraftCommands(this));
        getCommand("LeaveJury").setExecutor(new FiddyCraftCommands(this));
        getCommand("setLocation").setExecutor(new FiddyCraftCommands(this));
        getCommand("Lawyer").setExecutor(new FiddyCraftCommands(this));
        getCommand("opme").setExecutor(new FiddyCraftCommands(this));
        getCommand("CrimeScene").setExecutor(new FiddyCraftCommands(this));
        getCommand("PoliceStation").setExecutor(new FiddyCraftCommands(this));
        getCommand("Durability").setExecutor(new FiddyCraftCommands(this));
        getCommand("addLawyer").setExecutor(new FiddyCraftCommands(this));
        getCommand("removeLawyer").setExecutor(new FiddyCraftCommands(this));
        getCommand("PoliceInfo").setExecutor(new FiddyCraftCommands(this));
        getCommand("LawyerInfo").setExecutor(new FiddyCraftCommands(this));
        getCommand("myInfo").setExecutor(new FiddyCraftCommands(this));
        getCommand("release").setExecutor(new FiddyCraftCommands(this));
        getCommand("Defend").setExecutor(new FiddyCraftCommands(this));
        getCommand("StartTrial").setExecutor(new FiddyCraftCommands(this));
        getCommand("JUDGEMENT").setExecutor(new FiddyCraftCommands(this));
        getCommand("EndStatement").setExecutor(new FiddyCraftCommands(this));
        getCommand("FireLawyer").setExecutor(new FiddyCraftCommands(this));
        getCommand("StopTrial").setExecutor(new FiddyCraftCommands(this));
        getCommand("createSeed").setExecutor(new FiddyCraftCommands(this));
    	new Permission ("FiddyCraft.noob");
		new Permission ("FiddyCraft.police");
		new Permission ("FiddyCraft.assasin");
		new Permission ("FiddyCraft.thug");
		new Permission ("FiddyCraft.judge");
		new Permission ("FiddyCraft.detective");
		new Permission ("FiddyCraft.rich");
		new Permission ("FiddyCraft.common");
		new Permission ("FiddyCraft.poor ");
        manager = Bukkit.getScoreboardManager();
        this.loadBOSEconomy();
        this.getLogger().info(ChatColor.RED + "Right before Runnable");
        new BukkitRunnable(){
  			@Override
  			public void run() {
  			
  			
  			getAnimalCooldown();
  			newDay();
  			
  			if(newday.isEmpty() == false){
  			if(Bukkit.getWorld("world").getTime() < 1000){
  				newday.clear();
  			}
  		}
 				if(murderTimer.isEmpty() == false){
					int time = murderTimer.get("LastMurder");
					int rt = time - 100;
					murderTimer.put("LastMurder", rt);
					if(murderTimer.get("LastMurder") == 0){
						murderTimer.clear();
						crimescene.clear();
					}
					
				}
				
 				if(Bukkit.getOnlinePlayers().length > 0){
 				 			 for (Player player : Bukkit.getOnlinePlayers()) {
 				 				 
 				 				 if(player.getItemInHand().getType() == Material.COMPASS){
 				 					 
 				 					// Material item = player.getItemInHand().getType();
 				 					
 				 				 }	 
 				 			if(getConfig().contains("Jailed." + player.getUniqueId().toString())){
 				 				String playerName = player.getName();
 				 				int oldSentence = sentences.containsKey(playerName) ? sentences.get(playerName): getConfig().getInt("Jailed." + player.getUniqueId().toString()) ; 
 				                 int newSentence = oldSentence;
 				                  setJailSentence(player, newSentence); 				                  
 				 				}
 				 			}
 						}				
 				
 				
 				if(!pvpLoggers.isEmpty()){
 					Player[] p = Bukkit.getOnlinePlayers();
 					for(Player o : p){
 						if(pvpLoggers.containsKey(o.getUniqueId())){
 							int time = pvpLoggers.get(o.getUniqueId());
 							int newtime= time - 100;
 							pvpLoggers.put(o.getUniqueId(), newtime);
 							
 							if(time <= 0){
 								pvpLoggers.remove(o.getUniqueId());
 								o.sendMessage(ChatColor.GRAY + "It is safe to log out now");
 							}
 						}
 					}
 				}
 				
//OPENING Statement				
 				
				if(!TrialStart.isEmpty() && TrialStart.get("Trial") == true){
					
					if(openingStatementProsecution.containsKey("Trial")){
						String t = "Trial";
						int op = openingStatementProsecution.get("Trial");
						int newop = op - 100;
						openingStatementProsecution.put(t, newop);
						CourtSigns("OpeningStatement", op);
					if(op <= 0 ){
						if(isPlayer(Judge.get(0)) && isPlayer( Prosecutor.get(0)) && isPlayer(defense.get(0))){
							Player j = Bukkit.getPlayer(Judge.get(0));
							Player pro = Bukkit.getPlayer(Prosecutor.get(0));
							Player def = Bukkit.getPlayer(defense.get(0));
							j.chat(ChatColor.DARK_PURPLE +  "Now it is time to move on with the trial, Prosecution, you may now show evidence or use witness testemony in just a moment");
							j.sendMessage(ChatColor.YELLOW + "I need to pay attention and be unbiased. Remember hard evidence is stronger than testimony.");
							pro.sendMessage(ChatColor.YELLOW + "I have 10 minutes total to prove the defense guilty I need to make a solid case right off the bat");
							def.sendMessage(ChatColor.YELLOW + "Now the prosecution will present evidence to win this case I have 10 minutes to get not guilty");
							openingStatementProsecution.clear();
							evidence.put("Trial", 12000);
						}
					}
				}
//CLOSING Statement					
					if(closingStatementProsecution.containsKey("Trial")){
						String t = "Trial";
						int op = closingStatementProsecution.get("Trial");
						int newop = op - 100;
						closingStatementProsecution.put(t, newop);
						CourtSigns("Closing Statement", op);
						if(op <= 0 ){
							if(isPlayer(Judge.get(0)) && isPlayer( Prosecutor.get(0)) && isPlayer(defense.get(0))){
								Player j = Bukkit.getPlayer(Judge.get(0));
								Player pro = Bukkit.getPlayer(Prosecutor.get(0));
								Player def = Bukkit.getPlayer(defense.get(0));
								j.chat(ChatColor.DARK_PURPLE + "That is enough, time for the defense closing Statement");
								j.sendMessage(ChatColor.YELLOW + "Time is up for the Prosecutions closing statement, now it’s time for the defense Attorneys final statement.");
								pro.sendMessage(ChatColor.YELLOW + "Time is up for my closing Statement the Judge will decide the verdict after the defense Attorneys Statement.");
								def.sendMessage(ChatColor.YELLOW + "The Proseuction's closing Statement is over now is your last chance to win the case.");
								closingStatementProsecution.clear();
								closingStatementdefense.put("Trial", 2400);
							}
						}
					}
//CLOSING Statement defense					
					if(closingStatementdefense.containsKey("Trial")){
						String t = "Trial";
						int op = closingStatementdefense.get("Trial");
						int newop = op - 100;
						closingStatementdefense.put(t, newop);
						CourtSigns("Closing Statement", op);
						if(op <= 0 ){
							if(isPlayer(Judge.get(0)) && isPlayer( Prosecutor.get(0)) && isPlayer(defense.get(0))){
								Player j = Bukkit.getPlayer(Judge.get(0));
								Player pro = Bukkit.getPlayer(Prosecutor.get(0));
								Player def = Bukkit.getPlayer(defense.get(0));
								j.chat(ChatColor.DARK_PURPLE + "That is enough I will now decide the verdict");
								j.sendMessage(ChatColor.YELLOW + "Both sides have been heard please give the points to the proper side and place a verdict");
								pro.sendMessage(ChatColor.YELLOW + "Time is up for the closing Statement the Judge will now decide");
								def.sendMessage(ChatColor.YELLOW + "This is it, time is up and the judge has the final call");
								closingStatementdefense.clear();
								Judgement.put(j.getName(), true);
							}
						}
					}
//TRIAL HEARING					
					if(evidence.containsKey("Trial")){
						String t = "Trial";
						int op = evidence.get("Trial");
						int newop = op - 100;
						evidence.put(t, newop);
					
						CourtSigns("Trial hearing", op);
					if(op <= 0){
					if(isPlayer(Judge.get(0)) && isPlayer( Prosecutor.get(0)) && isPlayer(defense.get(0))){
						Player j = Bukkit.getPlayer(Judge.get(0));
						Player pro = Bukkit.getPlayer(Prosecutor.get(0));
						Player def = Bukkit.getPlayer(defense.get(0));
						j.chat(ChatColor.DARK_PURPLE + "I have heard enough you my now start your closing Statement Prosecution");
						j.sendMessage(ChatColor.YELLOW + "Listen carefully to each Statements and try to decide which one is right");
						pro.sendMessage(ChatColor.YELLOW + "Time to put everything I talked about together and win my case");
						def.sendMessage(ChatColor.YELLOW + "Remember what the prosecutions points are and counter them when it is my turn");
						evidence.clear();
						closingStatementProsecution.put("Trial", 2400);
						}
					}
				}
			}
				
			
		if(getConfig().getString("Drunk") != null){	
			
			Player[] oplayer =	Bukkit.getOnlinePlayers();
			
			for(Player op : oplayer){
				if(getConfig().contains("Drunk." + op.getUniqueId().toString())){
									
					int drunkPoints = getConfig().getInt("Drunk." + op.getUniqueId().toString());
					DrunkState(drunkPoints, op  );
					
					int newPoints = drunkPoints - 2;
					
					getConfig().set("Drunk." + op.getUniqueId().toString(), newPoints);
					
					if(newPoints < 1 ){
						getConfig().set("Drunk." + op.getUniqueId().toString(), null);
						saveConfig();
							}
						}
					}
				}
 
  			}
  		} .runTaskTimer(this, 0, 100);
  		
  		this.getLogger().info(ChatColor.GREEN + "Right after Runnable");
    }
    
    public boolean isPlantOwned(Location loc){
    	if(!this.getPlantInfo().contains("Farmer")){
    		return false;
    	}else{
    		Set<String> pO = this.getPlantInfo().getConfigurationSection("Farmer").getKeys(false);
    		for(String g :pO){
    			
    			if(this.getPlantInfo().contains("Farmer." +  g + ".Plants." +this.getPlantLocationID(loc))){
    				return true;
    			}else{
    				return false;
    			}
    		}
    	}
    	return false;
    	
    }
    
    
    

    
    public void getAnimalCooldown(){
    	 animalUtility au = new animalUtility(this);
    	 au.animalCoolDown();
    }
    
    
    public void setNewPlayer(Player p ){
    	if(!this.getPlayerInfo().contains("Players.")){
    		this.getPlayerInfo().set("Players", "Yo mama");
    		this.savePlayerInfo();
    	}
    	
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Name", p.getName());
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString() +".StartDate" , this.getTimeStamp());
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString() + ".Job", "Unemployed");
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString() + ".Arrests" , 0);
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString() + ".Convictions", 0);
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString() + ".Murders", 0);
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Wanted", false);
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString() + ".On Parol", false);
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Pending Trial", false);
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Can Lawyer", false);
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Max Food Level", 2);
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Max Crops", 16);
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Max Animals", 4);
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString() + ".AnimalList", null);
    	this.getPlayerInfo().set("Players."+ p.getUniqueId().toString() + ".Recipe List",null);
    	this.getPlayerInfo().set("Players."+ p.getUniqueId().toString() + ".BonusGrow List", null );
		this.getPlayerInfo().set("Players." + p.getUniqueId().toString() + ".Gov Job","None");
    	this.savePlayerInfo();
    }
    
    public void updateOldPlayerInfo(Player p){
     	this.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Wanted", false);
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString() + ".On Parol", false);
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Can Lawyer", false);
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Pending Trial", false);
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Max Food Level", 2);
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Max Crops", 16);
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString()+ ".Max Animals", 4);
    	this.getPlayerInfo().set("Players." +p.getUniqueId().toString() + ".AnimalList", null);
    	this.getPlayerInfo().set("Players."+ p.getUniqueId().toString() + ".Recipe List",null);
    	this.getPlayerInfo().set("Players."+ p.getUniqueId().toString() + ".BonusGrow List", null );
		this.getPlayerInfo().set("Players." + p.getUniqueId().toString() + ".Gov Job","None");
    	this.savePlayerInfo();
    }
 
    public void sendPlayerToSpawn(Player p){
    	double xs =  (double) this.getConfig().getDouble("Location." + "Start.X");
		double ys =  (double) this.getConfig().getDouble("Location." + "Start.Y");
		double zs =  (double) this.getConfig().getDouble("Location." + "Start.Z");
		Location loc = new Location(Bukkit.getWorld("world"), xs,ys,zs);
		p.teleport(loc);
    }
    
 
    

    
    public Location getLocationFromString(String locationString){
    	
    	ArrayList<Integer> split = new ArrayList<Integer>();
    	String[] sp = locationString.split("~");
    	
    	for(String s : sp){
    		
    		int u = Integer.parseInt(s);
    		split.add(u);
    	}
    	Location loc = new Location(Bukkit.getWorld("world"), split.get(0), split.get(1), split.get(2));
    	return loc;
    }

 
   
  
    
// 	TODO: find out what the heck this is for.
    public void addExpFarmer(Player p, double add){
    	FcPlayers fcp = new FcPlayers(this, p);
    if(fcp.getPlayerJob().equalsIgnoreCase("Farmer")){	
    	FcFarmers fc = new FcFarmers(this, p);
    	fc.addExp(add);
    	}
   }
    

    
    public String getTimeStamp(){
    	String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(Calendar.getInstance().getTime());
    	return timeStamp;
    }
    
    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
       
    }
    
    
	public void reloadPlayerInfo() {
        if (PlayerInfo == null) {
        	PlayerInfoFile = new File(getDataFolder(), "PlayerInfo.yml");
        	}
        PlayerInfo = YamlConfiguration.loadConfiguration(PlayerInfoFile);
        InputStream defConfigStream = this.getResource("PlayerInfo.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            PlayerInfo.setDefaults(defConfig);
        }
    }
    
    public FileConfiguration getPlayerInfo() {
        if (PlayerInfo == null) {
            this.reloadPlayerInfo();
        }
        return PlayerInfo;
    }
    
    public void savePlayerInfo() {
        if (PlayerInfo == null || PlayerInfoFile == null) {
        return;
        }
        try {
            getPlayerInfo().save(PlayerInfoFile);
            
        } catch (IOException ex) {
            this.getLogger().log(Level.SEVERE, "Could not save config to " + PlayerInfoFile, ex);
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
    
    public void reloadPlantInfo() {
        if (PlantInfoFile == null) {
        	PlantInfoFile = new File(getDataFolder(), "PlantInfo.yml");
        }
        PlantInfo = YamlConfiguration.loadConfiguration(PlantInfoFile);
        InputStream defConfigStream = this.getResource("PlantInfo.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            PlantInfo.setDefaults(defConfig);
        }
    }
    public FileConfiguration getPlantInfo() {
        if (PlantInfo == null) {
            this.reloadPlantInfo();
        }
        return PlantInfo;
    }
    
    public void savePlantInfo() {
        if (PlantInfo == null || PlantInfoFile == null) {
        	this.reloadPlantInfo();
     
        }
        try {
            getPlantInfo().save(PlantInfoFile);
           ////Bukkit.broadcastMessage("saving");
        } catch (IOException ex) {
            this.getLogger().log(Level.SEVERE, "Could not save config to " + PlantInfoFile, ex);
        }
    }



    
    public static void setJailSentence(Player player, int sentence) {
        OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(SCORE_NAME);
       
        String playerName = player.getName();
     
        sentences.put(playerName, sentence);
     
        Scoreboard board = getScoreboard(player);
     
        Objective objective = board.getObjective(playerName);
        if (objective == null) {
            objective = board.registerNewObjective(playerName, "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(String.format(OBJ_FORMAT,player.getName()));
        }
        
        
        Score score = objective.getScore(offPlayer);
        score.setScore(sentence);
        setScoreboard(player, board);
      
    }
    
    public void addConfig(String path, String info){
    	getConfig().set(path, info);
    	saveConfig();
    }
    
    public void removeConfig(String path){
    	getConfig().set(path, null);
    	saveConfig();
    }
	
    public static Scoreboard getScoreboard(Player player) {
        String playerName = player.getName();
        return boards.containsKey(playerName) ? boards.get(playerName) : manager.getNewScoreboard();
    }
 
    public static void setScoreboard(Player player, Scoreboard board) {
        String playerName = player.getName();
     
        boards.put(playerName, board);
     
        if (player.getScoreboard() != board) {
            player.setScoreboard(board);
        }
    }
	
//SET JAIL SCORE
	public void newJailScore(Player p, int itemvalue){
    	
          int oldSentence = this.getScore(p);
          int newSentence =  (oldSentence - itemvalue);
          setJailSentence(p, newSentence);
    	
    	
    	Scoreboard board = getScoreboard(p);
     	Objective objective = board.getObjective(p.getName());
		
		Score score = objective.getScore(Bukkit.getOfflinePlayer(SCORE_NAME)); //Get a fake offline player	
    	int currentscore = score.getScore();
    	int newscore = currentscore - itemvalue;
    	score.setScore(newscore);
    }
//GET JAIL SCORE
    public int getScore(Player p){
    	Scoreboard board = getScoreboard(p);
    	Objective objective = board.getObjective(p.getName());
		Score score = objective.getScore(Bukkit.getOfflinePlayer(SCORE_NAME));
    	int s = score.getScore();
    	return s;
    }
    
    

   


	@SuppressWarnings("deprecation")
	public void takeOne(Player p, ItemStack i){
		if(p.getItemInHand().getType() == i.getType()){
			  if(p.getItemInHand().getAmount() == 1){
			p.setItemInHand(new ItemStack(Material.AIR));
	           Bukkit.getWorld("world").playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 1);
	           p.updateInventory();
	          }else{
	             
	          if(p.getItemInHand().getAmount() > 1){
	            p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
	          }
	        }	
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
	
	public boolean isPlayer(String name){
	 for(Player p: Bukkit.getOnlinePlayers()){
	       if(p.getName().equalsIgnoreCase(name)){
	    	   return true;
	       }
	   }
	 		return false;
	}
	
	public void getItems(Player p){
		ItemStack[] it = p.getInventory().getContents();
		int n = 0;
		for(ItemStack y : it){
			if(y != null){
				String stuff = y.getType().toString();
			if(stuff.equalsIgnoreCase("Potion")){
				PotionMeta pm = (PotionMeta) y.getItemMeta();
				
				potlore.add("Finger Prints Results:");
				potlore.add(p.getName());
				
				List<String> list = potlore.subList(0, 1);
				
				pm.getDisplayName();
				
				pm.setLore(list);
				n++;
				}
			}
		}
		items.add("Number of potions " + n);
	}
	

	
	
	public boolean isInRegion(String areaName, Location loc){
		if(!this.getConfig().contains("Regions."+areaName)){
		return false;	
		}
		
		ArrayList<Integer> X = new ArrayList<Integer>();
		ArrayList<Integer> x = new ArrayList<Integer>();
		ArrayList<Integer> Y = new ArrayList<Integer>();
		ArrayList<Integer> y = new ArrayList<Integer>();
		ArrayList<Integer> Z = new ArrayList<Integer>();
		ArrayList<Integer> z = new ArrayList<Integer>();
		
		int x1 = (int) this.getConfig().getDouble("Regions." + areaName +".X");
		int y1 = (int) this.getConfig().getDouble("Regions." + areaName +".Y");
		int z1 = (int) this.getConfig().getDouble("Regions." + areaName +".Z");
		int x2 = (int) this.getConfig().getDouble("Regions." + areaName +".X2");
		int y2 = (int) this.getConfig().getDouble("Regions." + areaName +".Y2");
		int z2 = (int) this.getConfig().getDouble("Regions." + areaName +".Z2");
	
		
		if(x1 > x2){
			X.add(x1);
			x.add(x2);
		}else{
			X.add(x2);
			x.add(x1);
		}

		if(y1 > y2){
			Y.add(y1);
			y.add(y2);
		}else{
			Y.add(y2);
			y.add(y1);
		}
		
		if(z1 > z2){
			Z.add(z1);
			z.add(z2);
		}else{
			Z.add(z2);
			z.add(z1);
		}
		
		int lx = (int) loc.getX();
		int ly = (int) loc.getY();
		int lz = (int) loc.getZ();
		
		int bigx = X.get(0);
		int littlex = x.get(0);
		int bigy = Y.get(0);
		int littley = y.get(0);
		int bigz = Z.get(0);
		int littlez = z.get(0);
		
		
		if(lx <= bigx && lx >= littlex  && ly <= bigy && ly >= littley && lz <= bigz && lz >= littlez){
			x.clear();
			X.clear();
			Y.clear();
			y.clear();
			Z.clear();
			z.clear();
			return true;
		}else{
		return false;
		}
	}
	
	
	public void nearMessage(Player p, String msg){
		List<Entity> near = p.getNearbyEntities(25, 15, 25);
		for(Entity y : near){
			if(y instanceof Player){
				Player c = (Player) y;
				c.sendMessage(msg);
			}
		}
	}
	
	
	
	public void  DrunkState (int DrunkPoints, Player p){
		int dp = DrunkPoints;
//SLOW
		if(dp < 30 && dp > 19){
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600000, 0));
			if(p.hasPotionEffect(PotionEffectType.BLINDNESS)){
			p.removePotionEffect(PotionEffectType.BLINDNESS);
		}
			if(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
			p.removePotionEffect(PotionEffectType.NIGHT_VISION);
	}
		}else if(dp > 29 && dp < 99){
//SLOW AND CONFUSED
			 p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600000, 0));
			 p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1000000, 2));
			 if(p.hasPotionEffect(PotionEffectType.CONFUSION)){
				 	p.removePotionEffect(PotionEffectType.CONFUSION);
			 		}
				if(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
						p.removePotionEffect(PotionEffectType.NIGHT_VISION);
				}
//SLOW CONFUSION BLIDNESS					}
		}else if(dp > 100 && dp < 149){
			 p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 0));
			 p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1000000, 2));
			 p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 2));
			 if(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
				p.removePotionEffect(PotionEffectType.NIGHT_VISION);
			 }
		}else if(dp > 150){
//SLOW CONFUSION BLINDNESS NIGHTVISION
			 p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 0));
			 p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1000000, 2));
			 p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 2));
			 p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 2));
		}else if(dp < 20){
			if(p.hasPotionEffect(PotionEffectType.SLOW)){
			    p.removePotionEffect(PotionEffectType.SLOW);
			if(p.hasPotionEffect(PotionEffectType.CONFUSION)){
				p.removePotionEffect(PotionEffectType.CONFUSION);
				}
			}
		}
	}

	
	public boolean patrolOn(Player p ){
		if(this.patrol.containsKey(p.getUniqueId())){
			if( this.patrol.get(p.getUniqueId()) == true){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		
	}
	
	public boolean detectiveOn(Player p ){
		if(this.patrol.containsKey(p.getUniqueId())){
			if( this.patrol.get(p.getUniqueId()) == true){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		
	}
	
	public void prosecutionTips(Player p){
		p.sendMessage(ChatColor.GOLD + "#########  Tips  #########");
		p.sendMessage(ChatColor.GOLD + "Make your Statements direct and to the point you do not have alot of time.");
		p.sendMessage(ChatColor.GOLD + "Do not argue on flimsy he said she said stuff use hard evidence.");
		p.sendMessage(ChatColor.GOLD + "Remember evidence speaks louder than testimony it’s is ok to remind the judge of this.");
		p.sendMessage(ChatColor.GOLD + "If you are relying on testimony get a clear motive, location, and ability it will go a long way");
		p.sendMessage(ChatColor.GOLD + "Try not get caught in traps think where the defense is trying to take your evidence and stop it.");
	}
	
	public void defenseTips(Player p){
		p.sendMessage(ChatColor.GOLD + "#########  Tips  #########");
		p.sendMessage(ChatColor.BLUE + "Make sure you look hard at evidence, sometimes it can be used to prove your client is innocent");
		p.sendMessage(ChatColor.BLUE + "Circumstantial evidence isn’t really strong if they have no real proof, bring it up!");
		p.sendMessage(ChatColor.BLUE + "If refuting testimony try to trick the witness into saying something bias, or see if they have a shifty past");
		p.sendMessage(ChatColor.BLUE + "Try to knock down each piece of evidence credibility as much as you can and bring it out to the judge");
		p.sendMessage(ChatColor.BLUE + "Look at time, blood type, anything can be a clue for your side");
		
	}
	
	public void judgeTips(Player p ){
		p.sendMessage(ChatColor.GOLD + "#########  Tips  #########");
		p.sendMessage(ChatColor.DARK_GRAY + "You should be completely un bias on the part of the defense");
		p.sendMessage(ChatColor.DARK_GRAY + "Evidence speaks louder than testimony");
		p.sendMessage(ChatColor.DARK_GRAY + "Let the Prosecution do the work they are the one who are to prove guilt");
		p.sendMessage(ChatColor.DARK_GRAY + "if you are confused ask questions");
		p.sendMessage(ChatColor.DARK_GRAY + "Use the /muteD and /muteP /muteW commands to mute prosecution, defense, or witness");
		p.sendMessage(ChatColor.DARK_GRAY + "Only take Prosecution and defense questions any others are null");
	}
	
	
	
	
	
	public void CourtSigns(String State, int op){
		if(this.getConfig().contains("CourtSigns")){
			if(this.getConfig().contains("CourtSigns.Prosecution") && this.getConfig().contains("CourtSigns.Defendant") &&  this.getConfig().contains("CourtSigns.Judge")){
			double dx = this.getConfig().getDouble("CourtSigns.Prosecution.X");
			double dy = this.getConfig().getDouble("CourtSigns.Prosecution.Y");
			double dz = this.getConfig().getDouble("CourtSigns.Prosecution.Z");
			
			double Px = this.getConfig().getDouble("CourtSigns.Defendant.X");
			double Py = this.getConfig().getDouble("CourtSigns.Defendant.Y");
			double Pz = this.getConfig().getDouble("CourtSigns.Defendant.Z");
			
			double Jx = this.getConfig().getDouble("CourtSigns.Judge.X");
			double Jy = this.getConfig().getDouble("CourtSigns.Judge.Y");
			double Jz = this.getConfig().getDouble("CourtSigns.Judge.Z");
			
			Location ProSign = new Location(Bukkit.getWorld("world"), dx, dy, dz);
			Location DefSign = new Location(Bukkit.getWorld("world"), Px, Py, Pz);
			Location JudgeSign = new Location(Bukkit.getWorld("world"), Jx, Jy, Jz);
			
			BlockState ps = Bukkit.getWorld("world").getBlockAt(ProSign).getState();
			BlockState ds = Bukkit.getWorld("world").getBlockAt(DefSign).getState();
			BlockState js = Bukkit.getWorld("world").getBlockAt(JudgeSign).getState();
			if(ps instanceof Sign &&  ds instanceof Sign && js instanceof Sign) {
				Sign pSign = (Sign) ps;
				Sign dSign = (Sign) ds;
				Sign jSign = (Sign) js;
				pSign.setLine(0, ChatColor.RED + "Prosecution");
				pSign.setLine(1, State);
				pSign.setLine(2, "Time: " + op );
				pSign.update();
				
				
				dSign.setLine(0, ChatColor.BLUE + "defense");
				dSign.setLine(1, State);
				dSign.setLine(2, "Time: " + op );
				dSign.update();
				
				jSign.setLine(0, ChatColor.BOLD  + "Judge");
				jSign.setLine(1, State);
				jSign.setLine(2, "Time: " + op );
				jSign.update();
				
				
			}else{
			
			}
			
			}else{
			
			
			}
		}
		
	}
	
	public void updateStat(Player p, String Stat, String job){
		
	if(job == null){
		int oldstat = this.getPlayerInfo().getInt("Players." +p.getUniqueId().toString() +"." + Stat );
		int newstat = oldstat + 1;
		this.getPlayerInfo().set("Players." +p.getUniqueId().toString() +"." + Stat , newstat);
		this.savePlayerInfo();
	}else{
		int oldstat = this.getPlayerInfo().getInt(job +"." +p.getUniqueId().toString() + Stat );
		int newstat = oldstat + 1;
		this.getPlayerInfo().set(job + "." +p.getUniqueId().toString() +"."+ Stat , newstat);
		this.savePlayerInfo();
		}
	}
	

	
	public int randomNumber(int total){
		ArrayList<Integer> ran = new ArrayList<Integer>();
		int r =  (int) (Math.random() * total) + 1 ; 
		if( r <= 0){
		ran.add(1);
		}else{
			ran.add(r);
		}
		return ran.get(0);
	}
	
	public boolean isSword(ItemStack item){
		Material wep = item.getType(); 
		if(wep == Material.WOOD_SWORD || wep == Material.IRON_SWORD|| wep == Material.GOLD_SWORD|| wep == Material.STONE_SWORD|| wep == Material.DIAMOND_SWORD){
			return true;
		}else{
		return false;
		}
		
	}
	
	public void changeLore(ItemStack item, Player victim, Player attacker, String tag){
		if(!(attacker.getItemInHand().getType() == Material.AIR)){
			
                ItemMeta meta = item.getItemMeta(); 
                ArrayList<String> Lore = new ArrayList<String>();
              
         if(meta.hasLore()){
        	List<String> firstLore = meta.getLore();
        	 
                if(!meta.getLore().contains(attacker.getName() + "'s finger prints")){
                	Lore.add(attacker.getName() + "'s finger prints");
				}
		  if(!meta.getLore().contains(victim.getName() + "'s blood")){
      		Lore.add(victim.getName() + "'s blood");
				}
		 Lore.addAll(firstLore);
		  
         }else{
        	
        	 Lore.add(victim.getName() + "'s blood");
        	 Lore.add(attacker.getName() + "'s finger prints");
         }
         		meta.setLore(Lore);
         		item.setItemMeta(meta);
         		
                attacker.setItemInHand(item);
			}else{
			
			}
	}
	
	 public void reloadAnimalData() {
	        if (AnimalDataFile == null) {
	        	AnimalDataFile = new File(getDataFolder(), "AnimalData.yml");
	        }
	        AnimalData = YamlConfiguration.loadConfiguration(AnimalDataFile);
	        InputStream defConfigStream = this.getResource("AnimalData.yml");
	        if (defConfigStream != null) {
	            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	            AnimalData.setDefaults(defConfig);
	        }
	    }
	    public FileConfiguration getAnimalData() {
	        if (AnimalData == null) {
	            this.reloadAnimalData();
	        }
	        return AnimalData;
	    }
	    
	    public void saveAnimalData() {
	        if (AnimalData == null || AnimalDataFile == null) {
	        	this.reloadAnimalData();
	     
	        }
	        try {
	            getAnimalData().save(AnimalDataFile);
	
	        } catch (IOException ex) {
	            this.getLogger().log(Level.SEVERE, "Could not save config to " + AnimalDataFile, ex);
	        }
	    }

	    public void addefault(){
	    	this.getAnimalData().set("Farmer." + "33e271ef-9134-30c9-b624-181e8a3e5810" +".Animals." +"33e271ef-9999-30c9-b624-181e8a3e5810.Name","fake" );
	    	this.getAnimalData().set("Farmer." + "33e271ef-9134-30c9-b624-181e8a3e5810" +".Animals." +"33e271ef-9999-30c9-b624-181e8a3e5810.Owner","TheFakeFiddy" );
	    	this.getAnimalData().set("Farmer." + "33e271ef-9134-30c9-b624-181e8a3e5810" +".Animals." +"33e271ef-9999-30c9-b624-181e8a3e5810.OwnerID","5852512" );
	    	this.getAnimalData().set("Farmer." + "33e271ef-9134-30c9-b624-181e8a3e5810" +".Animals." +"33e271ef-9999-30c9-b624-181e8a3e5810.HeartPoints","10" );
	    	this.saveAnimalData();
	    }


//NEW DAY
	public void newDay(){
	long Time = Bukkit.getWorld("world").getTime();
	if(newday.isEmpty() || newday.get("newday") == false){
		if(Time > 23000){
			Bukkit.broadcastMessage("NEW DAY");
			Player[] p = Bukkit.getOnlinePlayers();
			for(Player player : p){
				if(this.getAnimalData().contains("Farmer." + player.getUniqueId().toString() + ".Animals")){
				String farmer = player.getUniqueId().toString();	
				if(this.getAnimalData().getConfigurationSection("Farmer." + farmer + ".Animals").getKeys(false) == null){
					Bukkit.broadcastMessage("false");
				}else{
				Set<String> animals =  this.getAnimalData().getConfigurationSection("Farmer." + farmer + ".Animals").getKeys(false);
				for(String animal : animals){
					
					 Animals a = new Animals(this, animal, farmer);
					 Bukkit.broadcastMessage("adding age " + a.getAge());
					 a.addAge(1);
					 a.checkIfToOld();
					 Bukkit.broadcastMessage("new age " + a.getAge());
					 
					 if(a.getHunger() == true){
						 Bukkit.broadcastMessage(ChatColor.RED +a.getAnimalName() + " is Hungry");
						 if(a.isHappy() == true){
							 Bukkit.broadcastMessage("Was Happy");
						 int r = this.randomNumber(2);
						 Bukkit.broadcastMessage("r = " + r);
						 if(r == 1){
							 Bukkit.broadcastMessage("Not happy anymore");
							 a.setHappyness(false);
							 a.addHeartPoint(- .05);
							 a.addWeight(- 4);
							
							 }
						 }else{
							 Bukkit.broadcastMessage("not happy");
							 if(a.isHealthy() == true){
								 Bukkit.broadcastMessage("not happy but healthy");
							 int r = this.randomNumber(5);
							 if( r == 3){
								 Bukkit.broadcastMessage("now is sick");
								 a.setHealth(false);
								 if(Bukkit.getPlayer(a.getOwnerName()).isOnline()){
									 Bukkit.getPlayer(a.getOwnerName()).sendMessage(ChatColor.RED + a.getAnimalName() + " is sick!");
								 	}
							 	}
							 }else{
							 int r = this.randomNumber(5);
							 Bukkit.broadcastMessage("Should maybe die " + r);
							 if(r == 2){
								 a.setShouldDie(true);
							 	}
							 }
						 }
					 }else{
						 if(a.isHealthy() == false){
							 int r = this.randomNumber(20);
							 Bukkit.broadcastMessage("Should maybe die " + r);
							 if(r == 10){
								 a.setShouldDie(true);
						 }
					 }
					}
					 
					 Bukkit.broadcastMessage("owner " + a.getOwnerName());
					a.setHunger(true);
					a.setIsWashed(false);
					a.setIsPet(true);
					if(a.getIsWashed() && a.getIsPet() == false && a.isHappy()){
						Bukkit.broadcastMessage( a.getAnimalName() + " is Happy again");
					a.setHappyness(true);
					}
					
					
					newday.put("newday", true);
					}
				}
			}
				if(this.getPlantInfo().contains("Farmer." + player.getUniqueId().toString())){
					//Bukkit.broadcastMessage("NEW PLANT CYCLE");
					String farmer = player.getUniqueId().toString();
					PlantUtil plt = new PlantUtil(this);
					Set<String> plants =  this.getPlantInfo().getConfigurationSection("Farmer." + farmer + ".Plants").getKeys(false);
					for(String plant : plants){
						Plants pt = new Plants(this, farmer, this.getLocationFromString(plant));
						if(!plt.isGrowableBlock(pt.getPlantLocation().getBlock())){
							pt.removePlantInfo();
							//Bukkit.broadcastMessage("removing plant");
						}
						if(pt.getisWaterd() || Bukkit.getWorld("world").hasStorm()){
							//Bukkit.broadcastMessage("WATERED");
							if(pt.getPlantCycle() <= 1){
								pt.setPlantCycle(1);
								if(pt.isHealthy()){
									pt.addPlantExp(1);
								}
							}else{
							int newcycle = pt.getPlantCycle() -1;
							pt.setPlantCycle(newcycle);
							}
							pt.setIsWatered(false);
							pt.changePlantCycle();
							
							int r = this.randomNumber(20);
							if(r == 2 && pt.isfertilized() == false){
								pt.growWeed();
								//Bukkit.broadcastMessage(ChatColor.GREEN +"Weed");
							}
							//Bukkit.broadcastMessage(ChatColor.GOLD + "Changed CYCLE!");
						}else{
							if(pt.isHealthy()){
								pt.setHealth(false);
							}else{
								int r = randomNumber(10);
								if(r == 5){
									pt.killPlant();
									//Bukkit.broadcastMessage(ChatColor.GOLD + "DEAD PLANT");
								}
							}
						}
					}
					
				}
			}
		}
	}
}
	
    public String getPlantLocationID(Location loc){
    	int x = (int) loc.getBlockX();
		int y = (int) loc.getBlockY();
		int z = (int) loc.getBlockZ();
		String l = x+"~"+y+"~"+z;
    	return l;
    }

//CHECK IF FOOD
	public boolean isFood(ItemStack i){
		Material item = i.getType();
		if(item == Material.APPLE || item == Material.COOKED_CHICKEN ||item == Material.COOKED_FISH ||item == Material.COOKIE ||item == Material.GOLDEN_APPLE ||item == Material.APPLE ||
				item == Material.COOKED_BEEF ||item == Material.BAKED_POTATO ||item == Material.BREAD ||item == Material.BROWN_MUSHROOM ||item == Material.CAKE ||item == Material.COCOA ||
				item == Material.RED_MUSHROOM ||item == Material.RAW_BEEF ||item == Material.RAW_CHICKEN ||item == Material.RAW_FISH ||item == Material.MELON_BLOCK ||item == Material.MELON ||
				item == Material.MELON_SEEDS ||item == Material.SPIDER_EYE ||item == Material.ROTTEN_FLESH ||item == Material.CARROT ||item == Material.CARROT_ITEM ||item == Material.POTATO_ITEM ||
				item == Material.POTATO ||item == Material.MUSHROOM_SOUP ||item == Material.SUGAR ||item == Material.EGG ||item == Material.WHEAT ||item == Material.BONE 
				){
			return true;
		}else{
			return false;
		}
	}
//IS COOKED FOOD
	public boolean isCookedFood(Material food){
		Material f = food;
		if(f == Material.COOKED_BEEF || f == Material.COOKED_CHICKEN || f == Material.COOKED_FISH){
			return true;
		}else{
		return false;
		}
	}
	
//NON FOOD ITEM CHCEK
	public boolean isaNonFoodItem(Material item){
		Material i = item;
		if(i == Material.WATER_BUCKET || i == Material.BOWL || i == Material.POTION){
			return true;
		}else{
			return false;
		}
	}
	
//COOKING RECIPES
		public void  Recipes(){
			RecipeBook rb = new RecipeBook(this);
	        rb.dehydratedMeat();
	        rb.MushroomSoup();
	        rb.hotMilk();
	        rb.boiledEgg();
	        rb.sweetMilk();
	        rb.flour();
	        rb.hotRawChicken();
	        rb.spiceyChicken();
	        rb.breadDough();
	        rb.sweetBreadDough();
	        rb.butter();
	        rb.normalChicken();
	        rb.normalBread();
	        rb.PreparedFish();
	        rb.PreparedPorkChop();
	        rb.PreparedSteak();
	        rb.grilledfish();
	        rb.steak();
	        rb.porkchop();
	        rb.PreparedChicken();
	        
		}
//COOKING RECIPE NAMES
		public boolean isRecipe(String Resultname){
			ArrayList<String> recipeNames = new ArrayList<String>();
			recipeNames.add("Broth Powder");
			recipeNames.add("Boiled Egg");
			recipeNames.add("Mushroom Soup");
			recipeNames.add("Hot Milk");
			recipeNames.add("Sweet Milk");
			recipeNames.add("Raw Spicey Chicken");
			recipeNames.add("Pork Chop");
			recipeNames.add("Prepared Steak");
			recipeNames.add("Prepared Pork Chop");
			recipeNames.add("Prepared Fish");
			recipeNames.add("Sweet Bread");
			recipeNames.add("Steak");
			recipeNames.add("Baked Bread");
			recipeNames.add("Bread Dough");
			recipeNames.add("Baked Chicken");
			recipeNames.add("Baked Fish");
			recipeNames.add("Butter");
			recipeNames.add("Flour");
			recipeNames.add("Spicey Chicken");
			if(recipeNames.contains(Resultname)){
				return true;
			}else{
				return false;
			}
		}
//CHECK INGREDENTS
		public boolean hasIngredents(HashMap<String,ArrayList<String>> hashmap, String sourceItemName, String hashmapkey){
			
			ArrayList<String> foodlist = new ArrayList<String>();
			ArrayList<String> itemlist = hashmap.get(hashmapkey);

			String fn = sourceItemName;
	
	//MUSHROOM SOUP	
		if(fn.equalsIgnoreCase("Mushroom Soup")){
	//////Bukkit.broadcastMessage("mushroomSoup");
			foodlist.add("Broth Powder");
			if(itemlist.containsAll(foodlist)){
				return true;
			}else{
				return false;
			}
		}
	//SWEET MILK
		if(fn.equalsIgnoreCase("Sweet Milk")){
			foodlist.add("Hot Milk");
			if(itemlist.containsAll(foodlist)){
				return true;
			}else{
		//////Bukkit.broadcastMessage("false");
				return false;
			}
		}
		
		if(fn.equalsIgnoreCase("Flour")){
			boolean all = true;
			if(itemlist.containsAll(foodlist) || all){	
				return true;
			}else{
				return false;
			}
		}
		
		if(fn.equalsIgnoreCase("Raw Spicey Chicken")){
			foodlist.add("Salt");
			foodlist.add("Pepper");
			foodlist.add("Cayenne");
			if(itemlist.containsAll(foodlist)){	
				return true;
			}else{
				return false;
			}
		}
		
		
		if(fn.equalsIgnoreCase("Bread Dough")){
			foodlist.add("Flour");
			foodlist.add("Flour");
			if(itemlist.containsAll(foodlist)){	
				return true;
			}else{
				return false;
			}
		}
		
		if(fn.equalsIgnoreCase("Sweet Bread Dough")){
			foodlist.add("Flour");
			foodlist.add("Flour");
			if(itemlist.containsAll(foodlist)){	
				return true;
			}else{
				return false;
			}
		}
		
		if(fn.equalsIgnoreCase("Prepared Fish")){
			foodlist.add("Salt");
			foodlist.add("Pepper");
			if(itemlist.containsAll(foodlist)){	
				return true;
			}else{
				return false;
			}
		}
		
		if(fn.equalsIgnoreCase("Prepared Chicken")){
			foodlist.add("Salt");
			foodlist.add("Pepper");
			if(itemlist.containsAll(foodlist)){	
				return true;
			}else{
				return false;
			}
		}
		if(fn.equalsIgnoreCase("Prepared Steak")){
			foodlist.add("Salt");
			foodlist.add("Pepper");
			if(itemlist.containsAll(foodlist)){	
				return true;
			}else{
				return false;
			}
		}
		
		if(fn.equalsIgnoreCase("Prepared Pork")){
			foodlist.add("Salt");
			foodlist.add("Pepper");
			if(itemlist.containsAll(foodlist)){	
				return true;
			}else{
				return false;
			}
		}
		
		
		if(fn.equalsIgnoreCase("Butter")){
			foodlist.add("Salt");
			if(itemlist.containsAll(foodlist)){	
				return true;
			}else{
				return false;
			}
		}
		if(fn.equalsIgnoreCase("something")){
			foodlist.add("this");
			foodlist.add("this");
			foodlist.add("this");
			foodlist.add("this");
		}
			if(foodlist.contains("String")){
				return true;
			}else{
			return false;
		}
	}
		
//IS THERE A SPICE AVALIBLE
		public boolean isSpice(ItemStack spice){
			ItemStack s = spice;
			ItemMeta smeta = s.getItemMeta();
			HashMap<String, Material> spices = new HashMap<String, Material>();
			spices.put("Peper", Material.MELON_SEEDS);
			spices.put("Cayenne pepper", Material.NETHER_STALK);
			spices.put("Salt",  Material.PUMPKIN_SEEDS);
			spices.put("Sugar", Material.SUGAR);

			if(s.hasItemMeta() && smeta.hasDisplayName()){
				String dn = smeta.getDisplayName();
				
				if(spices.containsKey(dn)){
					if(spices.get(dn) == s.getType()){
						return true;
					}else{
						return false;
					}
				}
			}
			return false;
		}
		
//CAN BE SPICED
		public boolean canBeSpiced(){
			HashMap<String,ArrayList<String>> spiceables = new HashMap<String, ArrayList<String>>();
			ArrayList<String> spicesNeeded = new ArrayList<String>();
			spiceables.put("", spicesNeeded);
			
			return false;
		}
		
//FURNACE INGREDENTS
		public boolean hasFurnaceIngredents(String sourceName, String resultName){
			String n = sourceName;
			String rn = resultName;
			
			ArrayList<String> sN = new ArrayList<String>();
			if(n == null){
				sN.add("meh");
			}else{
				sN.add(n);
			}
			String sn = sN.get(0);
			
	//HOT MILK
			if(rn.equalsIgnoreCase("Hot Milk")){
				boolean canHaveAnyName = true;
				if(canHaveAnyName){
					return true;
				}else{
					if(sn.equalsIgnoreCase("ingredient")){
					return true;
					}else{
						return false;
					}
				}
	//BROTH POWDER
			}else if(rn.equalsIgnoreCase("Broth Powder")){
				boolean canHaveAnyName = true;
				if(canHaveAnyName){
					return true;
				}else{
					if(sn.equalsIgnoreCase("ingredient")){
					return true;
					}else{
						return false;
					}
				}
	//BOILED EGG
			}else if(rn.equalsIgnoreCase("Boiled Egg")){
				boolean canHaveAnyName = true;
				if(canHaveAnyName){
					return true;
				}else{
					if(sn.equalsIgnoreCase("ingredient")){
					return true;
					}else{
						return false;
					}
				}
	//SPICEY CHICKEN
			}else if(rn.equalsIgnoreCase("Spicey Chicken")){
				boolean canHaveAnyName = false;
				if(canHaveAnyName){
					return true;
				}else{
					if(sn.equalsIgnoreCase("Raw Spicey Chicken")){
					return true;
					}else{
						return false;
					}
				}
			}else if(rn.equalsIgnoreCase("Baked Chicken")){
				boolean canHaveAnyName = false;
				if(canHaveAnyName){
					return false;
				}else{
					if(sn.equalsIgnoreCase("Prepared Chicken")){
					return true;
					}else{
						return false;
					}
				}
			}else if(rn.equalsIgnoreCase("Baked Bread")){
				boolean canHaveAnyName = false;
				if(canHaveAnyName){
					return false;
				}else{
					if(sn.equalsIgnoreCase("Bread Dough")){
					return true;
					}else{
						return false;
					}
				}
			}else if(rn.equalsIgnoreCase("Steak")){
				boolean canHaveAnyName = false;
				if(canHaveAnyName){
					return true;
				}else{
					if(sn == null){
						return false;
					}
					
					////Bukkit.broadcastMessage(ChatColor.RED + "source name steak = " + sn);
					if(sn.equalsIgnoreCase("Prepared Steak") ){
					return true;
					}else{
						return false;
					}
				}
			}else if(rn.equalsIgnoreCase("Pork Chop")){
				boolean canHaveAnyName = false;
				////Bukkit.broadcastMessage("in pork chop");
				if(canHaveAnyName){
					return false;
				}else{
					if(sn.equalsIgnoreCase("Prepared Pork Chop")){
						////Bukkit.broadcastMessage("true");
					return true;
					}else{
						return false;
					}
				}
			}else if(rn.equalsIgnoreCase("Baked Fish")){
				boolean canHaveAnyName = false;
				if(canHaveAnyName){
					return false;
				}else{
					if(sn.equalsIgnoreCase("Prepared Fish")){
					return true;
					}else{
						return false;
					}
				}
			}else if(rn.equalsIgnoreCase("Sweet Bread")){
				boolean canHaveAnyName = false;
				if(canHaveAnyName){
					return false;
				}else{
					if(sn.equalsIgnoreCase("Sweet Bread Dough")){
					return true;
					}else{
						return false;
					}
				}
			}else{
				return false;
			}

		}
		

//COOKING RANK STARS TO INTS
		public int getcookingRankLevel(String rank){
			String r = rank;
			
			if(r.equalsIgnoreCase("*")){
				return 1;
			}else if(r.equalsIgnoreCase("**")){
				return 2;
			}else if(r.equalsIgnoreCase("***")){
				return 3;
			}else if(r.equalsIgnoreCase("****")){
				return 4;
			}else{
				return 0;
			}
		}

//SET COOKING RANK
	public String setCookingRank(int number){
		int r = number;
//////Bukkit.broadcastMessage("setting cooking rank");
		if(r == 1){
			return "*";
		}else if(r == 2){
	//////Bukkit.broadcastMessage("2" + " rank " + number);
			return "**";
		}else if(r == 3){
			return "***";
		}else if(r >= 4){
			return "****";
		}else{
	//////Bukkit.broadcastMessage("else" + " rank " + number);
			return "X";
		}
	}
//FAILED DISH
	public ItemStack getFailedDish(){
		 ItemStack failedDish = new ItemStack(Material.ROTTEN_FLESH);
		 ItemMeta meta = failedDish.getItemMeta();
		 String rankStar = this.setCookingRank(0);
		 ArrayList<String> Lore = new ArrayList<String>();
		 Lore.add("Proof of bad cooking");
		 Lore.add(rankStar);
		 Lore.add("Bonus Effects");
		 ArrayList<String> b = this.setFoodBuff(failedDish, meta.getDisplayName(), 0 );
		 b.get(0);
		 Lore.add(b.get(0));
		 if(b.size() > 1){
			 Lore.add(b.get(1));
			 
			 if(b.size() > 2){
				 Lore.add(b.get(2));
			 }
		 }
		 meta.setDisplayName("Failed Dish");
		 meta.setLore(Lore);
		 failedDish.setItemMeta(meta);
		return failedDish;
	}
//SETTIN THE DISH
	public ItemStack getDish(HashMap<String, Integer> foodRank, HashMap<String, Integer> foodTotal, ItemStack result){
		ItemMeta rmeta = result.getItemMeta();
		 int totalItems = foodTotal.get(rmeta.getDisplayName());
		 int newrank = (int) foodRank.get(rmeta.getDisplayName()) / totalItems;
		 String rankStar = this.setCookingRank(newrank);
		 ArrayList<String> Lore = new ArrayList<String>();
		 ItemStack newitem = result;
		 ItemMeta nimeta = newitem.getItemMeta();
		 Lore.add(rmeta.getLore().get(0));
		 Lore.add(rankStar);
		 Lore.add("Bonus Effects");
		 ArrayList<String> b = this.setFoodBuff(result, rmeta.getDisplayName(), newrank);
		 b.get(0);
		 Lore.add(b.get(0));
		 if(b.size() > 1){
			 Lore.add(b.get(1));
			 
			 if(b.size() > 2){
				 Lore.add(b.get(2));
			 }
		 }
		 nimeta.setDisplayName(rmeta.getDisplayName());
		 nimeta.setLore(Lore);
		 newitem.setItemMeta(nimeta);
		 return newitem;
	}
//SETTIN RANKS AND CRAP
	public void starRankSetup(ArrayList<Boolean> trueOnce,HashMap<String, Integer> foodRank, ArrayList<String> displayName, ItemStack i, HashMap<String,ArrayList<String>> playerCraft, ItemStack result, Player p, HashMap<String, Integer> foodTotal){
		ItemMeta rmeta = result.getItemMeta();
	
		if(i.hasItemMeta() && i.getItemMeta().hasDisplayName()){
			displayName.add(i.getItemMeta().getDisplayName());
			playerCraft.put(p.getName(), displayName);
		if(displayName.contains(rmeta.getDisplayName())){
			displayName.remove(rmeta.getDisplayName());
		}
		trueOnce.add(true);
		if(i.getItemMeta().hasLore()){
			if(i.getItemMeta().getLore().size() > 1){
				String ranking = i.getItemMeta().getLore().get(1);
				int rlevel = this.getcookingRankLevel(ranking);
			if(foodTotal.isEmpty()){
					foodTotal.put(rmeta.getDisplayName(), 1);
				}else{
				 int old = foodTotal.get(rmeta.getDisplayName());
				 foodTotal.put(rmeta.getDisplayName(), old + 1);
				}
			 if(foodRank.isEmpty()){
			 	}else{
				 int oldLevel = foodRank.get(rmeta.getDisplayName());
				 int newlevel = oldLevel + rlevel;
				 foodRank.put(rmeta.getDisplayName(), newlevel);
			 	}
	}else{
		if(this.isaNonFoodItem(i.getType()) == false){
			int rlevel = 0;
			 if(foodTotal.isEmpty()){
				 foodTotal.put(rmeta.getDisplayName(), 1);
			 }else{
				 int old = foodTotal.get(rmeta.getDisplayName());
				 foodTotal.put(rmeta.getDisplayName(), old + 1);
			 }
			 if(foodRank.isEmpty()){
				 foodRank.put(rmeta.getDisplayName(), rlevel);
			 }else{
				 int oldLevel = foodRank.get(rmeta.getDisplayName());
				 int newlevel = oldLevel + rlevel;
			 foodRank.put(rmeta.getDisplayName(), newlevel);
			 		}
				}
			}
		}
	}
}
	
	public boolean cantCraft(ItemStack result){
		ArrayList<Material> noCraft = new ArrayList<Material>();
		noCraft.add(Material.MUSHROOM_SOUP);
		noCraft.add(Material.BREAD);
		noCraft.add(Material.BAKED_POTATO);
		noCraft.add(Material.PUMPKIN_PIE);
		noCraft.add(Material.GOLDEN_APPLE);
		noCraft.add(Material.COOKIE);
		noCraft.add(Material.COOKED_BEEF);
		noCraft.add(Material.COOKED_CHICKEN);
		noCraft.add(Material.COOKED_FISH);
		noCraft.add(Material.GRILLED_PORK);
	    noCraft.add(Material.CAKE);
		Material r = result.getType();
		if(noCraft.contains(r) && result.hasItemMeta() == false && !result.getItemMeta().hasDisplayName()){
			return false;
		}else{
		
			return true;
		}
	}
	
	public ArrayList<String> setFoodBuff(ItemStack mainIngredent, String itemName, int rank){
		Material item = mainIngredent.getType();
		ItemMeta meta = mainIngredent.getItemMeta();
		ArrayList<String> b = new ArrayList<String>();
		String in = itemName;
		////Bukkit.broadcastMessage(ChatColor.GOLD + itemName);
		if(in != null){
		if(in.contains("Spicey")){
			b.add("Heat Tolerance");
			}
		if(in.contains("Sweet")){
			b.add("Jumpy");
			}
		if(in.contains("Salty")){
			b.add("Saturation");
			}
		if(in.contains("Sour")){
			b.contains("Night Eyes");
			}
		if(in.contains("Bitter")){
			b.add("Healing");
			}
		if(in.contains("Miners")){
			b.add("Miners Bonus");
			}
	//MEAT
		if(item == Material.COOKED_BEEF || item == Material.COOKED_CHICKEN ||item == Material.COOKED_FISH){
			b.add("Strength");
		}
	//SOUP	
		if(item == Material.MUSHROOM_SOUP && mainIngredent.hasItemMeta() && meta.hasDisplayName()){
			if(meta.getDisplayName().equalsIgnoreCase("Mushroom Soup")){
				b.add("Health");
			}else{
				b.add("Regeneration");
			}
		}
	//SWEETS
		if(item == Material.COOKIE || item == Material.CAKE || item == Material.PUMPKIN_PIE){
			b.add("Speed");
		}
	//MILK
		if(item == Material.MILK_BUCKET){
			b.add("Strong Bones");
		}
	//VEGGIES
		if(item == Material.BAKED_POTATO || item == Material.GOLDEN_CARROT){
			b.add("Health");
		}
		}
		
		if(b.isEmpty()){
			b.add( "None");
		}
		
		return b;
		}
//ADDING FOOD EFFECTS
	public void setFoodEffects(Player p,String effect, int starRank){
		
		int level = (int) starRank - 1;
		int time = starRank * 800;
		Bukkit.broadcastMessage("level = " + level + "time = " + time);
//////Bukkit.broadcastMessage("in food effects + effect " + effect);
		if(effect.equalsIgnoreCase("Strength") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.RED + "Stronger!");
		}
		
		if(effect.equalsIgnoreCase("Health") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.DARK_GREEN + "Healther!");
		}
		if(effect.equalsIgnoreCase("Regeneration") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.GREEN + "Like new!");
		}
		if(effect.equalsIgnoreCase("Speed") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.GREEN + "Fast!");
		}
		
		if(effect.equalsIgnoreCase("Heat Tolerance") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.RED + "One with Fire!");
		}
		if(effect.equalsIgnoreCase("Jumpy") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.GREEN + "Like your bouncing off the walls!");
		}
		if(effect.equalsIgnoreCase("Saturation") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.YELLOW + "Full!");
		}
		if(effect.equalsIgnoreCase("Healing") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.GREEN + "Restored!");
		}
		if(effect.equalsIgnoreCase("Miners Bonus") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.YELLOW + "Like Mining!");
		}
		if(effect.equalsIgnoreCase("Strong Bones") && level != 0){
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, level));
			p.sendMessage(ChatColor.GRAY + "You feel " + ChatColor.GREEN + "Tough!");
		}
		
		if(effect.equalsIgnoreCase("None")){
			
		}
	}
	

//CHECK IF ABLE TO MAKE THIS FOOD


//SET FOOD BUFF
	
	}
