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
package es.uvigo.ei.sing.s2p.gui.spots.summary;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.table.AbstractTableModel;

import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.ConditionsSummary;
import es.uvigo.ei.sing.s2p.core.entities.SpotSummary;
import es.uvigo.ei.sing.s2p.core.entities.SpotsData;
import es.uvigo.ei.sing.s2p.core.operations.ConditionsSummarizer;

public class ConditionsSummaryTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	private static final SpotSummary NA = new SpotSummary(
		0, 0, Double.NaN, Double.NaN, Collections.emptyList());
	
	private static final Map<Integer, String> SUMMARY_FIELD = new HashMap<>();
	
	static {
		SUMMARY_FIELD.put(0, "Presence (%)");
		SUMMARY_FIELD.put(1, "Presence");
		SUMMARY_FIELD.put(2, "Mean");
		SUMMARY_FIELD.put(3, "Std. Dev.");
	}

	private static final int OFFSET = 1;
	private static final int SUMMARY_FIELDS = 4;
	
	private List<String> spots;
	private ConditionsSummary summary;
	private SpotsData data;

	public ConditionsSummaryTableModel(SpotsData data) {
		this.data = data;
		this.spots = data.getSpots();
		this.summary = ConditionsSummarizer.summary(data.getSpots(), data);
	}

	@Override
	public int getRowCount() {
		return data.getSpots().size();
	}

	@Override
	public int getColumnCount() {
		return OFFSET + data.getConditions().size() * SUMMARY_FIELDS;
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == 0) {
			return "Spot";
		} else {
			return SUMMARY_FIELD.get((columnIndex - 1) % SUMMARY_FIELDS);
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return spots.get(rowIndex);
		} else {
			int fieldIndex = (columnIndex - 1) % SUMMARY_FIELDS;
			Condition c = getConditionAt(columnIndex).get();
			String spot = spots.get(rowIndex);

			return field(fieldIndex, getSpotSummary(spot, c));
		}
	}
	
	public SpotSummary getSpotSummary(String spot, Condition c) {
		return this.summary.get(c).getOrDefault(spot, NA);
	}

	public Optional<Condition> getConditionAt(int columnIndex) {
		if(columnIndex == 0) {
			return Optional.empty();
		} else {
			int conditionIndex = (columnIndex - 1) / SUMMARY_FIELDS;
			return Optional.of(data.getConditions().get(conditionIndex));
		}
	}

	public String getSpotAt(int rowIndex) {
		return this.spots.get(rowIndex);
	}

	private static Object field(int field, SpotSummary spotSummary) {
		int samples = spotSummary.getTotalSamples();
		int presentSamples = spotSummary.getNumSamples();

		switch (field) {
		case 0:
			return Double.valueOf(spotSummary.getPop());
		case 1:
			return presentSamples + "/" + samples;
		case 2:
			return Double.valueOf(spotSummary.getMean());
		case 3:
			return Double.valueOf(spotSummary.getStdDev());
		default:
			return null;
		}
	}
}
