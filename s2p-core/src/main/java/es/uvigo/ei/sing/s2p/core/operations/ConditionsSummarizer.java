/*
 * #%L
 * S2P Core
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
package es.uvigo.ei.sing.s2p.core.operations;

import static es.uvigo.ei.sing.s2p.core.util.ArrayUtils.doubleArray;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.ConditionSummary;
import es.uvigo.ei.sing.s2p.core.entities.ConditionsSummary;
import es.uvigo.ei.sing.s2p.core.entities.SpotSummary;
import es.uvigo.ei.sing.s2p.core.entities.SpotsData;

public class ConditionsSummarizer {

	public static ConditionsSummary summary(List<String> spots, SpotsData data) {
		ConditionsSummary summary = new ConditionsSummary();
		data.getConditions().forEach(c -> {
			summary.put(c, conditionSummary(spots, c));
		});
		return summary;
	}

	private static ConditionSummary conditionSummary(List<String> spots,
		Condition c
	) {
		ConditionSummary summary = new ConditionSummary();
		spots.forEach(spot -> {
			summary.put(spot, spotSummary(spot, c));
		});
		return summary;
	}

	private static SpotSummary spotSummary(String spot, Condition c) {
		List<Double> spotValues = new LinkedList<Double>();

		c.getSamples().forEach(s -> { 
			if(s.getSpotValues().containsKey(spot)) {
				double spotValue = s.getSpotValues().get(spot);
				if (!Double.isNaN(spotValue)) {
					spotValues.add(spotValue);
				}
			}
		});

		DescriptiveStatistics statistics = 
			new DescriptiveStatistics(doubleArray(spotValues));

		return new SpotSummary(
			(int) statistics.getN(), 
			c.getSamples().size(), 
			statistics.getMean(), 
			statistics.getStandardDeviation(),
			spotValues
		);
	}
}
