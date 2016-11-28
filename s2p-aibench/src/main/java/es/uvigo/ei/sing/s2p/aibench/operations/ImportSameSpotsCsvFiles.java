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

import static es.uvigo.ei.sing.s2p.aibench.operations.dialogs.AbstractCsvInputDialog.CSV_FORMAT;
import static es.uvigo.ei.sing.s2p.aibench.operations.dialogs.AbstractCsvInputDialog.CSV_FORMAT_DESCRIPTION;
import static es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsCsvDirectoryLoader.loadDirectory;

import java.io.File;
import java.util.List;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.s2p.aibench.datatypes.SameSpotsAnalysisDatatype;
import es.uvigo.ei.sing.s2p.core.entities.SameSpotsThrehold;
import es.uvigo.ei.sing.s2p.core.entities.Sample;

@Operation(
	name = "Import SameSpots CSV files", 
	description = "Imports a set of CSV files containing spots data obtained with Progenesis SameSpots."
)
public class ImportSameSpotsCsvFiles {
	private static final String DEFAULT_P = "1";
	private static final String DEFAULT_FOLD = "0";

	private File directory;
	private double pValue;
	private double fold;
	private CsvFormat csvFormat;

	@Port(
		direction = Direction.INPUT, 
		name = "SameSpots CSV directory",
		description = "The directory containing the CSV files generated by the "
			+ "Protenesis SameSpots software. Please, note that you must "
			+ "select a directory and not the individual files.",
		order = 1,
		extras="selectionMode=directories"
	)
	public void setCsvFile(File directory) {
		this.directory = directory;
	}
	
	@Port(
		direction = Direction.INPUT, 
		name = "p-value", 
		defaultValue = DEFAULT_P,
		description = "The maximum p-value allowed for spots to be loaded. "
			+ "Spots within a higher p-value will not be loaded.",
		order = 2
	)
	public void setPValue(double pValue) {
		this.pValue = pValue;
	}
	
	@Port(
		direction = Direction.INPUT, 
		name = "Fold",
		defaultValue = DEFAULT_FOLD,
		description = "The minimum fold allowed for spots to be loaded. "
			+ "Spots within a lower fold will not be loaded.",
		order = 3
	)
	public void setFold(double fold) {
		this.fold = fold;
	}

	@Port(
		direction = Direction.INPUT, 
		name = CSV_FORMAT,
		description = CSV_FORMAT_DESCRIPTION,
		order = 4
	)
	public void setCsvFormat(CsvFormat csvFormat) {
		this.csvFormat = csvFormat;
	}	
	
	@Port(direction = Direction.OUTPUT, order = 5)
	public SameSpotsAnalysisDatatype loadData() throws Exception {
		SameSpotsThrehold threshold = new SameSpotsThrehold(pValue, fold);
	
		List<Sample> samples = loadDirectory(directory, threshold, csvFormat);

		return new SameSpotsAnalysisDatatype(directory, threshold, samples);
	}
}
