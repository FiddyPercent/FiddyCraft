package io.github.FiddyPercent.fiddycraft;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;



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
	
}
