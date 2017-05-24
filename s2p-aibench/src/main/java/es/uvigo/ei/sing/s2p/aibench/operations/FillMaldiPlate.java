/*
 * #%L
 * S2P
 * %%
 * Copyright (C) 2016 - 2017 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
 * 			Reboiro-Jato, Hugo López-Fernández, and Daniel Glez-Peña
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package es.uvigo.ei.sing.s2p.aibench.operations;

import static es.uvigo.ei.sing.s2p.aibench.operations.dialogs.AbstractMaldiPlateCreationDialog.ROWS_POSITIONS;
import static es.uvigo.ei.sing.s2p.aibench.operations.dialogs.AbstractMaldiPlateCreationDialog.COLUMNS_POSITIONS;

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
import es.uvigo.ei.sing.s2p.core.entities.MaldiPlate.Positions;

@Operation(
	name = "Fill plate", 
	description = "Distributes spots data into one or several Maldi plates."
)
public class FillMaldiPlate {
	
	private int numRows;
	private Positions rowsPositions;
	private int numColumns;
	private Positions columnsPositions;
	private boolean addCalibrants;
	private SpotsDataDatatype spots;
	private boolean random;
	private int numReplicates;

	@Port(
		direction = Direction.INPUT, 
		name = "Rows", 
		defaultValue = "15",
		description = "The number of matrix rows.",
		order = 1
	)
	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}
	
	@Port(
		direction = Direction.INPUT, 
		name = ROWS_POSITIONS, 
		description = "The type of rows (i.e. numeric or letters).",
		order = 2
	)
	public void setRowPositions(Positions rowsPositions) {
		this.rowsPositions = rowsPositions;
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Columns", 
		defaultValue = "24",
		description = "The number of matrix columns.",
		order = 3
	)
	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}

	@Port(
		direction = Direction.INPUT, 
		name = COLUMNS_POSITIONS, 
		description = "The type of columns (i.e. numeric or letters).",
		order = 4
	)
	public void setColumnsPositions(Positions columnsType) {
		this.columnsPositions = columnsType;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Calibrants",
		defaultValue = "true",
		description = "Whether calibrants must be added to the matrix or not.",
		order = 5
	)
	public void setAddCalibrants(boolean addCalibrants) {
		this.addCalibrants = addCalibrants;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Spots data",
		description = "Loaded spots data from the clipboard.",
		order = 6
	)
	public void setSpots(SpotsDataDatatype spots) {
		this.spots = spots;
	}
	
	@Port(
		direction = Direction.INPUT, 
		name = "Replicates", 
		defaultValue = "2",
		description = "The number of spot replicates that must be included in "
			+ "the Maldi plate.",
		order = 7
	)
	public void setSpotReplicates(int numReplicates) {
		this.numReplicates = numReplicates;
	}
	
	@Port(
		direction = Direction.INPUT,
		name = "Random",
		defaultValue = "false",
		description = "Whether spots must be distributed randomly or not.",
		order = 8
	)
	public void setRandomizeSpots(boolean random) {
		this.random = random;
		
		invokeLater(this::createPlates);
	}

	private void createPlates()  {
		createMaldiPlates().map(MaldiPlateDatatype::new).forEach(this::putItem);
	}
	
	private Stream<MaldiPlate> createMaldiPlates() {
		return 	generateMaldiPlates(
					spots.getSpots(), numReplicates, numRows, numColumns, 
					rowsPositions, columnsPositions, addCalibrants, random
				).stream();
	}

	private void putItem(MaldiPlateDatatype item) {
		Core.getInstance().getClipboard().putItem(item, item.getName());
	}
}
