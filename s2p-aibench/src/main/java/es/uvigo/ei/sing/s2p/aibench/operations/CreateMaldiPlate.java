package es.uvigo.ei.sing.s2p.aibench.operations;

import java.io.IOException;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.s2p.aibench.datatypes.MaldiPlateDatatype;

@Operation(
	name = "Create Maldi plate", 
	description = "Allows designing a Maldi plate."
)
public class CreateMaldiPlate {
	
	
	private int numRows;
	private int numColumns;

	@Port(
		direction = Direction.INPUT, 
		name = "Rows", 
		defaultValue = "16",
		description = "Number of rows.",
		order = 1
	)
	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}
	
	@Port(
		direction = Direction.INPUT, 
		name = "Columns", 
		defaultValue = "24",
		description = "Number of columns.",
		order = 1
	)
	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}
	
	@Port(
		direction = Direction.OUTPUT, 
		order = 3
	)
	public MaldiPlateDatatype createPlate() throws IOException {
		return new MaldiPlateDatatype(this.numRows, this.numColumns);
	}
}
