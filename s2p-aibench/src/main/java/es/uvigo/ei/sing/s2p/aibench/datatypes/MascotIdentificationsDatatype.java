package es.uvigo.ei.sing.s2p.aibench.datatypes;

import java.io.File;

import es.uvigo.ei.aibench.core.datatypes.annotation.Datatype;
import es.uvigo.ei.aibench.core.datatypes.annotation.Property;
import es.uvigo.ei.aibench.core.datatypes.annotation.Structure;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

@Datatype(
	structure = Structure.COMPLEX, 
	namingMethod = "getName", 
	renameable = true 
)
public class MascotIdentificationsDatatype extends MascotIdentifications {
	private static final long serialVersionUID = 1L;
	
	private File file;

	public MascotIdentificationsDatatype(MascotIdentifications data, File f) {
		this.addAll(data);
		this.file = f;
	}

	public String getName() {
		return file.getName();
	}
	
	@Property(name = "File")
	public String getFile() {
		return file.getAbsolutePath();
	}

	@Property(name = "Total identifications")
	public int getIdentifications() {
		return this.size();
	}
}
