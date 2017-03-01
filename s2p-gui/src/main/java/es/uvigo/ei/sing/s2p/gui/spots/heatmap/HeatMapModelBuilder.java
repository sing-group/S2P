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
package es.uvigo.ei.sing.s2p.gui.spots.heatmap;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;

import es.uvigo.ei.sing.hlfernandez.visualization.JHeatMapModel;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;
import es.uvigo.ei.sing.s2p.gui.table.ExtendedCsvTable;

public class HeatMapModelBuilder {

	private ExtendedCsvTable table;
	private int[] visibleRows;
	private int[] visibleColumns;
	private SpotRenderer spotRenderer;
	private Optional<SpotMascotIdentifications> mascotIdentifications;
	private Map<String, String> sampleLabels;

	private HeatMapModelBuilder(ExtendedCsvTable table,
		SpotRenderer spotRenderer
	) {
		this.table = table;
		this.spotRenderer = spotRenderer;

		this.initData();
	}

	private void initData() {
		this.table.selectAll();
		visibleColumns = this.table.getSelectedColumns();
		visibleRows = this.table.getSelectedRows();
		this.table.clearSelection();
	}

	public static HeatMapModelBuilder createHeatMapModelBuilder(
		ExtendedCsvTable table, SpotRenderer spotRenderer
	) {
		return new HeatMapModelBuilder(table, spotRenderer);
	}

	public HeatMapModelBuilder withMascotIdentifications(
		Optional<SpotMascotIdentifications> identifications
	) {
		this.mascotIdentifications = identifications;
		return this;
	}

	public HeatMapModelBuilder withSampleLabels(Map<Sample, String> sampleLabels) {
		this.sampleLabels = sampleLabels.keySet().stream().collect(Collectors.toMap(Sample::getName, s -> sampleLabels.get(s)));
		return this;
	}
	
	public JHeatMapModel build() {
		return this.createHeatMapModel();
	}

	private JHeatMapModel createHeatMapModel() {
		List<Integer> visibleModelColumns = getVisibleModelIndexes(
			visibleColumns, this.table::convertColumnIndexToModel);
		
		List<Integer> visibleModelRows = getVisibleModelIndexes(
			visibleRows, this.table::convertRowIndexToModel);
		visibleModelColumns.remove(new Integer(0));
			
		double[][] data = getMatrixData(visibleModelRows, visibleModelColumns);
		
		String[] colNames = getColNames(visibleModelColumns, sampleLabels);
		String[] rowNames = getHeatmapRowNames(visibleModelRows, spotRenderer);
			
		return new JHeatMapModel(data, rowNames, colNames);
	}

	private List<Integer> getVisibleModelIndexes(int[] visibleColumns,
		IntUnaryOperator mapper
	) {
		return 	Arrays.stream(visibleColumns).map(mapper).boxed()
				.collect(toList());
	}
		
	private double[][] getMatrixData(List<Integer> visibleRows,
		List<Integer> visibleColumns
	) {
		double[][] data = new double[visibleRows.size()][visibleColumns.size()];
		int currentRow = 0;
		for (int row : visibleRows) {
			int currentColumn = 0;
			for (int col : visibleColumns) {
				data[currentRow][currentColumn++] = (double) 
					this.table.getModel().getValueAt(row, col);
			}
			currentRow++;
		}
		return data;
	}

	private String[] getColNames(List<Integer> visibleModelColumns,
		Map<String, String> sampleLabels
	) {
		List<String> colNames = visibleModelColumns.stream()
			.map(this.table.getModel()::getColumnName)
			.map(sampleName -> {
				if(sampleLabels.containsKey(sampleName)) {
					return sampleName + " [" + sampleLabels.get(sampleName) + "]";
				} else {
					return sampleName;
				}
			})
			.collect(toList());
		
		return colNames.toArray(new String[colNames.size()]);
	}
	
	private String[] getHeatmapRowNames(List<Integer> visibleModelRows,
		SpotRenderer spotRenderer
	) {
		List<String> spots = new LinkedList<String>();
		
		visibleModelRows.forEach(r -> {
			spots.add(this.table.getModel().getValueAt(r, 0).toString());
		});
		
		List<String> rowNames = new LinkedList<String>();
		spots.forEach(spot -> {
			MascotIdentifications identifications =
				this.mascotIdentifications.isPresent() ?
					this.mascotIdentifications.get().get(spot) :
					new MascotIdentifications();

			rowNames.add(
				spotRenderer.getSpotValue(spot, identifications)
			);
		});

		return rowNames.toArray(new String[rowNames.size()]);
	}
}
