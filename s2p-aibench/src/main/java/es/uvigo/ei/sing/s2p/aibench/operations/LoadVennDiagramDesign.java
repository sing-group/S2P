package es.uvigo.ei.sing.s2p.aibench.operations;

import java.io.File;
import java.io.IOException;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.s2p.aibench.datatypes.VennDiagramDesignDatatype;
import es.uvigo.ei.sing.vda.core.VennDiagramDesign;
import es.uvigo.ei.sing.vda.core.io.SerializationVennDiagramDesignReader;

@Operation(
	name = "Load a Venn Diagram design", 
	description = "Loads a Venn Diagram design from a file."
)
public class LoadVennDiagramDesign {
	private File file;

	@Port(
		direction = Direction.INPUT, 
		name = "Source file", 
		order = 1
	)
	public void setCsvFile(File file) {
		this.file = file;
	}

	@Port(
		direction = Direction.OUTPUT, 
		order = 2
	)
	public VennDiagramDesignDatatype loadData() throws IOException {
		SerializationVennDiagramDesignReader reader =
			new SerializationVennDiagramDesignReader();
		VennDiagramDesign readed = reader.read(file);
		
		return new VennDiagramDesignDatatype(readed.getSets());
	}
}
