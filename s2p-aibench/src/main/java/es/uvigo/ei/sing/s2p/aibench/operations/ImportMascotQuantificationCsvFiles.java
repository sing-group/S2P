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

import static es.uvigo.ei.sing.s2p.core.io.quantification.QuantificationCsvDatasetLoader.load;

import java.io.File;
import java.io.IOException;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.s2p.aibench.datatypes.QuantificationDatasetDatatype;
import es.uvigo.ei.sing.s2p.core.entities.quantification.MascotQuantificationMethod;
import es.uvigo.ei.sing.s2p.core.operations.quantification.Normalization;
import es.uvigo.ei.sing.s2p.core.operations.quantification.NormalizationFactor;
import es.uvigo.ei.sing.s2p.core.operations.quantification.NormalizationFactorEnum;
import es.uvigo.ei.sing.s2p.core.operations.quantification.NormalizationStrategy;
import es.uvigo.ei.sing.s2p.core.operations.quantification.comparison.ComparisonMode;

@Operation(
	name = "Import Mascot quantifications", 
	description = "Imports Mascot quantifications from a set of CSV files."
)
public class ImportMascotQuantificationCsvFiles {
	private File directory;
	private MascotQuantificationMethod quantificationMethod;
	private NormalizationStrategy normalizationStrategy;
	private NormalizationFactor normalizationFactor;
	private ComparisonMode comparisonMode;

	@Port(
		direction = Direction.INPUT, 
		name = "Quantifications CSV directory",
		description = "The directory containing the CSV files generated by the "
			+ "Mascot quantification report. Please, note that you must "
			+ "select a directory and not the individual files.",
		order = 1,
		extras="selectionMode=directories"
	)
	public void setDirectory(File directory) {
		this.directory = directory;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Mascot quantification method",
		description = "The quantification method used in Mascot.",
		order = 2
	)
	public void setMascotQuantificationMethod(MascotQuantificationMethod quantificationMethod) {
		this.quantificationMethod = quantificationMethod;
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Normalization level", 
		description = "The normalization level at which it should be applied.",
		order = 3
	)
	public void setNormalizationStrategy(Normalization normalization) {
		this.normalizationStrategy = normalization.getStrategy();
	}	

	@Port(
		direction = Direction.INPUT, 
		name = "Normalization factor", 
		description = "The normalization factor to use.",
		order = 4
	)
	public void setNormalizationFactor(NormalizationFactorEnum normalizationFactor) {
		this.normalizationFactor = normalizationFactor.getNormalizationFactor();
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Condition comparison", 
		description = "The protein value to use.",
		order = 5
	)
	public void setConditionComparisonMode(ComparisonMode comparisonMode) {
		this.comparisonMode = comparisonMode;
	}

	@Port(
		direction = Direction.OUTPUT, 
		order = 6
	)
	public QuantificationDatasetDatatype loadData() throws IOException {
		return new QuantificationDatasetDatatype(
			load(directory, quantificationMethod, normalizationStrategy, normalizationFactor),
			comparisonMode,
			directory
		);
	}
}
