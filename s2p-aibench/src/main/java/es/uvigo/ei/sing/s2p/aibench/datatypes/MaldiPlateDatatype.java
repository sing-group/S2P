package es.uvigo.ei.sing.s2p.aibench.datatypes;

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
	private static int INSTANCES = 0;
	private int instanceNumber;
	
	public MaldiPlateDatatype(int rows, int cols) {
		super(rows, cols);
		this.instanceNumber = ++INSTANCES;
	}
	
	public String getName() {
		return "Plate " + this.instanceNumber;
	}
}
