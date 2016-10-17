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
import es.uvigo.ei.sing.s2p.gui.table.ExtendedCsvTable;

public class HeatMapModelBuilder {

	private ExtendedCsvTable table;
	private int[] visibleRows;
	private int[] visibleColumns;
	private SpotRenderer spotRenderer;
	private Optional<Map<String, MascotIdentifications>> mascotIdentifications;
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
		Optional<Map<String, MascotIdentifications>> identifications
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
					this.mascotIdentifications.get()
					.getOrDefault(spot, new MascotIdentifications()) :
					new MascotIdentifications();

			rowNames.add(
				spotRenderer.getSpotValue(spot, identifications)
			);
		});

		return rowNames.toArray(new String[rowNames.size()]);
	}
}
