/*
 * #%L
 * S2P
 * %%
 * Copyright (C) 2016 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
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

import static es.uvigo.ei.sing.s2p.aibench.util.PortConfiguration.EXTRAS_CSV_FILES;
import static es.uvigo.ei.sing.s2p.aibench.operations.dialogs.AbstractCsvInputDialog.CSV_FORMAT;
import static es.uvigo.ei.sing.s2p.aibench.operations.dialogs.AbstractCsvInputDialog.CSV_FORMAT_DESCRIPTION;
import java.io.File;
import java.io.IOException;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.s2p.aibench.datatypes.MaldiPlateDatatype;
import es.uvigo.ei.sing.s2p.core.entities.MaldiPlate;
import es.uvigo.ei.sing.s2p.core.io.MaldiPlateLoader;

@Operation(
	name = "Import Maldi plate", 
	description = "Imports a Maldi plate from a CSV file."
)
public class ImportMaldiPlate {

	private File file;
	private CsvFormat csvFormat;

	@Port(
		direction = Direction.INPUT, 
		name = "CSV File", 
		description = "A CSV containing the Maldi plate to be imported.",
		allowNull = false,
		order = 1,
		extras = EXTRAS_CSV_FILES
	)
	public void setFile(File file) {
		this.file = file;
	}

	@Port(
		direction = Direction.INPUT, 
		name = CSV_FORMAT,
		description = CSV_FORMAT_DESCRIPTION,
		order = 2
	)
	public void setCsvFormat(CsvFormat csvFormat) {
		this.csvFormat = csvFormat;
	}	

	@Port(
		direction = Direction.OUTPUT, 
		order = 3
	)
	public MaldiPlateDatatype loadPlate() throws IOException,
		ClassNotFoundException 
	{
		MaldiPlate plate = MaldiPlateLoader.importCsv(file, this.csvFormat);
		return new MaldiPlateDatatype(plate, file);
	}
}
