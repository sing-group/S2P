package es.uvigo.ei.sing.s2p.aibench.datatypes;

import java.io.File;

import es.uvigo.ei.aibench.core.datatypes.annotation.Datatype;
import es.uvigo.ei.aibench.core.datatypes.annotation.Structure;
import es.uvigo.ei.sing.s2p.core.entities.MaldiPlate;

@Datatype(
	structure = Structure.SIMPLE, 
	namingMethod = "getName", 
	renameable = true,
	clipboardClassName = "Maldi plate",
	autoOpen = true
)
public class MaldiPlateDatatype extends MaldiPlate {
	private static final long serialVersionUID = 1L;

	private static int INSTANCES = 0;
	private String name;
	
	public MaldiPlateDatatype(int rows, int cols) {
		super(rows, cols);
		this.name = "Plate " + (++INSTANCES);
	}
	
	public MaldiPlateDatatype(MaldiPlate plate, File f) {
		super(plate.getRowNames(), plate.getColNames(), plate.getData(), plate.getInfo());
		this.name = f.getName();
	}

	public String getName() {
		return this.name;
	}
}
