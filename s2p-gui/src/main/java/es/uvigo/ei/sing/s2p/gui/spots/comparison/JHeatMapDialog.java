package es.uvigo.ei.sing.s2p.gui.spots.comparison;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;

import es.uvigo.ei.sing.hlfernandez.dialog.AbstractInputJDialog;
import es.uvigo.ei.sing.hlfernandez.visualization.JHeatMap;
import es.uvigo.ei.sing.hlfernandez.visualization.JHeatMapModel;
import es.uvigo.ei.sing.hlfernandez.visualization.JHeatMapPanel;

public class JHeatMapDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private JPanel inputComponents;

	private JHeatMapModel model;
	
	public JHeatMapDialog(JFrame parent, JHeatMapModel model) {
		super(parent);
		
		this.model = model;
		this.configureDialog();
	}

	private void configureDialog() {
		this.setResizable(true);
		this.getDescriptionPane().setVisible(false);
		this.cancelButton.setVisible(false);
		this.okButton.setEnabled(true);
		this.setModal(false);
		
		this.inputComponents.add(getJHeatMapPanel(), BorderLayout.CENTER);
		
		this.pack();
	}

	private Component getJHeatMapPanel() {
		return new JHeatMapPanel(new JHeatMap(model));
	}

	@Override
	protected String getDialogTitle() {
		return "HeatMap view";
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
