package es.uvigo.ei.sing.s2p.aibench.operations;

import java.io.File;
import java.io.IOException;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.s2p.aibench.datatypes.MaldiPlateDatatype;
import es.uvigo.ei.sing.s2p.core.entities.MaldiPlate;
import es.uvigo.ei.sing.s2p.core.io.MaldiPlateLoader;

@Operation(
	name = "Import Maldi plate", 
	description = "Allows importing a Maldi plate from a CSV file."
)
public class ImportMaldiPlate {

	private File file;
	private CsvFormat csvFormat;

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
		name = "CSV format", 
		description = "The format of the CSV file..",
		order = 2
	)
	public void setCsvFormat(CsvFormat csvFormat) {
		this.csvFormat = csvFormat;
	}	

	@Port(
		direction = Direction.OUTPUT, 
		order = 3
	)
	public MaldiPlateDatatype loadPlate() throws IOException,
		ClassNotFoundException 
	{
		MaldiPlate plate = MaldiPlateLoader.importCsv(file, this.csvFormat);
		return new MaldiPlateDatatype(plate, file);
	}
}
