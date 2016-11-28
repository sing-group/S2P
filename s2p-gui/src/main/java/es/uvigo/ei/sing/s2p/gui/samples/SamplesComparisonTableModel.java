/*
 * #%L
 * S2P GUI
 * %%
 * Copyright (C) 2016 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
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
