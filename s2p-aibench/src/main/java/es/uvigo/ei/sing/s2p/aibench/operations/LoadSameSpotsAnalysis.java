package es.uvigo.ei.sing.s2p.aibench.operations;

import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsDirectoryLoader.load;

import java.io.File;
import java.io.IOException;
import java.util.List;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.s2p.aibench.datatypes.SameSpotsAnalysisDatatype;
import es.uvigo.ei.sing.s2p.core.entities.SameSpotsThrehold;
import es.uvigo.ei.sing.s2p.core.entities.Sample;

@Operation(
	name = "Load SameSpots report", 
	description = "Loads a Progenesis SameSpots report from the generated .HTML files."
)
public class LoadSameSpotsAnalysis {
	private static final String DEFAULT_P = "0.05";
	private static final String DEFAULT_FOLD = "1.5";

	private File directory;
	private double pValue;
	private double fold;

	@Port(
		direction = Direction.INPUT, 
		name = "SameSpots report directory",
		description = "Progenesis SameSpots report directory containing the .HTM files.",
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

	@Port(direction = Direction.OUTPUT, order = 4)
	public SameSpotsAnalysisDatatype loadData() throws IOException {
		SameSpotsThrehold threshold = new SameSpotsThrehold(pValue, fold);
		List<Sample> samples = load(directory, threshold);

		return new SameSpotsAnalysisDatatype(directory, threshold, samples);
	}
}
