package io.github.FiddyPercent.fiddycraft;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;



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
                					
                					player.setScoreboard(plugin.manager.getNewScoreboard());
                					Scoreboard board = plugin.manager.getNewScoreboard();
                					Team team = board.registerNewTeam("teamname");
                					team.addPlayer(player);
                					team.setDisplayName("Jailed");
                					Objective objective = board.registerNewObjective("test", "trigger");
                					objective.setDisplayName("Jailed");
                					Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Labor:")); //Get a fake offline player
                					score.setScore(250);
                					objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                					player.setScoreboard(board);
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
			Location loc = event.getEntity().getLocation();
			List<Entity> witnesses = event.getEntity().getNearbyEntities(10, 10, 10);
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
			bm.setPage(1, ChatColor.RED+ "Killer: " + ChatColor.BLACK + killer + "\n" +"\n"
					   + ChatColor.RED+ "Cause of death: "+ChatColor.BLACK + causeOfDeath + 
					   "\n" +"\n"+ ChatColor.RED+"Time of Death: " +ChatColor.BLACK +timeStamp
					   +"\n" + "\n" +ChatColor.GREEN+"People near by: " +ChatColor.BLACK + witness.toString());
			book.getItemMeta().setDisplayName(ChatColor.RED+ "Evidence");
			book.setItemMeta(bm);
			world.dropItem(loc, book);
			witness.clear();
			
		}else if(event.getEntityType().toString().equalsIgnoreCase("Zombie")){
		
			
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
		List<String> firstNames = Arrays.asList("Bill", "Tom", "Bullet", "WolfGang", "Stein", "Rango", "Wiggens", "Pete", "Sue", "Mary",
				"Texas", "Sandy", "Lary", "Thigh", "Booner", "Link", "Fish", "Basil", "Ann", "Stan", "Chuck", "Nuns", "Rocko", "Ippo", "FiddyFive");
		
		if(event.getEntityType().getName().equalsIgnoreCase("Villager")){
		  
			if(!(event.getSpawnReason() == SpawnReason.BREEDING)){
				int fnl = firstNames.size();
				
				
				int rFnl =  1 + (int) (Math.random() * fnl ); 
				
				
				event.getEntity().setCustomName(firstNames.get(rFnl) );
				event.getEntity().setCustomNameVisible(false);
				
			}
		}else if(event.getEntityType().getName().equalsIgnoreCase("Zombie")){
			event.getEntity().setCustomName("Tax Zombie");
			
			
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
	
	@EventHandler
	public void logout(PlayerQuitEvent event){
		Player p = event.getPlayer();
		
		if(plugin.getConfig().contains("Jailed."+p.getName())){
			Objective objective = p.getScoreboard().getObjective("Jailed");
			Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Labor:")); //Get a fake offline player
			int s = score.getScore();
			plugin.getConfig().set("Jailed."+p.getName(), s);
			plugin.saveConfig();
			plugin.manager.getNewScoreboard();
		}
	}
	
	@EventHandler
	public void ojJoin(PlayerJoinEvent event ){
		Player p = event.getPlayer();
		if(plugin.getConfig().contains("Jailed."+p.getName())){
			

			Scoreboard board = plugin.manager.getNewScoreboard();
			Objective objective = board.registerNewObjective("Jailed", "trigger");
			objective.setDisplayName("Jailed");
			int labor = plugin.getConfig().getInt("Jailed."+p.getName());
			Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Labor:")); //Get a fake offline player
			score.setScore(labor);
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			p.setScoreboard(board);
			
			
			
			
			}
		
		}
	@EventHandler
	public void signSetUp(Player.)
}

	

