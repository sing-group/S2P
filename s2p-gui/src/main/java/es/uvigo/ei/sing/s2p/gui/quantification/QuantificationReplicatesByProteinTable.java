package es.uvigo.ei.sing.s2p.gui.quantification;

import static java.lang.Double.isNaN;
import static java.lang.String.format;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;

import org.jdesktop.swingx.renderer.DefaultTableRenderer;

import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;
import es.uvigo.ei.sing.s2p.gui.table.ExtendedCsvTable;

public class QuantificationReplicatesByProteinTable extends ExtendedCsvTable {
	private static final long serialVersionUID = 1L;

	public QuantificationReplicatesByProteinTable(QuantificationDataset dataset) {
		super(new QuantificationReplicatesByProteinTableModel(dataset));

		this.initComponent();
	}

	private void initComponent() {
		this.addExportToCsvAction();
		QuantificationReplicatesTableCellRenderer renderer = 
			new QuantificationReplicatesTableCellRenderer();
		this.setDefaultRenderer(Double.class, renderer);
		this.setHorizontalScrollEnabled(true);
		this.packAll();
	}
	
	private class QuantificationReplicatesTableCellRenderer
	extends DefaultTableRenderer 
{
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table,
		Object value, boolean isSelected, boolean hasFocus, int row,
		int column
	) {
		final Component c = super.getTableCellRendererComponent(
			table, value, isSelected, hasFocus, row, column
		);

		JLabel label = (JLabel) c;

			if (!(value instanceof String)) {
				Double doubleValue = Double.parseDouble(value.toString());
				String cellValue = isNaN((double) doubleValue)
					? "" : format("%6.2e", doubleValue);
				if (!cellValue.isEmpty()) {
					label.setToolTipText(doubleValue.toString());
				}
				label.setText(cellValue);
			}
		
		return c;
	}
}
}
