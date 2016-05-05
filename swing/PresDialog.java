package swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

public class PresDialog extends JDialog{
	private JButton okButton;
	private JButton cancelButton;
	private JSpinner portSpinner;
	private SpinnerNumberModel portModel;
	private JTextField userField;
	private JPasswordField passwordField;
	private PresListener presListener;
	
	public PresDialog(JFrame parent){
		super(parent, "Preferences", false);
		initComponents();
		layoutComponents();
		
		setSize(300, 180);
		setLocationRelativeTo(parent);
	}

	private void initComponents() {
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		portModel = new SpinnerNumberModel(3306, 0, 9999, 1);
		portSpinner = new JSpinner(portModel);
		userField = new JTextField(10);
		passwordField = new JPasswordField(10);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer port = (Integer) portSpinner.getValue();
				String user = userField.getText();
				String password = new String(passwordField.getPassword());
				
				PresEvent pe = new PresEvent(user, password, port);
				
				if (presListener != null){
					presListener.setPreferences(pe);
				}
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
		
		JPanel controlsPanel = new JPanel();
		JPanel btnsPanel = new JPanel();
		
		Border titleBorder = BorderFactory.createTitledBorder("Database");
		Border outsideBorder = BorderFactory.createEmptyBorder(5, 10, 0, 10);
		controlsPanel.setBorder(BorderFactory.createCompoundBorder(outsideBorder, titleBorder));
		
		controlsPanel.setLayout(new GridBagLayout());
		GridBagConstraints gd = new GridBagConstraints();
		
		////////// First Row //////////
		gd.gridx = 0;
		gd.gridy = 0;
		gd.weightx = 1;
		gd.weighty = 1;
		gd.insets = new Insets(0, 30, 0, 0);
		controlsPanel.add(new JLabel("User: "), gd);
		
		gd.gridx++;
		gd.insets = new Insets(0, 0, 0, 0);
		controlsPanel.add(userField, gd);
		
		////////// Next Row //////////
		gd.gridx = 0;
		gd.gridy = 1;
		gd.weightx = 1;
		gd.weighty = 1;
		controlsPanel.add(new JLabel("Password: "), gd);
		
		gd.gridx++;
		controlsPanel.add(passwordField, gd);
		
		////////// Next Row //////////
		gd.gridx = 0;
		gd.gridy = 2;
		gd.weightx = 1;
		gd.weighty = 1;
		gd.insets = new Insets(0, 33, 0, 0);
		controlsPanel.add(new JLabel("Port: "), gd);
		
		gd.gridx++;
		gd.weightx = 1;
		gd.weighty = 1;
		gd.insets = new Insets(0, 0, 0, 0);
		controlsPanel.add(portSpinner, gd);
		
		////////// Buttons Row //////////
		btnsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		Dimension btnSize = cancelButton.getPreferredSize();
		okButton.setPreferredSize(btnSize);
		btnsPanel.add(cancelButton);
		btnsPanel.add(okButton);
		
		////////// Add SubPanels //////////
		setLayout(new BorderLayout());
		add(controlsPanel, BorderLayout.CENTER);
		add(btnsPanel, BorderLayout.SOUTH);
	}

	public void setPresListener(PresListener presListener) {
		this.presListener = presListener;
	}

	public void setDefault(int port, String user, String password) {
		portSpinner.setValue(port);
		userField.setText(user);
		passwordField.setText(password);
	}
}
