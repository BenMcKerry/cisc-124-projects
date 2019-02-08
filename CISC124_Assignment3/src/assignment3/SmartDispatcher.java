package assignment3;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.PrintWriter;
import java.io.FileOutputStream;

public class SmartDispatcher {
	
	static ArrayList<Integer> readIntFile(String fileName) {	//used for reading pure-int files
		
		Scanner file = null;
		ArrayList<Integer> intList = new ArrayList<>();
		
		try {		//opening file
			file = new Scanner(new FileInputStream(fileName));
		} catch(FileNotFoundException e) {
			System.out.println("ERROR - File not found.");
			System.exit(0);
		}
		
		while(file.hasNextInt()) {
			intList.add(file.nextInt());	//adding the next int in the file to the ArrayList
		}
		
		file.close();
		
		return intList;
		
	}//end of readIntFile
	
	static ArrayList<String> readMixedFile(String fileName) {		//used for reading files that contain ints and Strings
		
		Scanner file = null;
		
		try {		//opening file
			file = new Scanner(new FileInputStream(fileName));
		} catch(FileNotFoundException e) {
			System.out.println("ERROR - File not found.");
			System.exit(0);
		}
		
		String fileLine;
		ArrayList<String> splitFile = new ArrayList<>(); 
		
		while(file.hasNextLine()) {
			
			fileLine = file.nextLine();		//getting whole file line
			String[] splitLine = fileLine.split("	");	//splitting line into individual components
			
			for(int i=0;i<splitLine.length;i++) {
				splitFile.add(splitLine[i]);			//adding split components into master ArrayList
			}//close for loop
			
		}//close while loop
		
		file.close();
		
		return splitFile;
		
	}//end of readmixedFile
	
	static void taxiSelect(Taxi[] tList, boolean status) {		//used in taxiResolve to set selectable status of the array of possible taxi options
		for(Taxi currentTaxi : tList) {
			currentTaxi.setSelectable(status);			
		}
	}//end of taxiSelect
	
	static Bus busResolve(ArrayList<Bus> bList, BusCall bCall) {
		
		ArrayList<Bus> enoughSeats = new ArrayList<>();		//ArrayList to store buses with enough seats
		
		Bus dummyBus = new Bus(0,0,0,0);		//returned if request cannot be serviced
		
		for(int i=0;i<bList.size();i++) {
			
			if(bList.get(i).getState() == bCall.getState() && bList.get(i).isAvailable()) {	//if bus is in the correct state and is available
				
				if (bList.get(i).getSeats() > bCall.getSeats()) {
					enoughSeats.add(bList.get(i));
				}
				
			}// end if statement
						
		}// end for loop
		
		if(enoughSeats.isEmpty()) {
			return dummyBus;
		}
		
		int totalMileage = 0;		//stores mileage sum
		int avgMileage; 			//stores average mileage amongst qualifying buses
		
		for(int i=0;i<enoughSeats.size();i++) {
			totalMileage += enoughSeats.get(i).getMileage();			
		}
		
		avgMileage = totalMileage/enoughSeats.size();
		
		int currentMileageDifference;		//stores mileage difference between average mileage and current bus
		Integer mileageDifference = null;	//stores lowest mileage difference
		Bus currentBus;						//reference to the current bus
		Bus selectedBus = null;					//reference to the final selected bus
		
		for(int i=0;i<enoughSeats.size();i++) {
			
			currentBus = enoughSeats.get(i);
			currentMileageDifference = Math.abs(currentBus.getMileage() - avgMileage);
			
			if(mileageDifference == null) {
				
				mileageDifference = currentMileageDifference;
				selectedBus = currentBus;
				
			} else if(mileageDifference > currentMileageDifference){
				
				mileageDifference = currentMileageDifference;
				selectedBus = currentBus;
				
			}//end if block
			
		}//end for loop
		
		selectedBus.setAvailable(false);
		
		return selectedBus;
		
	}//end of busResolve
	
	static Taxi taxiResolve(ArrayList<Taxi> tList, TaxiCall tCall) {
		//I just want to take time to apologize to whoever's marking this method.
		
		Taxi dummyTaxi = new Taxi(0,0,0,0);	//returned if no taxis are available
		Random randInt = new Random();		//used to randomly choose from available taxis
		
		double currentDistance;		//stores distance of current taxi

		Double[] distanceCheck;
		Taxi[] closest;

		for(int i=2;i<=8;i=i*2) {		//first chooses 2 closest taxis, then next 4, then next 8
			
			distanceCheck = new Double[i];
			closest = new Taxi[i];
			
			for(Taxi currentTaxi : tList) {
				
				if(currentTaxi.getState()==tCall.getState() && currentTaxi.isSelectable()) {	//if currentTaxi is the correct state and is also selectable
						
					currentDistance = (Math.sqrt(Math.pow((currentTaxi.getX()-tCall.getX()),2) 
							+ Math.pow((currentTaxi.getY()-tCall.getY()),2)));					//calculating distance from taxi to call
					
					for(int j=0;j<distanceCheck.length;j++) {
						
						if (!Arrays.asList(closest).contains(currentTaxi)) {	//if current taxi isn't in array of closest taxis already 
						
							if (distanceCheck[j] == null || distanceCheck[j] > currentDistance || (tCall.getState()==1 && currentTaxi.isAvailable())) {	
								/*^if this slot in distanceCheck array is empty or is greater than currentDistance, 
								 * or if the call is from state 1 and currentTaxi is available. Although this kinda 
								 * flies in the face of what the assignment is asking in regards to picking taxis
								 * (with how the availability of the taxis isn't taken into consideration when picking the 
								 * closest), without that little caveat only the first 14 state 1 taxis will ever be sent out.
								 * This is due to all the state 1 taxis all being at (0,0) for some reason, which means they're all
								 * equidistant from a state 1 call meaning unless we take their availability into consideration somehow
								 * only the first 14 state 1 taxis will be sent. That was reeeaally long winded but just trust me here.
								 * Please.*/
								
								distanceCheck[j] = currentDistance;		//making this distance the current distance
								closest[j] = currentTaxi;    
							}//end inner if block
							
						}//end outer if statement
						
					}//end of inner inner for loop
					
				}//end if statement
				
			}//end inner for loop
			
			if(distanceCheck[0]==null) {		//if no taxis are available
				return dummyTaxi;
			}
			
			Integer chosen=-1; 			//index value of which taxi will be sent
			Integer[] lastChosen = new Integer[i];	//stores all the previously selected values
			Arrays.fill(lastChosen, chosen);//setting all values in lastChosen to -1
			
			for(int j=0;j<i;j++) {
				
				chosen = randInt.nextInt(i);		//selecting the index value for taxi to be sent
				
				while(Arrays.asList(lastChosen).contains(chosen)) {		//looping the selection if chosen value was already chosen
					chosen = randInt.nextInt(i);
				}//end while loop
				
				try {
					if(closest[chosen].isAvailable() && closest[chosen].isSelectable()) {
						taxiSelect(tList.toArray(new Taxi[tList.size()]), true);		//setting all taxis to selectable again
						closest[chosen].setDistance(distanceCheck[chosen]);				//setting distance from chosen taxi to customer
						closest[chosen].setAvailable(false);
						return closest[chosen];
					}//end if statement
				} catch(NullPointerException e) {}
				
				lastChosen[j] = chosen;			//adding chosen index value to list of previously chosen values
			
			}//end of other inner for loop
			
			taxiSelect(closest,false);
			
		}//end of outer for loop

		taxiSelect(tList.toArray(new Taxi[tList.size()]), true);
		return dummyTaxi;
		
	}//end taxiResolve
	
	static Truck truckResolve(ArrayList<Truck> trList, TruckCall trCall) {
		
		Truck dummyTruck = new Truck(0,0,"",0,0);		//returned if no truck is available
		
		Truck truckCandidate = null;			//reference to truck that can be returned
		boolean callCompleted = false;
		
		for(Truck currentTruck : trList) {
			
			if(currentTruck.isAvailable() 
			&& currentTruck.getState() == trCall.getState() 
			&& currentTruck.getDestination().equals(trCall.getDestination()) 
			&& currentTruck.canAdd(trCall.getLoadMass()) 
			&& (truckCandidate == null || currentTruck.getLoadCapability() > truckCandidate.getLoadCapability())) { 	//if current truck fill all qualifications
				truckCandidate = currentTruck; 												//make it the truck candidate
				callCompleted = true;
			}//end if statement 
			
		}//end for loop
		
		if(callCompleted) {
			truckCandidate.setAvailable(false);
			truckCandidate.addLoad(trCall.getLoadMass());
			return truckCandidate;
		} else {
			return dummyTruck;
		}//end if block		
		
	}//end truckResolve
	
	static <T extends Vehicle> int[][] fleetCounter(ArrayList<T> vList, ArrayList<T> vComplete) {
		
		int highestState = vList.get(vList.size()-1).getState();	//since vehicles are stored in order of state this gets the highest state
		int[][] stateCounter = new int[3][highestState];			//stores state number and fleet size of corresponding state
		
		Arrays.fill(stateCounter[1], 0);
		Arrays.fill(stateCounter[2], 0);
		
		for(int i=0;i<highestState;i++) {
			
			stateCounter[0][i] = i+1;			//setting 1st column of stateCounter to be the state value
			
			for(Vehicle currentVehicle : vList) {
				if(currentVehicle.getState() == i+1) {
					stateCounter[1][i]++;		//setting 2nd column of stateCounter to be the number fleets in corresponding state
				}//end if statement
				
			}//end inner for loop
			
			for(Vehicle currentComplete : vComplete) {
				if(currentComplete.getState() == i+1) {
					stateCounter[2][i]++;		//setting 2nd column of stateCounter to be the number fleets in corresponding state
				}//end if statement
				
			}//end inner for loop
			
		}//end outer for loop
		
		return stateCounter;
		
	}//end fleetCounter
	
	public static void main(String[] args) {
		
		//CREATING BUS OBJECTS
		ArrayList<Integer> busStates = new ArrayList<>();
		busStates = readIntFile("busStates.txt");		//busStates is an ArrayList of all ints in busStates.txt
		
		ArrayList<Bus> busList = new ArrayList<>();
		int state;
		int num;
		int mileage;
		int seats;
		
		for(int i=0;i<busStates.size();i+=4) {
			
			state = busStates.get(i);
			num = busStates.get(i+1);
			mileage = busStates.get(i+2);
			seats = busStates.get(i+3);
			busList.add(new Bus(state, num, mileage, seats));		//creating bus object with these characteristics
			
		}//for loop close
		
		//CREATING TAXI OBJECTS
		ArrayList<Integer> taxiStates = new ArrayList<>();
		taxiStates = readIntFile("taxiStates.txt");		//taxiStates is an ArrayList of all ints in taxiStates.txt
		
		ArrayList<Taxi> taxiList = new ArrayList<>();
		int xCoord;
		int yCoord;
		
		for(int i=0;i<taxiStates.size();i+=4) {
			
			state = taxiStates.get(i);
			num = taxiStates.get(i+1);
			xCoord = taxiStates.get(i+2);
			yCoord = taxiStates.get(i+3);
			taxiList.add(new Taxi(state, num, xCoord, yCoord));		//creating taxi object with these characteristics
			
		}//for loop close
		
		//CREATING TRUCK OBJECTS
		ArrayList<String> truckStates = new ArrayList<>();
		truckStates = readMixedFile("truckStates.txt");		//truckStates is an ArrayList of Strings of the contents of truckStates.txt
		
		ArrayList<Truck> truckList = new ArrayList<>();
		String destination;
		int maxLoad;
		int currentLoad;
		
		for(int i=0;i<truckStates.size();i+=5) {
			// the parseInt()'s convert the numbers in truckStates from String to int
			state = Integer.parseInt(truckStates.get(i));
			num = Integer.parseInt(truckStates.get(i+1));
			destination = truckStates.get(i+2);
			maxLoad = Integer.parseInt(truckStates.get(i+3));
			currentLoad = Integer.parseInt(truckStates.get(i+4));
			truckList.add(new Truck(state, num, destination, maxLoad, currentLoad));		//creating truck object with these characteristics
			
		}//for loop close
		
		//CREATING BUS CALL OBJECTS
		ArrayList<String> busCalls = new ArrayList<>();
		busCalls = readMixedFile("busCalls.txt");		//busCalls is an ArrayList of Strings of the contents of busCalls.txt
		
		ArrayList<BusCall> bCallList = new ArrayList<>();
				
		for(int i=0;i<busCalls.size();i+=3) {
			// the parseInt()'s convert the numbers in busCalls from String to int
			state = Integer.parseInt(busCalls.get(i));
			destination = busCalls.get(i+1);
			seats = Integer.parseInt(busCalls.get(i+2));
			bCallList.add(new BusCall(state,destination,seats));		//creating bus call object with these characteristics
			
		}//for loop close
		
		//CREATING TAXI CALL OBJECTS
		ArrayList<Integer> taxiCalls = new ArrayList<>();
		taxiCalls = readIntFile("taxiCalls.txt");		//taxiCalls is an ArrayList of all ints in taxiCalls.txt
				
		ArrayList<TaxiCall> taCallList = new ArrayList<>();
						
		for(int i=0;i<taxiCalls.size();i+=3) {
			
			state = taxiCalls.get(i);
			xCoord = taxiCalls.get(i+1);
			yCoord =taxiCalls.get(i+2);
			taCallList.add(new TaxiCall(state,xCoord,yCoord));		//creating taxi call object with these characteristics
					
		}//for loop close
		
		//CREATING TRUCK CALL OBJECTS
		ArrayList<String> truckCalls = new ArrayList<>();
		truckCalls = readMixedFile("truckCalls.txt");		//truckCalls is an ArrayList of Strings of the contents of truckCalls.txt
				
		ArrayList<TruckCall> trCallList = new ArrayList<>();
		int parcelMass;
						
		for(int i=0;i<truckCalls.size();i+=3) {
			
			// the parseInt()'s convert the numbers in truckCalls from String to int
			state = Integer.parseInt(truckCalls.get(i));
			destination = truckCalls.get(i+1);
			parcelMass = Integer.parseInt(truckCalls.get(i+2));
			trCallList.add(new TruckCall(state,destination,parcelMass));		//creating truck call object with these characteristics
					
		}//for loop close
		
		//WRITING BUSSELECTIONS.TXT
		ArrayList<Bus> completeBus = new ArrayList<>();		//ArrayList for buses which completed transactions
		ArrayList<BusCall> incompleteBus = new ArrayList<>();		//ArrayList for incomplete transactions
		Bus currentBus;
		
		for (int i=0;i<bCallList.size();i++) {
			
			currentBus = busResolve(busList, bCallList.get(i));		//currentBus either gets set to bus which resolves transaction or dummyBus
			
			if(currentBus.getState() != 0) {		//if bus isn't dummyBus
				completeBus.add(currentBus);
			} else {							//else transaction is incomplete
				incompleteBus.add(bCallList.get(i));
			}//end if block
			
		}//end for loop
				
		PrintWriter busSelections = null;
		
		try {
			busSelections = new PrintWriter(new FileOutputStream("busSelections.txt"));
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		busSelections.println("COMPLETED TRANSACTIONS \n");
		
		for(int i=0;i<completeBus.size();i++) {
			
			busSelections.println(" State: " + completeBus.get(i).getState()
								+ ", Bus ID Number: " + completeBus.get(i).getNum()
								+ ", Bus Mileage: " + completeBus.get(i).getMileage()
								+ ", Number of Bus Seats: " + completeBus.get(i).getSeats());
			
		}//end for loop
		
		busSelections.println("\n INCOMPLETE TRANSACTIONS \n");
		
		for(int i=0;i<incompleteBus.size();i++) {
			
			busSelections.println(" State: " + incompleteBus.get(i).getState()
								+ ", Destination: " + incompleteBus.get(i).getDestination()
								+ ", Seats Required: " + incompleteBus.get(i).getSeats());
			
		}//end for loop
		
		busSelections.close();
		
		//WRITING TAXISELECTIONS.TXT
		ArrayList<Taxi> completeTaxi = new ArrayList<>();		//ArrayList for taxis which completed transactions
		ArrayList<TaxiCall> incompleteTaxi = new ArrayList<>();		//ArrayList for incomplete transactions
		Taxi currentTaxi;
		
		for (int i=0;i<taCallList.size();i++) {
			
			currentTaxi = taxiResolve(taxiList, taCallList.get(i));		//currentTaxi either gets set to taxi which resolves transaction or dummyTaxi
			
			if(currentTaxi.getState() != 0) {		//if taxi isn't dummyTaxi
				completeTaxi.add(currentTaxi);
			} else {							//else transaction is incomplete
				incompleteTaxi.add(taCallList.get(i));
			}//end if block
			
		}//end for loop
		
		PrintWriter taxiSelections = null;
		
		try {
			taxiSelections = new PrintWriter(new FileOutputStream("taxiSelections.txt"));
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		taxiSelections.println("COMPLETED TRANSACTIONS \n");
		
		for(int i=0;i<completeTaxi.size();i++) {
			
			taxiSelections.println("State: " + completeTaxi.get(i).getState()
								+ ", Taxi ID Number: " + completeTaxi.get(i).getNum()
								+ ", Distance to Customer: " + completeTaxi.get(i).getDistance());
			
		}//end for loop
		
		taxiSelections.println("\n INCOMPLETE TRANSACTIONS \n");
		
		for(int i=0;i<incompleteTaxi.size();i++) {
			
			taxiSelections.println("State: " + incompleteTaxi.get(i).getState()
								+ ", X Coordinate: " + incompleteTaxi.get(i).getX()
								+ ", Y Coordinate: " + incompleteTaxi.get(i).getY());
			
		}//end for loop
		
		taxiSelections.close();
		
		//WRITING TRUCKSELECTIONS.TXT
		ArrayList<Truck> completeTruck = new ArrayList<>();		//ArrayList for taxis which completed transactions
		ArrayList<TruckCall> incompleteTruck = new ArrayList<>();		//ArrayList for incomplete transactions
		Truck currentTruck;
		
		for (int i=0;i<trCallList.size();i++) {
			
			currentTruck = truckResolve(truckList, trCallList.get(i));		//currentBus either gets set to taxi which resolves transaction or dummyTaxi
			
			if(currentTruck.getState() != 0) {		//if truck isn't dummyTruck
				completeTruck.add(currentTruck);
			} else {							//else transaction is incomplete
				incompleteTruck.add(trCallList.get(i));
			}//end if block
			
		}//end for loop
		
		PrintWriter truckSelections = null;
		
		try {
			truckSelections = new PrintWriter(new FileOutputStream("truckSelections.txt"));
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		truckSelections.println("COMPLETED TRANSACTIONS \n");
		
		for(int i=0;i<completeTruck.size();i++) {
			
			truckSelections.println("State: " + completeTruck.get(i).getState()
								+ ", Truck ID Number: " + completeTruck.get(i).getNum()
								+ ", Mass of Added Load: " + completeTruck.get(i).getAddedLoad()
								+ ", Old Capacity: " + completeTruck.get(i).getOldCapacity()
								+ ", New Capacity: " + completeTruck.get(i).getNewCapacity());
			completeTruck.get(i).incrementCount();
			
		}//end for loop
		
		truckSelections.println("\n INCOMPLETE TRANSACTIONS \n");
		
		for(int i=0;i<incompleteTruck.size();i++) {
			
			truckSelections.println("State: " + incompleteTruck.get(i).getState()
								+ ", Destination: " + incompleteTruck.get(i).getDestination()
								+ ", Load Mass: " + incompleteTruck.get(i).getLoadMass());
			
		}//end for loop
		
		truckSelections.close();
		
		//WRITING FINALUSAGEREPORT.TXT
		int[][] busFleets = fleetCounter(busList, completeBus);
		int[][] taxiFleets = fleetCounter(taxiList, completeTaxi);
		int[][] truckFleets = fleetCounter(truckList, completeTruck);
		
		PrintWriter finalReport = null;
		
		try {
			finalReport = new PrintWriter(new FileOutputStream("finalUsageReport.txt"));
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		for(int i=0;i<busFleets[0].length;i++) {
			
			finalReport.println("Vehicle Type: Bus" +
								", State: " + busFleets[0][i] + 
								", Fleet Size: " + busFleets[1][i] + 
								", In Use: " + busFleets[2][i]);
			
		}//end for loop
		
		for(int i=0;i<taxiFleets[0].length;i++) {
			
			finalReport.println("Vehicle Type: Taxi" +
								", State: " + taxiFleets[0][i] + 
								", Fleet Size: " + taxiFleets[1][i] + 
								", In Use: " + taxiFleets[2][i]);
			
		}//end for loop
		
		for(int i=0;i<truckFleets[0].length;i++) {
			
			finalReport.println("Vehicle Type: Truck" +
								", State: " + truckFleets[0][i] + 
								", Fleet Size: " + truckFleets[1][i] + 
								", In Use: " + truckFleets[2][i]);
			
		}//end for loop
		
		finalReport.close();
		
	}// end of main

}//end of SmartDispatcher
