package swing;

import java.io.File;

public class Utils {
	public static String getFileExtension(File file){
		String fileName = file.getName();
		int pointIndex = fileName.lastIndexOf(".");
		
		if (pointIndex == -1 && pointIndex == fileName.length()-1)
			return null;
		
		return fileName.substring(pointIndex+1, fileName.length());
	}
}
