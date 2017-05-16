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
package es.uvigo.ei.sing.s2p.gui.table.spots;

import java.util.List;
import java.util.function.Function;

import javax.swing.table.TableModel;

import es.uvigo.ei.sing.s2p.gui.table.ExtendedCsvTable;

public class SpotsCsvTable extends ExtendedCsvTable {
	private static final long serialVersionUID = 1L;
	private Function<String, String> spotValue;
	
	public SpotsCsvTable(TableModel dm, Function<String, String> spotValue) {
		super(dm);
		this.spotValue = spotValue;
	}
	
	@Override
	public Object[][] getData(List<Integer> rows, List<Integer> columns) {
		Object[][] data = super.getData(rows, columns);

		if(columns.get(0).intValue() == 0) {
			for(int i = 0; i < data.length; i++) {
				data[i][0] = spotValue.apply(data[i][0].toString());
			}
		}
		
		return data;
	}
}