package es.uvigo.ei.sing.s2p.gui.mascot;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

public class MascotIdentificationsSummaryTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	private static final Map<Integer, String> TABLE_FIELDS = 
		new HashMap<Integer, String>();
	
	static {
		TABLE_FIELDS.put(0, "Analyzed spots");
		TABLE_FIELDS.put(1, "Identified spots");
		TABLE_FIELDS.put(2, "Unique proteins identified");
	}
	private static Map<Integer, String> tableValues = new HashMap<>();
	
	private Set<String> spots;
	private Map<String, MascotIdentifications> spotIdentifications;

	public MascotIdentificationsSummaryTableModel(Set<String> spots,
			Map<String, MascotIdentifications> spotIdentifications) {
		this.spots = spots;
		this.spotIdentifications = spotIdentifications;
		
		this.initData();
	}

	private void initData() {
		tableValues.put(0, String.valueOf(spots.size()));
		System.err.println("\nSpot identifications map\n" + spotIdentifications.keySet());
		tableValues.put(1, identifiedSpotsCount());
		tableValues.put(2, uniqueProteinsCount());
	}

	private String identifiedSpotsCount() {
		Set<String> unidentifiedSpots = new HashSet<>(spots);
		unidentifiedSpots.removeAll(spotIdentifications.keySet());
		return String.valueOf(spots.size() - unidentifiedSpots.size());
	}

	private String uniqueProteinsCount() {
		return 	String.valueOf(this.spotIdentifications.keySet().stream()
				.filter(spot -> spots.contains(spot))
				.map(spot -> this.spotIdentifications.get(spot))
				.flatMap(Collection::stream).map(MascotEntry::getTitle)
				.distinct().count());
	}

	@Override
	public int getRowCount() {
		return 3;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnIndex == 0 ? "" : "Value";
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return this.getValueAt(0, columnIndex).getClass();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0) {
			return TABLE_FIELDS.get(rowIndex);
		} else {
			return tableValues.get(rowIndex);
		}
	}
}
