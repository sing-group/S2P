package es.uvigo.ei.sing.s2p.aibench.datatypes;

import java.io.File;

import es.uvigo.ei.aibench.core.datatypes.annotation.Datatype;
import es.uvigo.ei.aibench.core.datatypes.annotation.Property;
import es.uvigo.ei.aibench.core.datatypes.annotation.Structure;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;

@Datatype(
	structure = Structure.COMPLEX, 
	namingMethod = "getName", 
	renameable = true,
	clipboardClassName = "Spot Identifications",
	autoOpen = true
)
public class SpotMascotIdentificationsDatatype extends SpotMascotIdentifications {
	private File file;

	public SpotMascotIdentificationsDatatype(SpotMascotIdentifications data, File f) {
		super(data);
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
