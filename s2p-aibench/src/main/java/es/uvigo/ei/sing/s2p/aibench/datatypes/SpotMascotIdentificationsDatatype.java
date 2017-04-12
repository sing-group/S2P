package es.uvigo.ei.sing.s2p.aibench.datatypes;

import java.io.File;

import es.uvigo.ei.aibench.core.datatypes.annotation.Datatype;
import es.uvigo.ei.aibench.core.datatypes.annotation.Structure;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;

@Datatype(
	structure = Structure.COMPLEX, 
	namingMethod = "getName", 
	renameable = true,
	clipboardClassName = "Spot identifications",
	autoOpen = true
)
public class SpotMascotIdentificationsDatatype extends SpotMascotIdentifications {
	private static int INSTANCES = 0;
	private String name;
	private File file;

	public SpotMascotIdentificationsDatatype(SpotMascotIdentifications data) {
		super(data);

		this.name = "Spot identifications " + (++INSTANCES);
	}

	public SpotMascotIdentificationsDatatype(SpotMascotIdentifications data,
		File f
	) {
		super(data);

		this.file = f;
		this.name = this.file.getName();
	}

	public String getName() {
		return name;
	}
}
