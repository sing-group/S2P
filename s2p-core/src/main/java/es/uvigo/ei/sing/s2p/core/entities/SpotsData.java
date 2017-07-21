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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SpotsData {

	private List<String> spots;
	private List<String> sampleNames;
	private List<String> sampleLabels;
	private double[][] data;
	
	private List<Condition> conditions;
	
	public SpotsData(List<String> spots, List<String> sampleNames,
		List<String> sampleLabels, double[][] data
	) {
		this.spots = spots;
		this.sampleNames = sampleNames;
		this.sampleLabels = sampleLabels;
		this.data = data;
	}
	
	public List<String> getSpots() {
		return spots;
	}
	
	public List<String> getSampleLabels() {
		return sampleLabels;
	}
	
	public List<String> getSampleNames() {
		return sampleNames;
	}
	
	public double[][] getData() {
		return data;
	}
	
	public List<Condition> getConditions() {
		if (this.conditions == null) {
			List<String> orderedConditionsNames = new LinkedList<>();
			Map<String, List<Sample>> labelSamples = new HashMap<>();
			for (int column = 0; column < getSampleNames().size(); column++) {
				Map<String, Double> sampleSpotsValues = new HashMap<>();

				for (int row = 0; row < getSpots().size(); row++) {
					double value = getData()[row][column];
					if (!Double.isNaN(value)) {
						sampleSpotsValues.put(getSpots().get(row), value);
					}
				}

				String sampleLabel = getSampleLabels().get(column);
				labelSamples.putIfAbsent(sampleLabel, new LinkedList<Sample>());
				if (!orderedConditionsNames.contains(sampleLabel)) {
					orderedConditionsNames.add(sampleLabel);
				}
				labelSamples.get(sampleLabel).add(
					new Sample(
						getSampleNames().get(column), sampleSpotsValues
					));
			}
		
			this.conditions = new LinkedList<Condition>();
			for (String condition : orderedConditionsNames) {
				this.conditions
					.add(new Condition(condition, labelSamples.get(condition)));
			}
		}
		return this.conditions;
	}
}
