package es.uvigo.ei.sing.s2p.gui.mascot;

import java.awt.BorderLayout;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import es.uvigo.ei.sing.hlfernandez.dialog.AbstractInputJDialog;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

public class MascotIdentificationsDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	private MascotIdentificationsTable table;
	private JPanel inputComponents;
	
	public MascotIdentificationsDialog(JFrame parent, Set<String> spots,
			Map<String, MascotIdentifications> spotIdentifications
		) {
		super(parent);
		
		this.table = new MascotIdentificationsTable(spots, spotIdentifications);
		this.configureDialog();
	}
	

	private void configureDialog() {
		this.setResizable(true);
		this.getDescriptionPane().setVisible(false);
		this.cancelButton.setVisible(false);
		this.okButton.setEnabled(true);
		this.setModal(false);
		
		this.inputComponents.add(new JScrollPane(this.table), BorderLayout.CENTER);
		
		this.pack();
	}

	@Override
	protected String getDialogTitle() {
		return "Mascot identifications";
	}

	@Override
	protected String getDescription() {
		return "";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		this.inputComponents = new JPanel(new BorderLayout());
		return this.inputComponents;
	}
}
