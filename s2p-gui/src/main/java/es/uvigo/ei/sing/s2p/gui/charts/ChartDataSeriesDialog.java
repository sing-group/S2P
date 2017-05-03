package es.uvigo.ei.sing.s2p.gui.charts;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.JPanel;

import org.sing_group.gc4s.dialog.AbstractInputJDialog;

public class ChartDataSeriesDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	
	private JPanel inputComponents;
	private static Dimension LAST_COMPONENT_SIZE = null;

	private String title;
	private String xAxisTitle;
	private String yAxisTitle;
	private List<DataSeries> series;

	public ChartDataSeriesDialog(Window parent, String chartTitle,
		String xAxisTitle, String yAxisTitle, List<DataSeries> series
	) {
		super(parent);

		this.title = chartTitle;
		this.xAxisTitle = xAxisTitle;
		this.yAxisTitle = yAxisTitle;
		this.series = series;

		this.configureDialog();
	}

	private void configureDialog() {
		this.setResizable(true);
		if (LAST_COMPONENT_SIZE != null) {
			this.setPreferredSize(LAST_COMPONENT_SIZE);
		}
		this.getDescriptionPane().setVisible(false);
		this.cancelButton.setVisible(false);
		this.okButton.setEnabled(true);
		this.setModal(true);
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				ChartDataSeriesDialog.this.componentResized();
			}
		});
		this.inputComponents.add(getChart(), BorderLayout.CENTER);
		
		this.pack();
	}

	private void componentResized() {
		LAST_COMPONENT_SIZE = this.getSize();
	}
	
	private Component getChart() {
		return new ChartDataSeriesPanel(title, xAxisTitle, yAxisTitle, series);
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