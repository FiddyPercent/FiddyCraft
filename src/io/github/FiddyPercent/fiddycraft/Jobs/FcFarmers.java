package io.github.FiddyPercent.fiddycraft.Jobs;

import io.github.FiddyPercent.fiddycraft.FcPlayers;
import io.github.FiddyPercent.fiddycraft.FiddyCraft;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FcFarmers extends FcPlayers implements ExperienceAble {

	private String Rank;
	private double exp;
	private Set<String> allFarmers;
	private Player p;
	
	public FcFarmers(FiddyCraft plugin, Player p) {
		super(plugin,p);
		this.p = p;
		allFarmers = plugin.getPlantInfo().getConfigurationSection("Farmer").getKeys(false);
		exp = plugin.getPlayerInfo().getDouble("Players." + p.getUniqueId().toString() +".Farmer Exp");
		Rank = plugin.getPlayerInfo().getString("Players." + p.getUniqueId().toString() +".Farmer Rank");
	}
	
	public String getRank(){
		return Rank;
	}
	
	public List<String> getRanks(){
		List<String> ranks = Arrays.asList("Farmer","Great Farmer", "Legendary Farmer");
		return ranks;
	}
	public void setFarmerRank(String rank){
		plugin.getPlayerInfo().set("Players." + p.getUniqueId().toString() +".Farmer Rank", rank);
		
		plugin.savePlayerInfo();
	}
	public String getJobType(){
		return "Farmer";
	}
	
	public double getExp(){
		return exp;
	}
	public void addExp(Double ex){
		double old = this.getExp();
		double newexp = old + ex;
		this.setExp(newexp);
	}
	
	public void setExp(Double ex){
		Bukkit.broadcastMessage(ex + " exp");
		double roundOff = Math.round(ex * 100.0) / 100.0;
		plugin.getPlayerInfo().set("Players." + p.getUniqueId().toString() +".Farmer Exp", roundOff);
		plugin.savePlayerInfo();
		this.rankup(roundOff);
	}
	
	public Set<String> getAllFarmers(){
		return allFarmers;
	}
	
	@Override
	public double getCurrentExp() {
		return exp;
	}

	@Override
	public void addExp(double addedExp) {
		double newexp = exp + addedExp;
		this.setExp(newexp);
		
	}

	@Override
	public void removeExp(double removedExp) {
		double newexp = exp - removedExp;
		this.setExp(newexp);
		
	}

	@Override
	public void rankup(double exp) {
		
		if(exp < 50){
		//add a tag thing here;
			this.setFarmerRank("Farmer");
		}else if(exp > 49 && exp < 150){
			if(this.getRank().equalsIgnoreCase("Farmer")){
				Bukkit.broadcastMessage( ChatColor.GREEN + this.getfcPlayerName() + " is now a Great Farmer!");
			}
			this.setFarmerRank("Great Farmer");
		}else if(exp > 149){
			if(this.getRank().equalsIgnoreCase("Great Farmer")){
				Bukkit.broadcastMessage(ChatColor.GOLD +  this.getfcPlayerName() + " is now a Legendary Farmer!");
			}
			this.setFarmerRank("Legendary Farmer");
		}
		
		
	}
	
	
}
