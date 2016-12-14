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