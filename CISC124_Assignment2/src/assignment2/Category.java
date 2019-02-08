package assignment2;

public class Category {
	
	int catID;
	int[] aspect = new int[6];
	
	/*Category(int IDNum, int[] aspects) {
		catID = IDNum;
		aspect = aspects;
	}*/
	
	Category(int[] nums) {
		catID = nums[0];
		for(int i=0;i<6;i++) {
			aspect[i] = nums[i+1];
		}
	}
	
	int getCatID() {
		return catID;
	}
	
	int getAspect(int aspectIndex) {
		return aspect[aspectIndex];
	}
	
}
