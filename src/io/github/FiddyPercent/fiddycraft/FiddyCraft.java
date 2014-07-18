package io.github.FiddyPercent.fiddycraft;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
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
  			
  				
  			AnimalCoolDown();
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
					
					if(newPoints <1 ){
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

//CLAIMING A ANIMAL
	public void claimAnimal(Player p, Entity animal, ItemMeta meta){
		String owner = p.getUniqueId().toString();
		String pet = animal.getUniqueId().toString();
		if(!this.AnimalData.contains("Farmer." + owner)){
			this.AnimalData.set("Farmer."+ owner + ".Name", p.getName());
			this.saveAnimalData();
		}
		
			if(this.canHaveMoreAnimals(p)){
			this.AnimalData.set("Farmer."+ owner + "." +  pet + ".Name", meta.getDisplayName());
			this.AnimalData.set("Farmer."+ owner + "." +  pet + ".Owner", p.getName());
			this.AnimalData.set("Farmer."+ owner + "." +  pet + ".OwnerID", owner );
			this.AnimalData.set("Farmer."+ owner + "." +  pet + ".HeartPoints", 0 );
			this.AnimalData.set("Farmer."+ owner + "." +  pet + ".Hunger", true );
			this.AnimalData.set("Farmer."+ owner + "." +  pet + ".Thirst", true);
			this.AnimalData.set("Farmer."+ owner + "." +  pet + ".Healthy", true );
			this.AnimalData.set("Farmer."+ owner + "." +  pet + ".Brushed", false );
			this.AnimalData.set("Farmer."+ owner + "." +  pet + ".isPet", true );
			this.saveAnimalData();
			p.sendMessage(ChatColor.BLUE + "You now own " + meta.getDisplayName() + "!");
		
		
		
			}else{
				p.sendMessage(ChatColor.RED + "You Have to many animals you cannot claim anymore");
			}
	}
	
//CHECK IF CAN DROP PRODUCE
	public boolean canDropProduce(Player p, Entity e){
		
		if(!this.AnimalData.contains("AnimalCoolDown")){
			return true;
		}else{
		
		if(this.AnimalData.contains("AnimalCoolDown." + e.getUniqueId().toString())){
			int timeLeft = this.AnimalData.getInt("AnimalCoolDown." + e.getUniqueId().toString());
			
			if(timeLeft > 10){
				LivingEntity animal = (LivingEntity) e;
				p.sendMessage(ChatColor.YELLOW + animal.getCustomName() + " looks like it needs to rest a bit more.");
				return false;
			}else{
				this.AnimalData.set("AnimalCoolDown." + e.getUniqueId().toString(), null);
				return true;
			}
		}else{
			return true;
			}
		}
	}
//CHECK IF ANIMAL HAS A OWNER
	public boolean checkifHasOwner(String uuid){
	//getting the list of farmers
		ArrayList<String> allAnimals = new ArrayList<String>();
		if(!this.getAnimalData().contains("Farmer")){
			return false;
		}else{
		Set<String> farmList =  this.getAnimalData().getConfigurationSection("Farmer").getKeys(false);
	//getting the farmers animals
		for(String l : farmList){
			Set<String> t =  this.getAnimalData().getConfigurationSection("Farmer." + l).getKeys(false);
			
			for(String FarmAnimals : t){
				allAnimals.add(FarmAnimals);
			}
		}
		if(allAnimals.contains(uuid)){
	
			return true;
		}else{
			
			return false;
		}
	}
}
//CHECK IF PLAYER IS THE OWNER
	public boolean isAnimalOwner(Player p, Entity e){
		if(this.getAnimalData().contains("Farmer." + p.getUniqueId().toString() + "." + e.getUniqueId().toString())){
			return true;
		}else{
		
		return false;
		}
	}
//PRODUCE DROPPING
	public void DropProduce(Player p, Entity animal){
		String owner = p.getUniqueId().toString();
		String pet = animal.getUniqueId().toString();
		double hp = this.AnimalData.getInt("Farmer." + owner + "." +  pet + ".HeartPoints" );
	//COW
		if(animal instanceof Cow){
			int drop = this.randomNumber(2);
			ItemStack milk = new ItemStack(Material.MILK_BUCKET, drop);
			ItemMeta meta = milk.getItemMeta();
			ArrayList<String> milkLore = new ArrayList<String>();
			if(hp <= 3){
				meta.setDisplayName("Small Milk");
				milkLore.add("Low Quailty Milk");
				milkLore.add("*");
			}else if(hp >= 4 && hp < 6){
				meta.setDisplayName("Medium Milk");
				milkLore.add("Good Quality Milk");
				milkLore.add("**");
			}else if(hp >= 7 && hp < 9){
				meta.setDisplayName("Large Milk");
				milkLore.add("Great Quailty Milk");
				milkLore.add("***");
			}else{
				meta.setDisplayName("Golden Milk");
				milkLore.add("The Highest Quality Milk");
				milkLore.add("****");;
			}
			meta.setLore(milkLore);
			milk.setItemMeta(meta);
			Bukkit.getWorld("world").dropItem(animal.getLocation(), milk);
			this.getAnimalData().set("AnimalCoolDown." + pet, 6000);
			this.saveAnimalData();
	//CHICKEN	
		}else if(animal instanceof Chicken){
			ItemStack Egg = new ItemStack(Material.EGG);
			ItemMeta meta = Egg.getItemMeta();
			ArrayList<String> feathers = new ArrayList<String>();
			ArrayList<String> eggLore = new ArrayList<String>();
			int r = this.randomNumber(3);
			int rare = this.randomNumber(100);
			ItemStack Feather = new ItemStack(Material.FEATHER, r);
			ItemMeta fm = Feather.getItemMeta();
			
			if(rare == 100 && hp > 7){
				fm.setDisplayName("Golden Feather");
				feathers.add("A very rare feather that causes temporary flight");
			}else if(rare <= 95 && rare >= 85 && hp > 5){
				fm.setDisplayName("Grey Feather");
				feathers.add("A rare feather that causes temporary flight");
			}else{
				fm.setDisplayName("Normal Feather");
				feathers.add("A feather");
			}
			
			if(hp <= 1){
				meta.setDisplayName("Ugly Egg");
				eggLore.add("A warped egg not very good looking");
				eggLore.add("X");
			}else if(hp >= 2 && hp < 5){
				meta.setDisplayName("Normal Egg");
				eggLore.add("A normal looking egg");
				eggLore.add("*");
			}else if(hp >= 5 && hp < 6){
				meta.setDisplayName("Good Egg");
				eggLore.add("A good sized egg");
				eggLore.add("**");
			}else if(hp >= 7 && hp < 9){
				meta.setDisplayName("High Quality Egg");
				eggLore.add("A very good quality large egg");
				eggLore.add("***");
			}else{
				meta.setDisplayName("Golden Golden");
				eggLore.add("A rare Golden Egg the highest quality of egg");
				eggLore.add("****");
			}
			
		meta.setLore(eggLore);
		Egg.setItemMeta(meta);
		fm.setLore(feathers);
		Feather.setItemMeta(fm);
		
		Bukkit.getWorld("world").dropItem(animal.getLocation(), Egg);
		Bukkit.getWorld("world").dropItem(animal.getLocation(), Feather);
		this.getAnimalData().set("AnimalCoolDown." + pet, 6000);
		this.saveAnimalData();
	//SHEEP
		}else if(animal instanceof Sheep){
			
			
			int drops = this.randomNumber(4);
			ItemStack wool = new ItemStack(Material.WOOL, drops);
			ArrayList<String> woolLore = new ArrayList<String>();
			ItemMeta meta = wool.getItemMeta();
			if(hp <= 3){
				meta.setDisplayName("Small Wool");
				woolLore.add("Low Quality wool");
			}else if(hp >= 4 && hp < 6){
				meta.setDisplayName("Medium Wool");
				woolLore.add("Good Quality wool");
			}else if(hp >= 7 && hp < 9){
				meta.setDisplayName("Large Wool");
				woolLore.add("Great Quality wool");
			}else{
				meta.setDisplayName("Golden Wool");
				woolLore.add("The Highest Quality of Wool");
			}
			
			Sheep sheep = (Sheep) animal;
			sheep.setSheared(true);
			meta.setLore(woolLore);
			wool.setItemMeta(meta);
			Bukkit.getWorld("world").dropItem(animal.getLocation(), wool);
			this.getAnimalData().set("AnimalCoolDown." + pet, 6000);
			this.saveAnimalData();
		}
	}
//ADDING HEART POINTS
	public void addheartPoint(Entity e, Player p, double  hp){
		String owner = p.getUniqueId().toString();
		String pet = e.getUniqueId().toString();
		double oldhp =	this.AnimalData.getDouble("Farmer."+ owner +"." +  pet + ".HeartPoints" );
		double newhp = hp + oldhp;
		double roundOff = Math.round(newhp * 100.0) / 100.0;
		this.AnimalData.set("Farmer."+ owner +"." +  pet + ".HeartPoints", roundOff );
		this.saveAnimalData();
	}
//CHECK IF IS ANIMAL
	public boolean isAnimal(Entity e){
		
		if(e instanceof Cow || e instanceof Chicken || e instanceof Sheep || e instanceof Horse
				|| e instanceof Wolf || e instanceof Ocelot ||e instanceof Pig || e instanceof Mushroom){
			return true;
		}else{
			return false;
		}
	}
//ANIMAL COOL DOWN
	public void AnimalCoolDown(){
		
		if(!this.getAnimalData().contains("AnimalCoolDown")){
			this.getAnimalData().set("AnimalCoolDown", "test");
			this.saveAnimalData();
		}
		Player[] p = Bukkit.getOnlinePlayers();
			for(Player player : p){
			
				if(this.getAnimalData().contains("Farmer." + player.getUniqueId().toString())){
				String l = player.getUniqueId().toString();	
		
				Set<String> t =  this.getAnimalData().getConfigurationSection("Farmer." + l).getKeys(false);
				for(String aL : t){
					
					if(this.getAnimalData().contains("AnimalCoolDown." + aL)){
						int cooldown = this.getAnimalData().getInt("AnimalCoolDown." + aL);				
						int newcooldown = cooldown - 5;
						this.getAnimalData().set("AnimalCoolDown." + aL, newcooldown);
						this.saveAnimalData();
						if(newcooldown <= 0){
							this.getAnimalData().set("AnimalCoolDown." + aL, null);
							this.saveAnimalData();
						}
						
					}
				}
			}
		}
	}
	public void removeAnimal(Entity e){
				Set<String> farmers =  this.getAnimalData().getConfigurationSection("Farmer").getKeys(false);
				for(String farmer : farmers){
					Set<String> farmeranimals =  this.getAnimalData().getConfigurationSection("Farmer." + farmer).getKeys(false);
					
					for(String farmanimal : farmeranimals){
						if(farmanimal.equalsIgnoreCase(e.getUniqueId().toString())){
							LivingEntity le = (LivingEntity) e;
							this.getAnimalData().set("Farmer." + farmer +"."+ farmanimal, null);
							Player[] p = Bukkit.getOnlinePlayers();
							for(Player online : p){
							if(online.getUniqueId().toString().equalsIgnoreCase(farmer)){
							online.sendMessage(ChatColor.GRAY + "Your " + e.getType().toString() +  " " + le.getCustomName() + " has " + ChatColor.DARK_RED + "died.");
							}
							}
							if(this.getAnimalData().contains("AnimalCoolDown." + farmanimal)){
								this.getAnimalData().set("AnimalCoolDown." + farmanimal, null );
							}
							this.saveAnimalData();
							}
						}
					}
				}

//CHECK ANIMAL LIMITS
	public boolean canHaveMoreAnimals(Player p ){
		if(this.getAnimalData().getString("Farmer") == null){
			return true;
		}else{
			if(!this.getAnimalData().contains("Farmer." + p.getUniqueId().toString())){
				this.AnimalData.set("Farmer."+ p.getUniqueId().toString() + ".Name", p.getName());
				this.saveAnimalData();
			}
			Set<String> t =  this.getAnimalData().getConfigurationSection("Farmer." + p.getUniqueId().toString()).getKeys(false);
			ArrayList<String> animals = new ArrayList<String>();
			for(String aL : t){
				animals.add(aL);
			}
					if(animals.size() <= 11){
						return true;
					}else{
						return false;
			}
		}
	}

	public boolean getanimalHunger(String uuid, String auuid){
		return this.AnimalData.getBoolean("Farmer."+ uuid + "." +  auuid + ".Hunger");
	}
	
	public void setanimalHunger(String uuid, String auuid, boolean b){
		 this.AnimalData.set("Farmer."+ uuid + "." +  auuid + ".Hunger", b);
		 this.saveAnimalData();
	}
	
	public boolean getanimalThirst(String uuid, String auuid){
		return this.AnimalData.getBoolean("Farmer."+ uuid + "." +  auuid + ".Thirst");
	}
	
	public void setanimalThirst(String uuid, String auuid, boolean b){
		 this.AnimalData.set("Farmer."+ uuid + "." +  auuid + ".Thirst", b);
		 this.saveAnimalData();
	}
	
	public double getanimalHeartPoints(String uuid, String auuid){
		return this.AnimalData.getDouble("Farmer."+ uuid + "." +  auuid + ".HeartPoints");
	}
	
	public void setanimalHeartPoints(String uuid, String auuid, Double d){
		 this.AnimalData.set("Farmer."+ uuid + "." +  auuid + ".HeartPoints", d);
		 this.saveAnimalData();
	}
	
	public boolean isanimalWashed(String uuid, String auuid){
		return this.AnimalData.getBoolean("Farmer."+ uuid + "." +  auuid + ".Brushed");
	}
	
	public void setanimalWashed(String uuid, String auuid, Boolean b){
		this.AnimalData.set("Farmer."+ uuid + "." +  auuid + ".Brushed", b);
		this.saveAnimalData();
	}
	public boolean isanimalHealthy(String uuid, String auuid){
		return this.AnimalData.getBoolean("Farmer."+ uuid + "." +  auuid + ".Healthy");
	}
	
	public void setanimalHealth(String uuid, String auuid, Boolean b){
		this.AnimalData.getBoolean("Farmer."+ uuid + "." +  auuid + ".Healthy", b);
		this.saveAnimalData();
	}
	public String getanimalName(String uuid, String auuid){
		return this.AnimalData.getString("Farmer."+ uuid + "." +  auuid + ".Name");
	}
	
	public void setanimalName(String uuid, String auuid, String name){
		 this.AnimalData.set("Farmer."+ uuid + "." +  auuid + ".Name", name);
		 this.saveAnimalData();
	}
	public String getanimalOwner(String auuid){
		Set<String> farmers = this.getAnimalData().getConfigurationSection("Farmer").getKeys(false);
		for(String farmer : farmers){
			if(this.getAnimalData().contains("Farmer." + farmer +"." + auuid)){
				return this.AnimalData.getString("Farmer."+ farmer + "." +  auuid + ".Owner");
			}else{
				return "Unknown";
			}
				
		}
		return "Unknown";
		
	}
	
	public String getanimalOwnerID(String auuid){
		Set<String> farmers = this.getAnimalData().getConfigurationSection("Farmer").getKeys(false);
		for(String farmer : farmers){
			if(this.getAnimalData().contains("Farmer." + farmer +"." + auuid)){
				return this.AnimalData.getString("Farmer."+ farmer + "." +  auuid + ".OwnerID");
			}else{
				return "Unknown";
			}
				
		}
		return "Unknown";
		
	}
	public boolean isanimalAbletobePet(String uuid, String auuid){
		return this.AnimalData.getBoolean("Farmer."+ uuid + "." +  auuid + ".isPet");
	}
	
	public void setanimalAbletobePet(String uuid, String auuid, Boolean b){
		 this.AnimalData.set("Farmer."+ uuid + "." +  auuid + ".isPet", b);
		 this.saveAnimalData();
	}
	public void playAnimalSound(Entity e, Player p){
		String type = e.getType().toString();
		Location loc = p.getLocation();
		if(type.equalsIgnoreCase("Cow")){
			Bukkit.getWorld("world").playSound(loc, Sound.COW_IDLE, 0, 1);
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 5);
		}else if(type.equalsIgnoreCase("Sheep")){
			Bukkit.getWorld("world").playSound(loc, Sound.SHEEP_IDLE, 0, 1);
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 5);
		}else if(type.equalsIgnoreCase("Pig")){
			Bukkit.getWorld("world").playSound(loc, Sound.PIG_IDLE, 0, 1);
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 5);
		}else if(type.equalsIgnoreCase("chicken")){
			Bukkit.getWorld("world").playSound(loc, Sound.CHICKEN_IDLE, 0, 1);
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 5);
		}else if(type.equalsIgnoreCase("Horse")){
			Bukkit.getWorld("world").playSound(loc, Sound.HORSE_BREATHE, 0, 1);
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 5);
		}else if(type.equalsIgnoreCase("Wolf")){
			Bukkit.getWorld("world").playSound(loc, Sound.WOLF_BARK, 0, 1);
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 5);
		}
		
	}
	
	public String getAllFarmAnimals(){
		Player[] p = Bukkit.getOnlinePlayers();
		for(Player player : p){
		
			if(this.getAnimalData().contains("Farmer." + player.getUniqueId().toString())){
			String farmer = player.getUniqueId().toString();	
	
			Set<String> animals =  this.getAnimalData().getConfigurationSection("Farmer." + farmer).getKeys(false);
			for(String animal : animals){
		
		return animal;
				}
			}else{
				return null;
				}
			}
		return null;
	}
	
	public boolean animalFood(Entity e, Material item){
		
		if(e instanceof Cow || e instanceof Horse || e instanceof Pig || e instanceof Sheep ){
			if(item == Material.HAY_BLOCK){
				return true;
			}else{
				return false;
			}
		}else if(e instanceof Chicken){
			if(item == Material.SEEDS){
				return true;
			}else{
				return false;
			}
		}else if( e instanceof Wolf){
			if(item == Material.ROTTEN_FLESH || item == Material.RAW_BEEF || item == Material.PORK){
				return true;
			}else{
				return false;
			}
		}else if(e instanceof Ocelot){
			if(item == Material.RAW_FISH){
				return true;
			}else{
				return false;
			}
			
		}else{
			return false;
		}
	}
	
	public boolean isAnimalFood(Material item){
		if(item == Material.HAY_BLOCK || item == Material.SEEDS || item == Material.ROTTEN_FLESH || item == Material.RAW_BEEF || item == Material.PORK
				|| item == Material.RAW_FISH){
			return true;
		}else{
			return false;
		}
	}
	
	public void newDay(){
	long Time = Bukkit.getWorld("world").getTime();
	if(newday.isEmpty() || newday.get("newday") == false){
		if(Time > 23000){
			Player[] p = Bukkit.getOnlinePlayers();
			for(Player player : p){
				if(this.getAnimalData().contains("Farmer." + player.getUniqueId().toString())){
				String farmer = player.getUniqueId().toString();	
		
				Set<String> animals =  this.getAnimalData().getConfigurationSection("Farmer." + farmer).getKeys(false);
				for(String animal : animals){
					
					this.setanimalHunger(farmer, animal, true);
					this.setanimalThirst(farmer, animal, true);
					this.setanimalWashed(farmer, animal, false);
					this.setanimalAbletobePet(farmer, animal, true);
					newday.put("newday", true);
					}
				}
			}
		}
	}
}
	public void animalEat(Player p, ItemStack i){
		String uuid = p.getUniqueId().toString();
		if(this.getAnimalData().contains("Farmer." + uuid)){
			List<Entity> nearby = p.getNearbyEntities(2, 2, 2);
			for(Entity entity: nearby){
				if(this.isAnimal(entity)){
					if(this.isAnimalOwner(p, entity)){
						if(this.getanimalHunger(p.getUniqueId().toString(), entity.getUniqueId().toString())){
									if(this.animalFood(entity, i.getType())){
										this.playAnimalSound(entity, p);
										i.setType(Material.SEEDS);
										this.addheartPoint(entity, p, .03);
										this.setanimalHunger(p.getUniqueId().toString(), entity.getUniqueId().toString(), false);
								}
							}
						}	
					}
				}
			}
		}
	public void petAnimal(Player p , Entity e){
		String uuid = p.getUniqueId().toString();
		String auuid = e.getUniqueId().toString();
		if(this.isAnimalOwner(p, e)){
			if(this.isanimalAbletobePet(uuid, auuid)){
				this.playAnimalSound(e, p);
				p.sendMessage(ChatColor.YELLOW + "You pet " + this.getanimalName(uuid, auuid));
				this.addheartPoint(e, p, .01);
				this.setanimalAbletobePet(uuid, auuid, false);
			}else{
				p.sendMessage(ChatColor.YELLOW + "Looks like "  + this.getanimalName(uuid, auuid) +  " has been pet enough.");
			}
		}
	}


	public void showAnimalInfo(Player p, Entity e){
	if(this.checkifHasOwner(e.getUniqueId().toString())){
		String auuid = e.getUniqueId().toString();
		String uuid = this.getanimalOwnerID(auuid);
		int hp = (int) this.getanimalHeartPoints(uuid, auuid);
		p.sendMessage(ChatColor.YELLOW + "+++++ Animal Info +++++");
		p.sendMessage(ChatColor.YELLOW + "Animal Name: "  + ChatColor.GREEN+ getanimalName(uuid, e.getUniqueId().toString()) );
		p.sendMessage(ChatColor.YELLOW + "Animal Owner: "  + ChatColor.GREEN+ this.getanimalOwner(auuid));
		p.sendMessage(ChatColor.YELLOW + "Heart Level: " + ChatColor.GREEN + hp);
		p.sendMessage(ChatColor.YELLOW + "Is Hungry: " + ChatColor.GREEN + this.getanimalHunger(uuid, auuid));
		p.sendMessage(ChatColor.YELLOW + "Is Dirty: "  + ChatColor.GREEN + this.isanimalWashed(uuid, auuid));
		p.sendMessage(ChatColor.YELLOW + "Is lonely: "  + ChatColor.GREEN + this.isanimalAbletobePet(uuid, auuid));
		}
	}
	
	public void washAnimal(Player p , Entity e){
		String uuid = p.getUniqueId().toString();
		String auuid = e.getUniqueId().toString();
		if(this.isAnimalOwner(p, e)){
		if((this.isanimalWashed(uuid, auuid) == true)){
			p.sendMessage(ChatColor.RED + this.getanimalName(uuid, auuid) + " is already clean");
		}
		if((this.isanimalWashed(uuid, auuid) == false)){
			Bukkit.getWorld("world").playSound(p.getLocation(), Sound.SWIM, 1, 1);
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.POTION_BREAK, 0);
			p.sendMessage(ChatColor.YELLOW + this.getanimalName(uuid, auuid) + " is all clean!");
			this.setanimalWashed(uuid, auuid, true);
			this.addheartPoint(e, p, .02);
		}
		}
	}
	
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
	
	public boolean isCookedFood(Material food){
		Material f = food;
		if(f == Material.COOKED_BEEF || f == Material.COOKED_CHICKEN || f == Material.COOKED_FISH){
			return true;
		}else{
		return false;
		}
	}
	
//
	public boolean isaNoneFoodItem(Material item){
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
		}
//COOKING RECIPE NAMES
		public boolean isRecipe(String Resultname){
			ArrayList<String> recipeNames = new ArrayList<String>();
			recipeNames.add("Broth Powder");
			recipeNames.add("Boiled Egg");
			recipeNames.add("Mushroom Soup");
			recipeNames.add("Hot Milk");
			recipeNames.add("Sweet Milk");
			
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
			Bukkit.broadcastMessage("inside hasIngredents");
	//MUSHROOM SOUP	
		if(fn.equalsIgnoreCase("Mushroom Soup")){
		//	Bukkit.broadcastMessage("mushroomSoup");
			foodlist.add("Broth Powder");
		//	Bukkit.broadcastMessage(ChatColor.GREEN + "item list" + itemlist.toString());
		//	Bukkit.broadcastMessage(ChatColor.DARK_AQUA +"food list" + foodlist.toString());
			if(itemlist.containsAll(foodlist) && foodlist.size() == itemlist.size()){
				Bukkit.broadcastMessage("fl size " + foodlist.size()  );
			//	Bukkit.broadcastMessage("true");
				return true;
			}else{
			//	Bukkit.broadcastMessage("false");
				return false;
			}
		}
	//SWEET MILK
		if(fn.equalsIgnoreCase("Sweet Milk")){
			foodlist.add("Hot Milk");
			Bukkit.broadcastMessage(ChatColor.GREEN + "item list" + itemlist.toString());
			Bukkit.broadcastMessage(ChatColor.DARK_AQUA +"food list" + foodlist.toString());
			if(itemlist.containsAll(foodlist) && foodlist.size() == itemlist.size()){
			//	Bukkit.broadcastMessage("true");
				return true;
			}else{
				Bukkit.broadcastMessage("false");
			//	return false;
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
		
		
		public boolean canBeSpiced(){
			HashMap<String,ArrayList<String>> spiceables = new HashMap<String, ArrayList<String>>();
			ArrayList<String> spicesNeeded = new ArrayList<String>();
			spiceables.put("", spicesNeeded);
			
			return false;
		}
		
//FURNACE INGREDENTS
		public boolean hasFurnaceIngredents(String sourceName, String resultName){
			String sn = sourceName;
			String rn = resultName;
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
	    ItemStack MushRoomSoup = new ItemStack(Material.MUSHROOM_SOUP);
        ItemMeta imeta = MushRoomSoup.getItemMeta();
        ArrayList<String> Lore = new ArrayList<String>();
        imeta.setDisplayName("Sweet Milk");
        Lore.add("Sweetened Milk, may cause diabetes");
        imeta.setLore(Lore);
        MushRoomSoup.setItemMeta(imeta);
        ShapedRecipe sweetMilk = new ShapedRecipe(new ItemStack(MushRoomSoup));
        sweetMilk.shape("SSS"," M ","   ");
        sweetMilk.setIngredient('S', Material.SUGAR);
        sweetMilk.setIngredient('M', Material.MILK_BUCKET);
        getServer().addRecipe(sweetMilk);
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
		Bukkit.broadcastMessage("setting cooking rank");
		if(r == 1){
			return "*";
		}else if(r == 2){
			return "**";
		}else if(r == 3){
			return "***";
		}else if(r >= 4){
			return "****";
		}else{
			return "X";
		}
	}
}