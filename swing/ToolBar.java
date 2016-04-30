package swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class ToolBar extends JPanel implements ActionListener{
	
	private JButton leftButton;
	private JButton rightButton;
	
	private StringListener stringListener;
	
	public ToolBar(){
		leftButton = new JButton("Left");
		rightButton = new JButton("Right");
		
		leftButton.addActionListener(this);
		rightButton.addActionListener(this);
		
		setLayout(new FlowLayout(FlowLayout.LEADING));
		
		add(leftButton);
		add(rightButton);
		
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();
		if (stringListener != null){
			if (clicked == leftButton){
				stringListener.textEmmited("Left!\n");
			}
			else if (clicked == rightButton){
				stringListener.textEmmited("Right!\n");
			}
		}
	}
	
	public void setStringListener(StringListener stringListener) {
		this.stringListener = stringListener;
	}
}
