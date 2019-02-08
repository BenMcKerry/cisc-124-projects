package assignment3;

import java.util.ArrayList;

public class Truck extends Vehicle {
	
	int maxLoad;
	int currentLoad;
	int loadCapability;
	String destination;
	
	ArrayList<Integer> addedLoads = new ArrayList<>();		//stores all loads added to the truck
	ArrayList<Integer> oldCapacity = new ArrayList<>();		//stores truck's old capacity at all stages
	ArrayList<Integer> newCapacity = new ArrayList<>();		//stores truck's new capacity at all stages
	
	int count = 0;
	
	Truck(int state, int num, String destination, int maxLoad, int currentLoad) {
		this.state = state;
		this.num = num;
		this.maxLoad = maxLoad;
		this.currentLoad = currentLoad;
		this.destination = destination;
		loadCapability = maxLoad - currentLoad;
		available = true;
	}
	
	public int getMaxLoad() {
		return maxLoad;
	}

	public void setMaxLoad(int maxLoad) {
		this.maxLoad = maxLoad;
	}

	public int getCurrentLoad() {
		return currentLoad;
	}
	
	public int getLoadCapability() {
		return loadCapability;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	int getAddedLoad() {
		return addedLoads.get(count);
	}
	
	int getOldCapacity() {
		return oldCapacity.get(count);
	}
	
	int getNewCapacity() {
		return newCapacity.get(count);
	}
	
	void incrementCount() {		//used at the end of truckSelections to increment counter used to get added loads and old/new capacity
		count+=1;
	}
	
	boolean canAdd(int newLoad) {
		if((currentLoad+newLoad)<=maxLoad) {
			return true;
		}else {
			return false;
		}
	}
	
	public void addLoad(int newLoad) {
		addedLoads.add(newLoad);			//logs new load
		oldCapacity.add(loadCapability);	//logs capacity before new load is added
		
		currentLoad += newLoad;
		loadCapability = maxLoad-currentLoad;
		if(currentLoad >= 0.9*(double)maxLoad) {
			available = false; 
		}
		
		newCapacity.add(loadCapability);	//logs capacity after new load is added
	}
	
	public String toString() {
		String line = "state = " + state + " num = " + num + " destination = " + destination + " maxLoad = " + maxLoad + " currentLoad = " + currentLoad;
		return line;
	}
	
}
