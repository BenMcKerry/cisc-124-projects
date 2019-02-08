/*Author: Ben McKerry
 * NetID: 17BDM1
 * Student Number: 20073091*/

import java.util.Scanner;
import java.util.*;
import java.util.Random;

public class GradeCalculator {
	
	public static void main(String[] args) {
		
		Random randInt = new Random();
		
		ArrayList<Students> classList = new ArrayList<Students>();
		Scanner userInput = new Scanner(System.in);
		boolean run = true;
		
		int assignment1Weight = 0;
		int assignment2Weight = 0;
		int finalExamWeight = 0;
		
		do {
			System.out.println();
			System.out.println("Grade Calculator (Version 0.1). Author: Ben McKerry");
			System.out.println("1 - Simulate Course Marks");
			System.out.println("2 - View/Update Student Marks");
			System.out.println("3 - Run Mark Statistics");
			System.out.print("Select Option [1, 2, or 3] (9 to quit): ");

			boolean optionError = true;
			int option = 0;

			while (optionError) {
				try {
					option = userInput.nextInt();
					if (option == 1 || option == 2 || option == 3 || option == 9) {
						optionError = false;
					} else {
						System.out.println("ERROR - Please select option 1, 2, or 3 or press 9 to quit.");
					}
				} catch (Exception e) {
					System.out.println("ERROR - Please select option 1, 2, or 3 or press 9 to quit.");
					userInput.nextLine();
				}

			} // optionError close

			if (option == 1) {
				
				if (classList.size() > 0) {
					for (int i=0;i<classList.size();i++) {
						classList.get(i).invalidateStudent();
					}
				}
				
				System.out.println();
				System.out.print("Enter the size of the class you want to simulate: ");

				boolean classError = true;
				int classSize = 0;

				while (classError) {
					try {
						classSize = userInput.nextInt();
						if (classSize > 0) {
							classError = false;
						} else {
							System.out.println("ERROR - Please enter a class size greater than 0");
						}
					} catch (Exception e) {
						System.out.println("ERROR - Please enter the size of the class as an integer");
						userInput.nextLine();
					}

				}//classError close
				
				System.out.println();
				System.out.print("Enter the weight of assignment 1 (20-30% without the percent sign): ");

				boolean assignment1Error = true;
				
				while (assignment1Error) {
					try {
						assignment1Weight = userInput.nextInt();
						if (assignment1Weight >= 20 && assignment1Weight <= 30) {
							assignment1Error = false;
						} else {
							System.out.println("ERROR - Please enter value between 20-30%");
						}
					} catch (Exception e) {
						System.out.println("ERROR - Please the weight of assignment 1 as an integer");
						userInput.nextLine();
					}

				}//assignment1Error close
				
				System.out.println();
				System.out.print("Enter the weight of assignment 2 (20-30% without the percent sign): ");

				boolean assignment2Error = true;
				
				while (assignment2Error) {
					try {
						assignment2Weight = userInput.nextInt();
						if (assignment2Weight >= 20 && assignment2Weight <= 30) {
							assignment2Error = false;
						} else {
							System.out.println("ERROR - Please enter value between 20-30%");
						}
					} catch (Exception e) {
						System.out.println("ERROR - Please the weight of assignment 2 as an integer");
						userInput.nextLine();
					}

				}//assignment2Error close
				
				System.out.println();
				System.out.print("Enter the weight of the final exam (40-60% without the percent sign): ");

				boolean examError = true;
				
				while (examError) {
					try {
						finalExamWeight = userInput.nextInt();
						if (finalExamWeight >= 40 && finalExamWeight <= 60) {
							examError = false;
						} else {
							System.out.println("ERROR - Please enter value between 40-60%");
						}
					} catch (Exception e) {
						System.out.println("ERROR - Please the weight of the exam as an integer");
						userInput.nextLine();
					}

				}//examError close
				
				if ((assignment1Weight + assignment2Weight + finalExamWeight) == 100) {
										
					int studentNumber = 0;
					
					for (int i = 0;i<classSize;i++) {
						studentNumber = randInt.nextInt(90000000) + 10000000;
						generateStudentMarks(studentNumber,classList);					
					}
										
				} else {
					System.out.println("ERROR - Weights do not add up to 100%. Please restart.");
				}
			}//option 1 close
			
			else if (option  == 2) {
				
				int numInput = 00000000;
				boolean numError = true;
				boolean VUError = true;
				String viewUpdate = "";
				
				System.out.print("Enter the student number of the student you want to view/update: ");
				
				while (numError) {
					try {
						numInput = userInput.nextInt();
						if (numInput > 10000000 && numInput < 99999999) {
							numError = false;
						} else {
							System.out.println("ERROR - enter a student number");
						}
					} catch (Exception e) {
						System.out.println("ERROR - enter a student number");
						userInput.nextLine();
					}
				}//numError close
				
				boolean studentIsValid = false;
				
				for (int i = 0;i < classList.size();i++) {
					if (numInput == classList.get(i).getStudentNumber() && classList.get(i).getStudentStatus()) {
						
						studentIsValid = true;
						
						System.out.print("Student found - View or Update? (V/U): ");
						
						while (VUError) {
							try {
								
								viewUpdate = userInput.next();
								if (viewUpdate.toUpperCase().equals("V")  || viewUpdate.toUpperCase().equals("U")) {
									VUError = false;
								} else {
									System.out.println("ERROR - Please enter V for view or U for update");
								}
							} catch (Exception e) {
								System.out.println("ERROR - Please enter V for view or U for update");
								userInput.nextLine();
							}

						}//VUError close
						
						if (viewUpdate.toUpperCase().equals("V")) {
							System.out.println("The student's assignment 1 mark is " + classList.get(i).getMark("A1")+"%");
							System.out.println("The student's assignment 2 mark is " + classList.get(i).getMark("A2")+"%");
							System.out.println("The student's final exam mark is    " + classList.get(i).getMark("FE")+"%");
						} else { //ViewUpdate == U
							VUError = true;
							String markType = "";
							System.out.print("What mark type do you want to update? (A1, A2, or FE): ");
							
							while (VUError) {
								try {
									markType = userInput.next();
									if (markType.toUpperCase().equals("A1") ||markType.toUpperCase().equals("A2") || markType.toUpperCase().equals("FE")) {
										VUError = false;
									} else {
										System.out.println("ERROR - Please enter either A1, A2, or A3");
									}
								} catch (Exception e) {
									System.out.println("ERROR - Please enter either A1, A2, or A3");
									userInput.nextLine();
								}
							} // VUError close

							double newMark = 0;

							System.out.println("The student's " + markType + " mark is " + classList.get(i).getMark(markType) + "%");
							System.out.print("Enter the updated mark (0-100): ");

							try {
								newMark = userInput.nextDouble();
								if (newMark >= 0 && newMark <= 100) {
									classList.get(i).updateMark(markType,newMark);
								} else {
									System.out.println("ERROR - Entered mark was not between 0-100");
								}
							} catch (Exception e) {
								System.out.println("ERROR - Mark was not entered");
							}

						}//update close
							
					} 
					
				}// student number 'for' loop close
				
				if (!studentIsValid) {
					System.out.println("ERROR - " + numInput + " is invalid.");
				}
				
			}//option 2 close
			else if (option == 3) {
					
				printClassReport(classList, assignment1Weight, assignment2Weight, finalExamWeight);
				
				/*System.out.println("");
				System.out.print("Student Number          ");
				
				if (assignment1Weight >= 20) {
					System.out.print("A1(" + assignment1Weight + "%)          ");
				} else {
					System.out.print("A1([]%)          ");
				}
				
				if (assignment2Weight >= 20) {
					System.out.print("A2(" + assignment2Weight + "%)          ");
				} else {
					System.out.print("A2([]%)          ");
				}
				
				if (finalExamWeight >= 40) {
					System.out.print("FE(" + finalExamWeight + "%)          ");
				} else {
					System.out.print("FE([]%)          ");
				}
				
				System.out.println("Final Mark");
				
				System.out.println("**************          *******          *******          *******          **********");
					
				double[] finalMarks = new double[classList.size()];		//storing all final marks in an array
				for (int i = 0; i < classList.size();i++) {
					if (classList.get(i).getStudentStatus()) {
						finalMarks[i] = computeStudentGrade(classList.get(i),assignment1Weight,assignment2Weight,finalExamWeight);
					}
				}
				
				double sumA1Mark = 0;
				double sumA2Mark = 0;
				double sumExamMark = 0;
				double sumFinalMark = 0;
				
				double avgA1Mark = 0;
				double avgA2Mark = 0;
				double avgExamMark = 0;
				double avgFinalMark = 0;
				
				double highMark = 0;
				double lowMark = 100;
				
				for (int i = 0; i < classList.size();i++) {			//finding average assignment 1 mark
					if (classList.get(i).getStudentStatus()) {
						sumA1Mark = sumA1Mark + classList.get(i).getMark("A1");
					}
				}
				avgA1Mark = (sumA1Mark/classList.size());
				
				for (int i = 0; i < classList.size();i++) {			//finding average assignment 2 mark
					if (classList.get(i).getStudentStatus()) {
						sumA2Mark = sumA2Mark + classList.get(i).getMark("A2");
					}
				}
				avgA2Mark = (sumA2Mark/classList.size());
				
				for (int i = 0; i < classList.size();i++) {			//finding average exam mark
					if (classList.get(i).getStudentStatus()) {
						sumExamMark = sumExamMark + classList.get(i).getMark("FE");
					}
				}
				avgExamMark = (sumExamMark/classList.size());
				
				for (int i = 0; i < finalMarks.length;i++) {			//finding average final mark
					if (classList.get(i).getStudentStatus()) {
						sumFinalMark += finalMarks[i]; 
					}
				}
				avgFinalMark = (sumFinalMark/finalMarks.length);
				
				for (int i = 0; i < finalMarks.length;i++) {			//finding highest mark
					if (classList.get(i).getStudentStatus() && finalMarks[i] > highMark) {
						highMark = finalMarks[i];
					}
				}
				
				for (int i = 0; i < finalMarks.length;i++) {			//finding lowest mark
					if (classList.get(i).getStudentStatus() && finalMarks[i] < lowMark) {
						lowMark = finalMarks[i];
					}
				}
								
				for (int i = 0; i < classList.size();i++) {
					if (classList.get(i).getStudentStatus()) {
						System.out.printf("%-26d%-17.2f%-17.2f%-17.2f%.2f%n",classList.get(i).getStudentNumber(),classList.get(i).getMark("A1"),classList.get(i).getMark("A2"),classList.get(i).getMark("FE"),finalMarks[i]);
					}
				}
				
				System.out.println("**************          *******          *******          *******          **********");
				
				System.out.printf("%-26s%-17.2f%-17.2f%-17.2f%.2f%n","Averages:",avgA1Mark,avgA2Mark,avgExamMark,avgFinalMark);
				
				System.out.printf("%s%.2f%s%n","The highest final mark is ", highMark , "%.");
				System.out.printf("%s%.2f%s%n","The lowest final mark is ", lowMark , "%.");
				System.out.println("");

				Students.pressEnter(); // the 'press enter to continue' method
*/				
			}//option 3 close
			
			else { //option == 9
				run = false;
				System.out.println("Program will exit.");
			}
			
		} while (run);

	}//end of main
	
	private static void generateStudentMarks(int studentNum, ArrayList<Students> classList) {
		classList.add(new Students(studentNum));
	}
	
	private static double computeStudentGrade(Students student, int A1Weight, int A2Weight, int FEWeight) { //calculates student's final grade in course
		double weightedA1 = student.getMark("A1")*(A1Weight/100.0);
		double weightedA2 = student.getMark("A2")*(A2Weight/100.0);
		double weightedFE = student.getMark("FE")*(FEWeight/100.0);
		
		double finalMark = weightedA1 + weightedA2 + weightedFE;
		
		return finalMark;
		 
	}
	
	private static void printClassReport(ArrayList<Students> classList, int assignment1Weight, int assignment2Weight, int finalExamWeight) {
		System.out.println("");
		System.out.print("Student Number          ");
		
		if (assignment1Weight >= 20) {
			System.out.print("A1(" + assignment1Weight + "%)          ");
		} else {
			System.out.print("A1([]%)          ");
		}
		
		if (assignment2Weight >= 20) {
			System.out.print("A2(" + assignment2Weight + "%)          ");
		} else {
			System.out.print("A2([]%)          ");
		}
		
		if (finalExamWeight >= 40) {
			System.out.print("FE(" + finalExamWeight + "%)          ");
		} else {
			System.out.print("FE([]%)          ");
		}
		
		System.out.println("Final Mark");
		
		System.out.println("**************          *******          *******          *******          **********");
			
		double[] finalMarks = new double[classList.size()];		//storing all final marks in an array
		for (int i = 0; i < classList.size();i++) {
			if (classList.get(i).getStudentStatus()) {
				finalMarks[i] = computeStudentGrade(classList.get(i),assignment1Weight,assignment2Weight,finalExamWeight);
			}
		}
		
		double sumA1Mark = 0;
		double sumA2Mark = 0;
		double sumExamMark = 0;
		double sumFinalMark = 0;
		
		double avgA1Mark = 0;
		double avgA2Mark = 0;
		double avgExamMark = 0;
		double avgFinalMark = 0;
		
		double highMark = 0;
		double lowMark = 100;
		
		for (int i = 0; i < classList.size();i++) {			//finding average assignment 1 mark
			if (classList.get(i).getStudentStatus()) {
				sumA1Mark = sumA1Mark + classList.get(i).getMark("A1");
			}
		}
		avgA1Mark = (sumA1Mark/classList.size());
		
		for (int i = 0; i < classList.size();i++) {			//finding average assignment 2 mark
			if (classList.get(i).getStudentStatus()) {
				sumA2Mark = sumA2Mark + classList.get(i).getMark("A2");
			}
		}
		avgA2Mark = (sumA2Mark/classList.size());
		
		for (int i = 0; i < classList.size();i++) {			//finding average exam mark
			if (classList.get(i).getStudentStatus()) {
				sumExamMark = sumExamMark + classList.get(i).getMark("FE");
			}
		}
		avgExamMark = (sumExamMark/classList.size());
		
		for (int i = 0; i < finalMarks.length;i++) {			//finding average final mark
			if (classList.get(i).getStudentStatus()) {
				sumFinalMark += finalMarks[i]; 
			}
		}
		avgFinalMark = (sumFinalMark/finalMarks.length);
		
		for (int i = 0; i < finalMarks.length;i++) {			//finding highest mark
			if (classList.get(i).getStudentStatus() && finalMarks[i] > highMark) {
				highMark = finalMarks[i];
			}
		}
		
		for (int i = 0; i < finalMarks.length;i++) {			//finding lowest mark
			if (classList.get(i).getStudentStatus() && finalMarks[i] < lowMark) {
				lowMark = finalMarks[i];
			}
		}
						
		for (int i = 0; i < classList.size();i++) {
			if (classList.get(i).getStudentStatus()) {
				System.out.printf("%-26d%-17.2f%-17.2f%-17.2f%.2f%n",classList.get(i).getStudentNumber(),classList.get(i).getMark("A1"),classList.get(i).getMark("A2"),classList.get(i).getMark("FE"),finalMarks[i]);
			}
		}
		
		System.out.println("**************          *******          *******          *******          **********");
		
		System.out.printf("%-26s%-17.2f%-17.2f%-17.2f%.2f%n","Averages:",avgA1Mark,avgA2Mark,avgExamMark,avgFinalMark);
		
		System.out.printf("%s%.2f%s%n","The highest final mark is ", highMark , "%.");
		System.out.printf("%s%.2f%s%n","The lowest final mark is ", lowMark , "%.");
		System.out.println("");

		Students.pressEnter(); // the 'press enter to continue' method
	}
	
}


