package es.uvigo.ei.sing.s2p.aibench.operations;

import static es.uvigo.ei.sing.s2p.aibench.operations.dialogs.AbstractMaldiPlateCreationDialog.ROWS_POSITIONS;
import static es.uvigo.ei.sing.s2p.aibench.operations.dialogs.AbstractMaldiPlateCreationDialog.COLUMNS_POSITIONS;

import java.io.IOException;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.s2p.aibench.datatypes.MaldiPlateDatatype;
import es.uvigo.ei.sing.s2p.core.entities.MaldiPlate.Positions;

@Operation(
	name = "Create Maldi plate", 
	description = "Allows designing a Maldi plate."
)
public class CreateMaldiPlate {
	
	private int numRows;
	private Positions rowsPositions;
	private int numColumns;
	private Positions columnsPositions;
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
		name = ROWS_POSITIONS, 
		description = "Type of rows.",
		order = 2
	)
	public void setRowPositions(Positions rowsPositions) {
		this.rowsPositions = rowsPositions;
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Columns", 
		defaultValue = "24",
		description = "Number of columns.",
		order = 3
	)
	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}

	@Port(
		direction = Direction.INPUT, 
		name = COLUMNS_POSITIONS, 
		description = "Type of rows.",
		order = 4
	)
	public void setColumnsPositions(Positions columnsPositions) {
		this.columnsPositions = columnsPositions;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Calibrants",
		defaultValue = "true",
		description = "Add calibrants.",
		order = 5
	)
	public void setAddCalibrants(boolean addCalibrants) {
		this.addCalibrants = addCalibrants;
	}

	@Port(
		direction = Direction.OUTPUT,
		order = 6
	)
	public MaldiPlateDatatype createPlate() throws IOException {
		MaldiPlateDatatype plate = new MaldiPlateDatatype(
			this.numRows, this.numColumns, this.rowsPositions, this.columnsPositions);

		if(addCalibrants) {
			plate.addCalibrants();
		}

		return plate;
	}
}
