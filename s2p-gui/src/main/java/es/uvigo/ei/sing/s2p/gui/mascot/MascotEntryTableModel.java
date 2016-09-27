package es.uvigo.ei.sing.s2p.gui.mascot;

import java.util.HashMap;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

public class MascotEntryTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private static final Map<Integer, String> MASCOT_ENTRY_FIELDS = 
		new HashMap<Integer, String>();
	
	static {
		MASCOT_ENTRY_FIELDS.put(0, "Title");
		MASCOT_ENTRY_FIELDS.put(1, "Plate position");
		MASCOT_ENTRY_FIELDS.put(2, "Score");
		MASCOT_ENTRY_FIELDS.put(3, "Difference");
		MASCOT_ENTRY_FIELDS.put(4, "MS Coverage");
		MASCOT_ENTRY_FIELDS.put(5, "Accession");
	}
	
	private MascotIdentifications entries;

	public MascotEntryTableModel(MascotIdentifications entries) {
		this.entries = entries;
	}

	@Override
	public String getColumnName(int column) {
		return MASCOT_ENTRY_FIELDS.get(column);
	}
	
	@Override
	public int getRowCount() {
		return entries.size();
	}

	@Override
	public int getColumnCount() {
		return MASCOT_ENTRY_FIELDS.size();
	}
	
	@Override
	public java.lang.Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	};

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		MascotEntry entry = this.entries.get(rowIndex);
		
		switch(columnIndex) {
			case 0: return entry.getTitle();
			case 1: return entry.getPlatePosition();
			case 2: return entry.getMascotScore();
			case 3: return entry.getDifference();
			case 4: return entry.getMsCoverage();
			case 5: return entry.getAccession();
		}
		throw new RuntimeException("Invalid state");
	}
}
