package es.uvigo.ei.sing.s2p.gui.spots.comparison;

import javax.swing.table.AbstractTableModel;

import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.Sample;

public class ProteinDataComparisonTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private Condition condition1;
	private Condition condition2;

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
//			Set<String> intersection = new HashSet<>(row.getProteins());
			
//			intersection.retainAll(column.getProteins());
			
//			StringBuilder sb = new StringBuilder();
//			sb
//				.append(row.getProteins().size() - intersection.size())
//				.append(":")
//				.append(intersection.size())
//				.append(":")
//				.append(column.getProteins().size() - intersection.size());
			return new ProteinDataComparisonTableCell(row, column);
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
}
