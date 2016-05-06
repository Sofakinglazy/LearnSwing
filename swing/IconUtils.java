package swing;

import java.net.URL;
import javax.swing.*;

public class IconUtils {
	private static IconUtils iconUtils = null;
	
	public ImageIcon getIcon(String category, String name, int size){
		if (size != 16 && size != 24) return null;
		
		String path = "/toolbarButtonGraphics/" + category + "/" + name + size + ".gif";
		URL url = getClass().getResource(path);
//		System.out.println("url of the icon: " + url);
		
		return new ImageIcon(url);
	}
	
	public static ImageIcon getDevelopmentIcon(String name, int size){
		if (iconUtils == null) iconUtils = new IconUtils();
		return iconUtils.getIcon("development", name, size);
	}
	
	public static ImageIcon getGeneralIcon(String name, int size){
		if (iconUtils == null) iconUtils = new IconUtils();
		return iconUtils.getIcon("general", name, size);
	}
	
	public static ImageIcon getMediaIcon(String name, int size){
		if (iconUtils == null) iconUtils = new IconUtils();
		return iconUtils.getIcon("media", name, size);
	}
	
	public static ImageIcon getTableIcon(String name, int size){
		if (iconUtils == null) iconUtils = new IconUtils();
		return iconUtils.getIcon("table", name, size);
	}
	
	public static ImageIcon getTextIcon(String name, int size){
		if (iconUtils == null) iconUtils = new IconUtils();
		return iconUtils.getIcon("text", name, size);
	}
}
