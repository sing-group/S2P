package es.uvigo.ei.sing.s2p.gui.charts.spots;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.util.List;

import javax.swing.JPanel;

import es.uvigo.ei.sing.hlfernandez.dialog.AbstractInputJDialog;

public class SpotsSummaryChartDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private JPanel inputComponents;

	private List<ChartSpotSummary> summaries;

	public SpotsSummaryChartDialog(Window parent, 
		List<ChartSpotSummary> summaries
	) {
		super(parent);
		
		this.summaries = summaries;
		this.configureDialog();
	}

	private void configureDialog() {
		this.setResizable(true);
		this.getDescriptionPane().setVisible(false);
		this.cancelButton.setVisible(false);
		this.okButton.setEnabled(true);
		this.setModal(true);
		
		this.inputComponents.add(getChart(), BorderLayout.CENTER);
		
		this.pack();
	}

	private Component getChart() {
		return new SpotsSummaryChart(summaries);
	}

	@Override
	protected String getDialogTitle() {
		return "Expression values comparison";
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
