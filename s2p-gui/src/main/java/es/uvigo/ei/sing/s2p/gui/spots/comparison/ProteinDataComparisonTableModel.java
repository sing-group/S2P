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


import static es.uvigo.ei.sing.s2p.core.entities.Util.getSampleProteins;
import static java.util.Collections.emptySet;

import java.util.Optional;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;
import es.uvigo.ei.sing.s2p.core.operations.StringSetComparison;

public class ProteinDataComparisonTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	private Condition condition1;
	private Condition condition2;
	private Optional<SpotMascotIdentifications> mascotIdentifications =
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
		SpotMascotIdentifications identifications
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
