package es.uvigo.ei.sing.s2p.aibench.operations;

import static es.uvigo.ei.sing.s2p.aibench.operations.dialogs.AbstractCsvInputDialog.CSV_FORMAT;
import static es.uvigo.ei.sing.s2p.core.io.MascotCsvLoader.load;

import java.io.File;
import java.io.IOException;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.s2p.aibench.datatypes.MascotIdentificationsDatatype;

@Operation(
	name = "Load Mascot identifications", 
	description = "Loads Mascot identifications from a CSV file."
)
public class LoadMascotCsvFile {

	private File file;
	private CsvFormat csvFormat;
	private int minimumMS;
	private boolean removeDuplicates;

	@Port(
		direction = Direction.INPUT, 
		name = "CSV File", 
		description = "Source file (*.CSV).",
		allowNull = false,
		order = 1
	)
	public void setFile(File file) {
		this.file = file;
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
		direction = Direction.INPUT, 
		name = "Minimum Mascot Score", 
		description = "Entries within a lower Mascot Score are not loaded.",
		order = 3,
		defaultValue = "0"
	)
	public void setMinimumMascotScore(int minimumMS) {
		this.minimumMS = minimumMS;
	}
	
	@Port(
		direction = Direction.INPUT, 
		name = "Remove duplicates", 
		description = "Check this option to remove duplicated entries.",
		order = 4,
		defaultValue = "true"
	)
	public void setRemoveDuplicates(boolean removeDuplicates) {
		this.removeDuplicates = removeDuplicates;
	}

	@Port(
		direction = Direction.OUTPUT, 
		order = 5
	)
	public MascotIdentificationsDatatype loadData() throws IOException {
		return new MascotIdentificationsDatatype(
				load(file, csvFormat, minimumMS, removeDuplicates), 
			file, minimumMS);
	}
}
