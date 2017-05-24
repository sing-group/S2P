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
import es.uvigo.ei.aibench.core.datatypes.annotation.Property;
import es.uvigo.ei.aibench.core.datatypes.annotation.Structure;
import es.uvigo.ei.sing.s2p.core.entities.SpotsData;

@Datatype(
	structure = Structure.COMPLEX, 
	namingMethod = "getName", 
	renameable = true,
	clipboardClassName = "Spots Data (*.CSV)",
	autoOpen = true
)
public class SpotsDataDatatype extends SpotsData {

	private File file;

	public SpotsDataDatatype(SpotsData data, File f) {
		super(
			data.getSpots(), 
			data.getSampleNames(),
			data.getSampleLabels(), 
			data.getData()
		);
		this.file = f;
	}

	public String getName() {
		return file.getName();
	}
	
	@Property(name = "File")
	public String getFile() {
		return file.getAbsolutePath();
	}
}
