package es.uvigo.ei.sing.s2p.gui.mascot;

import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.gui.table.CSVTable;

public class MascotEntryTable extends CSVTable {
	private static final long serialVersionUID = 1L;

	public MascotEntryTable(MascotIdentifications entries) {
		super(new MascotEntryTableModel(entries));
		
		this.initComponent();
	}

	private void initComponent() {
		this.setColumnControlVisible(true);
	}
}
