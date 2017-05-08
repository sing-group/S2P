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

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.table.AbstractTableModel;

import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;

public class SpotMascotIdentificationsTableModel extends AbstractTableModel
	implements Observer
{
	private static final long serialVersionUID = 1L;

	private static final Map<Integer, String> TABLE_FIELDS = new HashMap<>();

	static {
		TABLE_FIELDS.put(0, "Spot");
		TABLE_FIELDS.put(1, "Title");
		TABLE_FIELDS.put(2, "Plate position");
		TABLE_FIELDS.put(3, "Score");
		TABLE_FIELDS.put(4, "Difference");
		TABLE_FIELDS.put(5, "MS Coverage");
		TABLE_FIELDS.put(6, "Protein MW");
		TABLE_FIELDS.put(7, "Method");
		TABLE_FIELDS.put(8, "pI-Value");
		TABLE_FIELDS.put(9, "Accession");
		TABLE_FIELDS.put(10, "Source");
	}

	private static final Map<Integer, Class<?>> TABLE_CLASSES = new HashMap<>();

	static {
		TABLE_CLASSES.put(0, String.class);
		TABLE_CLASSES.put(1, String.class);
		TABLE_CLASSES.put(2, String.class);
		TABLE_CLASSES.put(3, Integer.class);
		TABLE_CLASSES.put(4, Integer.class);
		TABLE_CLASSES.put(5, Integer.class);
		TABLE_CLASSES.put(6, Double.class);
		TABLE_CLASSES.put(7, String.class);
		TABLE_CLASSES.put(8, Double.class);
		TABLE_CLASSES.put(9, String.class);
		TABLE_CLASSES.put(10, File.class);
	}

	private Set<String> spots;
	private SpotMascotIdentifications spotIdentifications;
	private List<Object[]> rows;
	private Map<Integer, MascotEntry> rowToEntry;

	public SpotMascotIdentificationsTableModel(Set<String> spots,
		SpotMascotIdentifications spotIdentifications
	) {
		this.spots = spots;
		this.spotIdentifications = spotIdentifications;
		this.spotIdentifications.addObserver(this);

		this.initMatrixData();
	}

	private void initMatrixData() {
		rows = new LinkedList<Object[]>();
		rowToEntry = new HashMap<>();
		final AtomicInteger rowCount = new AtomicInteger(0);
		spots.forEach(s -> {
			this.spotIdentifications.get(s)
				.forEach(identification -> {
					Object[] row = new Object[TABLE_FIELDS.size()];
					row[0] = s;
					row[1] = identification.getTitle();
					row[2] = identification.getPlatePosition();
					row[3] = new Integer(identification.getMascotScore());
					row[4] = new Integer(identification.getDifference());
					row[5] = new Integer(identification.getMsCoverage());
					row[6] = new Double(identification.getProteinMW());
					row[7] = identification.getMethod();
					row[8] = new Double(identification.getpIValue());
					row[9] = identification.getAccession();
					row[10] = identification.getSource();
					rows.add(row);
					rowToEntry.put(rowCount.getAndIncrement(), identification);
			});
		});
		this.fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public int getColumnCount() {
		return TABLE_FIELDS.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return TABLE_FIELDS.get(columnIndex);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return TABLE_CLASSES.get(columnIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return rows.get(rowIndex)[columnIndex];
	}

	@Override
	public void update(Observable o, Object arg) {
		this.initMatrixData();
	}

	public String getSpotAtRow(int row) {
		return this.getValueAt(row, 0).toString();
	}

	public MascotEntry getMascotEntryAtRow(int row) {
		return rowToEntry.get(row);
	}
}
