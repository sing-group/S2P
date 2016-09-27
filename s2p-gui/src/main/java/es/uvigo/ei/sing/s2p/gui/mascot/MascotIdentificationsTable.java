package es.uvigo.ei.sing.s2p.gui.mascot;

import java.util.Map;
import java.util.Set;

import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.gui.table.CSVTable;

public class MascotIdentificationsTable extends CSVTable {
	private static final long serialVersionUID = 1L;

	public MascotIdentificationsTable(Set<String> spots,
		Map<String, MascotIdentifications> spotIdentifications
	) {
		super(new MascotIdentificationsTableModel(spots, spotIdentifications));
		
		this.initComponent();
	}

	private void initComponent() {
		this.setAutoCreateRowSorter(true);
		this.setColumnControlVisible(true);
		this.getRowSorter().toggleSortOrder(3);
		this.getRowSorter().toggleSortOrder(3); 
	}
}
