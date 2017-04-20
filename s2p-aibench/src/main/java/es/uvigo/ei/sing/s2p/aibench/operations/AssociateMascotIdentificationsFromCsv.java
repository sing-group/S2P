package es.uvigo.ei.sing.s2p.aibench.operations;

import static es.uvigo.ei.sing.s2p.aibench.operations.dialogs.AbstractCsvInputDialog.CSV_FORMAT;
import static es.uvigo.ei.sing.s2p.aibench.operations.dialogs.AbstractCsvInputDialog.CSV_FORMAT_DESCRIPTION;
import static es.uvigo.ei.sing.s2p.aibench.util.PortConfiguration.EXTRAS_CSV_FILES;
import static es.uvigo.ei.sing.s2p.core.io.MascotCsvLoader.load;

import java.io.File;
import java.io.IOException;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

@Operation(
	name = "Associate Mascot identifications from a CSV file", 
	description = "Associates Mascot identifications from a CSV file with a Maldi plate."
)
public class AssociateMascotIdentificationsFromCsv
	extends AbstractAssociateMascotIdentifications {

	private File file;
	private CsvFormat csvFormat;

	@Port(
		direction = Direction.INPUT, 
		name = "Protein identifications", 
		description = "A CSV file containing the Mascot identifications.",
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

	@Override
	protected MascotIdentifications getMascotIdentifications()
		throws IOException {
		return load(file, csvFormat, minimumMS, removeDuplicates);
	}
}
