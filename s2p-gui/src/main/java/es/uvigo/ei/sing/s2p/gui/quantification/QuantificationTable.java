package es.uvigo.ei.sing.s2p.gui.quantification;

import static es.uvigo.ei.sing.s2p.core.operations.quantification.QuantificationConditionsSummarizer.summary;
import static es.uvigo.ei.sing.s2p.gui.util.ColorUtils.getSoftColor;
import static java.lang.Double.isNaN;
import static java.lang.String.format;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.renderer.DefaultTableRenderer;

import com.itextpdf.text.Font;

import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationCondition;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationConditionsSummary;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;
import es.uvigo.ei.sing.s2p.gui.charts.ChartDataSeriesDialog;
import es.uvigo.ei.sing.s2p.gui.charts.DataSeries;
import es.uvigo.ei.sing.s2p.gui.table.ExtendedCsvTable;

public class QuantificationTable extends ExtendedCsvTable {
	private static final long serialVersionUID = 1L;

	private QuantificationDataset dataset;
	private QuantificationConditionsSummary datasetSummary;
	private List<QuantificationCondition> conditions;
	private HashMap<QuantificationCondition, Color> conditionsColors;

	public QuantificationTable(QuantificationDataset dataset) {
		super(new QuantificationTableModel(dataset));

		this.dataset = dataset;
		this.init();
	}

	private void init() {
		this.conditions = this.dataset.getConditions();
		this.datasetSummary = summary(this.dataset);
		this.initColors();
		this.addExportToCsvAction();
		this.setHorizontalScrollEnabled(true);
		this.packAll();
		ConditionsSummaryTableCellRenderer renderer = 
				new ConditionsSummaryTableCellRenderer();
		this.setDefaultRenderer(String.class, renderer);
		this.setDefaultRenderer(Double.class, renderer);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				Point p = e.getPoint();
				int row = table.rowAtPoint(p);
				if (e.getClickCount() == 2 && row != -1) {
					doubleClickAtRow(table.convertRowIndexToModel(row));
				}
			}
		});
	}

	protected void doubleClickAtRow(int rowModelIndex) {
		String clickedProtein = getTableModel().getProteinAt(rowModelIndex);
		ChartDataSeriesDialog dialog = new ChartDataSeriesDialog(
			getDialogParent(), clickedProtein, "", "Normalized protein values",
			getChartDataForProtein(clickedProtein)
		);
		dialog.setVisible(true);
	}

	private List<DataSeries> getChartDataForProtein(String clickedProtein) {
		List<DataSeries> chartData = new LinkedList<>();
		for (QuantificationCondition qC : this.datasetSummary.keySet()) {
			List<Double> proteinValues = this.datasetSummary.get(qC)
				.get(clickedProtein).getNormalizedProteinValues();
	
			if(!proteinValues.isEmpty()) {
				String condition = qC.getName();
				chartData.add(
					new DataSeries(
						condition, 
						getConditionColor(condition), 
						proteinValues
					)
				);
			}
		}
		return chartData;
	}

	private Color getConditionColor(String condition) {
		return conditionsColors.get(findQuantificationCondition(condition));
	}

	private QuantificationCondition findQuantificationCondition(String name) {
		return conditions.stream()
			.filter(q -> q.getName().equals(name)).findFirst().get();
	}

	private Window getDialogParent() {
		return SwingUtilities.getWindowAncestor(this);
	}

	private void initColors() {
		this.conditionsColors = new HashMap<QuantificationCondition, Color>();
		conditions.forEach(c -> { 
			Color conditionColor = getSoftColor(conditions.indexOf(c));
			this.conditionsColors.put(c, conditionColor);
		});
	}
	
	private class ConditionsSummaryTableCellRenderer
		extends DefaultTableRenderer 
	{
		private static final long serialVersionUID = 1L;
	
		public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column
		) {
			final int columnModel = table.convertColumnIndexToModel(column);
			
			final Component c = super.getTableCellRendererComponent(
				table, value, isSelected, hasFocus, row, column
			);

			JLabel label = (JLabel) c;

			Optional<QuantificationCondition> condition =
				getTableModel().getConditionAt(columnModel);
			if(condition.isPresent()) {
				c.setBackground(conditionsColors.get(condition.get()));
				if (!(value instanceof String)) {
					Double doubleValue = Double.parseDouble(value.toString());
					String cellValue = 	isNaN((double) doubleValue) ? 
						"" : format("%6.2e", doubleValue);
					if (!cellValue.isEmpty()) {
						label.setToolTipText(doubleValue.toString());
					}
					label.setText(cellValue);
				}
			} else {
				if (!(value instanceof String)) {
					double d = Double.parseDouble(value.toString());
					if (d < 0.05) {
						label.setFont(c.getFont().deriveFont(Font.BOLD));
						label.setForeground(new Color(237, 67, 55));
					} else if (isNaN(d)) {
						label.setText("");
					}
				}
			}
			
			return c;
		}
	}

	public QuantificationTableModel getTableModel() {
		return (QuantificationTableModel) this.getModel();
	}

	public Map<QuantificationCondition, Color> getConditionColors() {
		return this.conditionsColors;
	}
}
