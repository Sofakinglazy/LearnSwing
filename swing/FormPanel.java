package swing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class FormPanel extends JPanel implements ActionListener{
	
	private JLabel nameLabel;
	private JLabel occupationLabel;
	private JTextField nameField;
	private JTextField occupationField;
	private JButton okButton;
	private JList<AgeCategory> ageList;
	private JComboBox<String> empCombo;
	private JCheckBox citizenCheck;
	private JLabel taxLabel;
	private JTextField taxId;
	private JRadioButton maleRadio;
	private JRadioButton femaleRadio;
	private ButtonGroup genderGroup;
	private FormEventListener formListener;
	
	public FormPanel (){
		Dimension dim = getPreferredSize();
		
		dim.width = 250;
		dim.height = 100;
		setPreferredSize(dim);
		setMinimumSize(dim);
		
		initComponents();
		
		citizenCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isTicked = citizenCheck.isSelected();
				taxLabel.setEnabled(isTicked);
				taxId.setEnabled(isTicked);
				if (!isTicked)
					taxId.setText("");
			}
		});
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String occupation = occupationField.getText();
				int ageCat = ageList.getSelectedValue().getId();
				String emp = (String) empCombo.getSelectedItem();
				String tax = taxId.getText();
				boolean isUkCitizen = citizenCheck.isSelected();
				String gender = genderGroup.getSelection().getActionCommand();
				
				
				FormEvent ev = new FormEvent(this, name, occupation, ageCat, 
						emp, tax, isUkCitizen, gender);
				
				if (formListener != null){
					formListener.FormEventOccured(ev);
				}
				
				// Reset the form panel
				resetFormPanel();
			}
		});
		
		Border insideBorder = BorderFactory.createTitledBorder("Add Person");
		Border outsideBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
		
		layoutComponents();
	}

	private void initComponents() {
		nameLabel = new JLabel("Name: ");
		occupationLabel = new JLabel("Occupation: ");
		nameField = new JTextField(10);
		occupationField = new JTextField(10);
		
		okButton = new JButton("OK");
		
		ageList = new JList<>();
		DefaultListModel<AgeCategory> ageModel = new DefaultListModel<>();
		ageModel.addElement(new AgeCategory(0, "Under 18"));
		ageModel.addElement(new AgeCategory(1, "18 to 65"));
		ageModel.addElement(new AgeCategory(2, "Over 65"));
		ageList.setModel(ageModel);	
		ageList.setPreferredSize(new Dimension(130, 70));
		ageList.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		ageList.setSelectedIndex(1);
		
		empCombo = new JComboBox<>();
		DefaultComboBoxModel<String> empModel = new DefaultComboBoxModel<>();
		empModel.addElement("Employed");
		empModel.addElement("Half-employed");
		empModel.addElement("Unemployed");
		empCombo.setModel(empModel);
		empCombo.setEditable(true);
		empCombo.setSelectedIndex(0);
		
		taxLabel = new JLabel("Tax: ");
		taxId = new JTextField(10);
		taxLabel.setEnabled(false);
		taxId.setEnabled(false);
		
		citizenCheck = new JCheckBox();
		
		maleRadio = new JRadioButton("Male");
		femaleRadio = new JRadioButton("Female");
		genderGroup = new ButtonGroup();
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);
		maleRadio.setActionCommand("Male");
		femaleRadio.setActionCommand("Female");
		maleRadio.setSelected(true);
		
	}
	
	private void layoutComponents() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints gd = new GridBagConstraints();
		
		/******************First Row*****************/
		gd.gridx = 0;
		gd.gridy = 0;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.anchor = GridBagConstraints.LINE_END;
		gd.fill = GridBagConstraints.NONE;
		gd.insets = new Insets(0, 0, 0, 2);
		add(nameLabel, gd);
		
		gd.gridx = 1;
		gd.gridy = 0;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.anchor = GridBagConstraints.LINE_START;
		gd.fill = GridBagConstraints.NONE;
		gd.insets = new Insets(0, 0, 0, 0);
		add(nameField, gd);
		
		/******************Second Row*****************/
		gd.gridy++;
		
		gd.gridx = 0;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.anchor = GridBagConstraints.LINE_END;
		gd.fill = GridBagConstraints.NONE;
		gd.insets = new Insets(0, 0, 0, 2);
		add(occupationLabel, gd);
		
		gd.gridx = 1;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.anchor = GridBagConstraints.LINE_START;
		gd.fill = GridBagConstraints.NONE;
		gd.insets = new Insets(0, 0, 0, 0);
		add(occupationField, gd);
		
		/******************Third Row*****************/
		gd.gridy++;
		
		gd.gridx = 0;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.anchor = GridBagConstraints.FIRST_LINE_END;
		gd.fill = GridBagConstraints.NONE;
		gd.insets = new Insets(0, 0, 0, 2);
		add(new JLabel("Age: "), gd);
		
		
		gd.gridx = 1;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.anchor = GridBagConstraints.LINE_START;
		gd.fill = GridBagConstraints.NONE;
		gd.insets = new Insets(0, 0, 0, 0);
		add(ageList, gd);
		
		/******************Fourth Row*****************/
		gd.gridy++;
		
		gd.gridx = 0;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.anchor = GridBagConstraints.LINE_END;
		gd.fill = GridBagConstraints.NONE;
		gd.insets = new Insets(0, 0, 0, 2);
		add(new JLabel("Employment: "), gd);
		
		gd.gridx = 1;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.anchor = GridBagConstraints.LINE_START;
		gd.fill = GridBagConstraints.NONE;
		gd.insets = new Insets(0, 0, 0, 0);
		add(empCombo, gd);
		
		/******************Fifth Row*****************/
		gd.gridy++;
		
		gd.gridx = 0;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.anchor = GridBagConstraints.LINE_END;
		gd.fill = GridBagConstraints.NONE;
		gd.insets = new Insets(0, 0, 0, 2);
		add(new JLabel("UK Citizen: "), gd);
		
		gd.gridx = 1;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.anchor = GridBagConstraints.LINE_START;
		gd.fill = GridBagConstraints.NONE;
		gd.insets = new Insets(0, 0, 0, 0);
		add(citizenCheck, gd);
		
		/******************Sixth Row*****************/
		gd.gridy++;
		
		gd.gridx = 0;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.anchor = GridBagConstraints.LINE_END;
		gd.fill = GridBagConstraints.NONE;
		gd.insets = new Insets(0, 0, 0, 2);
		add(taxLabel, gd);
		
		gd.gridx = 1;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.anchor = GridBagConstraints.LINE_START;
		gd.fill = GridBagConstraints.NONE;
		gd.insets = new Insets(0, 0, 0, 0);
		add(taxId, gd);
		
		/******************Seventh Row*****************/
		gd.gridy++;
		
		gd.gridx = 0;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.anchor = GridBagConstraints.LINE_END;
		gd.fill = GridBagConstraints.NONE;
		gd.insets = new Insets(0, 0, 0, 2);
		add(new JLabel("Gender: "), gd);
		
		gd.gridx = 1;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.anchor = GridBagConstraints.LINE_START;
		gd.fill = GridBagConstraints.NONE;
		gd.insets = new Insets(0, 0, 0, 0);
		add(maleRadio, gd);
		
		/******************Eighth Row*****************/
		gd.gridy++;
		
		gd.gridx = 1;
		gd.weightx = 1;
		gd.weighty = 0.05;
		gd.anchor = GridBagConstraints.LINE_START;
		gd.fill = GridBagConstraints.NONE;
		gd.insets = new Insets(0, 0, 0, 0);
		add(femaleRadio, gd);
		
		
		/******************Ninth Row*****************/
		gd.gridy++;
		
		gd.gridx = 1;
		gd.weightx = 1;
		gd.weighty = 1;
		gd.anchor = GridBagConstraints.FIRST_LINE_START;
		gd.fill = GridBagConstraints.NONE;
		gd.insets = new Insets(0, 0, 0, 0);
		add(okButton, gd);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	public void setFormListener(FormEventListener formListener){
		this.formListener = formListener;
	}
	
	private void resetFormPanel() {
		nameField.setText("");
		occupationField.setText("");
		ageList.setSelectedIndex(1);
		empCombo.setSelectedIndex(0);
		citizenCheck.setSelected(false);
		taxLabel.setEnabled(false);
		taxId.setEnabled(false);
		taxId.setText("");
		maleRadio.setSelected(true);
	}

}

class AgeCategory{
	private int id;
	private String ageText;
	
	public AgeCategory(int id, String ageText){
		this.id = id;
		this.ageText = ageText;
	}
	
	public int getId() {
		return id;
	}

	public String toString(){
		return ageText;
	}
}
