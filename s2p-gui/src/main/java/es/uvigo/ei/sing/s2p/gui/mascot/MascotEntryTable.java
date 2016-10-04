package es.uvigo.ei.sing.s2p.gui.mascot;

import org.jdesktop.swingx.decorator.HighlighterFactory;

import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.gui.table.ExtendedCsvTable;

public class MascotEntryTable extends ExtendedCsvTable {
	private static final long serialVersionUID = 1L;

	public MascotEntryTable(MascotIdentifications entries) {
		super(new MascotEntryTableModel(entries));
		
		this.initComponent();
	}

	private void initComponent() {
		this.setAutoCreateRowSorter(true);
		this.setColumnControlVisible(true);
		this.addHighlighter(HighlighterFactory.createAlternateStriping());
		this.addExportToCsvAction();
	}
}
