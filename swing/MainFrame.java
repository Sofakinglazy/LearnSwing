package swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import controller.Controller;

public class MainFrame extends JFrame {

	private TextPanel textPanel;
	private ToolBar toolBar;
	private FormPanel formPanel;
	private TablePanel tablePanel;
	private SafeFileChooser fileChooser;
	private Controller controller;

	public MainFrame() {
		super("Hello World");
		controller = new Controller();
		textPanel = new TextPanel();
		toolBar = new ToolBar();
		formPanel = new FormPanel();
		tablePanel = new TablePanel();
		fileChooser = new SafeFileChooser();

		fileChooser.setFileFilter(new PersonFileFilter());

		setJMenuBar(createMenu());

		toolBar.setStringListener(new StringListener() {
			public void textEmmited(String text) {
				textPanel.append(text);
			}
		});

		formPanel.setFormListener(new FormEventListener() {
			public void FormEventOccured(FormEvent e) {
				controller.addPerson(e);
				tablePanel.refresh();
			}
		});

		tablePanel.setDatabase(controller.getDatabase());

		// add(toolBar, BorderLayout.NORTH);
		add(tablePanel, BorderLayout.CENTER);
		add(formPanel, BorderLayout.WEST);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1200, 700));
		setVisible(true);
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
		showMenu.add(personFormCheckBox);
		windowMenu.add(showMenu);

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
				int result = JOptionPane.showConfirmDialog(MainFrame.this, "Do you want to save before clear action?",
						"Warning", JOptionPane.YES_NO_CANCEL_OPTION);
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
		});

		importDataItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_I, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		quitItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		quitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit the app?",
						"Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
				if (choice == JOptionPane.OK_OPTION)
					System.exit(0);
			}
		});

		// Set up Window Menu
		personFormCheckBox.setSelected(true);
		personFormCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
				formPanel.setVisible(menuItem.isSelected());
			}
		});

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
}
