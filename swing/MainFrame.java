package swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

import javax.swing.*;

import controller.Controller;
import model.Person;

public class MainFrame extends JFrame {

	private TextPanel textPanel;
	private ToolBar toolBar;
	private FormPanel formPanel;
	private TablePanel tablePanel;
	private JSplitPane splitPanel;
	private JTabbedPane tabbedPanel;
	private MessagePanel messagePanel;
	private SafeFileChooser fileChooser;
	private PresDialog presDialog;
	private Controller controller;
	private Preferences pres;

	public MainFrame() {
		super("Population Record System");
		initComponents();

		setJMenuBar(createMenu());

		toolBar.setToolbarListener(new ToolbarListener() {
			public void saveEventOccurred() {
				connectDatabase();
				try {
					controller.save();
					JOptionPane.showMessageDialog(MainFrame.this, "Successfully save to database!", "Save",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Cannot save to database.",
							"Database Connection Problem", JOptionPane.ERROR_MESSAGE);
				}
			}

			public void refreshEventOccurred() {
				connectDatabase();
				try {
					if (!controller.getDatabase().isEmpty()) showSaveWarningDialog();
					controller.load();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Cannot load from database.",
							"Database Connection Problem", JOptionPane.ERROR_MESSAGE);
				}

				tablePanel.refresh();
			}
		});

		formPanel.setFormListener(new FormEventListener() {
			public void FormEventOccured(FormEvent e) {
				controller.addPerson(e);
				tablePanel.refresh();
			}
		});

		tablePanel.setDatabase(controller.getDatabase());

		tablePanel.setPersonTableListener(new PersonTableListener() {
			public void deleteRow(int row) {
				controller.removePersonAt(row);
			}
		});

		presDialog.setPresListener(new PresListener() {
			public void setPreferences(PresEvent pe) {
				int port = pe.getPort();
				String user = pe.getUser();
				String password = pe.getPassword();

				pres.putInt("port", port);
				pres.put("user", user);
				pres.put("password", password);
			}
		});

		int port = pres.getInt("port", 3306);
		String user = pres.get("user", "");
		String password = pres.get("password", "");

		presDialog.setDefault(port, user, password);
		
		tabbedPanel.add("Person Database", tablePanel);
		tabbedPanel.add("Messages", messagePanel);

		add(toolBar, BorderLayout.PAGE_START);
		add(splitPanel, BorderLayout.CENTER);

		setWindowlistener();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setMinimumSize(new Dimension(1200, 700));
		setVisible(true);
	}

	private void setWindowlistener() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				controller.disconnect();
				dispose();
				System.gc();
			}
		});
	}

	private void initComponents() {
		controller = new Controller();
		textPanel = new TextPanel();
		toolBar = new ToolBar();
		formPanel = new FormPanel();
		tablePanel = new TablePanel();
		fileChooser = new SafeFileChooser();
		presDialog = new PresDialog(this);
		pres = Preferences.userRoot().node("db");
		tabbedPanel = new JTabbedPane();
		messagePanel = new MessagePanel();
		splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tabbedPanel);
		
		fileChooser.setFileFilter(new PersonFileFilter());
	}

	private JMenuBar createMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem exportDataItem = new JMenuItem("Export Data ...");
		JMenuItem importDataItem = new JMenuItem("Import Data ...");
		JMenuItem clearDataItem = new JMenuItem("Clear");
		JMenuItem quitItem = new JMenuItem("Quit");
		fileMenu.add(importDataItem);
		fileMenu.add(exportDataItem);
		fileMenu.addSeparator();
		fileMenu.add(clearDataItem);
		fileMenu.addSeparator();
		fileMenu.add(quitItem);

		JMenu windowMenu = new JMenu("Window");
		JMenu showMenu = new JMenu("Show");
		JMenuItem personFormCheckBox = new JCheckBoxMenuItem("Person Form");
		JMenuItem presItem = new JMenuItem("Preferences...");
		showMenu.add(personFormCheckBox);
		windowMenu.add(showMenu);
		windowMenu.add(presItem);

		menuBar.add(fileMenu);
		menuBar.add(windowMenu);

		// Set up File Menu
		importDataItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFromFile();
			}
		});

		exportDataItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveTofile();
			}
		});

		clearDataItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showSaveWarningDialog();
			}
		});

		importDataItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_I, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		exportDataItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		quitItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		quitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit the app?",
						"Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
				if (choice == JOptionPane.OK_OPTION){
					WindowListener[] listeners = getWindowListeners();
					for (WindowListener listener : listeners)
						listener.windowClosing(new WindowEvent(MainFrame.this, 0));
				}
					
			}
		});

		// Set up Window Menu
		personFormCheckBox.setSelected(true);
		personFormCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
				if (menuItem.isSelected()){
					splitPanel.setDividerLocation((int) formPanel.getMinimumSize().getWidth());
				}
				formPanel.setVisible(menuItem.isSelected());
			}
		});

		presItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				presDialog.setVisible(true);
			}
		});

		presItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_P, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		return menuBar;
	}

	private void saveTofile() {
		if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
			try {
				controller.saveToFile(fileChooser.getSelectedFile());
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(MainFrame.this, "Failed to save data!", "Error",
						JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
	}

	private void loadFromFile() {
		if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
			try {
				controller.loadFromFile(fileChooser.getSelectedFile());
				tablePanel.refresh();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(MainFrame.this, "Failed to load data!", "Error",
						JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
	}

	private void connectDatabase() {
		try {
			controller.connect();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to database.", "Connection Problem",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void showSaveWarningDialog(){
		int result = JOptionPane.showConfirmDialog(MainFrame.this, "Do you want to save the file?", "Warning",
				JOptionPane.YES_NO_CANCEL_OPTION);
		switch (result) {
		case JOptionPane.YES_OPTION:
			saveTofile();
			return;
		case JOptionPane.NO_OPTION:
			controller.clear();
			return;
		case JOptionPane.CANCEL_OPTION:
			return;
		}
	}
}
