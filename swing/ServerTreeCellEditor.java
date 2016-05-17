package swing;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;

public class ServerTreeCellEditor extends AbstractCellEditor implements TreeCellEditor{

	private ServerTreeCellRenderer renderer;
	private JCheckBox checkBox;
	private ServerInfo info;
	
	public ServerTreeCellEditor() {
		renderer = new ServerTreeCellRenderer();
	}
	
	@Override
	public Object getCellEditorValue() {
		info.setChecked(checkBox.isSelected());
		return info;
	}

	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
			boolean leaf, int row) {
		
		Component component = renderer.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, true);
		
		if (leaf){
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
			info = (ServerInfo) treeNode.getUserObject();
			
			checkBox = (JCheckBox) component;
			ItemListener itemListener = new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					fireEditingStopped();
					checkBox.removeItemListener(this);
				}
			};
			checkBox.addItemListener(itemListener);
		}
		return component;
	}

	@Override
	public boolean isCellEditable(EventObject e) {
		if (!(e instanceof MouseEvent)) return false;
		MouseEvent mouseEvent = (MouseEvent) e;
		JTree tree = (JTree) mouseEvent.getSource();
		TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
		if (path == null) return false;
		Object component = path.getLastPathComponent();
		if (component == null) return false;
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) component;
		return treeNode.isLeaf();
		
	}

	
}
