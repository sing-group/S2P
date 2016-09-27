package es.uvigo.ei.sing.s2p.aibench.datatypes;

import java.io.File;
import java.util.List;

import es.uvigo.ei.aibench.core.datatypes.annotation.Datatype;
import es.uvigo.ei.aibench.core.datatypes.annotation.Property;
import es.uvigo.ei.aibench.core.datatypes.annotation.Structure;
import es.uvigo.ei.sing.s2p.core.entities.SameSpotsThrehold;
import es.uvigo.ei.sing.s2p.core.entities.Sample;

@Datatype(
	structure = Structure.COMPLEX, 
	namingMethod = "getName", 
	renameable = true ,
	clipboardClassName = "SameSpot analysis",
	autoOpen = true
)
public class SameSpotsAnalysisDatatype {

	private List<Sample> samples;
	private File directory;
	private SameSpotsThrehold threshold;

	public SameSpotsAnalysisDatatype(File directory,
			SameSpotsThrehold threshold, List<Sample> samples
	) {
		this.directory = directory;
		this.threshold = threshold;
		this.samples = samples;
	}
	
	public String getName() {
		return directory.getName();
	}
	
	@Property(name = "Directory")
	public String getFile() {
		return directory.getAbsolutePath();
	}
	
	@Property(name = "p-value")
	public double getPValue() {
		return threshold.getP();
	}
	
	@Property(name = "fold")
	public double getFold() {
		return threshold.getFold();
	}
	
	public List<Sample> getSamples() {
		return samples;
	}
}
