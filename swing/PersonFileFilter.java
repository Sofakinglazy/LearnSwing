package swing;

import java.io.File;
import javax.swing.filechooser.*;

public class PersonFileFilter extends FileFilter {

	public boolean accept(File file) {
		if (file.isDirectory())
			return true;
		
		String[] filters = {"per"};
		
		String extension = Utils.getFileExtension(file);
		
		if (extension == null)
			return false;
		
		for (String filter : filters){
			if (extension.equals(filter))
				return true;
		}
		
		return false;
	}

	public String getDescription() {
		return "Person database files (*.per)";
	}
	
}
