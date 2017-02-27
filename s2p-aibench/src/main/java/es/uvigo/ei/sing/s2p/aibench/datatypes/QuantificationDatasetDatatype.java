package es.uvigo.ei.sing.s2p.aibench.datatypes;

import java.io.File;

import es.uvigo.ei.aibench.core.datatypes.annotation.Datatype;
import es.uvigo.ei.aibench.core.datatypes.annotation.Property;
import es.uvigo.ei.aibench.core.datatypes.annotation.Structure;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;

@Datatype(
	structure = Structure.COMPLEX, 
	namingMethod = "getName", 
	renameable = true,
	clipboardClassName = "Mascot Quantifications",
	autoOpen = true
)
public class QuantificationDatasetDatatype extends QuantificationDataset {
	private static final long serialVersionUID = 1L;
	private File directory;

	public QuantificationDatasetDatatype(QuantificationDataset load,
		File directory
	) {
		super(load);
		this.directory = directory;
	}
	
	public String getName() {
		return directory.getName();
	}

	@Property(name = "Directory")
	public String getFile() {
		return directory.getAbsolutePath();
	}

	@Property(name = "Samples")
	public int getSamplesCount() {
		return this.size();
	}
}
