package es.uvigo.ei.sing.s2p.gui.samples;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;

import es.uvigo.ei.sing.s2p.core.entities.Sample;

public class SamplesComparisonTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private List<Sample> samples;
	private List<String> proteins;

	public SamplesComparisonTableModel(List<Sample> samples) {
		this.samples = samples;
		this.proteins = new LinkedList<String>(this.samples.stream().map(Sample::getSpots).flatMap(Collection::stream).collect(Collectors.toSet()));
	}
	
	@Override
	public String getColumnName(int column) {
		if(column == 0) {
			return "Spot";
		} else {
			return this.samples.get(column - 1).getName();
		}
	}
	
	@Override
	public int getRowCount() {
		return this.proteins.size();
	}

	@Override
	public int getColumnCount() {
		return this.samples.size() + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String protein = this.proteins.get(rowIndex);
		if(columnIndex == 0) {
			return protein;
		} else {
			Double proteinValue = this.samples.get(columnIndex - 1).getSpotValues().get(protein);
			return proteinValue == null ? Double.NaN : proteinValue;
		}
	}

}
