package io.github.FiddyPercent.fiddycraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Mushroom;

public class animalUtility{
	
	private final FiddyCraft plugin;
	
	public animalUtility(FiddyCraft pp){
		this.plugin = pp;
	}

//CHECKING OWNER
	public boolean isAnimalOwner(Player p, Entity e){
		if(plugin.getAnimalData().contains("Farmer." + p.getUniqueId().toString() + ".Animals." + e.getUniqueId().toString())){
			return true;
		}else{
			return false;
		}
	}
	
//CLAIMING A ANIMAL
	public boolean claimAnimal(Player p, Entity animal, ItemMeta meta){
		String owner = p.getUniqueId().toString();
		String pet = animal.getUniqueId().toString();
		if(!plugin.getAnimalData().contains("Farmer." + owner + ".Animals")){
			plugin.getAnimalData().set("Farmer."+ owner + ".Name", p.getName() + ".Animals");
			plugin.saveAnimalData();
		}
			if(this.canHaveMoreAnimals(p)){
				if(this.checkIfHasSameName(owner, meta.getDisplayName())){
			plugin.getAnimalData().set("Farmer."+ owner + ".Animals." +  pet + ".Name", meta.getDisplayName());
			plugin.getAnimalData().set("Farmer."+ owner + ".Animals." +  pet + ".Owner", p.getName());
			plugin.getAnimalData().set("Farmer."+ owner + ".Animals." +  pet + ".OwnerID", owner );
			plugin.getAnimalData().set("Farmer."+ owner + ".Animals." +  pet + ".HeartPoints", 0 );
			plugin.getAnimalData().set("Farmer."+ owner + ".Animals." +  pet + ".Hunger", true );
			plugin.getAnimalData().set("Farmer."+ owner + ".Animals." +  pet + ".Healthy", true );
			plugin.getAnimalData().set("Farmer."+ owner + ".Animals." +  pet + ".Happy", true );
			plugin.getAnimalData().set("Farmer."+ owner + ".Animals." +  pet + ".Washed", false );
			plugin.getAnimalData().set("Farmer."+ owner + ".Animals." +  pet + ".isPet", true );
			plugin.getAnimalData().set("Farmer."+ owner + ".Animals." +  pet + ".WillDie", false);
			plugin.getAnimalData().set("Farmer."+ owner + ".Animals." +  pet + ".Age", 0);
			plugin.getAnimalData().set("Farmer."+ owner + ".Animals." +  pet + ".Weight", 40);
			plugin.saveAnimalData();
			p.sendMessage(ChatColor.BLUE + "You now own " + meta.getDisplayName() + "!");
			return true;
				}else{
					p.sendMessage(ChatColor.RED + "You cannot name two animals the same name");
					LivingEntity an = (LivingEntity)animal;
					an.setCustomName(null);
					return false;
					
				}
			}else{
			p.sendMessage(ChatColor.RED + "You Have to many animals you cannot claim anymore");
			return false;
		}
	}
	
	public boolean checkIfHasSameName(String uuid, String metaname){
		if(plugin.getAnimalData().getString("Farmer."+ uuid + ".Animals") == null){
			Bukkit.broadcastMessage("NULL");
			return true;
		}
		Set<String> auuids = plugin.getAnimalData().getConfigurationSection("Farmer."+ uuid + ".Animals").getKeys(false);
		for(String auuid : auuids){
			String can = plugin.getAnimalData().getString("Farmer."+ uuid + ".Animals." +  auuid + ".Name");
			Bukkit.broadcastMessage(can);
			if(can.equalsIgnoreCase(metaname)){
				Bukkit.broadcastMessage("same name bro");
				return false;
				
			}
		}
		Bukkit.broadcastMessage("just whatever false");
		return true;
	}
	
//CHECK IF ANIMAL HAS A OWNER
	public boolean checkifHasOwner(String uuid){
	ArrayList<String> allAnimals = new ArrayList<String>();
		if(!plugin.getAnimalData().contains("Farmer")){
			return false;
		}else{
		Set<String> farmers =  plugin.getAnimalData().getConfigurationSection("Farmer").getKeys(false);
	//getting the farmers animals
		for(String farmer : farmers){
			if(plugin.getAnimalData().get("Farmer." + farmer + ".Animals") == null){
				break;
			}
			Set<String> t =  plugin.getAnimalData().getConfigurationSection("Farmer." + farmer + ".Animals").getKeys(false);
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
	public void animalCoolDown(){
		if(!plugin.getAnimalData().contains("AnimalCoolDown")){
			plugin.getAnimalData().set("AnimalCoolDown", "test");
			plugin.saveAnimalData();
		}
		
	
		Player[] p = Bukkit.getOnlinePlayers();
			for(Player player : p){
				if(plugin.getAnimalData().contains("Farmer." + player.getUniqueId().toString() + ".Animals")){
					String l = player.getUniqueId().toString();	
					Set<String> t =  plugin.getAnimalData().getConfigurationSection("Farmer." + l + ".Animals").getKeys(false);
				for(String aL : t){
					
					if(plugin.getAnimalData().contains("AnimalCoolDown." + aL)){
						int cooldown = plugin.getAnimalData().getInt("AnimalCoolDown." + aL);				
						int newcooldown = cooldown - 5;
						plugin.getAnimalData().set("AnimalCoolDown." + aL, newcooldown);
						plugin.saveAnimalData();
						if(newcooldown <= 0){
							plugin.getAnimalData().set("AnimalCoolDown." + aL, null);
							plugin.saveAnimalData();
						}
					}
				}
			}
		}
	}

//REMOVING ANIMAL DEATH REPORTING
	public void removeAnimal(Entity e){
		Set<String> farmers =  plugin.getAnimalData().getConfigurationSection("Farmer").getKeys(false);
		for(String farmer : farmers){
			Set<String> farmeranimals =  plugin.getAnimalData().getConfigurationSection("Farmer." + farmer + ".Animals").getKeys(false);
			
			for(String farmanimal : farmeranimals){
				if(farmanimal.equalsIgnoreCase(e.getUniqueId().toString())){
					LivingEntity le = (LivingEntity) e;
					plugin.getAnimalData().set("Farmer." + farmer +".Animals."+ farmanimal, null);
					Player[] p = Bukkit.getOnlinePlayers();
					for(Player online : p){
					if(online.getUniqueId().toString().equalsIgnoreCase(farmer)){
					online.sendMessage(ChatColor.GRAY + "Your " + e.getType().toString() +  " " + le.getCustomName() + " has " + ChatColor.DARK_RED + "died.");
					}
					}
					if(plugin.getAnimalData().contains("AnimalCoolDown." + farmanimal)){
						plugin.getAnimalData().set("AnimalCoolDown." + farmanimal, null );
					}
					plugin.saveAnimalData();
					}
				}
			}
		}
	
//CHECKING ANIMAL LIMIT
	public boolean canHaveMoreAnimals(Player p ){
		if(plugin.getAnimalData().getString("Farmer") == null){
			return true;
		}else{
			
			if(!plugin.getAnimalData().contains("Farmer." + p.getUniqueId().toString() + ".Animals")){
				plugin.getAnimalData().set("Farmer."+ p.getUniqueId().toString() + ".Name", p.getName() + ".Animals");
				plugin.saveAnimalData();
			}
			
		if(!plugin.getAnimalData().contains("Farmer." + p.getUniqueId().toString() + ".Animals")){	
			return true;
		}
			Set<String> t =  plugin.getAnimalData().getConfigurationSection("Farmer." + p.getUniqueId().toString() + ".Animals").getKeys(false);
			ArrayList<String> animals = new ArrayList<String>();
			for(String aL : t){
				animals.add(aL);
			}
			FcPlayers fc = new FcPlayers(plugin, p);
			String job = fc.getPlayerJob();
				if(job.equalsIgnoreCase("Farmer")){
				 FcFarmers fcf = new FcFarmers(plugin,p);
				 String rank = fcf.getRank();
					if(rank.equalsIgnoreCase("Legendary Farmer")){
						if(animals.size() <= 12){
							Bukkit.broadcastMessage("legendary");
							return true;
						}else{
							return false;
						}
					}else if(rank.equalsIgnoreCase("Great Farmer")){
						if(animals.size() <= 8){
							Bukkit.broadcastMessage("great");
							return true;
						}else{
							return false;
						}
					}else{
						if(animals.size() <= 6){
							Bukkit.broadcastMessage("normal");
							return true;
						}else{
							return false;
						}
						}
					}else{
						if(animals.size() <= 4){
							Bukkit.broadcastMessage("not a farmer");
							return true;
						}
					}
				}
		 return true;
			}
		
	
	
//CHECKING WHAT ANIAMLS EAT WHAT
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

//ANIMAL NOISES
	public void playAnimalSound(Entity e, Player p){
		String type = e.getType().toString();
		Location loc = p.getLocation();
		if(type.equalsIgnoreCase("Cow")){
			Bukkit.getWorld("world").playSound(loc, Sound.COW_IDLE, 1, 1);
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 5);
		}else if(type.equalsIgnoreCase("Sheep")){
			Bukkit.getWorld("world").playSound(loc, Sound.SHEEP_IDLE, 1, 1);
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 5);
		}else if(type.equalsIgnoreCase("Pig")){
			Bukkit.getWorld("world").playSound(loc, Sound.PIG_IDLE, 1, 1);
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 5);
		}else if(type.equalsIgnoreCase("chicken")){
			Bukkit.getWorld("world").playSound(loc, Sound.CHICKEN_IDLE, 1, 1);
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 5);
		}else if(type.equalsIgnoreCase("Horse")){
			Bukkit.getWorld("world").playSound(loc, Sound.HORSE_BREATHE, 1, 1);
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 5);
		}else if(type.equalsIgnoreCase("Wolf")){
			Bukkit.getWorld("world").playSound(loc, Sound.WOLF_BARK, 1, 1);
			Bukkit.getWorld("world").playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 5);
		}
		
	}
	
//FINDING WHO OWNS THIS ANIAML
	public String getAnimalOwnerID(String auuid){
		
		Set<String> farmers = plugin.getAnimalData().getConfigurationSection("Farmer").getKeys(false);
		for(String farmer : farmers){
			if(plugin.getAnimalData().contains("Farmer." + farmer +".Animals." + auuid)){
				return plugin.getAnimalData().getString("Farmer."+ farmer + ".Animals." +  auuid + ".OwnerID");
			}else{
				return "Unknown";
			}
		}
		return "Unknown";
	}
	
//TYPES OF ANIMAL FOOD
	public boolean isAnimalFood(Material item){
		if(item == Material.HAY_BLOCK || item == Material.SEEDS || item == Material.ROTTEN_FLESH || item == Material.RAW_BEEF || item == Material.PORK
				|| item == Material.RAW_FISH){
			return true;
		}else{
			return false;
		}
	}

//ANIMAL EATING EVENT
	public void animalEat(Player p, ItemStack i){
		String uuid = p.getUniqueId().toString();
		if(plugin.getAnimalData().contains("Farmer." + uuid)){
			List<Entity> nearby = p.getNearbyEntities(2, 2, 2);
			for(Entity entity: nearby){
				if(this.isAnimal(entity)){
					if(this.isAnimalOwner(p, entity)){
						Animals a = new Animals(plugin, entity.getUniqueId().toString(), p.getUniqueId().toString());
						if(a.getHunger()){
								if(this.animalFood(entity, i.getType())){
										this.playAnimalSound(entity, p);
										ItemMeta meta = i.getItemMeta();
										meta.setDisplayName("remove");
										i.setItemMeta(meta);
										a.addHeartPoint(.03);
										a.setHunger(false);
										p.sendMessage(ChatColor.YELLOW + a.getAnimalName() +" is fed.");
										a.addWeight(.4);
										int r = plugin.randomNumber(5);
									
										if(r == 2){
											Location loc = entity.getLocation();
											p.getWorld().dropItem(loc, plugin.getfertilizerItem());
											p.getWorld().playSound(loc, Sound.FALL_BIG, 1, 1);
										}
									}
								if(a.isHealthy() == false){
									a.neglectDeath(p, entity);
								}else{
								a.oldAgeDeath(p, entity);
								}
								}
							}	
						}
					}
				}
			}
	
//GET ALL FARM ANIMALS
	public String getAllFarmAnimals(){
		Player[] p = Bukkit.getOnlinePlayers();
			for(Player player : p){
			if(plugin.getAnimalData().contains("Farmer." + player.getUniqueId().toString())){
			String farmer = player.getUniqueId().toString();	
			Set<String> animals =  plugin.getAnimalData().getConfigurationSection("Farmer." + farmer + ".Animals").getKeys(false);
			for(String animal : animals){
		return animal;
				}
			}else{
				return null;
				}
			}
		return null;
	}

	
	
//SHOW BASIC ANIMAL INFO
	public void showAnimalInfo(Player p, Entity e){
	if(this.checkifHasOwner(e.getUniqueId().toString())){
		Animals a = new Animals(plugin, e.getUniqueId().toString(), p.getUniqueId().toString());
		int hp = (int) a.getHeartPoints();
		p.sendMessage(ChatColor.YELLOW + "+++++ Animal Info +++++");
		p.sendMessage(ChatColor.YELLOW + "Animal Name: "  + ChatColor.GREEN+ a.getAnimalName());
		p.sendMessage(ChatColor.YELLOW + "Animal Owner: "  + ChatColor.GREEN+ a.getOwnerName());
		p.sendMessage(ChatColor.YELLOW + "Heart Level: " + ChatColor.GREEN + hp);
		p.sendMessage(ChatColor.YELLOW + "Is Clean: "  + ChatColor.GREEN + a.getIsWashed());
		p.sendMessage(ChatColor.YELLOW + "Is happy: "  + ChatColor.GREEN + a.isHappy());
		p.sendMessage(ChatColor.YELLOW + "Is lonely: "  + ChatColor.GREEN + a.getIsPet());
		p.sendMessage(ChatColor.YELLOW + "Is Hungry: " + ChatColor.GREEN + a.getHunger());
		p.sendMessage(ChatColor.YELLOW + "Is Healthy: "  + ChatColor.GREEN + a.isHealthy());
		p.sendMessage(ChatColor.YELLOW + "Is Weight: "  + ChatColor.GREEN + a.getWeight());
		}
	}
}
