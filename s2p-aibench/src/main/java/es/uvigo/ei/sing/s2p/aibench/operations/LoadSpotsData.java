package es.uvigo.ei.sing.s2p.aibench.operations;

import static es.uvigo.ei.sing.s2p.aibench.operations.dialogs.AbstractCsvInputDialog.CSV_FORMAT;
import static es.uvigo.ei.sing.s2p.core.io.SpotsDataLoader.load;

import java.io.File;
import java.io.IOException;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.s2p.aibench.datatypes.SpotsDataDatatype;

@Operation(
	name = "Load spots data",
	description = "Loads spots data from a .CSV file."
)
public class LoadSpotsData {
	private File csvFile;
	private CsvFormat csvFormat;

	@Port(
		direction = Direction.INPUT, 
		name = "Input data", 
		description = "A .CSV containing the spots data.",
		order = 1
	)
	public void setCsvFile(File csvFile) {
		this.csvFile = csvFile;
	}

	@Port(
		direction = Direction.INPUT, 
		name = CSV_FORMAT,
		description = "The format of the CSV file.",
		order = 2
	)
	public void setCsvFormat(CsvFormat csvFormat) {
		this.csvFormat = csvFormat;
	}

	@Port(
		direction = Direction.OUTPUT, 
		order = 3
	)
	public SpotsDataDatatype loadData() throws IOException {
		return new SpotsDataDatatype(load(csvFile, csvFormat), csvFile);
	}
}
