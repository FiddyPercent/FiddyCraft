package io.github.FiddyPercent.fiddycraft;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;



public class FiddyCraftListener implements Listener{
	
HashMap<String, Integer> Attacked = new HashMap<String, Integer>();
	
	@EventHandler(priority = EventPriority.HIGH)
	public void attack(EntityDamageEvent event) {
		if(event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && event.getEntityType() 
				== EntityType.PLAYER){
			Player player = (Player) event.getEntity();
			String name = player.getName();
		if(!Attacked.containsKey(name) || Attacked.get(name) == 0){
				Attacked.put(name, 1);
				player.sendMessage(ChatColor.RED + "Run fool!");
	  }else if(Attacked.get(name) <= 3 ){
				int numberAttacked = Attacked.get(name);
				int total = numberAttacked +1;
				Attacked.put(name, total);
	  }else{
		  	Attacked.put(name, 0);
		  	player.sendMessage(ChatColor.YELLOW + "DODGE!!");
			}
		}	
    }
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void EntityDeath(EntityDeathEvent event){
		String entityName = event.getEntityType().getName();
		
		Bukkit.broadcastMessage(ChatColor.RED + entityName + " died!");
		
		if(entityName.equalsIgnoreCase("Villager")){
			ArrayList<String> witness = new ArrayList<String>();
			Location loc = event.getEntity().getLocation();
			List<Entity> witnesses = event.getEntity().getNearbyEntities(10, 10, 10);
			String killer = event.getEntity().getKiller().getName();
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
			bm.setTitle("The Death of a Villager");
			bm.addPage(">_>");
			bm.setPage(1, ChatColor.RED+ "Killer: " + ChatColor.BLACK + killer + "\n" +"\n"
					   + ChatColor.RED+ "Cause of death: "+ChatColor.BLACK + causeOfDeath + 
					   "\n" +"\n"+ ChatColor.RED+"Time of Death: " +ChatColor.BLACK +timeStamp
					   +"\n" + "\n" +ChatColor.GREEN+"People near by: " +ChatColor.BLACK + witness.toString());
			book.getItemMeta().setDisplayName(ChatColor.RED+ "Evidence");
			book.setItemMeta(bm);
			world.dropItem(loc, book);
			witness.clear();
			
		}
	}
	
}
