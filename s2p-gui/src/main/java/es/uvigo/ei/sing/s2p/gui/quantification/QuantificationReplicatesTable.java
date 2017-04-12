package es.uvigo.ei.sing.s2p.gui.quantification;

import static org.sing_group.jsparklines_factory.JSparklinesBarChartTableCellRendererFactory.createMaxValueBarChartRenderer;

import java.util.Arrays;

import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;
import es.uvigo.ei.sing.s2p.gui.table.ExtendedCsvTable;

public class QuantificationReplicatesTable extends ExtendedCsvTable {
	private static final long serialVersionUID = 1L;

	public QuantificationReplicatesTable(QuantificationDataset dataset) {
		super(new QuantificationReplicatesTableModel(dataset));

		this.initComponent();
	}

	private void initComponent() {
		this.addExportToCsvAction();
		this.updateSparklinesRenderers();
	}

	private void updateSparklinesRenderers() {
		for(int i : Arrays.asList(3, 4, 5)) {
			createMaxValueBarChartRenderer(this, i).showNumberAndChart(true, 40);
		}
	}
}
