package es.uvigo.ei.sing.s2p.gui.components.dialog;

import java.awt.Window;

import javax.swing.JFileChooser;

import es.uvigo.ei.sing.s2p.gui.util.CommonFileChooser;

/**
 * An extension of GC4S's {@link ExportCsvDialog} in order to return the common
 * file chooser configured in S2P.
 * 
 * @author Hugo López-Fernández
 *
 */
public class ExportCsvDialog
	extends org.sing_group.gc4s.dialog.ExportCsvDialog {
	private static final long serialVersionUID = 1L;

	public ExportCsvDialog(Window parent) {
		super(parent);
	}

	@Override
	protected JFileChooser getFileChooser() {
		return CommonFileChooser.getInstance().getFilechooser();
	}
}
