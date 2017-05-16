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
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

@Operation(
	name = "Associate Mascot identifications from a HTM Mascot report file", 
	description = "Associates Mascot identifications from a HTM Mascot report file with a Maldi plate."
)
public class AssociateMascotIdentificationsFromReport
	extends AbstractAssociateMascotIdentifications {

	private File htmlFile;

	@Port(
		direction = Direction.INPUT, 
		name = "Protein identifications", 
		description = "A HTM file containing the Mascot report.",
		order = 1,
		extras = EXTRAS_HTML_FILES,
		validateMethod = "validateHtmlFile"
	)
	public void setHtmlFile(File htmlFile) {
		this.htmlFile = htmlFile;
	}

	public void validateHtmlFile(File htmlFile) {
		try {
			load(htmlFile, 0, false);
		} catch (IOException e) {
			throw new IllegalArgumentException("Can't load " + htmlFile.getAbsolutePath());
		}
	}

	@Override
	protected MascotIdentifications getMascotIdentifications() throws IOException {
		return load(htmlFile, minimumMS, removeDuplicates);
	}
}
