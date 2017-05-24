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
package es.uvigo.ei.sing.s2p.aibench.datatypes;

import java.io.File;

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
	private static final long serialVersionUID = 1L;

	private static int INSTANCES = 0;
	private String name;
	
	public MaldiPlateDatatype(int rows, int cols, Positions rowsPositions,
		Positions columnsPositions
	) {
		super(rows, cols, rowsPositions, columnsPositions);
		this.name = "Plate " + (++INSTANCES);
	}

	public MaldiPlateDatatype(MaldiPlate plate) {
		super(plate);
		this.name = "Plate " + (++INSTANCES);
	}

	public MaldiPlateDatatype(MaldiPlate plate, File f) {
		super(plate);
		this.name = f.getName();
	}

	public String getName() {
		return this.name;
	}
}
