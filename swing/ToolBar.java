package swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class ToolBar extends JPanel implements ActionListener{
	
	private JButton saveButton;
	private JButton refreshButton;
	
	private ToolbarListener toolbarListener;
	
	public ToolBar(){
		saveButton = new JButton("Save");
		refreshButton = new JButton("Refresh");
		
		saveButton.addActionListener(this);
		refreshButton.addActionListener(this);
		
		setLayout(new FlowLayout(FlowLayout.LEADING));
		
		add(saveButton);
		add(refreshButton);
		
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();
		if (toolbarListener != null){
			if (clicked == saveButton){
				toolbarListener.saveEventOccurred();
			}
			else if (clicked == refreshButton){
				toolbarListener.refreshEventOccurred();
			}
		}
	}
	
	public void setToolbarListener(ToolbarListener toolbarListener) {
		this.toolbarListener = toolbarListener;
	}
}
