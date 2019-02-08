package assignment2;

public class ErrorSong {
	
	String errorLine;
	String errorMessage;
	
	ErrorSong(String songLine, int errorNum) {
		errorLine = songLine;
		switch(errorNum) {			//choosing error message to include
		case 1:
			errorMessage = "Line contains invalid aspect";
			break;
		case 2:
			errorMessage = "Line contains too many aspects";
			break;
		case 3:
			errorMessage = "Line contains too few aspects";
			break;
		case 4:
			errorMessage = "Song ID string contains commas";
			break;
		case 5:
			errorMessage = "Line contains aspect exceeding 10";
			break;
		case 6: 
			errorMessage = "Line contains aspect less than 0";
		default:
			errorMessage = "Unknown Error";
		}
	}
	
	String getErrorLine() {
		return errorLine;
	}
	
	String getErrorMessage() {
		return errorMessage;
	}
	
}