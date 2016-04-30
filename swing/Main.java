package swing;

import java.awt.Color;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class Main {
	public static void main(String[] args) {
		UIManager.put("Table.gridColor", new ColorUIResource(Color.gray));
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}
		});
	}
}
