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

import static es.uvigo.ei.sing.s2p.aibench.util.PortConfiguration.EXTRAS_MPL_FILES;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.Workbench;
import es.uvigo.ei.sing.s2p.aibench.datatypes.SpotMascotIdentificationsDatatype;
import es.uvigo.ei.sing.s2p.core.entities.MaldiPlate;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;
import es.uvigo.ei.sing.s2p.core.io.MaldiPlateLoader;
import es.uvigo.ei.sing.s2p.core.operations.SpotMascotEntryPositionJoiner;

public abstract class AbstractAssociateMascotIdentifications {

	protected int minimumMS;
	protected boolean removeDuplicates;
	protected File maldiPlateFile;
	protected SpotMascotIdentifications spotIdentifications;

	@Port(
		direction = Direction.INPUT, 
		name = "Minimum Mascot Score", 
		description = "The minimum Mascot Score for identifications to be "
			+ "loaded. Entries within a lower Mascot Score are not loaded.",
		order = 3,
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
		order = 4,
		defaultValue = "true"
	)
	public void setRemoveDuplicates(boolean removeDuplicates) {
		this.removeDuplicates = removeDuplicates;
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Maldi plate",
		description = "A MPL file containing the Maldi plate.",
		allowNull = false,
		order = 5,
		extras = EXTRAS_MPL_FILES
	)
	public void setMaldiPlateFile(File maldiPlateFile) {
		this.maldiPlateFile = maldiPlateFile;
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Spot identifications",
		description = "Optionally, a previously loaded Spot identifications to add the new data.",
		allowNull = true,
		order = 6
	)
	public void setSpotIdentifications(
		SpotMascotIdentificationsDatatype spotIdentifications
	) {
		this.spotIdentifications = spotIdentifications;

		this.loadData();
	}
	
	public void loadData() {

		MascotIdentifications mascotIdentifications = null;
		try {
			mascotIdentifications = getMascotIdentifications();
		} catch (IOException e) {
			showError("An error ocurred when loading Mascot identifications.", e);
			return;
		}

		MaldiPlate plate = null;
		try {
			plate = MaldiPlateLoader.readFile(maldiPlateFile);
		} catch (ClassNotFoundException | IOException e) {
			showError("An error ocurred when loading the Maldi plate from " + maldiPlateFile.getAbsolutePath(), e);
			return;
		}

		SpotMascotIdentifications loadedSpotIdentifications = 
			SpotMascotEntryPositionJoiner.join(plate.asMap(), mascotIdentifications);

		if(spotIdentifications != null) {
			spotIdentifications.merge(loadedSpotIdentifications);
		} else {
			SpotMascotIdentificationsDatatype datatype =
				new SpotMascotIdentificationsDatatype(loadedSpotIdentifications);
			Core.getInstance().getClipboard().putItem(datatype, datatype.getName());
		}
	}

	protected abstract MascotIdentifications getMascotIdentifications() throws IOException;

	protected void showError(String errorMessage, Exception e) {
		JOptionPane.showMessageDialog(
			Workbench.getInstance().getMainFrame(),  
			errorMessage + "\n " + e.getMessage(), 
			"Error", JOptionPane.ERROR_MESSAGE
		);	
	}
}
