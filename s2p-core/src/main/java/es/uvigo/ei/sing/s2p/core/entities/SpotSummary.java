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
package es.uvigo.ei.sing.s2p.core.entities;

import java.util.List;

public class SpotSummary {
	
	private int numSamples;
	private int totalSamples;
	private double mean;
	private double std;
	private List<Double> spotValues;

	public SpotSummary(int numSamples, int totalSamples, double mean,
		double std, List<Double> spotValues
	) {
		this.numSamples = numSamples;
		this.totalSamples = totalSamples;
		this.mean = mean;
		this.std = std;
		this.spotValues = spotValues;
	}
	
	public int getNumSamples() {
		return numSamples;
	}
	
	public int getTotalSamples() {
		return totalSamples;
	}
	
	public double getPop() {
		return 100d * (double) getNumSamples() / (double) getTotalSamples();
	}
	
	public double getMean() {
		return mean;
	}

	public double getStdDev() {
		return std;
	}

	public List<Double> getSpotValues() {
		return spotValues;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb
			.append("[SpotSummary] Total = ")
			.append(getTotalSamples())
			.append(" | Spots = ")
			.append(getNumSamples())
			.append(" | Mean = ")
			.append(getMean())
			.append(" | Std = ")
			.append(getStdDev())
			.append(" | POP = ")
			.append(getPop());

		return sb.toString();
	}
}
