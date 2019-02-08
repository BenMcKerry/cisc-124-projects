package assignment3;

public class TruckCall extends Calls{
	
	int loadMass;
	
	TruckCall(int state, String destination, int loadMass) {
		
		this.state = state;
		this.destination = destination;
		this.loadMass =loadMass;
		
	}

	public int getLoadMass() {
		return loadMass;
	}

	public void setLoadMass(int loadMass) {
		this.loadMass = loadMass;
	}
	
	public String toString() {
		return ("state: "+state + " destination: " + destination + " loadMass: "+loadMass);
	}

}
