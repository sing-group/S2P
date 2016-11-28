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
package es.uvigo.ei.sing.s2p.gui.mascot;

import java.util.HashMap;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

public class MascotEntryTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private static final Map<Integer, String> MASCOT_ENTRY_FIELDS = 
		new HashMap<Integer, String>();
	
	static {
		MASCOT_ENTRY_FIELDS.put(0, "Title");
		MASCOT_ENTRY_FIELDS.put(1, "Plate position");
		MASCOT_ENTRY_FIELDS.put(2, "Score");
		MASCOT_ENTRY_FIELDS.put(3, "Difference");
		MASCOT_ENTRY_FIELDS.put(4, "MS Coverage");
		MASCOT_ENTRY_FIELDS.put(5, "Protein MW");
		MASCOT_ENTRY_FIELDS.put(6, "Method");
		MASCOT_ENTRY_FIELDS.put(7, "pI-Value");
		MASCOT_ENTRY_FIELDS.put(8, "Accession");
	}
	
	private MascotIdentifications entries;

	public MascotEntryTableModel(MascotIdentifications entries) {
		this.entries = entries;
	}

	@Override
	public String getColumnName(int column) {
		return MASCOT_ENTRY_FIELDS.get(column);
	}
	
	@Override
	public int getRowCount() {
		return entries.size();
	}

	@Override
	public int getColumnCount() {
		return MASCOT_ENTRY_FIELDS.size();
	}
	
	@Override
	public java.lang.Class<?> getColumnClass(int columnIndex) {
		return 	getRowCount() > 0 ?
				getValueAt(0, columnIndex).getClass() : Object.class;
	};

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		MascotEntry entry = this.entries.get(rowIndex);
		
		switch(columnIndex) {
			case 0: return entry.getTitle();
			case 1: return entry.getPlatePosition();
			case 2: return entry.getMascotScore();
			case 3: return entry.getDifference();
			case 4: return entry.getMsCoverage();
			case 5: return entry.getProteinMW();
			case 6: return entry.getMethod();
			case 7: return entry.getpIValue();
			case 8: return entry.getAccession();
		}
		throw new RuntimeException("Invalid state");
	}
}
