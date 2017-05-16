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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import es.uvigo.ei.sing.s2p.core.entities.quantification.ProteinQuantification;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationReplicate;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationSample;

public class QuantificationReplicatesByProteinTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	private static final int COLUMNS_PER_REPLICATE = 2;
	private static final String COL_QUANTIFICATION = "";
	private static final String COL_NORMALIZED_QUANTIFICATION = " [Normalized]";

	private QuantificationDataset dataset;
	private List<String> proteins;
	private List<QuantificationReplicate> replicates;
	private Map<QuantificationReplicate, String> replicatesConditions;
	private Map<QuantificationReplicate, String> replicatesNames;

	public QuantificationReplicatesByProteinTableModel(QuantificationDataset dataset) {
		this.dataset = dataset;
		this.createData();
	}

	private void createData() {
		this.proteins = new LinkedList<>(this.dataset.getProteins());
		this.replicates = new LinkedList<>();
		this.replicatesConditions = new HashMap<>();
		this.replicatesNames = new HashMap<>();
		for(QuantificationSample sample : this.dataset) {
			int replicatesCount = 1;
			for(QuantificationReplicate replicate : sample.getReplicates()) {
				this.replicates.add(replicate);
				if(sample.getCondition().isPresent()) {
					this.replicatesConditions.put(replicate,  sample.getCondition().get());
				}
				this.replicatesNames.put(replicate, sample.getName() + " " + (replicatesCount++));
			}
		}
	}

	@Override
	public int getRowCount() {
		return this.proteins == null ? 0 : this.proteins.size();
	}

	@Override
	public int getColumnCount() {
		return 1 + getReplicatesColumnCount();
	}

	private int getReplicatesColumnCount() {
		return this.replicates.size() * COLUMNS_PER_REPLICATE;
	}

	@Override
	public String getColumnName(int columnIndex) {
		if(columnIndex == 0) {
			return "Protein";
		} else {
			return getColumnNameForReplicate(columnIndex);
		}
	}

	private String getColumnNameForReplicate(int columnIndex) {
		String replicateName = this.replicatesNames.get(getReplicateAtIndex(columnIndex));
		int replicateColumn = getReplicateColumnIndex(columnIndex);
		if(replicateColumn == 0) {
			return replicateName + COL_QUANTIFICATION;
		} else {
			return replicateName +	COL_NORMALIZED_QUANTIFICATION;
		}
	}

	private QuantificationReplicate getReplicateAtIndex(int columnIndex) {
		return this.replicates.get(getReplicateIndex(columnIndex));
	}

	private int getReplicateIndex(int columnIndex) {
		return (columnIndex - 1) / COLUMNS_PER_REPLICATE;
	}

	private int getReplicateColumnIndex(int columnIndex) {
		return (columnIndex - 1) % COLUMNS_PER_REPLICATE;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String protein = this.proteins.get(rowIndex);
		if(columnIndex == 0) {
			return protein;
		} else {
			return getQuanfificationForReplicate(columnIndex, protein);
		}
	}

	private Object getQuanfificationForReplicate(int columnIndex,
		String protein
	) {
		QuantificationReplicate replicate = getReplicateAtIndex(columnIndex);
		int replicateColumn = getReplicateColumnIndex(columnIndex);
		if (replicate.findProtein(protein).isPresent()) {
			ProteinQuantification pQ = replicate.findProtein(protein).get();
			if (replicateColumn == 0) {
				return pQ.getValue();
			} else {
				return pQ.getNormalizedValue();
			}
		} else {
			return Double.NaN;
		}
	}
}
