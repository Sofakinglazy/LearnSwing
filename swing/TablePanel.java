package swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

import model.Person;

public class TablePanel extends JPanel{
	private JTable table;
	private PersonTableModel model;
	private JPopupMenu popup;
	private PersonTableListener ptl;
	
	public TablePanel(){
		model = new PersonTableModel();
		table = new JTable(model);
		popup = new JPopupMenu();
		
		makePopupMenu();
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);
				if (e.getButton() == MouseEvent.BUTTON3)
					popup.show(table, e.getX(), e.getY());
			}
		});
				
		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	private void makePopupMenu() {
		JMenuItem deleteRow = new JMenuItem("Delete Row");
		popup.add(deleteRow);
		
		deleteRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (ptl != null){
					ptl.deleteRow(row);
					model.fireTableRowsDeleted(row, row);
				}
			}
		});
	}

	public void setDatabase(List<Person> list) {
		model.setDatabase(list);
	}

	public void refresh() {
		model.fireTableDataChanged();
	}

	public void setPersonTableListener(PersonTableListener ptl) {
		this.ptl = ptl;
	}
	
}
