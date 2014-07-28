package io.github.FiddyPercent.fiddycraft;

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
    	reloadPlantInfo();
    	this.Recipes();
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
    
    public String getPlayerPlantAbleList(Player p){
    	FcPlayers fc = new FcPlayers(this, p);
    	if(fc.getPlayerJob().equalsIgnoreCase("Farmer")){
    		FcFarmers fm = new FcFarmers(this, p);
    		String rank = fm.getRank();
    		if(rank.equalsIgnoreCase("Farmer")){
    			String base = "OXEYE_DAISY:AZURE_BLUET:POTATO:CARROT:WHEAT:RED_TULIP:ORANGE_TULIP:PINK_TULIP:WHITE_TULIP:DANDELION";
    	    	String extra = fc.getGrowableList();
    	    	return base+extra;
    		}else if(rank.equalsIgnoreCase("Great Farmer")){
    			String base = "OXEYE_DAISY:AZURE_BLUET:POTATO:CARROT:POPPY:ALLIUM:MELON:RED_TULIP:ORANGE_TULIP:PINK_TULIP:WHITE_TULIP:WHEAT";
    	    	String extra = fc.getGrowableList();
    	    	return base+extra;
    		}else if(rank.equalsIgnoreCase("Legendary Farmer")){
    			String base = "OXEYE_DAISY:AZURE_BLUET:POTATO:CARROT:POPPY:ALLIUM:MELON:PUMPKIN;PEPPER:RED_TULIP:ORANGE_TULIP:PINK_TULIP:WHITE_TULIP:WHEAT:ROSE:BLUE_ORCHID";
    	    	String extra = fc.getGrowableList();
    	    	return base+extra;
    		}else{
    			String base = "WHEAT:RED_TULIP:ORANGE_TULIP:PINK_TULIP:WHITE_TULIP:DANDELION:";
    	    	String extra = fc.getGrowableList();
    	    	return base+extra;
    		}
    	}else{
    	String base = "WHEAT:RED_TULIP:ORANGE_TULIP:PINK_TULIP:WHITE_TULIP:DANDELION:";
    	String extra = fc.getGrowableList();
    	return base+extra;
    	}
    }
    
    public boolean canPlantThis(Player p,String seedName){
    	
    	String list = this.getPlayerPlantAbleList(p);
    	ArrayList<String> l = new ArrayList<String>();

    	String[] li = list.split("~");
    	for(String i : li){
    		l.add(i);
    	}
    	String sn = seedName.replace(" Seeds", "").replace(" ", "_").toUpperCase();
    
    	if(l.toString().contains(sn)){
    		return true;
    	}else{
    		return false;
    	}
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
    	this.getPlayerInfo().set("Players."+ p.getUniqueId().toString() + ".Growable List", this.getPlayerPlantAbleList(p));
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
    	this.getPlayerInfo().set("Players."+ p.getUniqueId().toString() + ".Growable List", this.getPlayerPlantAbleList(p));
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
    
    public String getPlantLocationID(Location loc){
    	int x = (int) loc.getBlockX();
		int y = (int) loc.getBlockY();
		int z = (int) loc.getBlockZ();
		String l = x+"~"+y+"~"+z;
    	return l;
    }
    
    public Location getFirstPlantLocation(Location loc){
    	int x = (int) loc.getBlockX();
		int y = (int) loc.getBlockY() + 1;
		int z = (int) loc.getBlockZ();
		Location l = new Location(Bukkit.getWorld("world"), x,y,z);
    	return l;
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
    public boolean isPlayerPlantOwner(Player p, Location loc){
    	if(this.getPlantInfo().contains("Farmer." + p.getUniqueId().toString() + ".Plants." + this.getPlantLocationID(loc))){
    		return true;
    	}else{
    		return false;
    	}
    }
    public boolean isGrowableBlock(Block b){
    	Material bl = b.getType();
    	////Bukkit.broadcastMessage("Block type = " + bl.toString());
    	if(bl == Material.GRASS || bl == Material.CROPS || bl == Material.LONG_GRASS || bl == Material.DEAD_BUSH 
    			|| bl == Material.YELLOW_FLOWER || bl == Material.RED_ROSE || bl == Material.WHEAT || bl ==Material.PUMPKIN_STEM
    			|| bl == Material.MELON_STEM || bl == Material.PUMPKIN || bl == Material.MELON_BLOCK || bl == Material.COCOA||
    			bl == Material.CARROT || bl == Material.POTATO || bl == Material.SUGAR_CANE_BLOCK || bl == Material.NETHER_WARTS || bl == Material.getMaterial(175) ){
    		////Bukkit.broadcastMessage("pass");
    		return true;
    	}else{
    		//Bukkit.broadcastMessage("False man not an itme");
    		return false;
    	}
    }
    public boolean isASeed(ItemStack item){
    	Material m = item.getType();
    	if(m == Material.SEEDS || m == Material.PUMPKIN_SEEDS || m == Material.CARROT_ITEM || m == Material.POTATO_ITEM ||
    			m == Material.MELON_SEEDS){
    		return true;
    	}else{
    		return false;
    	}
    }
    public void plantNewSeed(Player p, Block e, String plantType, ItemStack Seeds){
    if(!this.getPlantInfo().contains("Farmer")){
    		this.getPlantInfo().set("Farmer.", p.getUniqueId().toString());
    		this.savePlantInfo();
		}
    	ItemMeta meta = Seeds.getItemMeta();
    	cropSeed cs = cropSeed.valueOf(plantType);
    	int cyc = cs.getcycles();
    	int x = (int) e.getLocation().getBlockX();
		int y = (int) e.getLocation().getBlockY() +1;
		int z = (int) e.getLocation().getBlockZ();
		String l = x+"~"+y+"~"+z;
    	String ownerUUID = p.getUniqueId().toString();
		//Bukkit.broadcastMessage("setting plant stuff in config");
		this.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Plant Location" + ".X", x);
		this.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Plant Location" + ".Y", y);
		this.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Plant Location" + ".Z",z);
		this.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Owner Name" , p.getName());
		this.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Plant Type",plantType);
		this.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Watered",false);
		this.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Plant Cycle",cyc);
		this.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Plant Quality", this.getcookingRankLevel(meta.getLore().get(1)));
		this.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Healthy", true);
		this.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Fertilized", false);
		this.getPlantInfo().set("Farmer." + ownerUUID + ".Plants." + l + ".Plant EXP", 0);
		this.savePlantInfo();
    }
    
    public ItemStack getfertilizerItem(){
    	ItemStack fertilized = new ItemStack(Material.DIRT, 1 , (short) 2);
		ItemMeta meta = fertilized.getItemMeta();
		meta.setDisplayName("fertilizer");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Good Old Fresh Steaming Manure");
		meta.setLore(lore);
		fertilized.setItemMeta(meta);
		return fertilized;
    }
    
    public void addExpFarmer(Player p, double add){
    	FcPlayers fcp = new FcPlayers(this, p);
    if(fcp.getPlayerJob().equalsIgnoreCase("Farmer")){	
    	FcFarmers fc = new FcFarmers(this, p);
    	fc.addExp(add);
    	}
   }
    
    public boolean matchesSeedType(String seedType){
		String dn = seedType;
		 ArrayList<cropSeed> test = new ArrayList<cropSeed>(Arrays.asList(cropSeed.values()));
		 String meh = dn.toUpperCase().replace(" ", "_");
		 if(test.toString().contains(meh)){
					return true;
			}else{
				return false;
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
	

	public TreeMap<String, Double> sortHashMap(HashMap<String, Double> map){

       
        ValueComparator bvc =  new ValueComparator(map);
        TreeMap<String,Double> sorted_map = new TreeMap<String,Double>(bvc);


        

        sorted_map.putAll(map);

       return sorted_map; 
    }
	
	class ValueComparator implements Comparator<String> {

	    Map<String, Double> base;
	    public ValueComparator(Map<String, Double> base) {
	        this.base = base;
	    }

	    // Note: this comparator imposes orderings that are inconsistent with equals.    
	    public int compare(String a, String b) {
	        if (base.get(a) >= base.get(b)) {
	            return -1;
	        } else {
	            return 1;
	        } // returning 0 would merge keys
	    }
	}
	
	public int randomNumber(int total){
		int r =  (int) (Math.random() * total) + 1 ; 
		return r;
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


//CHECK IF PLAYER IS THE OWNER

	

	public boolean isanimalAbletobePet(String uuid, String auuid){
		return this.AnimalData.getBoolean("Farmer."+ uuid + ".Animals." +  auuid + ".isPet");
	}

	public void setanimalAbletobePet(String uuid, String auuid, Boolean b){
		 this.AnimalData.set("Farmer."+ uuid + ".Animals." +  auuid + ".isPet", b);
		 this.saveAnimalData();
	}


//ANIMAL FOOD SETTINGS

//CHECK IF ANIMAL

//NEW DAY
	public void newDay(){
	long Time = Bukkit.getWorld("world").getTime();
	if(newday.isEmpty() || newday.get("newday") == false){
		if(Time > 23000){
			//Bukkit.broadcastMessage("NEW DAY");
			Player[] p = Bukkit.getOnlinePlayers();
			for(Player player : p){
				if(this.getAnimalData().contains("Farmer." + player.getUniqueId().toString())){
				String farmer = player.getUniqueId().toString();	
		
				Set<String> animals =  this.getAnimalData().getConfigurationSection("Farmer." + farmer + ".Animals").getKeys(false);
				for(String animal : animals){
					
					 Animals a = new Animals(this, farmer, animal);
					 
					 if(a.getHunger() == true){
						 
						 if(a.isHealthy()){
						 int r = this.randomNumber(10);
						 if(r == 3){
							 a.setHealth(false);
							 a.addHeartPoint(- 1);
							 if(Bukkit.getPlayer(a.getOwnerName()).isOnline()){
								 Bukkit.getPlayer(a.getOwnerName()).sendMessage(ChatColor.RED + a.getAnimalName() + " is sick!");
							 	}
							 }
						 }else{
							 int r = this.randomNumber(5);
							 if(r == 1){
								 animalUtility au = new animalUtility(this);
								 a.setShouldDie(true);
							 }
						 }
					 }
					 
					a.setHunger(true);
					a.setIsWashed(false);
					a.setIsPet(true);
					newday.put("newday", true);
					}
				}
				if(this.getPlantInfo().contains("Farmer." + player.getUniqueId().toString())){
					//Bukkit.broadcastMessage("NEW PLANT CYCLE");
					String farmer = player.getUniqueId().toString();
					Set<String> plants =  this.getPlantInfo().getConfigurationSection("Farmer." + farmer + ".Plants").getKeys(false);
					for(String plant : plants){
						Plants pt = new Plants(this, farmer, this.getLocationFromString(plant));
						if(!this.isGrowableBlock(pt.getPlantLocation().getBlock())){
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
//ANIMAL EAT

//PET ANIMAL


//SHOW ANIMAL INFORMATION

//PUBLIC VOID WASH ANIMAL

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
	        this.dehydratedMeat();
	        this.MushroomSoup();
	        this.hotMilk();
	        this.boiledEgg();
	        this.sweetMilk();
	        this.flour();
	        this.hotRawChicken();
	        this.spiceyChicken();
	        this.breadDough();
	        this.sweetBreadDough();
	        this.butter();
	        this.normalChicken();
	        this.normalBread();
	        this.PreparedFish();
	        this.PreparedPorkChop();
	        this.PreparedSteak();
	        this.grilledfish();
	        this.steak();
	        this.porkchop();
	        this.PreparedChicken();
	        
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
//HOT SWEET MILK
	public void sweetMilk(){
	    ItemStack item = new ItemStack(Material.MILK_BUCKET);
        ItemMeta imeta = item.getItemMeta();
        ArrayList<String> Lore = new ArrayList<String>();
        imeta.setDisplayName("Sweet Milk");
        Lore.add("Sweetened Milk, may cause diabetes");
        imeta.setLore(Lore);
        item.setItemMeta(imeta);
        ShapedRecipe sweetMilk = new ShapedRecipe(new ItemStack(item));
        sweetMilk.shape("S","M");
        sweetMilk.setIngredient('S', Material.SUGAR);
        sweetMilk.setIngredient('M', Material.MILK_BUCKET);
        getServer().addRecipe(sweetMilk);
	}
	
//FLOUR
	public void flour() {
		 ItemStack item = new ItemStack(Material.SUGAR);
		 ItemMeta imeta = item.getItemMeta();
	       ArrayList<String> Lore = new ArrayList<String>();
	       Lore.add("Flour for baking");
	       imeta.setDisplayName("Flour");
	       imeta.setLore(Lore);
	       item.setItemMeta(imeta);
	       
		ShapelessRecipe flour = new ShapelessRecipe(item);
		flour.addIngredient(Material.WHEAT);
		Bukkit.addRecipe(flour);
		}
	
//SPICEY RAW CHICKEN
	public void hotRawChicken() {
		 ItemStack item = new ItemStack(Material.RAW_CHICKEN);
		 ItemMeta imeta = item.getItemMeta();
	       ArrayList<String> Lore = new ArrayList<String>();
	       Lore.add("Raw Spicey Chicken");
	       imeta.setDisplayName("Raw Spicey Chicken");
	       imeta.setLore(Lore);
	       item.setItemMeta(imeta);
	       
		ShapelessRecipe hotRawChicken = new ShapelessRecipe(item);
		hotRawChicken.addIngredient(Material.NETHER_STALK);
		hotRawChicken.addIngredient(Material.PUMPKIN_SEEDS);
		hotRawChicken.addIngredient(Material.MELON_SEEDS);
		hotRawChicken.addIngredient(Material.RAW_CHICKEN);
		Bukkit.addRecipe(hotRawChicken);
		}
//STEAK
	public void PreparedSteak() {
		 ItemStack item = new ItemStack(Material.RAW_BEEF);
		 ItemMeta imeta = item.getItemMeta();
	       ArrayList<String> Lore = new ArrayList<String>();
	       Lore.add("Ready for Cooking");
	       imeta.setDisplayName("Prepared Steak");
	       imeta.setLore(Lore);
	       item.setItemMeta(imeta);
	       
		ShapelessRecipe PreparedSteak = new ShapelessRecipe(item);
		PreparedSteak.addIngredient(Material.PUMPKIN_SEEDS);
		PreparedSteak.addIngredient(Material.MELON_SEEDS);
		PreparedSteak.addIngredient(Material.RAW_BEEF);
		Bukkit.addRecipe(PreparedSteak);
		}
//PORK CHOP
	public void PreparedPorkChop() {
		 ItemStack item = new ItemStack(Material.PORK);
		 ItemMeta imeta = item.getItemMeta();
	       ArrayList<String> Lore = new ArrayList<String>();
	       Lore.add("Ready for Cooking");
	       imeta.setDisplayName("Prepared Pork Chop");
	       imeta.setLore(Lore);
	       item.setItemMeta(imeta);
		ShapelessRecipe PreparedPorkChop = new ShapelessRecipe(item);
		PreparedPorkChop.addIngredient(Material.PUMPKIN_SEEDS);
		PreparedPorkChop.addIngredient(Material.MELON_SEEDS);
		PreparedPorkChop.addIngredient(Material.PORK);
		Bukkit.addRecipe(PreparedPorkChop);
		}
//PREPAIRED FISH
	public void PreparedFish() {
		 ItemStack item = new ItemStack(Material.RAW_FISH);
		 ItemMeta imeta = item.getItemMeta();
	       ArrayList<String> Lore = new ArrayList<String>();
	       Lore.add("Ready for Cooking");
	       imeta.setDisplayName("Prepared Fish");
	       imeta.setLore(Lore);
	       item.setItemMeta(imeta);
	       
		ShapelessRecipe PreparedFish = new ShapelessRecipe(item);
		PreparedFish.addIngredient(Material.PUMPKIN_SEEDS);
		PreparedFish.addIngredient(Material.MELON_SEEDS);
		PreparedFish.addIngredient(Material.RAW_FISH);
		Bukkit.addRecipe(PreparedFish);
		}
	
	public void PreparedChicken() {
		 ItemStack item = new ItemStack(Material.RAW_CHICKEN);
		 ItemMeta imeta = item.getItemMeta();
	       ArrayList<String> Lore = new ArrayList<String>();
	       Lore.add("Ready for Cooking");
	       imeta.setDisplayName("Prepared Chicken");
	       imeta.setLore(Lore);
	       item.setItemMeta(imeta);
	       
		ShapelessRecipe PreparedFish = new ShapelessRecipe(item);
		PreparedFish.addIngredient(Material.PUMPKIN_SEEDS);
		PreparedFish.addIngredient(Material.MELON_SEEDS);
		PreparedFish.addIngredient(Material.RAW_CHICKEN);
		Bukkit.addRecipe(PreparedFish);
		}
//BREAD DOUGH
	public void breadDough() {
		 ItemStack item = new ItemStack(Material.INK_SACK, 1, (short) 9);
		 ItemMeta imeta = item.getItemMeta();
	       ArrayList<String> Lore = new ArrayList<String>();
	       Lore.add("Bread Dough for making bread");
	       imeta.setDisplayName("Bread milDough");
	       imeta.setLore(Lore);
	       item.setItemMeta(imeta);
	       
		ShapelessRecipe breadDough = new ShapelessRecipe(item);
		breadDough.addIngredient(Material.PUMPKIN_SEEDS);
		breadDough.addIngredient(Material.SUGAR);
		breadDough.addIngredient(Material.SUGAR);
		breadDough.addIngredient(Material.MILK_BUCKET);
		breadDough.addIngredient(Material.EGG);
		
		Bukkit.addRecipe(breadDough);
		}
//BUTTER
	public void butter() {
		 ItemStack item = new ItemStack(Material.INK_SACK, 1,  (short) 11);
		 ItemMeta imeta = item.getItemMeta();
	       ArrayList<String> Lore = new ArrayList<String>();
	       Lore.add("Butter made from milk");
	       imeta.setDisplayName("Butter");
	       imeta.setLore(Lore);
	       item.setItemMeta(imeta);
	       
		ShapelessRecipe Butter = new ShapelessRecipe(item);
		Butter.addIngredient(Material.PUMPKIN_SEEDS);
		Butter.addIngredient(Material.MILK_BUCKET);
		Bukkit.addRecipe(Butter);
		}
//SWEET BREAD DOUGH
	public void sweetBreadDough() {
		 ItemStack item = new ItemStack(Material.INK_SACK, 1,  (short) 13);
		 ItemMeta imeta = item.getItemMeta();
	       ArrayList<String> Lore = new ArrayList<String>();
	       Lore.add("Bread Dough for making bread");
	       imeta.setDisplayName("Sweet Bread Dough");
	       imeta.setLore(Lore);
	       item.setItemMeta(imeta);
	       
		ShapelessRecipe breadDough = new ShapelessRecipe(item);
		breadDough.addIngredient(Material.PUMPKIN_SEEDS);
		breadDough.addIngredient(Material.SUGAR);
		breadDough.addIngredient(Material.SUGAR);
		breadDough.addIngredient(Material.SUGAR);
		breadDough.addIngredient(Material.MILK_BUCKET);
		breadDough.addIngredient(Material.EGG);
		
		Bukkit.addRecipe(breadDough);
		}

	@SuppressWarnings("deprecation")
	public void  sweetlBread(){
		ItemStack BoiledEgg = new ItemStack(Material.BREAD);
		ItemMeta meta = BoiledEgg.getItemMeta();
		ArrayList<String> Lore = new ArrayList<String>();
		meta.setDisplayName("Baked Bread");
		Lore.add("Bread, thats about it");
		meta.setLore(Lore);
		BoiledEgg.setItemMeta(meta);
		this.getServer().addRecipe(new FurnaceRecipe(new ItemStack(BoiledEgg), Material.INK_SACK, 9));
	}
	
	@SuppressWarnings("deprecation")
	public void  normalBread(){
		ItemStack BoiledEgg = new ItemStack(Material.BREAD);
		ItemMeta meta = BoiledEgg.getItemMeta();
		ArrayList<String> Lore = new ArrayList<String>();
		meta.setDisplayName("Baked Bread");
		Lore.add("Bread, thats about it");
		meta.setLore(Lore);
		BoiledEgg.setItemMeta(meta);
		this.getServer().addRecipe(new FurnaceRecipe(new ItemStack(BoiledEgg), Material.INK_SACK, 9));
	}
	
//BPOILED EGG
	public void  boiledEgg(){
		ItemStack BoiledEgg = new ItemStack(Material.EGG);
		ItemMeta meta = BoiledEgg.getItemMeta();
		ArrayList<String> Lore = new ArrayList<String>();
		meta.setDisplayName("Boiled Egg");
		Lore.add("An egg boiled in water");
		meta.setLore(Lore);
		BoiledEgg.setItemMeta(meta);
		this.getServer().addRecipe(new FurnaceRecipe(new ItemStack(BoiledEgg), Material.EGG));
	}
//HOT MILK
	public void  hotMilk(){
		ItemStack HotMilk = new ItemStack(Material.MILK_BUCKET);
		ItemMeta meta = HotMilk.getItemMeta();
		ArrayList<String> Lore = new ArrayList<String>();
		meta.setDisplayName("Hot Milk");
		Lore.add("Milk that has been heated");
		meta.setLore(Lore);
		HotMilk.setItemMeta(meta);
		this.getServer().addRecipe(new FurnaceRecipe(new ItemStack(HotMilk), Material.MILK_BUCKET));
		
	}
	
//BROTH POWDER
	public void dehydratedMeat(){
		   ItemStack Broth = new ItemStack(Material.INK_SACK, 1 , (short) 3);
	        ItemMeta broth = Broth.getItemMeta();
	        ArrayList<String> Lore = new ArrayList<String>();
	        Lore.add("Home made broth powder");
	        broth.setLore(Lore);
	        broth.setDisplayName("Broth Powder");
	        Broth.setItemMeta(broth);
	        this.getServer().addRecipe(new FurnaceRecipe(new ItemStack(Broth), Material.COOKED_CHICKEN));
	}
//SPICEY CHICKEN
	public void spiceyChicken(){
		   ItemStack item = new ItemStack(Material.COOKED_CHICKEN);
	        ItemMeta meta = item.getItemMeta();
	        ArrayList<String> Lore = new ArrayList<String>();
	        Lore.add("Spicey Chicken careful its hot");
	        meta.setLore(Lore);
	        meta.setDisplayName("Spicey Chicken");
	        item.setItemMeta(meta);
	        this.getServer().addRecipe(new FurnaceRecipe(new ItemStack(item), Material.RAW_CHICKEN));
	}
//NORMAL CHICKEN
	public void normalChicken(){
		   ItemStack item = new ItemStack(Material.COOKED_CHICKEN);
	        ItemMeta meta = item.getItemMeta();
	        ArrayList<String> Lore = new ArrayList<String>();
	        Lore.add("Chicken, everyone loves chicken");
	        meta.setLore(Lore);
	        meta.setDisplayName("Baked Chicken");
	        item.setItemMeta(meta);
	        this.getServer().addRecipe(new FurnaceRecipe(new ItemStack(item), Material.RAW_CHICKEN));
	}
	
	public void steak(){
		   ItemStack item = new ItemStack(Material.COOKED_BEEF);
	        ItemMeta meta = item.getItemMeta();
	        ArrayList<String> Lore = new ArrayList<String>();
	        Lore.add("Steak, mmmm meaty");
	        meta.setLore(Lore);
	        meta.setDisplayName("Steak");
	        item.setItemMeta(meta);
	        this.getServer().addRecipe(new FurnaceRecipe(new ItemStack(item), Material.RAW_BEEF));
	}

	public void porkchop(){
		   ItemStack item = new ItemStack(Material.GRILLED_PORK);
	        ItemMeta meta = item.getItemMeta();
	        ArrayList<String> Lore = new ArrayList<String>();
	        Lore.add("Pork in the big city");
	        meta.setLore(Lore);
	        meta.setDisplayName("Pork Chop");
	        item.setItemMeta(meta);
	        this.getServer().addRecipe(new FurnaceRecipe(new ItemStack(item), Material.PORK));
	}
	public void grilledfish(){
		   ItemStack item = new ItemStack(Material.COOKED_FISH);
	        ItemMeta meta = item.getItemMeta();
	        ArrayList<String> Lore = new ArrayList<String>();
	        Lore.add("Smells fishy");
	        meta.setLore(Lore);
	        meta.setDisplayName("Baked Fish");
	        item.setItemMeta(meta);
	        this.getServer().addRecipe(new FurnaceRecipe(new ItemStack(item), Material.RAW_FISH));
	}
	
//MUSHROOM SOUP
	@SuppressWarnings("deprecation")
	public void MushroomSoup(){
        ItemStack MushRoomSoup = new ItemStack(Material.MUSHROOM_SOUP);
        ItemMeta imeta = MushRoomSoup.getItemMeta();
        ArrayList<String> Lore = new ArrayList<String>();
        imeta.setDisplayName("Mushroom Soup");
        Lore.add("Home made mushroom soup");
        imeta.setLore(Lore);
        MushRoomSoup.setItemMeta(imeta);
        ShapedRecipe msoup = new ShapedRecipe(new ItemStack(MushRoomSoup));
        msoup.shape(" B ","MWM"," K ");
        msoup.setIngredient('M', Material.BROWN_MUSHROOM);
        msoup.setIngredient('B', Material.INK_SACK, 3);
        msoup.setIngredient('W', Material.WATER_BUCKET);
        msoup.setIngredient('K', Material.BOWL);
        getServer().addRecipe(msoup);
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
	public boolean canCraft(ItemStack result){
//////Bukkit.broadcastMessage("cancraft");
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

//SET FOOD BUFF
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
	}
