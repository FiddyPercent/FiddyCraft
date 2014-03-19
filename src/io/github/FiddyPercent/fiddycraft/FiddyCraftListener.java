package io.github.FiddyPercent.fiddycraft;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.ScoreboardManager;



public class FiddyCraftListener implements Listener{
	
	HashMap<UUID, ItemStack> thug = new HashMap<UUID, ItemStack>();
	
	public final FiddyCraft plugin;
	 
	public FiddyCraftListener(FiddyCraft plugin){
	this.plugin = plugin;
	}
	
	
	
HashMap<String, Integer> Attacked = new HashMap<String, Integer>();
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void attack(EntityDamageEvent event) {
		if(event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && event.getEntityType() 
				== EntityType.PLAYER){
			Player player = (Player) event.getEntity();
			String name = player.getName();
			
			 if(event.getCause() == DamageCause.ENTITY_ATTACK) {
                 EntityDamageByEntityEvent evnt = (EntityDamageByEntityEvent) event;
                 Entity damager = evnt.getDamager();
                 if(damager instanceof Zombie) {
                	String b =  (((LivingEntity) damager).getCustomName()); 
                	if(b.equalsIgnoreCase("Tax Zombie")){
                		
                		if(!Attacked.containsKey(name) || Attacked.get(name) == 0){
                			if(!Attacked.containsKey(name)){
            				Attacked.put(name, 1);
                			}
            				double preamount = plugin.economy.getPlayerMoneyDouble(name);
                			int amount = (int) (preamount * .01);
                			
                			
                			
                			if(amount == 0){
                				ItemStack[] armor = player.getInventory().getArmorContents();
                				if(armor[0].getType().name().equalsIgnoreCase("air") && armor[1].getType().name().equalsIgnoreCase("air") &&
                        				armor[2].getType().name().equalsIgnoreCase("air")
                        				&& armor[3].getType().name().equalsIgnoreCase("air")){
                					player.sendMessage(ChatColor.RED +"What! you don't have enough for taxes? Its Jail time for you buddy");
                					
                					double x =  (double) plugin.getConfig().get("Jail.X");
                					double y =  (double) plugin.getConfig().get("Jail.y");
                					double z =  (double) plugin.getConfig().get("Jail.z");
                					 Location loc = new Location(Bukkit.getServer().getWorld("world"),x, y, z);
                				
                					player.teleport(loc);
                					plugin.getConfig().set("Jailed."+ player.getName(), 250);
                					plugin.saveConfig();
                					
                					plugin.setJailScoreBoard(player,"250");
                				}else{
                					
                					if (!(armor[3].getType().equals(Material.AIR))){
               						 // player is wearing a helmet
               						
               						ItemStack helmet = player.getInventory().getHelmet().clone();
               						((LivingEntity) damager).getEquipment().setHelmet(helmet);
               					
               						}
               						if (!(armor[2].getType().equals(Material.AIR))) {
               						 // player is wearing a chestplate
               						ItemStack chestplate =  player.getInventory().getChestplate().clone();
               						((LivingEntity) damager).getEquipment().setChestplate(chestplate);
               					
               						}
               						if (!(armor[1].getType().equals(Material.AIR))) {
               						 // player is wearing  leggings
               						ItemStack leggings =  player.getInventory().getLeggings().clone();
               						((LivingEntity) damager).getEquipment().setLeggings(leggings);
               						}
               						if (!(armor[0].getType().equals(Material.AIR))) {
               						ItemStack boots = armor[0].clone();
               						((LivingEntity) damager).getEquipment().setBoots(boots);
               						 // player is wearing  boots
               						}
               					
               						int numberAttacked = Attacked.get(name);
                    				int total = numberAttacked +1;
                    				Attacked.put(name, total);
               						player.getInventory().setArmorContents(null);
               						
               						player.sendMessage("No money to give huh? This armor looks pretty nice I'll take that");
               						
               						((Zombie) damager).getTarget().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 500, 3));
               						((Zombie) damager).getTarget().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 500, 3));
               						((Zombie) damager).setHealth(20);
               						
               						((Zombie) damager).setCustomName(ChatColor.RED + "Thug Zombie");
               						
               						
               						
                				}
                				
                				
                			}else{
                				double playerMoney = plugin.economy.getPlayerMoneyDouble(name);
                    			double FiddyMoney = plugin.economy.getPlayerMoneyDouble("Fiddy_percent");
                    			plugin.economy.setPlayerMoney(name, playerMoney - amount, false);
                    			plugin.economy.setPlayerMoney("Fiddy_percent",FiddyMoney + amount , false);
                    			
                    				player.sendMessage(ChatColor.GREEN +b + ChatColor.WHITE + " Thank you for your " + ChatColor.RED +amount + 
                    				ChatColor.WHITE +" dollar(s) in taxes.");
                					damager.remove();
                					if(Bukkit.getPlayer("Fiddy_percent").isOnline() && amount > 0){
                        				Player mp = Bukkit.getPlayer("Fiddy_percent");
                        				mp.sendMessage(ChatColor.GRAY + "You just got " + amount + " in taxes!");
                        			}
                			}
                			
            	  }else if(Attacked.get(name) <= 3 ){
            				int numberAttacked = Attacked.get(name);
            				int total = numberAttacked +1;
            				Attacked.put(name, total);
            	  }else{
            		  	Attacked.put(name, 0);
            		  	player.sendMessage(ChatColor.GREEN +b + ChatColor.WHITE + " Oh you wish to give more? " );
            			}
                	
                		}
     
                	}else if(damager instanceof Player){
                	boolean goldArmor = plugin.hasArmor(player, Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS);
                	boolean goldArmorAttacker = plugin.hasArmor((Player)damager, Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS);
                		if(goldArmor){
                			((Player) damager).sendMessage(ChatColor.YELLOW + "This player is wearing anti pvp armor");
                			event.setCancelled(true);
                			Bukkit.getWorld("world").playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
                			
                	}
                		
                	
                	   if(goldArmorAttacker){
                			((Player) damager).sendMessage(ChatColor.YELLOW + "You are wearing anti-PVP armor");
                			event.setCancelled(true);
                			Bukkit.getWorld("world").playSound(player.getLocation(), Sound.STEP_GRAVEL, 1, 1);
                	   		}
                		}
                
                	}
                }
		
		 if(event.getCause() ==  DamageCause.PROJECTILE){
			 EntityDamageByEntityEvent evnt = (EntityDamageByEntityEvent) event;
             org.bukkit.entity.Projectile damager = (org.bukkit.entity.Projectile) evnt.getDamager();
             Entity shooter = damager.getShooter();
             Entity victim = event.getEntity();
 			if(shooter instanceof Player && victim instanceof Player){
 				Player attacker = (Player) shooter;
 				Player shot = (Player) victim;
 				
 				
 				boolean goldArmor = plugin.hasArmor(shot, Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS);
            	boolean goldArmorAttacker = plugin.hasArmor(attacker, Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS);
            		if(goldArmor){
            			attacker.sendMessage(ChatColor.YELLOW + "This player is wearing anti pvp armor");
            			event.setDamage(0);
            			Bukkit.getWorld("world").playSound(attacker.getLocation(), Sound.ITEM_BREAK, 1, 1);
            			
            	}
            		
            	
            	   if(goldArmorAttacker){
            		   attacker.sendMessage(ChatColor.YELLOW + "You are wearing Anti-PVP armor");
            			event.setDamage(0);
            			Bukkit.getWorld("world").playSound(shot.getLocation(), Sound.STEP_GRAVEL, 1, 1);
            	   		}
            		}
            
            	}
 				
 			}
   
        
    


	
	

	@EventHandler
	public void EntityDeath(EntityDeathEvent event){
		String entityName = event.getEntityType().toString();

;		if(entityName.equalsIgnoreCase("Villager")){
			ArrayList<String> witness = new ArrayList<String>();
			ArrayList<String> killer = new ArrayList<String>();
			ArrayList<String> villagerName = new ArrayList<String>();
			Location loc = event.getEntity().getLocation();
			List<Entity> witnesses = event.getEntity().getNearbyEntities(10, 10, 10);
			if(event.getEntity().getCustomName() != null){
				villagerName.add(event.getEntity().getCustomName());
			}else{
				villagerName.add("Unknown");
			}
			if(!(event.getEntity().getKiller() == null)){
			String k = event.getEntity().getKiller().getName();
			killer.add(k);
			}else{
				 killer.add("Unknown");
			}
			String causeOfDeath = event.getEntity().getLastDamageCause().getCause().name();
			World world = Bukkit.getWorld("world");
			ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
			BookMeta bm = (BookMeta) book.getItemMeta();
			String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(Calendar.getInstance().getTime());
			
			for(Entity y : witnesses){
				if(y instanceof HumanEntity){
					String name = ((HumanEntity) y).getName();
					witness.add(name);
				}
			}	
			if(witness.isEmpty()){
				witness.add("none");
			}
			bm.setAuthor("?????");
			bm.setTitle("Evidence");
			bm.addPage(">_>");
			bm.setPage(1, ChatColor.RED+ "Villager Name: " + ChatColor.BLACK + villagerName  + "\n" +
			ChatColor.RED+ "Killer: " + ChatColor.BLACK + killer + "\n" +"\n"
					   + ChatColor.RED+ "Cause of death: "+ChatColor.BLACK + causeOfDeath + 
					   "\n" +"\n"+ ChatColor.RED+"Time of Death: " +ChatColor.BLACK +timeStamp
					   +"\n" + "\n" +ChatColor.GREEN+"People near by: " +ChatColor.BLACK + witness.toString());
			book.getItemMeta().setDisplayName(ChatColor.RED+ "Evidence");
			book.setItemMeta(bm);
			world.dropItem(loc, book);
			witness.clear();
			
		}else if(event.getEntityType().toString().equalsIgnoreCase("Zombie") && !(event.getEntity().getCustomName() == null)){
		
			
			if(event.getEntity().getCustomName().equalsIgnoreCase( ChatColor.RED+"Thug Zombie")){
				ItemStack b =  event.getEntity().getEquipment().getBoots().clone();
				ItemStack c =  event.getEntity().getEquipment().getChestplate().clone();
				ItemStack p =  event.getEntity().getEquipment().getLeggings().clone();
				ItemStack l =  event.getEntity().getEquipment().getHelmet().clone();
				
				World world = Bukkit.getWorld("world");
				
				
				Location loc = event.getEntity().getLocation();
				
				if(!b.getType().toString().equalsIgnoreCase("air")){
				world.dropItem(loc, b);
				}
				if(!c.getType().toString().equalsIgnoreCase("air")){
				world.dropItem(loc, c);
				}
			if(!p.getType().toString().equalsIgnoreCase("air")){	
				world.dropItem(loc, p);
			}
			if(!l.getType().toString().equalsIgnoreCase("air")){
				world.dropItem(loc, l);
			}
			}else{
				
			}
	}else{
	
		
	}
	
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void VillagerSpawn(CreatureSpawnEvent event){
		ArrayList<String> firstNames = new ArrayList<String>();
		OfflinePlayer[] oln = Bukkit.getOfflinePlayers();
		if(event.getEntityType().getName().equalsIgnoreCase("Villager")){
		  for(OfflinePlayer y  : oln){
			  firstNames.add(y.getName());
		  }
				int fnl = firstNames.size();
				int rFnl =  (int) (Math.random() * fnl ); 
				event.getEntity().setCustomName(firstNames.get(rFnl) );
				event.getEntity().setCustomNameVisible(false);
		}else if(event.getEntityType().getName().equalsIgnoreCase("Zombie")){
			}
		}
		
	@EventHandler
	public void playerDeath(PlayerRespawnEvent event){
		Player p = event.getPlayer();
		if(plugin.getConfig().contains("Jailed." + p.getName())){
			double x =  (double) plugin.getConfig().get("Jail.X");
			double y =  (double) plugin.getConfig().get("Jail.y");
			double z =  (double) plugin.getConfig().get("Jail.z");
			Location loc = new Location(p.getWorld(), x,y,z);
			p.teleport(loc);
			event.setRespawnLocation(loc);
		}
	}
	
	
	//@EventHandler
	//public void logout(PlayerQuitEvent event){
		//Player p = event.getPlayer();
		//if(plugin.getConfig().contains("Jailed."+p.getName())){
			//Objective objective = p.getScoreboard().getObjective("Jailed");
			//Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Labor:"));
			//int s = score.getScore();
		//	plugin.getConfig().set("Jailed."+p.getName(), s);
			//plugin.saveConfig();
			//p.setScoreboard(plugin.manager.getNewScoreboard());
		//	Bukkit.getServer().getLogger().info(ChatColor.RED + " in config quit");
	//	}
	
	
	@EventHandler
	public void ojJoin(PlayerJoinEvent event ){
		Player p = event.getPlayer();
		if(plugin.getConfig().contains("Jailed."+p.getName())){
			//plugin.getJailScoreBoard(p);
			//Scoreboard board = p.getScoreboard();
			//Objective objective = p.getScoreboard().getObjective("Jailed");
			//int labor = plugin.getConfig().getInt("Jailed."+p.getName());
			//Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Labor:")); //Get a fake offline player
			//score.setScore(labor);
			//objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			//p.setScoreboard(p.getScoreboard());
			
			}
		}
	
	@EventHandler
	public void signSetUp(SignChangeEvent event){
		Player p = event.getPlayer();
		if(p.isOp()){
			if(event.getLine(0).equalsIgnoreCase("[Labor]")){
				event.setLine(0, ChatColor.DARK_RED + "[Labor]" );
				
			  int x  = event.getBlock().getLocation().getBlockX();
			  int y  = event.getBlock().getLocation().getBlockY();
			  int z  = event.getBlock().getLocation().getBlockZ();
			  
			  plugin.getSignLocation().set("SignLocation.X."+x, p.getName());
			  plugin.getSignLocation().set("SignLocation.Y."+y, p.getName());
			  plugin.getSignLocation().set("SignLocation.Z."+z, p.getName());
			  plugin.saveSignLocation();
			  p.sendMessage(ChatColor.GREEN + "Labor shop created");
			}
		}
	}
	
	
    @EventHandler
    public void rightClicks(PlayerInteractEvent e){
        Player p = e.getPlayer();

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK){
        	 Block block = e.getClickedBlock();
        	 
        	if(block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
        	    Sign sign = (Sign) e.getClickedBlock().getState();
        	    
        	   if(sign.getLine(0).contains(ChatColor.DARK_RED + "[Labor]")) {
        		   
        	   if(plugin.getConfig().contains("Jailed." + p.getName())){
        	    Material have = p.getItemInHand().getType();
        	  
        	    ItemStack log = new ItemStack(Material.LOG);
				ItemStack cookedFish = new ItemStack(Material.COOKED_FISH);
        	    ItemStack rawFish = new ItemStack(Material.RAW_FISH);
        	    ItemStack coal = new ItemStack(Material.COAL);
        	    ItemStack ironIngot = new ItemStack(Material.IRON_INGOT);
        	    ItemStack coalblock = new ItemStack(Material.COAL_BLOCK);
        	    ItemStack redstone = new ItemStack(Material.REDSTONE);
        	    ItemStack diamond = new ItemStack(Material.DIAMOND);
        	    ItemStack gold = new ItemStack(Material.GOLD_INGOT);
        	        if(have.equals(rawFish.getType())){
        	        	int itemvalue = 10;
        	        	plugin.takeOne(p, rawFish);
        	        	plugin.newJailScore(p, itemvalue);
        	        }else if(have.equals(log.getType())){
        	        	int itemvalue = 2;
        	        	plugin.takeOne(p, log);
             	        plugin.newJailScore(p, itemvalue);
        	        }else if(have.equals(cookedFish.getType())){
        	        	int itemvalue = 15;
        	        	plugin.newJailScore(p, itemvalue);
        	        	plugin.takeOne(p, cookedFish); 
        	        }else if(have.equals(coal.getType())){
        	        	int itemvalue = 1;
        	        	plugin.newJailScore(p, itemvalue);
        	        	plugin.takeOne(p, coal);
        	        }else if(have.equals(ironIngot.getType())){
        	        	int itemvalue = 9;
        	        	plugin.newJailScore(p, itemvalue);
        	        	plugin.takeOne(p, ironIngot);	
        	        }else if(have.equals(coalblock.getType())){
        	        	int itemvalue = 30;
        	        	plugin.newJailScore(p, itemvalue);
        	        	plugin.takeOne(p, coalblock);
        	        }else if(have.equals(redstone.getType())){
        	        	int itemvalue = 5;
        	        	plugin.newJailScore(p, itemvalue);
        	        	plugin.takeOne(p, redstone);
        	        }else if(have.equals(diamond.getType())){
        	        	int itemvalue = 100;
        	        	plugin.newJailScore(p, itemvalue);
        	        	plugin.takeOne(p, diamond);
        	        }else if(have.equals(gold.getType())){
        	        	int itemvalue = 30;
        	        	plugin.newJailScore(p, itemvalue);
        	        	plugin.takeOne(p, gold);
        	        }else{
        	        		p.sendMessage("you have nothing");
        	        	}
        	        	Objective ob =  p.getScoreboard().getObjective("Jailed");
        	        	 ScoreboardManager manager = Bukkit.getScoreboardManager();
        	        	String sb = (ChatColor.GRAY + "Labor:");
        	        	 if(plugin.getScore(p, ob, sb) <= 0 ){
        	        		 p.sendMessage(ChatColor.YELLOW + "You have paid for you Crimes You are free to go");
        	        		 Bukkit.broadcastMessage(p.getName() + " has been released from jail");
        	        		 
        	        		 p.getServer().getWorld("world").setSpawnLocation(-1459, 74, -236);
        	        		 if(plugin.getConfig().contains("Release")){
        	        			double x =  (double) plugin.getConfig().get("Release.X");
        	     				double y =  (double) plugin.getConfig().get("Release.Y");
        	     				double z =  (double) plugin.getConfig().get("Release.Z");
        	     				Location loc = new Location(Bukkit.getServer().getWorld("world"),x, y, z);
        	     				p.getInventory().clear();
        	     				p.teleport(loc);
        	     				plugin.getConfig().set("Jailed." + p.getName(), null);
        	     				
        	     				p.setScoreboard(manager.getNewScoreboard());
        	     				 plugin.saveConfig();
        	     				 plugin.reloadConfig();
        	        		 }else{
        	        			 Location loc = new Location(p.getWorld(), -1305, 72, -329);
        	        			 p.getInventory().clear();
        	        			 p.teleport(loc);
        	        			 plugin.getConfig().set("Jailed." + p.getName(), null);
        	        			 p.setScoreboard(manager.getNewScoreboard());
        	        			 plugin.saveConfig();
        	        			 plugin.reloadConfig();
        	        		 }
        	        		 
        	        	 }
        	   		}
        	   }
        	}
        }    
    }
}

