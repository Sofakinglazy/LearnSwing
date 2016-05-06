package swing;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

public class MessagePanel extends JPanel {
	private JTree serverTree;
	private ServerTreeCellRenderer treeCellRenderer;
	
	public MessagePanel(){
		
		serverTree = new JTree(createTree());
		treeCellRenderer = new ServerTreeCellRenderer();
		
		serverTree.setCellRenderer(treeCellRenderer);
		
		serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		serverTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) serverTree.getLastSelectedPathComponent();
				Object nodeObject = node.getUserObject();
				System.out.println(nodeObject);
			}
		});
		
		setLayout(new BorderLayout());
		
		add(serverTree);
	}

	private DefaultMutableTreeNode createTree() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");
		
		DefaultMutableTreeNode USABranch = new DefaultMutableTreeNode("USA");
		DefaultMutableTreeNode USALeaf1 = new DefaultMutableTreeNode(new ServerInfo(0, "New York", true));
		DefaultMutableTreeNode USALeaf2 = new DefaultMutableTreeNode(new ServerInfo(1, "Los Angeles", false));
		
		DefaultMutableTreeNode UKBranch = new DefaultMutableTreeNode("UK");
		DefaultMutableTreeNode UKLeaf1 = new DefaultMutableTreeNode(new ServerInfo(2, "London", true));
		DefaultMutableTreeNode UKLeaf2 = new DefaultMutableTreeNode(new ServerInfo(3, "Manchester", false));
		DefaultMutableTreeNode UKLeaf3 = new DefaultMutableTreeNode(new ServerInfo(4, "Southampton", false));
		
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
