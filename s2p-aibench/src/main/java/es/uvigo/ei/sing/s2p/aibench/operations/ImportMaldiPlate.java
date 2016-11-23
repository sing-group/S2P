package es.uvigo.ei.sing.s2p.aibench.operations;

import static es.uvigo.ei.sing.s2p.aibench.operations.dialogs.AbstractCsvInputDialog.CSV_FORMAT;
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
	description = "Imports a Maldi plate from a CSV file."
)
public class ImportMaldiPlate {

	private File file;
	private CsvFormat csvFormat;

	@Port(
		direction = Direction.INPUT, 
		name = "CSV File", 
		description = "A CSV containing the Maldi plate to be imported.",
		allowNull = false,
		order = 1
	)
	public void setFile(File file) {
		this.file = file;
	}

	@Port(
		direction = Direction.INPUT, 
		name = CSV_FORMAT,
		description = "The CSV format of the CSV files. S2P includes two "
			+ "preconfigured formats for Excel and Open/LibreOffice "
			+ "applications. In case you need to specify a custom CSV format, "
			+ "you can select the Custom option, which allows you to configure "
			+ "the CSV format.",
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
