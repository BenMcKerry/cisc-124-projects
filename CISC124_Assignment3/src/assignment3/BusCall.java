package assignment3;

public class BusCall extends Calls{

	int seats;
	
	BusCall(int bCallState, String bDestination, int bSeats) {
		
		state = bCallState;
		destination = bDestination;
		seats = bSeats;
	}

	int getSeats() {
		return seats;
	}

	void setSeats(int seats) {
		this.seats = seats;
	}
	
}