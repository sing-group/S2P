package es.uvigo.ei.sing.s2p.gui.spots.comparison;

import static java.awt.BorderLayout.CENTER;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.text.DecimalFormat;

import javax.swing.JPanel;
import javax.swing.JRootPane;

import es.uvigo.ei.sing.hlfernandez.dialog.AbstractInputJDialog;
import es.uvigo.ei.sing.hlfernandez.visualization.JHeatMap;
import es.uvigo.ei.sing.hlfernandez.visualization.JHeatMapModel;
import es.uvigo.ei.sing.hlfernandez.visualization.JHeatMapPanel;

public class JHeatMapDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private JPanel inputComponents;
	private JHeatMapModel model;
	
	public JHeatMapDialog(Window parent, JHeatMapModel model) {
		super(parent);
		
		this.model = model;
		this.configureDialog();
	}

	private void configureDialog() {
		this.setResizable(true);
		this.getDescriptionPane().setVisible(false);
		this.cancelButton.setVisible(false);
		this.okButton.setEnabled(true);
		this.setModal(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		
		this.inputComponents.add(getJHeatMapPanel(), CENTER);
		
		this.maximize();
	}

	private Component getJHeatMapPanel() {
		return new JHeatMapPanel(getJHeatMap());
	}

	protected JHeatMap getJHeatMap() {
		JHeatMap heatmap = new JHeatMap(model);
		heatmap.setDecimalFormat(new DecimalFormat("0.00E0"));
		return heatmap;
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
