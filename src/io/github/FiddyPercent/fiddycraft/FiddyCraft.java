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
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
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
	  public HashMap<String, Boolean> TrialStart = new HashMap<String, Boolean>();
	  public HashMap<String, Boolean> TrialReady = new HashMap<String, Boolean>();
	  public HashMap<String, Integer> openingStatmentProsecution = new HashMap<String, Integer>();
	  public HashMap<String, Integer> testimony = new HashMap<String, Integer>();
	  public HashMap<String, Integer> closingStatmentProsecution = new HashMap<String, Integer>();
	  public HashMap<String, Integer> closingStatmentDefense= new HashMap<String, Integer>();
	  public HashMap<String, Integer> evidence = new HashMap<String, Integer>();
	  public HashMap<String, Integer> TrialTime = new HashMap<String, Integer>();
	  public HashMap<UUID, Integer> pvpLoggers = new HashMap<UUID, Integer>();
	  public HashMap<String, String> Charge = new HashMap<String, String>();
	  public ArrayList<String> defendant = new ArrayList<String>();
	  public ArrayList<String> Prosecutor = new ArrayList<String>();
	  public ArrayList<String> Defence = new ArrayList<String>();
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
	  FcPerm fp = new FcPerm(this);
	 
	  BOSEconomy economy = null;
	  
	  
    public void onDisable() {
        this.getLogger().info("FiddyCraft is Disabled");
        saveConfig();
        savePlayerInfo();
    	this.TrialStart.clear();
    	this.closingStatmentDefense.clear();
    	this.closingStatmentProsecution.clear();
    	this.evidence.clear();
    	this.defendant.clear();
    	this.Prosecutor.clear();
    	this.Defence.clear();
    	this.Judge.clear();
    	this.Judgement.clear();
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
        getCommand("Defend").setExecutor(new FiddyCraftCommands(this));
        getCommand("StartTrial").setExecutor(new FiddyCraftCommands(this));
        getCommand("JUDGEMENT").setExecutor(new FiddyCraftCommands(this));
        getCommand("EndStatment").setExecutor(new FiddyCraftCommands(this));
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
  			
 				if(murderTimer.isEmpty() == false){
					int time = murderTimer.get("LastMurder");
					int rt = time - 100;
					murderTimer.put("LastMurder", rt);
					if(murderTimer.get("LastMurder") == 0){
						murderTimer.clear();
						crimescene.clear();
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
 				
 				
 				
				if(!TrialStart.isEmpty() && TrialStart.get("Trial") == true){
					
					if(openingStatmentProsecution.containsKey("Trial")){
						String t = "Trial";
						int op = openingStatmentProsecution.get("Trial");
						int newop = op - 100;
						openingStatmentProsecution.put(t, newop);
						CourtSigns("OpeningStatment", op);
					if(op <= 0 ){
						if(isPlayer(Judge.get(0)) && isPlayer( Prosecutor.get(0)) && isPlayer(Defence.get(0))){
							Player j = Bukkit.getPlayer(Judge.get(0));
							Player pro = Bukkit.getPlayer(Prosecutor.get(0));
							Player def = Bukkit.getPlayer(Defence.get(0));
							j.chat(ChatColor.DARK_PURPLE +  "Now it is time to move on with the trial, Prosecution, you may now show evidence or use witness testemony in just a moment");
							j.sendMessage(ChatColor.YELLOW + "I need to pay attention and be unbiased. Remember hard evidence is stronger than testimony.");
							pro.sendMessage(ChatColor.YELLOW + "I have 10 minutes total to prove the defense guilty I need to make a solid case right off the bat");
							def.sendMessage(ChatColor.YELLOW + "Now the prosecution will present evidence to win this case I have 10 minutes to get not guilty");
							openingStatmentProsecution.clear();
							evidence.put("Trial", 12000);
						}
					}
				}
					
					if(closingStatmentProsecution.containsKey("Trial")){
						String t = "Trial";
						int op = closingStatmentProsecution.get("Trial");
						int newop = op - 100;
						closingStatmentProsecution.put(t, newop);
						CourtSigns("Closing Statment", op);
						if(op <= 0 ){
							if(isPlayer(Judge.get(0)) && isPlayer( Prosecutor.get(0)) && isPlayer(Defence.get(0))){
								Player j = Bukkit.getPlayer(Judge.get(0));
								Player pro = Bukkit.getPlayer(Prosecutor.get(0));
								Player def = Bukkit.getPlayer(Defence.get(0));
								j.chat(ChatColor.DARK_PURPLE + "That is enough, time for the defense closing statment");
								j.sendMessage(ChatColor.YELLOW + "Time is up for the Prosecutions closing statement, now it’s time for the Defense Attorneys final statement.");
								pro.sendMessage(ChatColor.YELLOW + "Time is up for my closing statment the Judge will decide the verdict after the Defense Attorneys statment.");
								def.sendMessage(ChatColor.YELLOW + "The Proseuction's closing statment is over now is your last chance to win the case.");
								closingStatmentProsecution.clear();
								closingStatmentDefense.put("Trial", 2400);
							}
						}
					}
					
					if(closingStatmentDefense.containsKey("Trial")){
						String t = "Trial";
						int op = closingStatmentDefense.get("Trial");
						int newop = op - 100;
						closingStatmentDefense.put(t, newop);
						CourtSigns("Closing Statment", op);
						if(op <= 0 ){
							if(isPlayer(Judge.get(0)) && isPlayer( Prosecutor.get(0)) && isPlayer(Defence.get(0))){
								Player j = Bukkit.getPlayer(Judge.get(0));
								Player pro = Bukkit.getPlayer(Prosecutor.get(0));
								Player def = Bukkit.getPlayer(Defence.get(0));
								j.chat(ChatColor.DARK_PURPLE + "That is enough I will now decide the verdict");
								j.sendMessage(ChatColor.YELLOW + "Both sides have been heard please give the points to the proper side and place a verdict");
								pro.sendMessage(ChatColor.YELLOW + "Time is up for the closing statment the Judge will now decide");
								def.sendMessage(ChatColor.YELLOW + "This is it, time is up and the judge has the final call");
								closingStatmentDefense.clear();
								Judgement.put(j.getName(), true);
							}
						}
					}
					
					if(evidence.containsKey("Trial")){
						String t = "Trial";
						int op = evidence.get("Trial");
						int newop = op - 100;
						evidence.put(t, newop);
						Bukkit.broadcastMessage("Evidence time" + op);
						CourtSigns("Trial hearing", op);
					if(op <= 0){
					if(isPlayer(Judge.get(0)) && isPlayer( Prosecutor.get(0)) && isPlayer(Defence.get(0))){
						Player j = Bukkit.getPlayer(Judge.get(0));
						Player pro = Bukkit.getPlayer(Prosecutor.get(0));
						Player def = Bukkit.getPlayer(Defence.get(0));
						j.chat(ChatColor.DARK_PURPLE + "I have heard enough you my now start your closing statment Prosecution");
						j.sendMessage(ChatColor.YELLOW + "Listen carefully to each statments and try to decide which one is right");
						pro.sendMessage(ChatColor.YELLOW + "Time to show my evidence for this trial ");
						def.sendMessage(ChatColor.YELLOW + "Rember this is the only chance you get to win you have Object, and cross examin to win");
						evidence.clear();
						closingStatmentProsecution.put("Trial", 2400);
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
		p.sendMessage(ChatColor.GOLD + "Make your statments direct and to the point you do not have alot of time.");
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
		p.sendMessage(ChatColor.DARK_GRAY + "Only take Prosecution and Defense questions any others are null");
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
				Bukkit.broadcastMessage(ChatColor.RED +"in SIgns");
				Sign pSign = (Sign) ps;
				Sign dSign = (Sign) ds;
				Sign jSign = (Sign) js;
				pSign.setLine(0, ChatColor.RED + "Prosecution");
				pSign.setLine(1, State);
				pSign.setLine(2, "Time: " + op );
				pSign.update();
				
				
				dSign.setLine(0, ChatColor.BLUE + "Defense");
				dSign.setLine(1, State);
				dSign.setLine(2, "Time: " + op );
				dSign.update();
				
				jSign.setLine(0, ChatColor.BOLD  + "Judge");
				jSign.setLine(1, State);
				jSign.setLine(2, "Time: " + op );
				jSign.update();
				
				
			}else{
				Bukkit.broadcastMessage("Not a sign");
			}
			
			}else{
				Bukkit.broadcastMessage("no config names");
			
			}
		}
		
	}
}


