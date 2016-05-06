package swing;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public class ServerTreeCellRenderer implements TreeCellRenderer {

	private JCheckBox leafRenderer;
	private DefaultTreeCellRenderer nonLeafRenderer;

	private Color textForeground;
	private Color textBackground;
	private Color selectionForeground;
	private Color selectionBackground;

	public ServerTreeCellRenderer() {
		leafRenderer = new JCheckBox();
		nonLeafRenderer = new DefaultTreeCellRenderer();

		nonLeafRenderer.setClosedIcon(IconUtils.getDevelopmentIcon("WebComponentAdd", 16));
		nonLeafRenderer.setOpenIcon(IconUtils.getDevelopmentIcon("WebComponent", 16));
		nonLeafRenderer.setLeafIcon(IconUtils.getDevelopmentIcon("Server", 16));

		textForeground = UIManager.getColor("Tree.textForeground");
		textBackground = UIManager.getColor("Tree.textBackground");
		selectionForeground = UIManager.getColor("Tree.selectionForeground");
		selectionBackground = UIManager.getColor("Tree.selectionBackground");
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		if (leaf) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			ServerInfo serverInfo = (ServerInfo) node.getUserObject();
			if (!selected) {
				leafRenderer.setForeground(textForeground);
				leafRenderer.setBackground(textBackground);
			} else {
				leafRenderer.setForeground(selectionForeground);
				leafRenderer.setBackground(selectionBackground);
			}
			leafRenderer.setText(serverInfo.toString());
			leafRenderer.setSelected(serverInfo.isChecked());
			return leafRenderer;
		} else {
			return nonLeafRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		}
	}

}
