package es.uvigo.ei.sing.s2p.gui.components.dialog;

import java.awt.Window;

import javax.swing.JFileChooser;

import es.uvigo.ei.sing.hlfernandez.dialog.AbstractInputJDialog;
import es.uvigo.ei.sing.s2p.gui.util.CommonFileChooser;

public abstract class AbstractFileInputJDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;

	protected AbstractFileInputJDialog(Window parent) {
		super(parent);
	}
	
	/**
	 * Returns the dialog filechooser.
	 * 
	 * @return the dialog filechooser.
	 */
	protected JFileChooser getFileChooser() {
		return CommonFileChooser.getInstance().getFilechooser();
	}
}
