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
		order = 1,
		extras="selectionMode=files"
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
