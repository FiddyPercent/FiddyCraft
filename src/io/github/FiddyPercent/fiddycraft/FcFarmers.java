package io.github.FiddyPercent.fiddycraft;

import java.util.Set;

import org.bukkit.entity.Player;

public class FcFarmers extends FcPlayers implements ExperienceAble {

	private String Rank;
	private double exp;
	private Set<String> allFarmers;
	private Player p;
	
	public FcFarmers(FiddyCraft plugin, Player p) {
		super(plugin,p);
		allFarmers = plugin.getPlantInfo().getConfigurationSection("Farmer").getKeys(false);
		exp = plugin.getPlayerInfo().getDouble("Players." + p.getUniqueId().toString() +".Farmer Exp");
		Rank = plugin.getPlayerInfo().getString("Players." + p.getUniqueId().toString() +".Farmer Rank");
	}
	
	public String getRank(){
		return Rank;
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
		plugin.getPlayerInfo().set("Players." + p.getUniqueId().toString() +".Farmer Exp", ex);
		plugin.savePlayerInfo();
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
	
	
}
