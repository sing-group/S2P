package es.uvigo.ei.sing.s2p.gui.spots.comparison;

import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.operations.SpotSetsComparator;

public class ProteinDataComparisonTableCell {

	private SpotSetsComparator comparison;
	private Sample column;
	private Sample row;

	public ProteinDataComparisonTableCell(Sample row, Sample column) {
		this.row = row;
		this.column = column;
		this.comparison = new SpotSetsComparator(row.getSpots(), column.getSpots());
	}
	
	public String getCellValue() {
		StringBuilder sb = new StringBuilder();
		sb
			.append(comparison.getSet1Unique())
			.append(":")
			.append(comparison.getIntersection())
			.append(":")
			.append(comparison.getSet2Unique());
		return sb.toString();
	}
	
	public String getCellTooltip() {
		StringBuilder sb = new StringBuilder();
		sb
			.append("<html><ul>")
			.append("<li>Unique ")
			.append(row.getName())
			.append(" spots: ")
			.append(comparison.getSet1Unique())
			.append("</li></li>")
			.append("<li>Common ")
			.append(" spots: ")
			.append(comparison.getIntersection())
			.append("</li></li>")
			.append("<li>Unique ")
			.append(column.getName())
			.append(" spots: ")
			.append(comparison.getSet2Unique())
			.append("</li></ul></html>");
		return sb.toString();
	}
}
