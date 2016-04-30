package swing;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class SafeFileChooser extends JFileChooser{
	@Override
	public void approveSelection() {
		File file = getSelectedFile();
		if (file.exists() && getDialogType() == SAVE_DIALOG) {
			int result = JOptionPane.showConfirmDialog(this,
					"The file exists already. Are you sure to overwite?", 
					"Warning", JOptionPane.YES_NO_CANCEL_OPTION);
			switch (result) {
			case JOptionPane.YES_OPTION:
				super.approveSelection();
				return;
			case JOptionPane.NO_OPTION:
				return;
			case JOptionPane.CANCEL_OPTION:
				cancelSelection();
			case JOptionPane.CLOSED_OPTION:
				return;
			}
		}
		super.approveSelection();
	}
}
