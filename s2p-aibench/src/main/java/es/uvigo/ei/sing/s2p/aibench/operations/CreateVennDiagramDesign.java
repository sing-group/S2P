package es.uvigo.ei.sing.s2p.aibench.operations;

import java.io.IOException;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.s2p.aibench.datatypes.VennDiagramDesignDatatype;

@Operation(
	name = "Design venn diagram", 
	description = "Allows designing a venn diagram."
)
public class CreateVennDiagramDesign {
	
	@Port(
		direction = Direction.OUTPUT, 
		order = 1
)
	public VennDiagramDesignDatatype loadData() throws IOException {
		return new VennDiagramDesignDatatype();
	}
}
