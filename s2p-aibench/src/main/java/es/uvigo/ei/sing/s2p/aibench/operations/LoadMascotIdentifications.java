package es.uvigo.ei.sing.s2p.aibench.operations;

import static es.uvigo.ei.sing.s2p.core.io.MascotProjectLoader.load;

import java.io.File;
import java.io.IOException;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.s2p.aibench.datatypes.MascotIdentificationsDatatype;

@Operation(
	name = "Load Mascot identifications", 
	description = "Loads Mascot identifications from a .HTM file."
)
public class LoadMascotIdentifications {
	private File htmlFile;

	@Port(
		direction = Direction.INPUT, 
		name = "Protein identifications", 
		description = "A .HTM file containing the Mascot report.",
		order = 1
	)
	public void setHtmlFile(File htmlFile) {
		this.htmlFile = htmlFile;
	}

	@Port(
		direction = Direction.OUTPUT, 
		order = 2
	)
	public MascotIdentificationsDatatype loadData() throws IOException {
		return new MascotIdentificationsDatatype(load(htmlFile), htmlFile);
	}
}
