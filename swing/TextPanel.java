package swing;

import java.awt.*;
import javax.swing.*;

public class TextPanel extends JPanel{
	
	private JTextArea textArea; 
	private JScrollPane scrollPanel;
	
	public TextPanel(){
		textArea = new JTextArea();
		scrollPanel = new JScrollPane(textArea);
		
		setLayout(new BorderLayout());
		
		add(scrollPanel, BorderLayout.CENTER);
	}
	
	public void append(String message){
		textArea.append(message);
	}
}
