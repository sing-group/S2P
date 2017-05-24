/*
 * #%L
 * S2P GUI
 * %%
 * Copyright (C) 2016 - 2017 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
 * 			Reboiro-Jato, Hugo López-Fernández, and Daniel Glez-Peña
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
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
