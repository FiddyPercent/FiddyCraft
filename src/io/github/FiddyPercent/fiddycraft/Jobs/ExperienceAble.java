package io.github.FiddyPercent.fiddycraft.Jobs;

public interface ExperienceAble  {

	double getCurrentExp();
	
	void addExp(double addedExp);
	
	void removeExp(double removedExp);
	
	void rankup(double exp);
	
}
