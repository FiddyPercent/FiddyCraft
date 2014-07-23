package io.github.FiddyPercent.fiddycraft;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class FcFarmers extends FcPlayers {

	private String jobType;
	private String Rank;
	private double exp;
	private ArrayList<String> cropsGrowAble;
	@SuppressWarnings("unused")
	private List<?> allFarmers;
	
	public FcFarmers(FiddyCraft plugin, Player P) {
		super(plugin, P);
		allFarmers = (List<?>) plugin.getPlantInfo().getConfigurationSection("Farmer").getKeys(false);
		jobType = "Farmer";
		// TODO Auto-generated constructor stub
	}
	
	public String getRank(){
		return Rank;
	}
	
	public String getJobType(){
		return jobType;
	}
	
	public double getExp(){
		return exp;
	}
	
	public  ArrayList<String> getCropsGrowAble(){
		return cropsGrowAble;
	}
	
	
	
	
}
