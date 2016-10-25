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
	private boolean addCalibrants;

	@Port(
		direction = Direction.INPUT, 
		name = "Rows", 
		defaultValue = "15",
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
		order = 2
	)
	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Calibrants",
		defaultValue = "true",
		description = "Add calibrants.",
		order = 3
	)
	public void setAddCalibrants(boolean addCalibrants) {
		this.addCalibrants = addCalibrants;
	}

	@Port(
		direction = Direction.OUTPUT,
		order = 4
	)
	public MaldiPlateDatatype createPlate() throws IOException {
		MaldiPlateDatatype plate = new MaldiPlateDatatype(this.numRows, this.numColumns);
		if(addCalibrants) {
			plate.addCalibrants();
		}
		return plate;
	}
}
