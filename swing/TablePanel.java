package swing;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import model.Person;

public class TablePanel extends JPanel{
	private JTable table;
	private PersonTableModel model;
	
	public TablePanel(){
		model = new PersonTableModel();
		table = new JTable(model);
				
		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	public void setDatabase(ArrayList<Person> database) {
		model.setDatabase(database);
	}

	public void refresh() {
		model.fireTableDataChanged();
	}
	
}
