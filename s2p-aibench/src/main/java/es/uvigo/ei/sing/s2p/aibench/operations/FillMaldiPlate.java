package es.uvigo.ei.sing.s2p.aibench.operations;

import static es.uvigo.ei.sing.s2p.core.operations.MaldiPlateOperations.generateMaldiPlates;
import static javax.swing.SwingUtilities.invokeLater;

import java.util.stream.Stream;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.s2p.aibench.datatypes.MaldiPlateDatatype;
import es.uvigo.ei.sing.s2p.aibench.datatypes.SpotsDataDatatype;
import es.uvigo.ei.sing.s2p.core.entities.MaldiPlate;

@Operation(
	name = "Fill Maldi plate", 
	description = "Allows distributing spots into one or several Maldi plates."
)
public class FillMaldiPlate {
	
	private int numRows;
	private int numColumns;
	private boolean addCalibrants;
	private SpotsDataDatatype spots;
	private boolean random;
	private int numReplicates;

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
		direction = Direction.INPUT,
		name = "Spots data",
		description = "spots data.",
		order = 4
	)
	public void setSpots(SpotsDataDatatype spots) {
		this.spots = spots;
	}
	
	@Port(
		direction = Direction.INPUT, 
		name = "Replicates", 
		defaultValue = "2",
		description = "Number of spot replicates.",
		order = 5
	)
	public void setSpotReplicates(int numReplicates) {
		this.numReplicates = numReplicates;
	}
	
	@Port(
		direction = Direction.INPUT,
		name = "Random",
		defaultValue = "false",
		description = "Randomize spots.",
		order = 6
	)
	public void setRandomizeSpots(boolean random) {
		this.random = random;
		
		invokeLater(this::createPlates);
	}

	private void createPlates()  {
		createMaldiPlates().map(MaldiPlateDatatype::new).forEach(this::putItem);
	}
	
	private Stream<MaldiPlate> createMaldiPlates() {
		return 	generateMaldiPlates(spots.getSpots(), 
			numReplicates, numRows, numColumns, addCalibrants, random)
			.stream();
	}

	private void putItem(MaldiPlateDatatype item) {
		Core.getInstance().getClipboard().putItem(item, item.getName());
	}
}
