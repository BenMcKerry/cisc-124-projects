/*Author: Ben McKerry
 * NetID: 17BDM1
 * Student Number: 20073091*/

import java.util.Random;

public class Students {
	
	boolean status;
	int studentNum;
	double assignment1Grade;
	double assignment2Grade;
	double examGrade;
	//double finalGrade;
	
	Students(int studentNumber) {											//constructor for student
		Random randInt = new Random();
		status = true;
		studentNum = studentNumber;
		//studentNum = randInt.nextInt(90000000) + 10000000;
		assignment1Grade = randInt.nextInt(100) + 1;
		assignment2Grade = randInt.nextInt(100) + 1;
		examGrade = randInt.nextInt(100) + 1;
/*		finalGrade=0; 
*/	}
	
	public double getMark(String markType) {
		
		if (markType.toUpperCase().equals("A1")) {
			return assignment1Grade;
		} else if (markType.toUpperCase().equals("A2")) {
			return assignment2Grade;
		} else if (markType.toUpperCase().equals("FE")) {
			return examGrade;
		}
		
		return -1;
	}
	
	/*double getFinalGrade() {
		return finalGrade;
	}*/
	
	int getStudentNumber() {									//gets student number
		return studentNum;
	}
	
	void setStudentNumber(int i) {
		if (i >= 10000000 && i <= 99999999) {
			studentNum = i;
		} else {
			System.out.println("ERROR - entered an invalid student number");
		}
	}
	
	void updateMark(String markType, double newMark) {

		if (markType.toUpperCase().equals("A1")) {
			assignment1Grade = newMark;
		} else if (markType.toUpperCase().equals("A2")) {
			assignment2Grade = newMark;
		} else if (markType.toUpperCase().equals("FE")) {
			examGrade = newMark;
		}

	}
	
	/*void setFinalGrade(int A1Weight, int A2Weight,int FEWeight) {
		finalGrade = ((A1Weight/100.0)*assignment1Grade + (A2Weight/100.0)*assignment2Grade + (FEWeight/100.0)*examGrade);
	}*/
	
	boolean getStudentStatus() {
		return status;
	}
	
	void invalidateStudent( ) {								//invalidates student
		status = false;
	}
	
	static void pressEnter() {										//pauses program until user presses Enter
		System.out.println();
		System.out.println("Press Enter to continue.");
		try {
			System.in.read();
		} catch(Exception e) {
			
		}
		
	}
}