package assignment3;

public class Vehicle {
	
	int state;
	int num;
	boolean available;
	
	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	int getState() {
		return state;
	}
	
	int getNum() {
		return num;
	}
	
	void setState(int newState) {
		state = newState;
	}
	
	void setNum(int newNum) {
		num = newNum;
	}

}
