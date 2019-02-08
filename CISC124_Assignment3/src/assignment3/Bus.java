package assignment3;

public class Bus extends Vehicle {

	int seats;
	int mileage;
	
	Bus(int busState, int busNum, int busMileage, int busSeat) {
		state = busState;
		num = busNum;
		mileage = busMileage;
		seats = busSeat;
		available = true;
	}
	
	
	int getSeats() {
		return seats;
	}
	
	int getMileage() {
		return mileage;
	}
	
	void setSeats(int newSeats) {
		seats = newSeats;
	}
	
	void setMileage(int newMileage) {
		mileage = newMileage;
	}
	
	public String toString() {
		String line = "state = " + state + " num = " + num + " mileage = " + mileage + "seats = " + seats  +"\n";
		return line;
	}
	
}
