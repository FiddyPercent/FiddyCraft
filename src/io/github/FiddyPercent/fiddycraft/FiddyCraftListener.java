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
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Jukebox;
import org.bukkit.block.Sign;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;



public class FiddyCraftListener implements Listener{
	
	HashMap<UUID, ItemStack> thug = new HashMap<UUID, ItemStack>();
	public String NotOwner = ChatColor.RED + "I do not own this animal";
	private final FiddyCraft plugin;
	 
	public FiddyCraftListener(FiddyCraft plugin){
	this.plugin = plugin;
	}
	

	ArrayList<UUID> PendingTrial = new ArrayList<UUID>();
	ArrayList<UUID> PVPDeath = new ArrayList<UUID>();
	HashMap<String, Integer> Attacked = new HashMap<String, Integer>();
	

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
                	 LivingEntity zb = (LivingEntity) damager;
                	 if(!(zb.getCustomName() == null)){
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
                					plugin.getConfig().set("Jailed."+ player.getUniqueId().toString(), 250);
                					plugin.saveConfig();
                					FiddyCraft.setJailSentence(player,250);
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
                				
//NEED A NEW ECON PLUGIN             				
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
     
                	}else if(damager instanceof Player && event.getEntity() instanceof Player){
                		
                		
                	Player attacker = (Player) damager;
                	
                	
				
                	
                	
                	boolean goldArmor = plugin.hasArmor(player, Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS);
                	boolean goldArmorAttacker = plugin.hasArmor((Player)damager, Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS);
                	
                		plugin.pvpLoggers.put(player.getUniqueId(), 200);
                		
                	
                		if(goldArmor && !plugin.getPlayerInfo().contains("Officers." + attacker.getUniqueId().toString() )){
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
 				
 				plugin.pvpLoggers.put(shot.getUniqueId(), 200);
 				boolean goldArmor = plugin.hasArmor(shot, Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS);
            	boolean goldArmorAttacker = plugin.hasArmor(attacker, Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS);
            		if(goldArmor  && !plugin.getPlayerInfo().contains("Officers." + attacker.getUniqueId().toString()) ){
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
		 
			Entity e = event.getEntity();
			
			Entity ent = event.getEntity();
		if(ent instanceof LivingEntity){	
			LivingEntity l = (LivingEntity) ent;
		if(l.getHealth() <= event.getDamage()){
			animalUtility au = new animalUtility(plugin);
			if(au.isAnimal(e)){
				if(au.checkifHasOwner(e.getUniqueId().toString())){
					if(event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK){ 
					String auuid = event.getEntity().getUniqueId().toString();
					EntityDamageByEntityEvent evnt = (EntityDamageByEntityEvent) event;
					Entity killer  =   evnt.getDamager();
					if(killer instanceof Player){
						Player k = (Player) killer;
						if(au.isAnimalOwner(k, event.getEntity())){
							Animals a = new Animals(plugin, auuid, k.getUniqueId().toString());
							event.setCancelled(true);
							a.deathDrop(k, event.getEntity());
							au.removeAnimal(ent);
							event.getEntity().remove();
						}else{
							event.setDamage(0);
							k.sendMessage(ChatColor.RED + "You can not kill another players animal");
						}
					}
				}
			}
		}	
	}
 }
}
//STOP PLANT GROWTH 
	@EventHandler
	public void stopGrow(BlockGrowEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void stopBoneMeal(StructureGrowEvent e){
		if (e.isFromBonemeal()){
			e.setCancelled(true);
		}
	
		
	}
//SHEAR SHEEP EVENT
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onShear(PlayerShearEntityEvent e){
		Player p = e.getPlayer();
		animalUtility au = new animalUtility(plugin);
	if(e.getEntity() instanceof Sheep){
		if(au.isAnimalOwner(p, e.getEntity())){
			 Animals a = new Animals(plugin,  e.getEntity().getUniqueId().toString(), p.getUniqueId().toString());
			e.setCancelled(true);
			if(a.canDropProduce(p, e.getEntity())){
			a.dropProduce(p, e.getEntity());
			a.addHeartPoint(.03);
			}
		}else{
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "You do not own this sheep and cannot shear it");
			}
		}
	}
//Feed animal and drop item
	@EventHandler(priority = EventPriority.LOW)
	public void onDropItem(PlayerDropItemEvent e){
		animalUtility au = new animalUtility(plugin);
		Player p = e.getPlayer();
		if(e.getItemDrop().getItemStack() instanceof ItemStack){
		ItemStack item =  e.getItemDrop().getItemStack();
		if(au.isAnimalFood(item.getType())){
			au.animalEat(p, item);
		}
	}
}
	
//STOP WOOL DIE EVENT	 
	@EventHandler(priority = EventPriority.HIGHEST)
	public void noDyingWool(SheepDyeWoolEvent e) {
		e.setCancelled(true);
	}
	

	
//USE OF MILK BUCKET
	@EventHandler(priority = EventPriority.HIGHEST)
	public void animalgoods(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
//CLAMING ANIMAL
		animalUtility au = new animalUtility(plugin);
			if ((au.isAnimal(e.getRightClicked()) && (p.getItemInHand()
					.getType().equals(Material.NAME_TAG)))) {
				ItemStack item = p.getItemInHand();
				ItemMeta meta = item.getItemMeta();
				
				if(item.hasItemMeta() == false){
					p.sendMessage(ChatColor.YELLOW + "I need to put a  name on this name tag /Name <Pick Animal Name>");
				}else{
					if(au.checkifHasOwner(e.getRightClicked().getUniqueId().toString())){
						p.sendMessage(ChatColor.RED + "This animal already has an owner");
						e.setCancelled(true);
					}else{
					au.claimAnimal(p, e.getRightClicked(), meta);
					}
				}
			}
			
			if(au.isAnimal(e.getRightClicked()) && (p.getItemInHand()
					.getType().equals(Material.BUCKET) && e.getRightClicked() instanceof Cow)){
				if(au.isAnimalOwner(p, e.getRightClicked())){
					Bukkit.broadcastMessage("using bucket");
					 Animals a = new Animals(plugin,  e.getRightClicked().getUniqueId().toString(), p.getUniqueId().toString());
				if(a.canDropProduce(p, e.getRightClicked())){
					a.dropProduce(p, e.getRightClicked());
					Bukkit.broadcastMessage("can drop");
					e.setCancelled(true);
					a.addHeartPoint(.03);
				}else{
					e.setCancelled(true);
					Bukkit.broadcastMessage("cant bucket");
				}
				}else{
					e.setCancelled(true);
					p.sendMessage(ChatColor.RED + "You do not own this animal and cannot milk it");
				}
				
			}
			
			 if(e.getRightClicked() instanceof Chicken  &&  p.getItemInHand().getType().equals(Material.AIR)){
				 if(au.isAnimalOwner(p, e.getRightClicked())){
					 Animals a = new Animals(plugin,  e.getRightClicked().getUniqueId().toString(), p.getUniqueId().toString());
						if(a.canDropProduce(p, e.getRightClicked())){	
					 a.canDropProduce(p, e.getRightClicked());
					 a.addHeartPoint(.03);
						}else{
							e.setCancelled(true);
						}
				 }else{
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "You do not own this animal and cannot gather eggs from it");
				 }
			 }
			 
			 if(au.isAnimal(e.getRightClicked()) && au.isAnimalOwner(p, e.getRightClicked())){
			 if(p.getItemInHand().getType().equals(Material.PAPER)) {
				 au.showAnimalInfo(p, e.getRightClicked());
			 }
			 Animals a = new Animals(plugin,  e.getRightClicked().getUniqueId().toString(), p.getUniqueId().toString());
			 if ((au.isAnimal(e.getRightClicked()) && (p.getItemInHand()
						.getType().equals(Material.SPONGE)))) {
				 a.washAnimal(p, e.getRightClicked());
			 }
			 
			 if ((au.isAnimal(e.getRightClicked()) && (p.getItemInHand()
						.getType().equals(Material.AIR)))) {
				 a.petAnimal(p, e.getRightClicked());
			 }
			}
	}
	
	@EventHandler
	public void entityDamage(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player){
		Player v = (Player) e.getEntity();
		if(e.getDamager() instanceof Player){
			Player attacker = (Player) e.getDamager();
			Player victim = v;
			plugin.attackedLast.put(victim.getUniqueId(), attacker.getUniqueId());
			
		if(plugin.attackedLast.containsKey(attacker.getUniqueId()) || plugin.attackedLast.get(attacker.getUniqueId()) == null){
			if(plugin.attackedLast.get(attacker.getUniqueId()) != victim.getUniqueId() || plugin.attackedLast.get(attacker.getUniqueId()) == null){
		
			ItemStack firstblood = new ItemStack(Material.REDSTONE);
			ItemMeta meta = firstblood.getItemMeta();
			String timeStamp = new SimpleDateFormat("MM/dd HH:mm").format(Calendar.getInstance().getTime());
			meta.setDisplayName("Evidence");
			ArrayList<String> Lore = new ArrayList<String>();
			Lore.add(v.getName()+ "'s blood ");
			if(attacker.getItemInHand() == null || attacker.getItemInHand().getType() == Material.AIR){
			Lore.add("Bloodstain Pattern Type: Expirated spatter");	
			}else{
			Lore.add("Bloodstain Pattern Type: IMPACT SPATTER");
			}
			Lore.add("Chemical Analisis: " + "Normal blood");
			Lore.add( "Time: "+ timeStamp);
			meta.setLore(Lore);
			
			firstblood.setItemMeta(meta);
			Bukkit.getWorld("world").dropItem(victim.getLocation(), firstblood);
					}else{
						int chance = 5;
						int random =  (int) (Math.random() * chance ); 
						if(random == 3){
							
							ItemStack firstblood = new ItemStack(Material.REDSTONE);
							ItemMeta meta = firstblood.getItemMeta();
							String timeStamp = new SimpleDateFormat("MM/dd HH:mm").format(Calendar.getInstance().getTime());
							meta.setDisplayName("Evidence");
							ArrayList<String> Lore = new ArrayList<String>();
							Lore.add(v.getName()+ "'s blood ");
							if(attacker.getItemInHand() == null || attacker.getItemInHand().getType() == Material.AIR){
							Lore.add("Bloodstain Pattern Type: Expirated spatter");	
							}else{
							Lore.add("Bloodstain Pattern Type: IMPACT SPATTER");
							}
							Lore.add("Chemical Analisis: " + "High adrenaline");
							Lore.add( "Time: "+ timeStamp);
							meta.setLore(Lore);
							
							firstblood.setItemMeta(meta);
							Bukkit.getWorld("world").dropItem(victim.getLocation(), firstblood);
						}
						
					}
				}
			}
		}
	}
	
	
	
//Need to make a Mod UUID List
	@EventHandler
	public void inCreative(InventoryCreativeEvent e){
		Player p = (Player) e.getWhoClicked();
		String name = p.getName();

		if(name.equalsIgnoreCase("Fiddy_percent") || name.equalsIgnoreCase("xxBoonexx") || name.equalsIgnoreCase("nuns")){
			e.setCancelled(false);
		}
			
	}
	
	@EventHandler
	public void blockBreak(BlockBreakEvent e){
		Player p = e.getPlayer();
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH").format(Calendar.getInstance().getTime());

		if(plugin.isGrowableBlock(e.getBlock())){
			if(plugin.isPlantOwned(e.getBlock().getLocation())){
				if(plugin.isPlayerPlantOwner(p, e.getBlock().getLocation())){
					Plants plant = new Plants(plugin, p.getUniqueId().toString(), e.getBlock().getLocation());
					//Bukkit.broadcastMessage("Removed plant data");
					
					plant.removePlantInfo();
					if(plant.getPlantCycle() == 1){
						plant.checkbonus();
						plugin.addExpFarmer(p, .02);
						e.setCancelled(true);
						e.getBlock().setType(Material.AIR);
					p.getWorld().dropItem(e.getBlock().getLocation(), plant.dropProduce());
					}
					
				}else{
					p.sendMessage(ChatColor.RED + "Can't destroy other players plants");
					e.setCancelled(true);
				}
			}
		}
		
		
		if(e.getBlock().getType() == Material.DIAMOND_ORE){
			if(!plugin.getConfig().contains("Snooper." + p.getName() + ".Diamonds." + timeStamp)){
				plugin.getConfig().set("Snooper." + p.getName()+ ".Dimaonds." + timeStamp, 1);
				plugin.saveConfig();
			}else{
				int d =  plugin.getConfig().getInt("Snooper." + p.getName()+ ".Dimaonds." + timeStamp);
				
				int newD = d + 1;
				plugin.getConfig().set("Snooper." + p.getName()+ ".Dimaonds." + timeStamp, newD);
				plugin.saveConfig();
			}
		}
		if(e.getBlock().getType() == Material.SAND){
			int r = plugin.randomNumber(10);
			if(r == 10){
				e.getBlock().setType(Material.AIR);
				ItemStack salt = new ItemStack(Material.PUMPKIN_SEEDS);
					ItemMeta meta = salt.getItemMeta();
					ArrayList<String> lore = new ArrayList<String>();
					meta.setDisplayName("Salt");
					lore.add("Salt makes everything taste better");
					lore.add(plugin.setCookingRank(plugin.randomNumber(4)));
					meta.setLore(lore);
					salt.setItemMeta(meta);
					p.getWorld().dropItem(e.getBlock().getLocation(), salt);
				
				
			}
			
		}
				
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void itemPickup(PlayerPickupItemEvent e){
		ItemStack item =  e.getItem().getItemStack();
		Player p = e.getPlayer();
		if(item.hasItemMeta()){
			if(item.getItemMeta().hasDisplayName()){
				if(item.getItemMeta().getDisplayName().equalsIgnoreCase("Evidence")){
					if(plugin.getPlayerInfo().contains("Officers." + p.getUniqueId().toString())){
						ArrayList<String> data = (ArrayList<String>) item.getItemMeta().getLore();
						ItemStack labreport = new ItemStack(Material.PAPER);
						ItemMeta  labmeta = labreport.getItemMeta();
						p.sendMessage(ChatColor.YELLOW + "Found Evidence");
						e.getItem().remove();
						e.setCancelled(true);
						labmeta.setDisplayName("Lab Report");
						labmeta.setLore(data);
						
						labreport.setItemMeta(labmeta);
						p.getWorld().dropItem(p.getLocation(), labreport);
					}else{
						e.setCancelled(true);
					}
				}
				if(item.getItemMeta().getDisplayName().equalsIgnoreCase("remove")){
					e.getItem().remove();
					e.setCancelled(true);
				}
			}
		}
		
		if(!item.hasItemMeta()){
			Material it = item.getType();
			if(it == Material.SEEDS || it == Material.PUMPKIN_SEEDS || it == Material.MELON_SEEDS || it == Material.EGG){
				e.getItem().remove();
				e.setCancelled(true);
			}
		}
	}
	
	
	@EventHandler(priority = EventPriority.LOW)
	public void onChat(AsyncPlayerChatEvent e){
			Player p = e.getPlayer();

			if(plugin.getConfig().getString("Drunk") != null){

				if(plugin.getConfig().contains("Drunk." + p.getUniqueId().toString())){
					String d = e.getMessage();
					int dp = plugin.getConfig().getInt("Drunk." + p.getUniqueId().toString());
					HashMap<UUID, String> m = new HashMap<UUID, String>();
					m.put(p.getUniqueId(), d);

				
					 String msg = (String)m.get(p.getUniqueId());

					if(dp < 30 && dp > 19){
			//SLOW			
						String t = msg.replaceAll("the","thee").replaceAll("this", "thiss").replaceAll("you", "yallls").replaceAll("lol", "lul").replaceAll("drink", "drunk");
						m.put(p.getUniqueId(), t);
					}else if(dp > 49 && dp < 99){
			//SLOW AND CONFUSED
						String t = msg.replaceAll("so", " *hick* ").replaceAll("lol", "I love my anus").replaceAll("this", p.getName()).replaceAll("why ", "I").replaceAll("its", "I'm"
								).replaceAll("the", "thuur").replaceAll("club", "the dancy place").replaceAll("milk", "more drinks").replaceAll("no", "yes");
						m.put(p.getUniqueId(), t);
			//SLOW CONFUSION BLIDNESS		

					}else if(dp > 100 && dp < 149){
						 String t = msg.replaceAll("so", "...").replaceAll("lol", "crap I peed myself").replaceAll("this", p.getName()).replaceAll("why", "I").replaceAll("no", "yes").replaceAll("yes", "no").replaceAll("help", "I will kill you").replaceAll(
								 "ugh", "man I'm sexy").replaceAll("sucks", "rocks").replaceAll("fun", "furrn").replaceAll("guys", "I'm so alone").replaceAll("the", "thuur").replaceAll("why", "I think").replaceAll("drink", "strip").replaceAll("come here", "get lost").replaceAll("like", "hate")
								 .replaceAll("this", "diss").replaceAll("jk", "I'm serious");
						 m.put(p.getUniqueId(), t);

					}else if(dp > 150){
			//SLOW CONFUSION BLINDNESS NIGHTVISION
						 String t = msg.replaceAll("so", "...").replaceAll("lol", "crap I peed myself").replaceAll("this", p.getName()).replaceAll("why", "I").replaceAll("no", "yes").replaceAll("yes", "no").replaceAll("help", "I will kill you").replaceAll(
								 "ugh", "man I'm sexy").replaceAll("sucks", "rocks").replaceAll("fun", "furrn").replaceAll("bad", "hot").replaceAll("the", "thuur").replaceAll("why", "I think").replaceAll("drink", "strip").replaceAll("hi", "get lost").replaceAll("like", "hate")
								 .replaceAll("this", "diss").replaceAll("jk", "I'm serious").replaceAll("-", "").replaceAll("brb", "I'm so alone").replaceAll("afk", "I want you bad").replaceAll("great", "greatsss").replaceAll("lag", "white power").replaceAll("nuns", "buns").replaceAll(
								 "boone", "booze").replaceAll("funny", "getting really hot").replaceAll("stupid", "..Who touched me").replaceAll("omg", "I think your sexy").replaceAll("wow", "amuzing").replaceAll("see", "rape");
						 m.put(p.getUniqueId(), t);
					}else if(dp <20){
						String t =msg.replaceAll(" ", "  ");
						m.put(p.getUniqueId(), t);
			//NEAR SOBER		
							}
					  e.setMessage((String)m.get(p.getUniqueId()));

					}
				}
			}



	
	

	@EventHandler
	public void EntityDeath(EntityDeathEvent event){
		animalUtility au = new animalUtility(plugin);
		String entityName = event.getEntityType().toString();
		Entity e = event.getEntity();
	
		if(au.isAnimal(e)){
			if(au.checkifHasOwner(e.getUniqueId().toString())){
				au.removeAnimal(e);
				Bukkit.broadcastMessage("has owner");
					}
				}
				
			
			
		
		
		
		if(entityName.equalsIgnoreCase("dog")){
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
			bm.setPage(1, ChatColor.RED+ "Villager Name: " + ChatColor.BLACK + villagerName  + "\n" + "\n" +
			ChatColor.RED+ "Killer: " +"\n"+ ChatColor.BLACK + killer + "\n" +"\n"
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
	
	
	
	
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onMove(PlayerMoveEvent event){
		Player p = event.getPlayer();
		
		if(p.hasPermission("FiddyCraft.noob") && p.isOp() == false){
			if(plugin.isInRegion("Town", p.getLocation())){
				
				Bukkit.getWorld("world").playSound(p.getLocation(), Sound.FIREWORK_TWINKLE2, 1, 1);
		Bukkit.broadcastMessage(ChatColor.GOLD + p.getName() + " has made it to the city!");
				p.sendMessage(ChatColor.YELLOW + "Congratulations! You have made it to the city!");
				
			if(plugin.getConfig().contains("CitySpawn")){
				double x = plugin.getConfig().getDouble("Location.city.X");
				double y = plugin.getConfig().getDouble("Location.city.Y");
				double z = plugin.getConfig().getDouble("Location.city.Z");
				
				Location loc = new Location(p.getWorld(), x,y,z);
				p.setBedSpawnLocation(loc);
				}else{
					Location loc = new Location(p.getWorld(), -1458,76,-231);
					p.setBedSpawnLocation(loc);
				}
		
			p.sendMessage(ChatColor.YELLOW + "You are now a low class citizen to the Ghetto with you!");
			double x = plugin.getConfig().getDouble("Location.ghetto.X");
			double y = plugin.getConfig().getDouble("Location.ghetto.Y");
			double z = plugin.getConfig().getDouble("Location.ghetto.Z");
			
			Location loc = new Location(p.getWorld(), x,y,z);
			p.teleport(loc);
			p.sendMessage(ChatColor.YELLOW + "Here is where you will start off life, you can now rent property but keep in mind " + ChatColor.RED + "if you can't pay rent everything will be repossessed.");
			p.sendMessage("Here is a book to help you");
			
			ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
			BookMeta bm = (BookMeta) book.getItemMeta();
			bm.setAuthor("City Hall");
			bm.setTitle("Noob Guide");
			bm.addPage("");
			bm.addPage("");
			bm.addPage("");
			bm.addPage("");
			bm.addPage("");
			bm.setPage(1, "Welcome, here is a handy book for everything you need to know.");
			bm.setPage(2, "Rules:" + "\n"  + "\n" + "Killing players or villagers == crime" + "\n"
					                + "\n" + "having harmful drugs(potions) == crime" + "\n"
					                + "\n" + "Hacks, harrasment, and any griefing is Bannable"
					                + "\n" + "Crimes are not bannable but jailable."
					                );
			bm.setPage(3, "How to make money:" + "\n" + "\n" + 
					                "Killing mobs" + "\n"+ "\n"+"Selling ores/items in the city" + "\n"+ "\n" + "Working(Laywer Cop etc)" + "\n"+ "\n"+ "*cough selling drugs cough");
			
			bm.setPage(4,  "How to buy a plot:" + "\n"  + "\n"+ 
			                "To rent just click the sign for the plot and use //rg rent." + "\n" + "\n"+" WARNING if you cannot afford the daily (IRL) rent all your items will be removed always keep a money buffer" );
			bm.setPage(5,  "useful commands" + "\n" + "/Help");
			book.getItemMeta().setDisplayName(ChatColor.RED+ "Noob Guide");
			book.setItemMeta(bm);
			Bukkit.getWorld("world").dropItem(p.getLocation(), book);
			
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "permissions setrank " + p.getUniqueId() +  " user");
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "permissions reload");
			
			p.setItemInHand(book);
			}
		}
		
		if(plugin.getConfig().contains("Regions.guardTower")){
			if(plugin.isInRegion("guardTower", p.getLocation())){
				if(!plugin.getPlayerInfo().contains("Officers." + p.getUniqueId().toString())){
					
					Bukkit.getWorld("world").playSound(p.getLocation(), Sound.EXPLODE, 1, 1);
					p.sendMessage(ChatColor.RED + "You have been Shot!");
					
					Location pl = p.getLocation();
					Location loc = new Location(p.getWorld(), pl.getX() + 2,pl.getY(),pl.getZ());
					p.teleport(loc);
					Bukkit.getWorld("world").playSound(p.getLocation(), Sound.EXPLODE, 1, 1);
					p.setHealth(0);
					
				}
			}
		}
	
	
	if(plugin.getConfig().contains("Regions.records")){
		if(plugin.isInRegion("records", p.getLocation())){
			if(!plugin.getPlayerInfo().contains("Officers." + p.getUniqueId().toString())){
				Bukkit.getWorld("world").playSound(p.getLocation(), Sound.EXPLODE, 1, 1);
				p.sendMessage(ChatColor.RED + "You have been Shot!");
				Location pl = p.getLocation();
				Location loc = new Location(p.getWorld(), pl.getX() + 2,pl.getY(),pl.getZ());
				p.teleport(loc);
				Bukkit.getWorld("world").playSound(p.getLocation(), Sound.EXPLODE, 1, 1);
				p.setHealth(0);
				
			}
		}
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
	public void respawn(PlayerRespawnEvent event){
		Player p = event.getPlayer();
		
	
		if(plugin.getConfig().contains("Jailed." + p.getUniqueId().toString())){
			double x =  (double) plugin.getConfig().get("Jail.X");
			double y =  (double) plugin.getConfig().get("Jail.y");
			double z =  (double) plugin.getConfig().get("Jail.z");
			Location loc = new Location(p.getWorld(), x,y,z);
			p.teleport(loc);
			event.setRespawnLocation(loc);
		}
		
		if(PendingTrial.contains(p.getUniqueId())){
			double x =  (double) plugin.getConfig().get("Jail.X");
			double y =  (double) plugin.getConfig().get("Jail.y");
			double z =  (double) plugin.getConfig().get("Jail.z");
			Location loc = new Location(p.getWorld(), x,y,z);
			p.teleport(loc);
			event.setRespawnLocation(loc);
			FiddyCraft.setJailSentence(p,2000);
			PendingTrial.remove(p.getUniqueId());
			plugin.getConfig().set("Jailed."+ p.getUniqueId().toString(), 2000);
			p.sendMessage(ChatColor.RED + "You have been sent to jail if you wish you can have a trial or simply plead guilty and skip it");
			
		}
		
		if(p.hasPermission("FiddyCraft.noob")){
			double xs =  (double) plugin.getConfig().getDouble("Location." + "Spawn.X");
			double ys =  (double) plugin.getConfig().getDouble("Location." + "Spawn.Y");
			double zs =  (double) plugin.getConfig().getDouble("Location." + "Spawn.Z");
			Location loc = new Location(p.getWorld(), xs,ys,zs);
			p.teleport(loc);
			event.setRespawnLocation(loc);
		}
		
		if(PVPDeath.contains(p.getUniqueId())){
			
			
			String causeOfDeath = p.getLastDamageCause().getCause().name();
			ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
			BookMeta bm = (BookMeta) book.getItemMeta();
			String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(Calendar.getInstance().getTime());
			bm.setAuthor("Doctor");
			bm.setTitle("Death Certificate");
			bm.addPage("");
			bm.setPage(1, ChatColor.RED+ "Victim: " + ChatColor.BLACK + p.getName()  + "\n" + "\n" +
					    ChatColor.RED+ "Cause of Death: "+ChatColor.BLACK + causeOfDeath + 
					   "\n" +"\n"+ ChatColor.RED+"Time of Death: " +ChatColor.BLACK +timeStamp);
					   
			book.getItemMeta().setDisplayName(ChatColor.RED+ "Death Certificate");
			book.setItemMeta(bm);
			p.setItemInHand(book);
			p.sendMessage(ChatColor.DARK_RED + "You have been murderd by a player, report your death by droping it off at your local police station");
			PVPDeath.remove(p.getUniqueId());
		}
		
	}
	@EventHandler
	public void onSmelt(FurnaceSmeltEvent e){
		ItemStack result = e.getResult();
		ItemStack source = e.getSource();
		ItemMeta smeta = source.getItemMeta();
		ItemMeta rmeta = result.getItemMeta();
		HashMap<String,ArrayList<String>> playerCraft = new HashMap<String,ArrayList<String>>();
		ArrayList<String> dn = new ArrayList<String>();
		playerCraft.put(e.getBlock().getLocation().toString(), dn);
		if(result.hasItemMeta()){
			if(plugin.isRecipe(rmeta.getDisplayName())){
				HashMap<String, Integer> foodRank = new HashMap<String, Integer>();
				if(source.hasItemMeta() && source.getItemMeta().hasDisplayName()){
					dn.add(smeta.getDisplayName());
				}
				if(plugin.hasFurnaceIngredents(smeta.getDisplayName(), rmeta.getDisplayName())){
					////Bukkit.broadcastMessage("good dish");
					if(smeta.hasLore() && smeta.getLore().size() > 1){
					 int rlevel = plugin.getcookingRankLevel(smeta.getLore().get(1));
					 foodRank.put(rmeta.getDisplayName(), rlevel);
					}else{
						foodRank.put(rmeta.getDisplayName(), 0);
					}

					 int newrank = (int) foodRank.get(rmeta.getDisplayName());
					 String rankStar = plugin.setCookingRank(newrank);
					 ArrayList<String> Lore = new ArrayList<String>();
					 ItemStack newitem = result;
					 ItemMeta nimeta = newitem.getItemMeta();
					 Lore.add(rmeta.getLore().get(0));
					 Lore.add(rankStar);
					 Lore.add("Bonus Effects");
					 ArrayList<String> b = plugin.setFoodBuff(result, rmeta.getDisplayName(), newrank);
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
					 e.setResult(newitem);
				}else{
					////Bukkit.broadcastMessage("failed dish");
					if(smeta.hasLore() && smeta.getLore().size() > 1){
					 int rlevel = plugin.getcookingRankLevel(smeta.getLore().get(1));
					 foodRank.put(rmeta.getDisplayName(), rlevel);
					}else{
						 int rlevel = 0;
						 foodRank.put(rmeta.getDisplayName(), rlevel);
					}
					 e.setResult(plugin.getFailedDish());
						}
					}else{
						
					}
				}
		if(plugin.canCraft(result) == false){
			//////Bukkit.broadcastMessage("method cancraft");
			 e.setResult(plugin.getFailedDish());
				}
			}
		

	@EventHandler
	public void crafting(PrepareItemCraftEvent e){
		Player p = (Player)e.getViewers().get(0);
		ItemStack[] items = e.getInventory().getContents();
		ItemStack result = e.getRecipe().getResult();
		ArrayList<Boolean> trueOnce = new ArrayList<Boolean>();
		HashMap<String,ArrayList<String>> playerCraft = new HashMap<String,ArrayList<String>>();
		ArrayList<String> displayName = new ArrayList<String>();
		if(result.hasItemMeta()){
			HashMap<String, Integer> foodRank = new HashMap<String, Integer>();
			HashMap<String, Integer> foodTotal = new HashMap<String, Integer>();
			ItemMeta rmeta = result.getItemMeta();
			if(rmeta.hasDisplayName()){
				if(plugin.isRecipe(rmeta.getDisplayName())){
					for(ItemStack i : items){
					
					plugin.starRankSetup(trueOnce, foodRank, displayName, i, playerCraft, result, p, foodTotal);
					}
							if(plugin.hasIngredents(playerCraft, rmeta.getDisplayName(), p.getName()) == false && trueOnce.isEmpty()){
								 e.getInventory().setItem(0,plugin.getFailedDish());
								}else{
									 e.getInventory().setItem(0, plugin.getDish(foodRank, foodTotal, result));
										}
									}	
								}
							}

		ItemStack[] allItems = e.getInventory().getContents();
		ArrayList<Boolean> trueOnce2 = new ArrayList<Boolean>();
		for(ItemStack is : allItems){
			if(plugin.isSpice(is)){
				trueOnce2.add(true);
			}
			if(trueOnce2.contains(true)){
			}
			
		}
		
		if(plugin.canCraft(result) == false){
	//////Bukkit.broadcastMessage("method cancraft");
			e.getInventory().setItem(0, plugin.getFailedDish());
		}
	}
	@SuppressWarnings("unused")
	@EventHandler
	public void PlayerDeath(PlayerDeathEvent event){
		
		 event.setDeathMessage(null);
		 //check if player is a player
	    if(event.getEntity() instanceof Player){
	      Player p = (Player) event.getEntity();
	    //check if killer is a player
	     if(p.getKiller() instanceof Player){
	     Player killer = p.getKiller();
//SKULL DEATH PERK	     
	     int kills = plugin.getPlayerInfo().getInt("Players." + killer.getUniqueId().toString()  + ".Murders");
	     int r = (int) (Math.random() * 10 + 1 ); 
	      if(r == 10 && kills > 10 ){
	    	  ItemStack skull = new ItemStack(Material.SKULL_ITEM);
	    	  SkullMeta meta = (SkullMeta) skull.getItemMeta();
	    	  meta.setOwner(p.getName());
	    	  skull.setItemMeta(meta);
	    	  Bukkit.getWorld("world").dropItem(killer.getLocation(), skull);
	    	  ////Bukkit.broadcastMessage(ChatColor.DARK_RED + p.getName() + "has been beheaded!");
	      }
	      
	    
	     
	     
	     if((plugin.patrol.containsKey(p.getUniqueId()) && plugin.patrolOn(p) == true)){
	    	 event.setDeathMessage(ChatColor.BLUE + "Officer Down");
	     }else if(plugin.patrol.containsKey(killer.getUniqueId()) && plugin.patrolOn(killer) == true){
	    	 event.setDeathMessage(ChatColor.GREEN + "Suspect has been captured");
	     }else{
	    	 event.setDeathMessage(ChatColor.DARK_RED + "Someone Has been murderd");
	     }
	     plugin.crimescene.put("murder", p.getLocation());
	     plugin.attackedLast.remove(p.getKiller().getUniqueId());
	     }
	     
//UPDATING MURDER STAT
	        	if(p.getKiller() instanceof Player) {
	        		  Player killer = p.getKiller();
	        		if(plugin.getPlayerInfo().contains("Players."+ killer.getUniqueId().toString())){
	        			int killed = plugin.getPlayerInfo().getInt("Players." +killer.getUniqueId().toString() +".Murders");
	        			int total = killed +1;
	        			plugin.getPlayerInfo().set("Players." + killer.getUniqueId().toString() + ".Murders", total);
	        			plugin.savePlayerInfo();
	        		}
//CHECKING IF OFFICER IS DOWN
	        		if(plugin.patrol.containsKey(p.getUniqueId())){
	        			if(plugin.getPlayerInfo().contains("Officers." + p.getUniqueId().toString())){
							int Arrests = plugin.getConfig().getInt("Officers." + p.getUniqueId().toString() + ".Deaths");
							int nA = Arrests + 1;
							plugin.getPlayerInfo().set("Officers." + p.getUniqueId().toString() + ".Deaths",  nA);
							plugin.savePlayerInfo();
							}
	        		}
	        		Location deathLocation = p.getLocation();
	        		plugin.murderTimer.put("LastMurder", 1200);
	        		
	        		Player[] olpys = Bukkit.getOnlinePlayers();

	        		for(Player ps : olpys){
	        			
	        			String n = ps.getUniqueId().toString();        			
	        			if(plugin.getPlayerInfo().contains("Officers." + n)){    				
	        			if(plugin.isInRegion("Town", ps.getLocation()))	
	        				ps.sendMessage(ChatColor.GOLD + "A player has been murderd report to the police station you have 1 minute use /"+ ChatColor.RED + "PoliceStation");
	        				ps.sendMessage(ChatColor.GOLD + "If you are already ready use /" + ChatColor.RED  + "CrimeScene "+ ChatColor.GOLD + "to warp directly to the murder scene");
	        				
	        			}
	        		}
	        		
	        		
	        		
//ADDING LORE TO ITEMS             
	                 Entity damager = p.getKiller();
	                 if(damager instanceof Player) {
	                	 		Player attacker = p.getKiller();
	                			Player victim = p;
	                			
	                			PVPDeath.add(p.getUniqueId());
	                			
	                			String xtimeStamp = " ";
	                		
	                			if(!(attacker.getItemInHand().getType() == Material.AIR)){
	                				ItemStack weapon = attacker.getItemInHand().clone();
	                	          
	                	                ItemStack item =  weapon;
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
	                	        	
				
				if(plugin.getPlayerInfo().contains("Officers." + attacker.getUniqueId().toString())){			
					if(plugin.patrolOn(attacker)){
					
					if(plugin.patrol.get(attacker.getUniqueId())){
						plugin.getConfig().set("PendingTrial." + p.getUniqueId().toString(), attacker.getUniqueId().toString());
						plugin.saveConfig();
						PendingTrial.add(p.getUniqueId());
						
						p.sendMessage(ChatColor.DARK_AQUA + "You have been charged with a crime you will be put on trial shortly");
						p.sendMessage(ChatColor.RED + "If you are unsure what you have done speak to the officer, It would be wise to look for a lawyer.");
						attacker.sendMessage(ChatColor.BLUE + "You have charged " + p.getName() + " with a crime he will be put on trial shortly if convicted you will get paid again");
						double playerMoney = plugin.economy.getPlayerMoneyDouble(attacker.getName());
            			double FiddyMoney = plugin.economy.getPlayerMoneyDouble("Fiddy_percent");
            			plugin.economy.setPlayerMoney(attacker.getName(), playerMoney + 500, false);
            			attacker.sendMessage(ChatColor.BLUE + "For capturing the suspect you get " + ChatColor.GOLD + 500);
            			
            			Player[] op = Bukkit.getOnlinePlayers();
            			
            			for(Player cops : op){
            				if(!(cops.getUniqueId() == attacker.getUniqueId())){
            				if(plugin.getPlayerInfo().contains("Officers." + cops.getUniqueId().toString())){
            					if(plugin.patrolOn(cops)){
            					plugin.economy.setPlayerMoney(cops.getName(), playerMoney + 250, true);
                    			cops.sendMessage(ChatColor.BLUE + "For attempting to capturing the suspect you get " + ChatColor.GOLD + 250);
            						}
            					}
            				}
            			}
						
						plugin.getItems(p);
						String causeOfDeath = event.getEntity().getLastDamageCause().getCause().name();
						World world = Bukkit.getWorld("world");
						ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
						BookMeta bm = (BookMeta) book.getItemMeta();
						String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(Calendar.getInstance().getTime());
						ArrayList<String> witness = new ArrayList<String>();
						
						if(plugin.getPlayerInfo().contains("Players." + p.getUniqueId().toString())){
						int Arrests = plugin.getPlayerInfo().getInt("Players." + p.getUniqueId().toString() + ".Arrests");
						int nA = Arrests + 1;
						plugin.getPlayerInfo().set("Players." +p.getUniqueId().toString() + ".Arrests",  nA);
						plugin.savePlayerInfo();
						
						}
						
						if(plugin.getPlayerInfo().contains("Officers." + attacker.getUniqueId().toString())){
							int Arrests = plugin.getPlayerInfo().getInt("Officers." +attacker.getUniqueId().toString() + ".Arrested");
							int nA = Arrests + 1;
							plugin.getPlayerInfo().set("Officers." + attacker.getUniqueId().toString() + ".Arrested",  nA);
							plugin.savePlayerInfo();;
							
							}
						
						
						List<Entity> witnesses = event.getEntity().getNearbyEntities(10, 10, 10);
						
						for(Entity y : witnesses){
							if(y instanceof HumanEntity){
								String name = ((HumanEntity) y).getName();
								witness.add(name);
							}
						}	
						if(witness.isEmpty()){
							witness.add("none");
						}
						
						
						bm.setAuthor(attacker.getName());
						bm.setTitle("Police Report");
						bm.addPage("");
						bm.addPage("");
						bm.addPage("");
						bm.addPage("");
						bm.setPage(1, ChatColor.RED+ "Suspect: " + ChatColor.BLACK + p.getName()  + "\n" + "\n" +
						ChatColor.RED+ "Officer: " +"\n"+ ChatColor.BLACK + attacker.getName() + "\n" +"\n"
								   + ChatColor.RED+ "Cause of death: "+ChatColor.BLACK + causeOfDeath + 
								   "\n" +"\n"+ ChatColor.RED+"Time of Death: " +ChatColor.BLACK +timeStamp
								   +"\n" + "\n" +ChatColor.GREEN+"People near by: " +ChatColor.BLACK + witness.toString());
						bm.setPage(2, plugin.items.toString());
						book.getItemMeta().setDisplayName(ChatColor.RED+ "Police Report");
						book.setItemMeta(bm);
						world.dropItem(attacker.getLocation(), book);
						witness.clear();
						plugin.items.clear();
						
						
						}
					}
				}
			}      
	    }
	}
}
	
	@EventHandler
	public void onEat(PlayerItemConsumeEvent e){
		Player p = e.getPlayer();
		String name = p.getUniqueId().toString();
//////Bukkit.broadcastMessage("on eat");
		if(p.getItemInHand() != null){
			ItemStack item = p.getItemInHand();
		
			ItemMeta meta = 	item.getItemMeta();
			
			if(item.getType() == Material.POTION){
			if(meta.hasDisplayName()){
				if(meta.getDisplayName().equalsIgnoreCase("Booner Brew")){
					if(plugin.getConfig().contains("Drunk." + name)){
						int dui = plugin.getConfig().getInt("Drunk." + name);
						int newdui = dui + 10;
						plugin.getConfig().set("Drunk." + name, newdui);
						plugin.saveConfig();
					}else{
						
						int newdui =  10;
						plugin.getConfig().set("Drunk." + name, newdui);
						plugin.saveConfig();
					}
				}else if(meta.getDisplayName().equalsIgnoreCase("Ender Pearl Brew")){
					if(plugin.getConfig().contains("Drunk." + name)){
						int dui = plugin.getConfig().getInt("Drunk." + name);
						int newdui = dui + 15;
						plugin.getConfig().set("Drunk." + name, newdui);
						plugin.saveConfig();
					}else{
						
						int newdui =  15;
						plugin.getConfig().set("Drunk." + name, newdui);
						plugin.saveConfig();
					}
				
				}else if(meta.getDisplayName().equalsIgnoreCase("Grey Ghast Vodka")){
					if(plugin.getConfig().contains("Drunk." + name)){
						int dui = plugin.getConfig().getInt("Drunk." + name);
						int newdui = dui + 20;
						plugin.getConfig().set("Drunk." + name, newdui);
						plugin.saveConfig();
					}else{
						
						int newdui =  20;
						plugin.getConfig().set("Drunk." + name, newdui);
						plugin.saveConfig();
					}
				
				}else if(meta.getDisplayName().equalsIgnoreCase("DiccyFart MoonShine")){
					if(plugin.getConfig().contains("Drunk." + name)){
						int dui = plugin.getConfig().getInt("Drunk." + name);
						int newdui = dui + 30;
						plugin.getConfig().set("Drunk." + name, newdui);
						plugin.saveConfig();
					}else{
						
						int newdui =  30;
						plugin.getConfig().set("Drunk." + name, newdui);
						plugin.saveConfig();
					}
					}else if(meta.getDisplayName().equalsIgnoreCase("Booze Begone")){
						if(plugin.getConfig().contains("Drunk." + name)){
							int dui = plugin.getConfig().getInt("Drunk." + name);
							int newdui = dui - dui;
							plugin.getConfig().set("Drunk." + name, newdui);
							plugin.saveConfig();
						}else{
							p.sendMessage(ChatColor.YELLOW + "Maybe I should take this when I'm actually drunk");
						}
					}
				}
			}
			
			if(meta.hasDisplayName() && meta.hasLore()){
				if(meta.getLore().size() > 3){
					List<String> lore = meta.getLore();
			//////Bukkit.broadcastMessage("checking bonus + " + lore.get(2));
					
					if(lore.get(2).equalsIgnoreCase("Bonus Effects")){
						String effect1 = lore.get(2);
				//////Bukkit.broadcastMessage("in bonus");
						int starRank = plugin.getcookingRankLevel(lore.get(1));
						plugin.setFoodEffects(p, effect1, starRank);
						
						if(lore.size() == 4){
							String effect2 = lore.get(3);
							plugin.setFoodEffects(p, effect2, starRank);
						}
						
						if(lore.size() == 5){
							String effect3 = lore.get(4);
							plugin.setFoodEffects(p, effect3, starRank);
						}
						
					}
					
				}
			
			
	
		}
	}
}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event ){
		Player p = event.getPlayer();
	if(p.isOp()){	
		p.setOp(false);;
	}
	if(plugin.getConfig().contains("pvpLoggers." + p.getUniqueId().toString())){
		Bukkit.broadcastMessage(ChatColor.YELLOW + p.getName() + " the coward has returned weak from his own fear!");
		 p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,2400 , 3));
		 plugin.getConfig().set("pvpLoggers." + p.getUniqueId().toString(), null);
		 plugin.pvpLoggers.remove(p.getUniqueId());
		 plugin.saveConfig();
	}
	
//New Players OnJoin
if(!plugin.getPlayerInfo().contains("Players."+ p.getName()) && plugin.getPlayerInfo().contains("Players." +
p.getUniqueId().toString()) == false){
	Bukkit.broadcastMessage(ChatColor.GOLD + p.getName() + " has logged in for the first time!");
		if(plugin.getConfig().contains("Location." + "Start")){
			plugin.sendPlayerToSpawn(p);
		}else{
			double X = -1460.7532303124158;
			double Y = 6.247856929346917;
		    double Z = -169.29275434773174;
		    Location locb = new Location(p.getWorld(), X,Y,Z);
		    p.teleport(locb);
		}
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "permissions setrank " + p.getUniqueId() +  " default");
			plugin.setNewPlayer(p);
			p.sendMessage(ChatColor.GREEN + "Welcome to FiddyCraft, you are now under the city if you wish to join make it to the city above you, good luck!");
			p.sendMessage(ChatColor.GRAY + "Pro tip: run forward in any direction and you can make it.");
		}else{
			
		if(plugin.getPlayerInfo().contains("Players." + p.getUniqueId().toString() + ".Can Lawyer" ) == false){
			p.sendMessage(ChatColor.GREEN + "Updating Player info");
		
			plugin.updateOldPlayerInfo(p);
			}
		}

	}
	
	
	
	@EventHandler
	public void signSetUp(SignChangeEvent event){
		Player p = event.getPlayer();
		if(p.isOp()){
			if(event.getLine(0).equalsIgnoreCase("[Labor]")){
				event.setLine(0, ChatColor.DARK_RED + "[Labor]" );
			  p.sendMessage(ChatColor.GREEN + "Labor shop created");
			}
		}
		
		if(event.getLine(0).equalsIgnoreCase("[Juke]")){
			if(p.isOp() == false){
				event.setCancelled(true);
				p.sendMessage(ChatColor.RED + "You cannot set up a juke sign");
			}else{
				event.setLine(0, ChatColor.DARK_PURPLE + "[Juke]" );
				event.setLine(1,  "Punch 2 play" );
			}
		}
		
		if(event.getLine(0).equalsIgnoreCase("ClubJ")){
			if(p.isOp() == false){
				event.setCancelled(true);
				p.sendMessage(ChatColor.RED + "You cannot set up a juke sign");
			}else{
				event.setLine(0, ChatColor.DARK_PURPLE + "[Club Juke]" );
				event.setLine(1,  "Punch 2 play" );
			}
		}
		
		if(event.getLine(0).equalsIgnoreCase("Pro")){
			if(p.isOp()){
				if(plugin.isInRegion("CourtRoom", p.getLocation())){
				double x = event.getBlock().getLocation().getX();
				double y = event.getBlock().getLocation().getY();
				double z = event.getBlock().getLocation().getZ();
				
				plugin.getConfig().set("CourtSigns.Prosecution.X", x);
				plugin.getConfig().set("CourtSigns.Prosecution.Y", y);
				plugin.getConfig().set("CourtSigns.Prosecution.Z", z);
				plugin.saveConfig();
				event.setLine(0, "[Prosecution]");
				
			}
		}
	}	
		if(event.getLine(0).equalsIgnoreCase("Def")){
			if(p.isOp()){
				if(plugin.isInRegion("CourtRoom", p.getLocation())){
						double x = event.getBlock().getLocation().getX();
						double y = event.getBlock().getLocation().getY();
						double z = event.getBlock().getLocation().getZ();
						
						plugin.getConfig().set("CourtSigns.Defendant.X", x);
						plugin.getConfig().set("CourtSigns.Defendant.Y", y);
						plugin.getConfig().set("CourtSigns.Defendant.Z", z);
						plugin.saveConfig();
						event.setLine(0, "[Defense]");
				}
			}
		}
		if(event.getLine(0).equalsIgnoreCase("Judge")){
			if(p.isOp()){
				if(plugin.isInRegion("CourtRoom", p.getLocation())){
						double x = event.getBlock().getLocation().getX();
						double y = event.getBlock().getLocation().getY();
						double z = event.getBlock().getLocation().getZ();
						
						plugin.getConfig().set("CourtSigns.Judge.X", x);
						plugin.getConfig().set("CourtSigns.Judge.Y", y);
						plugin.getConfig().set("CourtSigns.Judge.Z", z);
						plugin.saveConfig();
						event.setLine(0, "[Judge]");
				}
			}
		}	
	}
	
	
	@EventHandler
	public void mobSpawn(CreatureSpawnEvent  event){
		LivingEntity creature = event.getEntity();
		if(creature.getCustomName() == null){
			Location loc = creature.getLocation();
			
			if(plugin.isInRegion("Town", loc) || plugin.isInRegion("ghetto", loc)){
				
			if(event.getSpawnReason() == SpawnReason.NATURAL){
				event.setCancelled(true);
				}
			}
		}
		
		if(event.getSpawnReason() == SpawnReason.EGG || event.getSpawnReason() == SpawnReason.BREEDING){
			event.setCancelled(true);
		}
	}


	@EventHandler
	public void commands(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		if(p.isOp()){
			if(p.getName().equalsIgnoreCase("secretfish98") || p.getName().equalsIgnoreCase("linkstheman12")){
			String timeStamp = new SimpleDateFormat("MM/dd HH:mm").format(Calendar.getInstance().getTime());
			String m = e.getMessage();
			plugin.getConfig().set("Snooper.Commands."+ p.getName(),  ". Command" + m +".timeStamp." + timeStamp );
			plugin.saveConfig();
			}
		}
	}
    
	@EventHandler
    public void rightClicks(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if( e.getAction() == Action.LEFT_CLICK_BLOCK){
        	 Block block = e.getClickedBlock(); 
        	
        	if(block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
        	    Sign sign = (Sign) e.getClickedBlock().getState();
        	    
        	   if(sign.getLine(0).contains(ChatColor.DARK_RED + "[Labor]")) { 
        	   if(plugin.getConfig().contains("Jailed." + p.getUniqueId().toString())){
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
        	    ItemStack cobble = new ItemStack(Material.COBBLESTONE);
        	    ItemStack melon = new ItemStack(Material.MELON);
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
        	        }else if(have.equals(melon.getType())){
        	        	int itemvalue = 1;
        	        	plugin.newJailScore(p, itemvalue);
        	        	plugin.takeOne(p, melon);
        	        }else if(have.equals(cobble.getType())){
        	        	int itemvalue = 1;
        	        	plugin.newJailScore(p, itemvalue);
        	        	plugin.takeOne(p, cobble);
        	        }else{
        	        		p.sendMessage("you have nothing");
        	        	}
        	        	plugin.getConfig().set("Jailed." + p.getUniqueId().toString(), plugin.getScore(p));
        	        	plugin.saveConfig();
        	        	
        	        	 if(plugin.getScore(p) <= 0 ){
        	        		 p.sendMessage(ChatColor.YELLOW + "You have paid for you Crimes You are free to go");
        	        		 ////Bukkit.broadcastMessage(p.getName() + " has been released from jail");
        	        		 
        	        		 p.getServer().getWorld("world").setSpawnLocation(-1459, 74, -236);
        	        		 if(plugin.getConfig().contains("Release")){
        	        			double x =  (double) plugin.getConfig().get("Release.X");
        	     				double y =  (double) plugin.getConfig().get("Release.Y");
        	     				double z =  (double) plugin.getConfig().get("Release.Z");
        	     				Location loc = new Location(Bukkit.getServer().getWorld("world"),x, y, z);
        	     				p.getInventory().clear();
        	     				p.teleport(loc);
        	     				plugin.getConfig().set("Jailed." + p.getUniqueId().toString(), null);
        	     				plugin.saveConfig();
        	     				plugin.reloadConfig();
        	     				
        	     				 FiddyCraft.getScoreboard(p).clearSlot(DisplaySlot.SIDEBAR);
        	     				 FiddyCraft.boards.remove(p.getName());
        	        		 }else{
        	        			 Location loc = new Location(p.getWorld(), -1305, 72, -329);
        	        			 p.getInventory().clear();
        	        			 p.teleport(loc);
        	        			 plugin.getConfig().set("Jailed." + p.getUniqueId().toString(), null);
        	     
        	        			 plugin.saveConfig();
        	        			 plugin.reloadConfig();
        	        			
        	     				 FiddyCraft.getScoreboard(p).clearSlot(DisplaySlot.SIDEBAR);
        	     				 FiddyCraft.boards.remove(p.getName()); 
        	        		 } 
        	        	 }
        	   		}
        	    }else if(sign.getLine(0).contains(ChatColor.DARK_PURPLE + "[Juke]")){
        	    	int r =  (int) (Math.random() * 12 ); 
        	    	
        	    	BlockState bs = p.getWorld().getBlockAt(-1479, 70, -236).getState();
        	    			if(bs instanceof Jukebox) {	
        	    	
        	    	if(r == 1){
        	    		((Jukebox) bs).setPlaying(Material.GOLD_RECORD);
        	    	}else if(r == 2){
        	    		((Jukebox) bs).setPlaying(Material.GREEN_RECORD);
        	    	}else if(r == 3){
        	    		((Jukebox) bs).setPlaying(Material.RECORD_3);
        	    	}else if(r == 4){
        	    		((Jukebox) bs).setPlaying(Material.RECORD_4);
        	    	}else if(r == 5){
        	    		((Jukebox) bs).setPlaying(Material.RECORD_5);
        	    	}else if(r == 6){
        	    		((Jukebox) bs).setPlaying(Material.RECORD_6);
        	    	}else if(r == 7){
        	    		((Jukebox) bs).setPlaying(Material.RECORD_7);
        	    	}else if(r == 8){
        	    		((Jukebox) bs).setPlaying(Material.RECORD_8);
        	    	}else if(r == 9){
        	    		((Jukebox) bs).setPlaying(Material.RECORD_9);
        	    	}else if(r == 10){
        	    		((Jukebox) bs).setPlaying(Material.RECORD_10);
        	    	}else if(r == 11){
        	    		((Jukebox) bs).setPlaying(Material.RECORD_11);
        	    	}else if(r == 12){
        	    		((Jukebox) bs).setPlaying(Material.RECORD_12);
        	    		}else{
        	    			
        	    		}
        	    	}
        	    }
        	   
        	   if(sign.getLine(0).contains(ChatColor.DARK_PURPLE + "[Club Juke]")){
        	    	BlockState bs = p.getWorld().getBlockAt(-1479, 70, -236).getState();
        	    			if(bs instanceof Jukebox) {	
        	    		((Jukebox) bs).setPlaying(Material.RECORD_8);
        	    	
        	    			}
        	    		}
        	   		}
        	
        	 if(plugin.isGrowableBlock(e.getClickedBlock())){
     			
        		 if(p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasDisplayName()){
     			if( p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.getfertilizerItem().getItemMeta().getDisplayName())){
     				//Bukkit.broadcastMessage("has fertalizer");
     			Location loc = e.getClickedBlock().getLocation();
         		if(plugin.isPlantOwned(loc)){
         			//Bukkit.broadcastMessage("has owned");
         			if(plugin.getPlantInfo().contains("Farmer." + p.getUniqueId().toString() + ".Plants." + plugin.getPlantLocationID(loc))){
         				Plants pl = new Plants(plugin, p.getUniqueId().toString(), loc);	
         			if(!pl.isfertilized()){
         				e.setCancelled(true);
         				plugin.takeOne(p, plugin.getfertilizerItem());
         				pl.setfertilized(true);
         				int q = pl.getPlantQuailty();
         				plugin.addExpFarmer(p, .03);
         				if(q > 4){
         					
         				}else{
         					pl.setPlantQuality(q +1);
         				}
         				p.sendMessage(ChatColor.DARK_GREEN + "Plant fertilized!");
         				int r = plugin.randomNumber(20);
         				
         				if(r == 3){
         					p.sendMessage(ChatColor.ITALIC + "You hands feel warm and smelly");
         				}
         			}else{
         				p.sendMessage(ChatColor.YELLOW + "This is already fertilized");
         				e.setCancelled(true);
         					}
         				}
         			}
         		}
     		}
        }
       }
        if( e.getAction() == Action.RIGHT_CLICK_BLOCK ){
        	ItemStack hand = p.getItemInHand();
        	
        	if(e.getClickedBlock().getType() == Material.ANVIL){
        		if(p.getName().equalsIgnoreCase("xxBooneXX") || p.getName().equalsIgnoreCase("nuns") ||  p.getName().equalsIgnoreCase("Fiddy_Percent")){
        		p.sendMessage(ChatColor.YELLOW + "Remember this cannot be used in normal play just mod stuff");
        		}else{
        		p.sendMessage(ChatColor.YELLOW + "Hmm I cant use this anymore");
        		e.setCancelled(true);
        		}
        	}
        	if(e.getClickedBlock().getType() == Material.SOIL ){
        		if(hand.hasItemMeta() && hand.getItemMeta().hasDisplayName()){
        		ItemMeta meta = hand.getItemMeta();
        		String dn = meta.getDisplayName();	
       		 	ArrayList<cropSeed> test = new ArrayList<cropSeed>(Arrays.asList(cropSeed.values()));
       		 	String meh = dn.toUpperCase().replace(" SEEDS", "").replace(" ", "_").replace("SEEDS", "");
       		 	//Bukkit.broadcastMessage(ChatColor.RED + meh + " old " + dn);
       		 	if(test.toString().contains(meh)){
        			if(plugin.canPlantThis(p, dn)){
        				////Bukkit.broadcastMessage(ChatColor.GOLD + " a seed type" + meh);
        					plugin.plantNewSeed(p, e.getClickedBlock(), meh, hand);
        					plugin.addExpFarmer(p, .01);
        					e.setCancelled(false);
        					Plants pt = new Plants(plugin, p.getUniqueId().toString(), plugin.getFirstPlantLocation(e.getClickedBlock().getLocation()));
        					pt.setFirstPlanting();
        			}else{
        				e.setCancelled(true);
        				p.sendMessage(ChatColor.RED + "You cannot plant this type of seed yet.");
        				p.sendMessage("You can plant " + plugin.getPlayerPlantAbleList(p).split("~"));
        				
        			}
        			
        			}else{
        				////Bukkit.broadcastMessage("not a seed type = " + meh  + "list " + test.toString());
        				e.setCancelled(true);
        		}		
        	}else{
        		if(plugin.isASeed(p.getItemInHand())){
        		e.setCancelled(true);
        			}
        		}
        	}
        	
        	else if(plugin.isGrowableBlock(e.getClickedBlock()) && p.getItemInHand().getType() == Material.AIR){
        		Location loc = e.getClickedBlock().getLocation();
        		if(plugin.isPlantOwned(loc)){
        			////Bukkit.broadcastMessage("this plant is owned");
        			if(plugin.getPlantInfo().contains("Farmer." + p.getUniqueId().toString() + ".Plants." + plugin.getPlantLocationID(loc))){
        				Plants pl = new Plants(plugin, p.getUniqueId().toString(), loc);
        				////Bukkit.broadcastMessage("you own this plant");
        				p.sendMessage(ChatColor.DARK_GREEN + "Plant Type: " + ChatColor.YELLOW + pl.getPlantType().toLowerCase());
        				p.sendMessage(ChatColor.DARK_GREEN + "Is Watered: " + ChatColor.YELLOW + pl.getisWaterd());
        				p.sendMessage(ChatColor.DARK_GREEN + "Is Healthy: " + ChatColor.YELLOW + pl.isHealthy());
        				p.sendMessage(ChatColor.DARK_GREEN + "Is fertilized: " + ChatColor.YELLOW + pl.isfertilized());
        				p.sendMessage(ChatColor.DARK_GREEN + "Plant Growth Cycle: " +  ChatColor.YELLOW +pl.getPlantCycle());
        				
        				//p.sendMessage(ChatColor.GREEN + "this" +);
        				
        			}else{
        				
        			}
        		}
        		
        	}
        	
        	else if(plugin.isGrowableBlock(e.getClickedBlock()) && p.getItemInHand().getType() == Material.getMaterial(373)){
        		Location loc = e.getClickedBlock().getLocation();
        		if(plugin.isPlantOwned(loc)){
        			if(plugin.getPlantInfo().contains("Farmer." + p.getUniqueId().toString() + ".Plants." + plugin.getPlantLocationID(loc))){
        				Plants pl = new Plants(plugin, p.getUniqueId().toString(), loc);
        				if(!pl.getisWaterd()){
        				Bukkit.getWorld("world").playEffect(loc, Effect.POTION_BREAK, 0);
        				pl.setIsWatered(true);
        				plugin.addExpFarmer(p, .01);
        				
        				}else{
        				Bukkit.getWorld("world").playEffect(loc, Effect.POTION_BREAK, 0);
        				}
        			
        		}else{
            		e.setCancelled(true);
    				p.sendMessage(ChatColor.YELLOW + "Looks like this plant was planted by someone");
        			}
        		}
        		}
        	}
      	}	
	

	@EventHandler(priority = EventPriority.HIGH)
	public void onQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		if(!plugin.patrol.isEmpty() && plugin.patrol.containsKey(p.getUniqueId())){
			plugin.patrol.remove(p.getUniqueId());
		}
		
		if(plugin.pvpLoggers.containsKey(p.getUniqueId())){
	Bukkit.broadcastMessage(ChatColor.RED + p.getName() + " has pvp logged!");
			if(!plugin.getConfig().contains("pvpLoggers")){
			plugin.getConfig().set("pvpLoggers." + p.getUniqueId().toString(), p.getName());
			plugin.saveConfig();
			}else{
				plugin.getConfig().set("pvpLoggers." + p.getUniqueId().toString(), p.getName());
				plugin.saveConfig();
			}
			
			
		}
		
	}
	@EventHandler
	public void blockPlace(BlockPlaceEvent event){
		Player p = event.getPlayer();
		
		if(event.getBlock().getType() == Material.BREWING_STAND || event.getBlock().getType() == Material.BREWING_STAND_ITEM){
			
		if(!plugin.isInRegion("ThugHideOut", p.getLocation())){	
			event.setCancelled(true);
			p.sendMessage(ChatColor.YELLOW + "I should probably look for a more shadey area to make some drugs");
			}
		}
	}
	
}

