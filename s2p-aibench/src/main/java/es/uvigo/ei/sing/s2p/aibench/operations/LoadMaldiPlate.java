package es.uvigo.ei.sing.s2p.aibench.operations;

import java.io.File;
import java.io.IOException;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.s2p.aibench.datatypes.MaldiPlateDatatype;
import es.uvigo.ei.sing.s2p.core.entities.MaldiPlate;
import es.uvigo.ei.sing.s2p.core.io.MaldiPlateLoader;

@Operation(
	name = "Load Maldi plate", 
	description = "Loads a Maldi plate from a MPL file."
)
public class LoadMaldiPlate {

	private File file;

	@Port(
		direction = Direction.INPUT, 
		name = "File",
		description = "A MPL file containing the Maldi plate.",
		allowNull = false,
		order = 1,
		extras="selectionMode=files"
	)
	public void setFile(File file) {
		this.file = file;
	}
	
	@Port(
		direction = Direction.OUTPUT, 
		order = 2
	)
	public MaldiPlateDatatype loadPlate() throws IOException,
		ClassNotFoundException 
	{
		MaldiPlate plate = MaldiPlateLoader.readFile(file);
		return new MaldiPlateDatatype(plate, file);
	}
}
