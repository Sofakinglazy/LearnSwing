package swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PresDialog extends JDialog{
	private JButton okButton;
	private JButton cancelButton;
	private JSpinner portSpinner;
	private SpinnerNumberModel portModel;
	private ComponentBunch okCancelBunch;
	private JTextField userField;
	private JPasswordField passwordField;
	
	public PresDialog(JFrame parent){
		super(parent, "Preferences", false);
		initComponents();
		layoutComponents();
		
		setSize(300, 300);
		setLocationRelativeTo(parent);
	}

	private void initComponents() {
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		portModel = new SpinnerNumberModel(3306, 0, 9999, 1);
		portSpinner = new JSpinner(portModel);
		JButton[] btns = {cancelButton, okButton};
		okCancelBunch = new ComponentBunch(btns, 1, 2);
		userField = new JTextField(10);
		passwordField = new JPasswordField(10);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer value = (Integer) portSpinner.getValue();
				System.out.println("Port: " + value + "\n"
						+ "User: " + userField.getText() + "\n"
						+ "Password: " + new String(passwordField.getPassword()));
				setVisible(false);
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}
	
	private void layoutComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gd = new GridBagConstraints();
		
		////////// First Row //////////
		gd.gridx = 0;
		gd.gridy = 0;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.insets = new Insets(3, 60, 0, 0);
		add(new JLabel("User: "), gd);
		
		gd.gridx++;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.insets = new Insets(3, 30, 0, 0);
		add(userField, gd);
		
		////////// Next Row //////////
		gd.gridx = 0;
		gd.gridy = 1;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.insets = new Insets(3, 28, 0, 0);
		add(new JLabel("Password: "), gd);
		
		gd.gridx++;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.insets = new Insets(3, 28, 0, 0);
		add(passwordField, gd);
		
		////////// Next Row //////////
		gd.gridx = 0;
		gd.gridy = 2;
		gd.weightx = 1;
		gd.weighty = 0.2;
		gd.insets = new Insets(3, 60, 0, 0);
		add(new JLabel("Port: "), gd);
		
		gd.gridx++;
		gd.weightx = 1;
		gd.weighty = 0.2;
		gd.insets = new Insets(3, 60, 0, 20);
		add(portSpinner, gd);
		
		//////////Next Row //////////
		gd.gridx = 1;
		gd.gridy = 3;
		gd.weightx = 1;
		gd.weighty = 1;
		gd.fill = GridBagConstraints.NONE;
		gd.anchor = GridBagConstraints.PAGE_END;
		gd.insets = new Insets(0, 3, 5, 0);
		add(okCancelBunch, gd);
	}
}
