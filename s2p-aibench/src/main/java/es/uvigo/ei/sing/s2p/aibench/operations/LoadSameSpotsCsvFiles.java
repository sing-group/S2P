package es.uvigo.ei.sing.s2p.aibench.operations;

import static es.uvigo.ei.sing.s2p.aibench.operations.dialogs.AbstractCsvInputDialog.CSV_FORMAT;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsCsvDirectoryLoader.loadDirectory;

import java.io.File;
import java.util.List;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.s2p.aibench.datatypes.SameSpotsAnalysisDatatype;
import es.uvigo.ei.sing.s2p.core.entities.SameSpotsThrehold;
import es.uvigo.ei.sing.s2p.core.entities.Sample;

@Operation(
	name = "Load SameSpots CSV files", 
	description = "Loads Progenesis SameSpots .CSV files."
)
public class LoadSameSpotsCsvFiles {
	private static final String DEFAULT_P = "1";
	private static final String DEFAULT_FOLD = "0";

	private File directory;
	private double pValue;
	private double fold;
	private CsvFormat csvFormat;

	@Port(
		direction = Direction.INPUT, 
		name = "SameSpots data directory",
		description = "Progenesis SameSpots directory containing the .CSV files.",
		order = 1
	)
	public void setCsvFile(File directory) {
		this.directory = directory;
	}
	
	@Port(
		direction = Direction.INPUT, 
		name = "p-value", 
		defaultValue = DEFAULT_P,
		description = "Maximum p-value allowed for spots.",
		order = 2
	)
	public void setPValue(double pValue) {
		this.pValue = pValue;
	}
	
	@Port(
		direction = Direction.INPUT, 
		name = "Fold",
		defaultValue = DEFAULT_FOLD,
		description = "Minimum fold change allowed for spots.",
		order = 3
	)
	public void setFold(double fold) {
		this.fold = fold;
	}

	@Port(
		direction = Direction.INPUT, 
		name = CSV_FORMAT,
		description = "The format of the CSV file..",
		order = 4
	)
	public void setCsvFormat(CsvFormat csvFormat) {
		this.csvFormat = csvFormat;
	}	
	
	@Port(direction = Direction.OUTPUT, order = 5)
	public SameSpotsAnalysisDatatype loadData() throws Exception {
		SameSpotsThrehold threshold = new SameSpotsThrehold(pValue, fold);
	
		List<Sample> samples = loadDirectory(directory, threshold, csvFormat);

		return new SameSpotsAnalysisDatatype(directory, threshold, samples);
	}
}
