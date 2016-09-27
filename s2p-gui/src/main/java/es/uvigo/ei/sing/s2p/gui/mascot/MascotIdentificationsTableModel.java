package es.uvigo.ei.sing.s2p.gui.mascot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

public class MascotIdentificationsTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	private static final Map<Integer, String> TABLE_FIELDS = 
		new HashMap<Integer, String>();
		
	static {
		TABLE_FIELDS.put(0, "Spot");
		TABLE_FIELDS.put(1, "Protein");
		TABLE_FIELDS.put(2, "Plate position");
		TABLE_FIELDS.put(3, "Score");
		TABLE_FIELDS.put(4, "Difference");
		TABLE_FIELDS.put(5, "MS Coverage");
		TABLE_FIELDS.put(6, "Accession");
	}
	
	private Set<String> spots;
	private Map<String, MascotIdentifications> spotIdentifications;
	private List<Object[]> rows = new LinkedList<Object[]>();

	public MascotIdentificationsTableModel(Set<String> spots,
		Map<String, MascotIdentifications> spotIdentifications
	) {
		this.spots = spots;
		this.spotIdentifications = spotIdentifications;
		
		this.initMatrixData();
	}

	private void initMatrixData() {
		spots.forEach(s -> {
			this.spotIdentifications.getOrDefault(s, new MascotIdentifications())
				.forEach(identification -> {
				Object[] row = new Object[TABLE_FIELDS.size()];
				row[0] = s;
				row[1] = identification.getTitle(); 
				row[2] = identification.getPlatePosition(); 
				row[3] = new Integer(identification.getMascotScore()); 
				row[4] = new Integer(identification.getDifference()); 
				row[5] = new Integer(identification.getMsCoverage()); 
				row[6] = identification.getAccession();
				rows.add(row);
			});
		});
		this.fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public int getColumnCount() {
		return TABLE_FIELDS.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return TABLE_FIELDS.get(columnIndex);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return rows.get(rowIndex)[columnIndex];
	}
}
