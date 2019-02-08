package assignment2;

import java.util.Scanner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.PrintWriter;
import java.io.FileOutputStream;

import java.util.*;

public class Classifier {

	public static void main(String args[]) {
		
		ArrayList<Category> categories = new ArrayList<Category>();			//creating arraylist for category objects
		ArrayList<Song> songs = new ArrayList<Song>();						//creating arraylist for song objects
		ArrayList<ErrorSong> errorSongs = new ArrayList<ErrorSong>();		//creating arraylist for songs with errors objects
		
		Scanner categoryFile = null;
		
		String fileLine;
		
		try {		//opening catagories.txt
			categoryFile = new Scanner(new FileInputStream("categories.txt"));
		} catch(FileNotFoundException e) {
			System.out.println("ERROR - File not found.");
			System.exit(0);
		}
		
		String[] catAspectsString;	//this is so I can use .split() to break up the line then parse every array entry into an integer
		int[] catAspects = new int[7];				//since there's no errors with the categories, we just store the data in an array
				
		while(categoryFile.hasNextLine()) {
			catAspectsString = categoryFile.nextLine().split(",");
			for (int i=0;i<7;i++) {				
				catAspects[i] = Integer.parseInt(catAspectsString[i]);
			}
			categories.add(new Category(catAspects));		//creating category objects
		}//closing while loop
		
		categoryFile.close();		//closing categories.txt
		
		Scanner songFile = null;
		
		try {		//opening songs.txt
			songFile = new Scanner(new FileInputStream("songs.txt"));
		} catch(FileNotFoundException e) {
			System.out.println("ERROR - File not found.");
			System.exit(0);
		}		
		
		
		String wholeLine;		//the entire song line
		String[] splitLine;		//array which stores song ID and aspects as strings
		
		boolean noError;
		
		int testIfInvalid;
		double testIfDouble;

		while(songFile.hasNextLine()) {
			
			noError = true;		//true while no error is detected
			wholeLine = songFile.nextLine();
			splitLine = wholeLine.split(",");
			
			for (int i = 1; i<splitLine.length;i++) {
				try {		//testing if song ID contained a comma
					testIfInvalid = Integer.parseInt(splitLine[i]); 					
				} catch(NumberFormatException e) {
					if(i == 1) {
						try {
							testIfDouble = Double.parseDouble(splitLine[i]);
							errorSongs.add(new ErrorSong(wholeLine, 1));		//invalid aspect error
							noError = false;
						} catch(NumberFormatException ex) {
							errorSongs.add(new ErrorSong(wholeLine, 4));		//comma in song ID error
							noError = false;
						}//end catch						
					} else {
						errorSongs.add(new ErrorSong(wholeLine, 1));		//invalid aspect error
						noError = false;
					}//end else
					
				}//end catch
				
			}//close for loop
			
			for (int j=1;j<splitLine.length;j++) {			//testing if line contains aspects less/greater than allowed values
				if (noError && Integer.parseInt(splitLine[j]) > 10) {
					errorSongs.add(new ErrorSong(wholeLine, 5));		//aspect too high error
					noError = false;
				} else if (noError && Integer.parseInt(splitLine[j]) < 0) {
					errorSongs.add(new ErrorSong(wholeLine, 6));		//aspect too low error
					noError=false;
				}//end else if
			}//end for loop
			
			if(splitLine.length > 7 && noError) {
				errorSongs.add(new ErrorSong(wholeLine, 2));		//too many aspects error
				noError = false;
			} else if (splitLine.length < 7) {
				errorSongs.add(new ErrorSong(wholeLine, 3));		//too few aspect error
				noError = false;
			}
			
			if(noError) {		//if no error was detected, add song to song list
				songs.add(new Song(splitLine));
			}
			
		}//close while loop
		
		songFile.close();		//closing songs.txt
		
		/*CREATING/WRITING SONG_CATEGORY.TXT*/
		
		int closestCategoryID;		//stores ID integer of current closest category to current song
		int currentDistance;		//stores distance of current song to current category
		int categoryDistance;		//stores distance of current song to current closest category
		
		for(int i=0;i<songs.size();i++) {
			categoryDistance = -1;			//resetting categoryDistance
			closestCategoryID = 0;			//resetting closestCategoryID
			
			for(int j=0;j<categories.size();j++) {
				currentDistance = 0;
			
				for(int k=0;k<6;k++) {		//triple nested for loop monstrosity sums distance between aspects one at a time then compares sum to current lowest sum
					currentDistance += (songs.get(i).getAspect(k) - categories.get(j).getAspect(k))*(songs.get(i).getAspect(k) - categories.get(j).getAspect(k));
				}//close 3rd for loop
							
				if (categoryDistance == -1 || categoryDistance > currentDistance) {				//if categoryDistance hasn't been set to an actual distance yet or the current distance is greater than categoryDistance
					categoryDistance = currentDistance;		//categoryDistance is now the currentDistance
					closestCategoryID = categories.get(j).getCatID();
				} else if(categoryDistance == currentDistance) {					//if the current lowest distance and current distance are the same
					if(closestCategoryID > categories.get(j).getCatID()) {		//set whichever one has the lower ID string as the current lowest distance
						closestCategoryID = categories.get(j).getCatID();
					}
				} 
								
			}//close 2nd for loop
			
			songs.get(i).setCategoryDistance(categoryDistance);		//recording song's distance to closest category
			songs.get(i).setClosestCategory(closestCategoryID);		//recording closest category's ID
			
		}//close 1st for loop 
		
		PrintWriter songCategory = null;
		
		try {
			songCategory = new PrintWriter(new FileOutputStream("song_category.txt"));
		} catch(FileNotFoundException e) {
			System.out.println("song_category.txt not found - exiting");
			System.out.println("Message from exception: " + e.getMessage());
			System.exit(0);
		}
		
		for(int i=0; i<songs.size();i++) {			//writing info to song_category.txt
			songCategory.println("Song ID String: "+songs.get(i).getName() + ", Closest Category ID Number: "+songs.get(i).getClosestCategory());
		}
		
		songCategory.close();			//closing song_category.txt
		
		/*CREATING/WRITING CATEGORY_STATS.TXT*/
		
		int categoryCount = categories.size();
		int songCount = songs.size();
		
		int songDistance;
		String[] closestSong = new String[categoryCount];
		
		int[] numberOfSongsInCat = new int[categoryCount];		//tally of songs in category will be stored in same index as category itself
		
		for(int i=0;i<categoryCount;i++) {
			numberOfSongsInCat[i]=0;
		}
		
		for(int i=0;i<songCount;i++) {
			
			for(int j=0;j<categoryCount;j++) {
				if(songs.get(i).getClosestCategory() == categories.get(j).getCatID()) {		//if current song belongs in current category
					numberOfSongsInCat[j] ++;												//increment that category's song count
				}
				
			}//inner for loop ends

		}//outer for loop ends
		
		for(int i=0;i<categoryCount;i++) {
			songDistance = -1;
			
			for(int j=0;j<songCount;j++) {
				if (songDistance == -1) {		//if songDistance hasn't been set to a distance yet 
					closestSong[i] = songs.get(j).getName();
					songDistance = songs.get(j).getCategoryDistance();
				} else if (categories.get(i).getCatID() == songs.get(j).getClosestCategory() && songDistance > songs.get(j).getCategoryDistance()) {		//if songDistance is greater than current song's distance
					closestSong[i] = songs.get(j).getName();
					songDistance = songs.get(j).getCategoryDistance();
				}
			}//inner for loop ends
			
		}//outer for loop ends
		
		PrintWriter categoryStats = null;
		
		try {
			categoryStats = new PrintWriter(new FileOutputStream("category_stats.txt"));
		} catch(FileNotFoundException e) {
			System.out.println("category_stats.txt not found - exiting");
			System.out.println("Message from exception: " + e.getMessage());
			System.exit(0);
		}
		
		for (int i=0;i<categoryCount;i++) {
			categoryStats.println("Category ID number: " + categories.get(i).getCatID() + ", Number of songs in this category: " + numberOfSongsInCat[i] + ", ID string of closest song: " + closestSong[i]);
		}
		
		categoryStats.close();
		
		/*CREATING/WRITING ERRORS.TXT*/
		
		PrintWriter errors = null;
		
		int errorCount = errorSongs.size();
		
		try {
			errors = new PrintWriter(new FileOutputStream("errors.txt"));
		} catch(FileNotFoundException e) {
			System.out.println("errors.txt not found - exiting");
			System.out.println("Message from exception: " + e.getMessage());
			System.exit(0);
		}
		
		for(int i=0;i<errorCount;i++) {
			//System.out.println(errorSongs.get(i).getErrorLine());
			errors.println(errorSongs.get(i).getErrorLine() + ": " + errorSongs.get(i).getErrorMessage());
		}
		
		errors.close();
				
	}//end of main method
	
}

