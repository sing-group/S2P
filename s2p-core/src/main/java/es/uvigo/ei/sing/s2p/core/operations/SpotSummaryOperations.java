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
package es.uvigo.ei.sing.s2p.core.operations;

import static es.uvigo.ei.sing.math.statistical.StatisticsTestsUtils.correct;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.inference.MannWhitneyUTest;
import org.apache.commons.math3.stat.inference.TestUtils;

import es.uvigo.ei.sing.math.statistical.corrections.FDRCorrection;
import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.SpotSummary;

public class SpotSummaryOperations {
	
	public enum DifferentialSpotFunction {
		TTEST("t-test", (s1, s2) -> tTest(s1, s2)), 
		UMANN("U Mann-Whitney", (s1, s2) -> mannUTest(s1, s2)), 
		OVERLAPPING("Not overlapping", (s1, s2) -> notOverlapping(s1, s2)); 
		
		private String description;
		private BiFunction<SpotSummary, SpotSummary, Double> function;

		DifferentialSpotFunction(String description, BiFunction<SpotSummary, 
			SpotSummary, Double> function
		) {
			this.description = description;
			this.function = function;
		}
		
		@Override
		public String toString() {
			return this.description;
		}
		
		public BiFunction<SpotSummary, SpotSummary, Double> getFunction() {
			return function;
		}
	};

	public static Set<String> findDifferentialSpots(Set<String> spots, 
		Condition a, Condition b,
		BiFunction<String, Condition, SpotSummary> spotSummarySupplier,
		DifferentialSpotFunction function, double p, boolean correctP
	) {
		return findDifferentialSpots(
			spots, a, b, spotSummarySupplier, function.getFunction(), p, correctP);
	}
	
	private static Set<String> findDifferentialSpots(Set<String> spots, 
		Condition a, Condition b,
		BiFunction<String, Condition, SpotSummary> spotSummarySupplier,
		BiFunction<SpotSummary, SpotSummary, Double> testFunction,
		double p, boolean correctP
	) {
		Map<String, Double> pValues = spots.stream().collect(Collectors.toMap(
			spot -> spot, 
			spot -> {
				SpotSummary s1 = spotSummarySupplier.apply(spot, a);
				SpotSummary s2 = spotSummarySupplier.apply(spot, b);
				double pValue = testFunction.apply(s1, s2);
				return pValue;	
			}
		));

		if(correctP) {
			try {
				pValues = correct(new FDRCorrection(), pValues);
			} catch (InterruptedException e) {
			}
		}

		return 	pValues.entrySet().stream()
				.filter((e) -> e.getValue() < p)
				.map(Entry::getKey)
				.collect(Collectors.toSet());
	}
	
	private static double notOverlapping(SpotSummary s1, SpotSummary s2) {
		double x1 = s1.getMean() - s1.getStdDev();
		double y1 = s1.getMean() + s1.getStdDev();
		double x2 = s2.getMean() - s2.getStdDev();
		double y2 = s2.getMean() + s2.getStdDev();
		
		return (y1 < x2 || y2 < x1) ? -1 : +1;
	}
	
	private static double tTest(SpotSummary s1, SpotSummary s2) {
		return TestUtils.tTest(
			asPrimitiveArray(s1.getSpotValues()), 
			asPrimitiveArray(s2.getSpotValues())
		);
	}
	
	private static double mannUTest(SpotSummary s1, SpotSummary s2) {
		return new MannWhitneyUTest().mannWhitneyUTest(
			asPrimitiveArray(s1.getSpotValues()), 
			asPrimitiveArray(s2.getSpotValues())
		);
	}
	
	public static double[] asPrimitiveArray(List<Double> list) {
		double[] toret = new double[list.size()];
		for (int i = 0; i < toret.length; i++) {
			toret[i] = list.get(i);
		}
		return toret;
	}
}