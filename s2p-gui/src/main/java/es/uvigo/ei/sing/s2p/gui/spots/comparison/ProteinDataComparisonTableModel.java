package es.uvigo.ei.sing.s2p.gui.spots.comparison;


import static es.uvigo.ei.sing.s2p.core.entities.Util.getSampleProteins;
import static java.util.Collections.emptySet;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.operations.StringSetComparison;

public class ProteinDataComparisonTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	private Condition condition1;
	private Condition condition2;
	private Optional<Map<String, MascotIdentifications>> mascotIdentifications =
		Optional.empty();

	private boolean showProteinIdentifications = false;

	public ProteinDataComparisonTableModel(Condition condition1, Condition condition2) {
		this.condition1 = condition1;
		this.condition2 = condition2;
	}

	@Override
	public int getRowCount() {
		return this.condition1.getSamples().size() + 1;
	}

	@Override
	public int getColumnCount() {
		return this.condition2.getSamples().size() + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex == 0 && columnIndex == 0) {
			return "";
		} else if(rowIndex == 0) {
			return this.condition2.getSamples().get(columnIndex - 1).getName();
		} else if(columnIndex == 0) {
			return this.condition1.getSamples().get(rowIndex - 1).getName();
		} else {
			Sample row = this.condition1.getSamples().get(rowIndex - 1);
			Sample column = this.condition2.getSamples().get(columnIndex - 1);

			return createProteinDataComparisonTableCell(row, column);
		}
	}

	private ProteinDataComparisonTableCell createProteinDataComparisonTableCell(
		Sample row, Sample column
	) {
		return new ProteinDataComparisonTableCell(row.getName(), column.getName(), getComparison(row, column));
	}

	private StringSetComparison getComparison(Sample row, Sample column) {
		return new StringSetComparison(getComparisonItems(row), getComparisonItems(column));
	}

	private Set<String> getComparisonItems(Sample sample) {
		if(this.showProteinIdentifications) {
			if(this.mascotIdentifications.isPresent()) {
				return getSampleProteins(sample, this.mascotIdentifications.get());
			} else {
				return emptySet();
			}
		} else {
			return sample.getSpots();
		}
	}

	public void setComparison(Condition condition1, Condition condition2) {
		this.condition1 = condition1;	
		this.condition2 = condition2;
		
		this.fireTableStructureChanged();
	}

	public Sample[] getSamplesAt(int rowIndex, int colIndex) {
		Sample row = this.condition1.getSamples().get(rowIndex - 1);
		Sample column = this.condition2.getSamples().get(colIndex - 1);
		
		return new Sample[] { row, column };
	}

	public void setMascotIdentifications(
		Map<String, MascotIdentifications> identifications
	) {
		this.mascotIdentifications  = Optional.of(identifications);
	}

	public void setShowProteinIdentifications(boolean show) {
		this.showProteinIdentifications = show;
		
		this.fireTableDataChanged();
		this.fireTableStructureChanged();
	}

	public void removeMascotIdentifications() {
		this.mascotIdentifications = Optional.empty();
	}
}
