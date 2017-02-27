package es.uvigo.ei.sing.s2p.gui.quantification;

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
	}
}
