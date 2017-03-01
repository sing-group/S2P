package es.uvigo.ei.sing.s2p.aibench.operations;

import static es.uvigo.ei.sing.s2p.aibench.operations.dialogs.AbstractCsvInputDialog.CSV_FORMAT;
import static es.uvigo.ei.sing.s2p.aibench.operations.dialogs.AbstractCsvInputDialog.CSV_FORMAT_DESCRIPTION;
import static es.uvigo.ei.sing.s2p.aibench.util.PortConfiguration.EXTRAS_CSV_FILES;
import static es.uvigo.ei.sing.s2p.core.io.SpotMascotIdentificationsCsvLoader.load;

import java.io.File;
import java.io.IOException;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.s2p.aibench.datatypes.SpotMascotIdentificationsDatatype;

@Operation(
	name = "Load spot Mascot identifications", 
	description = "Loads spot Mascot identifications from a CSV file."
)
public class LoadSpotMascotIdentificationsCsvFile {

	private File file;
	private CsvFormat csvFormat;

	@Port(
		direction = Direction.INPUT, 
		name = "Spot protein identifications", 
		description = "A CSV file containing the Spot mascot identifications.",
		allowNull = false,
		order = 1,
		extras = EXTRAS_CSV_FILES
	)
	public void setFile(File file) {
		this.file = file;
	}

	@Port(
		direction = Direction.INPUT, 
		name = CSV_FORMAT,
		description = CSV_FORMAT_DESCRIPTION,
		order = 2
	)
	public void setCsvFormat(CsvFormat csvFormat) {
		this.csvFormat = csvFormat;
	}	

	@Port(
		direction = Direction.OUTPUT, 
		order = 3
	)
	public SpotMascotIdentificationsDatatype loadData() throws IOException {
		return new SpotMascotIdentificationsDatatype(
			load(file, csvFormat), file);
	}
}
