package swing;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import model.Message;
import model.MessageServer;

public class MessagePanel extends JPanel {
	private JTree serverTree;
	private ServerTreeCellRenderer treeCellRenderer;
	private ServerTreeCellEditor treeCellEditor;
	
	private MessageServer server;
	private Set<Integer> selected;
	
	public MessagePanel(){
		
		SetupMessageDatabase();
		
		serverTree = new JTree(createTree());
		treeCellRenderer = new ServerTreeCellRenderer();
		treeCellEditor = new ServerTreeCellEditor();
		
		serverTree.setCellRenderer(treeCellRenderer);
		serverTree.setCellEditor(treeCellEditor);
		serverTree.setEditable(true);
		
		serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		treeCellEditor.addCellEditorListener(new CellEditorListener() {
			public void editingStopped(ChangeEvent e) {
				ServerInfo info = (ServerInfo) treeCellEditor.getCellEditorValue();
				Integer id = info.getId();
				if (info.isChecked()) {selected.add(id);} else {selected.remove(id);}
				server.setSelectedServers(selected);
				
				RetrieveMessages();
			}
			public void editingCanceled(ChangeEvent e) {
			}
		});
		
		setLayout(new BorderLayout());
		
		add(serverTree);
	}
	
	private void RetrieveMessages() {
		
		System.out.println("Message waiting: " + server.getSelectedSize());
		
		SwingWorker<ArrayList<Message>, Integer> worker = new SwingWorker<ArrayList<Message>, Integer>(){
			protected ArrayList<Message> doInBackground() throws Exception {
				ArrayList<Message> retrieve = new ArrayList<>();
				int count = 0;
				for (Message m : server){
					System.out.println(m);
					retrieve.add(m);
					count++;
					publish(count);
				}
				return retrieve;
			}

			protected void process(List<Integer> chunks) {
				int retrieved = chunks.get(chunks.size() - 1);
				System.out.println("Got " + retrieved + " messages.");
			}

			protected void done() {
				try {
					get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		worker.execute();
	}

	private void SetupMessageDatabase() {
		server = new MessageServer();
		selected = new TreeSet<>();
//		selected.add(0);
//		selected.add(1);
//		selected.add(4);
	}

	private DefaultMutableTreeNode createTree() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");
		
		DefaultMutableTreeNode USABranch = new DefaultMutableTreeNode("USA");
		DefaultMutableTreeNode USALeaf1 = new DefaultMutableTreeNode(new ServerInfo(0, "New York", selected.contains(0)));
		DefaultMutableTreeNode USALeaf2 = new DefaultMutableTreeNode(new ServerInfo(1, "Los Angeles", selected.contains(1)));
		
		DefaultMutableTreeNode UKBranch = new DefaultMutableTreeNode("UK");
		DefaultMutableTreeNode UKLeaf1 = new DefaultMutableTreeNode(new ServerInfo(2, "London", selected.contains(2)));
		DefaultMutableTreeNode UKLeaf2 = new DefaultMutableTreeNode(new ServerInfo(3, "Manchester", selected.contains(3)));
		DefaultMutableTreeNode UKLeaf3 = new DefaultMutableTreeNode(new ServerInfo(4, "Southampton", selected.contains(4)));
		
		USABranch.add(USALeaf1);
		USABranch.add(USALeaf2);
		UKBranch.add(UKLeaf1);
		UKBranch.add(UKLeaf2);
		UKBranch.add(UKLeaf3);
		top.add(USABranch);
		top.add(UKBranch);
		
		return top;
	}
}

class ServerInfo{
	private int id;
	private String name;
	private boolean checked;
	
	public ServerInfo(int id, String name, boolean checked) {
		this.id = id;
		this.name = name;
		this.checked = checked;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public boolean isChecked(){
		return checked;
	}
	
	public void setChecked(boolean checked){
		this.checked = checked;
	}
	
	public String toString(){
		return name;
	}
}
