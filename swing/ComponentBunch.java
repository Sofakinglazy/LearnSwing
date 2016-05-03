package swing;

import java.awt.GridLayout;

import javax.swing.*;

public class ComponentBunch extends JPanel{
	public ComponentBunch(JComponent[] components, int rows, int cols){
		setLayout(new GridLayout(rows, cols));
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				int index = j + cols * i;
				add(components[index]);
			}
		}
		
	}
}
