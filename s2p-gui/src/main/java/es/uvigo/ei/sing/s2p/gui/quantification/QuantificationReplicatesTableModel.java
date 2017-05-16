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
package es.uvigo.ei.sing.s2p.gui.quantification;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.uvigo.ei.sing.s2p.core.entities.quantification.ProteinQuantification;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationReplicate;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationSample;

public class QuantificationReplicatesTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	private QuantificationDataset dataset;
	private List<QuantificationReplicatesTableRow> data;

	public QuantificationReplicatesTableModel(QuantificationDataset dataset) {
		this.dataset = dataset;
		this.createData();
	}

	private void createData() {
		this.data = new LinkedList<>();
		for(QuantificationSample sample : this.dataset) {
			int replicatesCount = 1;
			for(QuantificationReplicate replicate : sample.getReplicates()) {
				
				for (ProteinQuantification pQ : replicate.getProteins()) {
					this.data.add(
						new QuantificationReplicatesTableRow(
							sample.getName(), 
							Integer.toString(replicatesCount), 
							pQ.getProtein(), 
							pQ.getValue(),
							pQ.getNormalizedValue(),
							pQ.getNormalizedValue() * sample.getProteinMass()
						)
					);
				}
				replicatesCount++;
			}
		}
	}

	@Override
	public int getRowCount() {
		return this.data == null ? 0 : this.data.size();
	}

	@Override
	public int getColumnCount() {
		return QuantificationReplicatesTableRow.COLUMNS;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return QuantificationReplicatesTableRow.getColumnName(columnIndex);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return QuantificationReplicatesTableRow.getColumnClass(columnIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.data.get(rowIndex).getValueAt(columnIndex);
	}
	
	static class QuantificationReplicatesTableRow {
		public static final int COLUMNS = 6;
		
		private String sample;
		private String replicate;
		private String protein;
		private double value;
		private double normalizedValue;
		private double proteinMass;

		public QuantificationReplicatesTableRow(String sample, String replicate,
			String protein, double value, double normalizedValue, 
			double proteinMass
		) {
			this.sample = sample;
			this.replicate = replicate;
			this.protein = protein;
			this.value = value;
			this.normalizedValue = normalizedValue;
			this.proteinMass = proteinMass;
		}
		
		public Object getValueAt(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return this.sample;
			case 1:
				return this.replicate;
			case 2:
				return this.protein;
			case 3:
				return this.value;
			case 4:
				return this.normalizedValue;
			case 5:
				return this.proteinMass;
			}
			throw new IllegalStateException();
		}

		public static String getColumnName(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return "Sample";
			case 1:
				return "Replicate";
			case 2:
				return "Protein";
			case 3:
				return "Quantification";
			case 4:
				return "Normalized quantification";
			case 5:
				return "Protein mass";
			}
			throw new IllegalStateException();
		}

		public static Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return String.class;
			case 1:
				return String.class;
			case 2:
				return String.class;
			case 3:
				return Double.class;
			case 4:
				return Double.class;
			case 5:
				return Double.class;
			}
			throw new IllegalStateException();
		}
	}
}
