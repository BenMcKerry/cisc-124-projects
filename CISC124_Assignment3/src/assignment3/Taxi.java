package assignment3;

public class Taxi extends Vehicle {
	
	int xCoord;
	int yCoord;
	boolean selectable;

	double distance;

	Taxi(int taxiState, int taxiNum, int taxiX, int taxiY) {
		state = taxiState;
		num = taxiNum;
		xCoord = taxiX;
		yCoord = taxiY;
		available = true;
		selectable = true;
	}
	
	int getX() {
		return xCoord;
	}
	
	int getY() {
		return yCoord;
	}
	
	void setX(int x) {
		xCoord = x;
	}
	
	void setY(int y) {
		yCoord = y;
	}
	
	boolean isSelectable() {
		return selectable;
	}

	void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}
	
	double getDistance() {
		return distance;
	}

	void setDistance(double distance) {
		this.distance = distance;
	}
	
	public String toString() {
		return ("State: " +state+ " ID: "+num + " Available: " + available);
	}
	
}
