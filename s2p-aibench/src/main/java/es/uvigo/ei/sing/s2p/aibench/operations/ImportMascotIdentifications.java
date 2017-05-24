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

import static es.uvigo.ei.sing.s2p.aibench.util.PortConfiguration.EXTRAS_HTML_FILES;
import static es.uvigo.ei.sing.s2p.core.io.MascotProjectLoader.load;

import java.io.File;
import java.io.IOException;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.s2p.aibench.datatypes.MascotIdentificationsDatatype;

@Operation(
	name = "Import Mascot identifications", 
	description = "Imports Mascot identifications from a HTM Mascot report file."
)
public class ImportMascotIdentifications {
	private File htmlFile;
	private int minimumMS;
	private boolean removeDuplicates;

	@Port(
		direction = Direction.INPUT, 
		name = "Protein identifications", 
		description = "A HTM file containing the Mascot report.",
		order = 1,
		extras = EXTRAS_HTML_FILES
	)
	public void setHtmlFile(File htmlFile) {
		this.htmlFile = htmlFile;
	}
	
	@Port(
		direction = Direction.INPUT, 
		name = "Minimum Mascot Score", 
		description = "The minimum Mascot Score for identifications to be "
			+ "loaded. Entries within a lower Mascot Score are not loaded.",
		order = 2,
		defaultValue = "0"
	)
	public void setMinimumMascotScore(int minimumMS) {
		this.minimumMS = minimumMS;
	}
	
	@Port(
		direction = Direction.INPUT, 
		name = "Remove duplicates", 
		description = "whether duplicated identifications must be skipped. "
				+ "Check this option to remove duplicated identifications.",
		order = 3,
		defaultValue = "true"
	)
	public void setRemoveDuplicates(boolean removeDuplicates) {
		this.removeDuplicates = removeDuplicates;
	}
	
	@Port(
		direction = Direction.OUTPUT, 
		order = 4
	)
	public MascotIdentificationsDatatype loadData() throws IOException {
		return new MascotIdentificationsDatatype(
				load(htmlFile, minimumMS, removeDuplicates), 
			htmlFile);
	}
}
