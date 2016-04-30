package swing;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import model.Age;
import model.Employment;
import model.Gender;
import model.Person;

public class PersonTableModel extends AbstractTableModel {

	private ArrayList<Person> db;

	private String[] colNames = {"ID", "Name", "Occupation", "Age Category", "Employment", "UK Citizen", 
			"Tax ID", "Gender"};

	public PersonTableModel() {
	}

	public void setDatabase(ArrayList<Person> database) {
		db = database;
	}

	
	@Override
	public String getColumnName(int column) {
		return colNames[column];
	}

	@Override
	public int getRowCount() {
		return db.size();
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Person person = db.get(rowIndex);
		switch (columnIndex) {
		case 0: 
			return person.getId();
		case 1:
			return person.getName();
		case 2:
			return person.getOccupation();
		case 3:
			return person.getAge();
		case 4:
			return person.getEmp();
		case 5:
			return person.isUkCitizen();
		case 6:
			return person.getTaxId();
		case 7:
			return person.getGender();
		}
		return null;
	}

}
