package es.uvigo.ei.sing.s2p.aibench.datatypes;

import java.io.File;

import es.uvigo.ei.aibench.core.datatypes.annotation.Datatype;
import es.uvigo.ei.aibench.core.datatypes.annotation.Property;
import es.uvigo.ei.aibench.core.datatypes.annotation.Structure;
import es.uvigo.ei.sing.s2p.core.entities.SpotsData;

@Datatype(
	structure = Structure.COMPLEX, 
	namingMethod = "getName", 
	renameable = true,
	clipboardClassName = "Spots Data (*.CSV)",
	autoOpen = true
)
public class SpotsDataDatatype extends SpotsData {

	private File file;

	public SpotsDataDatatype(SpotsData data, File f) {
		super(
			data.getSpots(), 
			data.getSampleNames(),
			data.getSampleLabels(), 
			data.getData()
		);
		this.file = f;
	}

	public String getName() {
		return file.getName();
	}
	
	@Property(name = "File")
	public String getFile() {
		return file.getAbsolutePath();
	}
}
