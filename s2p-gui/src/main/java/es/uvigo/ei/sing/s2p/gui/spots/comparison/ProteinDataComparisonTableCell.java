package es.uvigo.ei.sing.s2p.gui.spots.comparison;

import es.uvigo.ei.sing.s2p.core.operations.StringSetComparison;

public class ProteinDataComparisonTableCell {

	private String set1;
	private String set2;
	private StringSetComparison comparison;

	public ProteinDataComparisonTableCell(String set1, String set2, StringSetComparison comparison) {
		this.set1 = set1;
		this.set2 = set2;
		this.comparison = comparison;
	}
	
	public String getCellValue() {
		StringBuilder sb = new StringBuilder();
		sb
			.append(comparison.getSet1UniqueSize())
			.append(":")
			.append(comparison.getIntersectionSize())
			.append(":")
			.append(comparison.getSet2UniqueSize());
		return sb.toString();
	}
	
	public String getCellTooltip() {
		StringBuilder sb = new StringBuilder();
		sb
			.append("<html><ul>")
			.append("<li>Unique ")
			.append(set1)
			.append(" spots: ")
			.append(comparison.getSet1UniqueSize())
			.append("</li></li>")
			.append("<li>Common ")
			.append(" spots: ")
			.append(comparison.getIntersectionSize())
			.append("</li></li>")
			.append("<li>Unique ")
			.append(set2)
			.append(" spots: ")
			.append(comparison.getSet2UniqueSize())
			.append("</li></ul></html>");
		
		return sb.toString();
	}
}
