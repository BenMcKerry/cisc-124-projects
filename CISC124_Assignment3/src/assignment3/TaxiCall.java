package assignment3;


public class TaxiCall extends Calls{
	
	int X;
	int Y;
	
	TaxiCall(int state, int x, int y) {
		
		this.state = state;
		X = x;
		Y = y;
		
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

}
