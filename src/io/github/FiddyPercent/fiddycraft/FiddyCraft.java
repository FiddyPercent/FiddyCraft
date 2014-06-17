package io.github.FiddyPercent.fiddycraft;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
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
	  public final FiddyCraftListener fcl = new FiddyCraftListener(this);
	  public ArrayList<Boolean> TrialStart = new ArrayList<Boolean>();
	  public ArrayList<String> defendant = new ArrayList<String>();
	  public ArrayList<String> Prosecutor = new ArrayList<String>();
	  public ArrayList<String> Defence = new ArrayList<String>();
	  public ArrayList<String> Judge = new ArrayList<String>();
	  public ArrayList<String> Jury = new ArrayList<String>();
	  public ArrayList<String> items = new ArrayList<String>();
	  public HashMap<UUID, UUID> attackedLast  = new HashMap<UUID, UUID>();
	  public HashMap<String, Integer> drunkLvl = new HashMap<String, Integer>();
	  public HashMap<UUID, Boolean> patrol = new HashMap<UUID, Boolean>();
	  public HashMap<String, Integer> murderTimer = new HashMap<String, Integer>(); 
	  public ArrayList<String > potlore = new ArrayList<String>();
	  public HashMap<String, Location> crimescene = new HashMap<String, Location>();
	  private static ScoreboardManager manager;
	  static HashMap<String, Scoreboard> boards = new HashMap<String, Scoreboard>();
	  private static HashMap<String, Integer> sentences = new HashMap<String, Integer>();
	  private static final String OBJ_FORMAT = "§a%s";
	  private static final String SCORE_NAME = "§bLabor";
	  FcPerm fp = new FcPerm(this);
	 
	  BOSEconomy economy = null;
	  
	  
    public void onDisable() {
        this.getLogger().info("FiddyCraft is Disabled");
        saveConfig();
        savePlayerInfo();
    }
    public void onEnable(){
    	loadConfig();
    	reloadPlayerInfo();
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
    	new BukkitRunnable(){
			@Override
			public void run() {
				
				if(murderTimer.isEmpty() == false){
					int time = murderTimer.get("LastMurder");
					int rt = time - 100;
					murderTimer.put("LastMurder", rt);
					if(murderTimer.get("LastMurder") == 0){
						murderTimer.clear();
						crimescene.clear();
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
	
}


