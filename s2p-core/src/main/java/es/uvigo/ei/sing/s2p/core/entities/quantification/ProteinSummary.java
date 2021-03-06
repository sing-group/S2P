/*
 * #%L
 * S2P Core
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
package es.uvigo.ei.sing.s2p.core.entities.quantification;

import static es.uvigo.ei.sing.s2p.core.util.ArrayUtils.doubleArray;

import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class ProteinSummary {

	private int numReplicates;
	private int totalReplicates;
	private List<Double> 			proteinValues;
	private DescriptiveStatistics 	proteinValuesStatistics;
	private List<Double> 			normalizedProteinValues;
	private DescriptiveStatistics 	normalizedProteinValuesStatistics;
	private List<Double> 			proteinMassValues;
	private DescriptiveStatistics 	proteinMassValuesStatistics;

	public ProteinSummary(int totalReplicates, List<Double> proteinValues,
		List<Double> proteinNormalizedValues, List<Double> proteinMassValues
	) {
		this.totalReplicates = totalReplicates;
		this.proteinValuesStatistics = 
			new DescriptiveStatistics(doubleArray(proteinValues));
		this.normalizedProteinValuesStatistics = 
			new DescriptiveStatistics(doubleArray(proteinNormalizedValues));
		this.proteinMassValuesStatistics =
			new DescriptiveStatistics(doubleArray(proteinMassValues));
		this.numReplicates = (int) this.proteinValuesStatistics.getN();
		this.proteinValues = proteinValues;
		this.normalizedProteinValues = proteinNormalizedValues;
		this.proteinMassValues = proteinMassValues;
	}

	public int getNumReplicates() {
		return numReplicates;
	}

	public int getTotalReplicates() {
		return totalReplicates;
	}

	public double getProteinValueMean() {
		return this.proteinValuesStatistics.getMean();
	}

	public double getProteinValueStd() {
		return this.proteinValuesStatistics.getStandardDeviation();
	}

	public double getProteinValueRsd() {
		return getProteinValueStd() / getProteinValueMean();
	}	

	public double getNormalizedProteinValueMean() {
		return this.normalizedProteinValuesStatistics.getMean();
	}

	public double getNormalizedProteinValueStd() {
		return this.normalizedProteinValuesStatistics.getStandardDeviation();
	}

	public double getNormalizedProteinValueRsd() {
		return getNormalizedProteinValueStd() / getNormalizedProteinValueMean();
	}

	public double getProteinMassValueMean() {
		return this.proteinMassValuesStatistics.getMean();
	}

	public double getProteinMassValueStd() {
		return this.proteinMassValuesStatistics.getStandardDeviation();
	}

	public double getProteinMassValueRsd() {
		return getProteinMassValueStd() / getProteinMassValueMean();
	}

	public double getPop() {
		return 100d * (double) getNumReplicates() / (double) getTotalReplicates();
	}

	public List<Double> getProteinValues() {
		return this.proteinValues;
	}
	
	public List<Double> getNormalizedProteinValues() {
		return this.normalizedProteinValues;
	}

	public List<Double> getProteinMassValues() {
		return proteinMassValues;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb
			.append("[SpotSummary] Total = ")
			.append(getTotalReplicates())
			.append(" | Spots = ")
			.append(getNumReplicates())
			.append(" | POP = ")
			.append(getPop());

		return sb.toString();
	}
}
