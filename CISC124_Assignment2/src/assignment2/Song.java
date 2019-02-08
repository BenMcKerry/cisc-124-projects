package assignment2;

  public class Song {
	
	String songName;
	int[] aspect = new int[6];
	
	int closestCategory;
	int categoryDistance;
	
	Song(String[] splitLine) {
		
		songName = splitLine[0];
		
		for(int i = 0;i<6;i++) {
			aspect[i] = Integer.parseInt(splitLine[i+1]);
		}
	
	}
	
	 String getName() {
		return songName;
	}
	
	 int getAspect(int catIndex) {
		return aspect[catIndex];
	}
	 
	 void setClosestCategory(int n) {
		 closestCategory = n;
	 }
	 
	 void setCategoryDistance(int n) {
		 categoryDistance = n;
	 }
	 
	 int getClosestCategory() {
		 return closestCategory;
	 }
	 
	 int getCategoryDistance() {
		 return categoryDistance;
	 }
	
}