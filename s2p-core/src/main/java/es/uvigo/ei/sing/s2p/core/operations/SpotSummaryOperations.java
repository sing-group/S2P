package es.uvigo.ei.sing.s2p.core.operations;

import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.inference.MannWhitneyUTest;
import org.apache.commons.math3.stat.inference.TestUtils;

import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.SpotSummary;

public class SpotSummaryOperations {
	
	public enum DifferentialSpotFunction {
		TTEST1("t-test < 0.05", (s1, s2) -> tTest(s1, s2, 0.05)), 
		TTEST2("t-test < 0.01", (s1, s2) -> tTest(s1, s2, 0.01)), 
		UMANN1("U Mann-Whitney < 0.05", (s1, s2) -> mannUTest(s1, s2, 0.05)), 
		UMANN2("U Mann-Whitney < 0.01", (s1, s2) -> mannUTest(s1, s2, 0.01)), 
		OVERLAPPING("Not overlapping", (s1, s2) -> notOverlapping(s1, s2)); 
		
		private String description;
		private BiFunction<SpotSummary, SpotSummary, Boolean> function;

		DifferentialSpotFunction(String description, BiFunction<SpotSummary, 
			SpotSummary, Boolean> function
		) {
			this.description = description;
			this.function = function;
		}
		
		@Override
		public String toString() {
			return this.description;
		}
		
		public BiFunction<SpotSummary, SpotSummary, Boolean> getFunction() {
			return function;
		}
	};

	public static Set<String> findDifferentialSpots(Set<String> spots, 
		Condition a, Condition b,
		BiFunction<String, Condition, SpotSummary> spotSummarySupplier,
		DifferentialSpotFunction function
	) {
		return findDifferentialSpots(
			spots, a, b, spotSummarySupplier, function.getFunction());
	}
	
	private static Set<String> findDifferentialSpots(Set<String> spots, 
		Condition a, Condition b,
		BiFunction<String, Condition, SpotSummary> spotSummarySupplier,
		BiFunction<SpotSummary, SpotSummary, Boolean> testFunction
	) {
		return spots.stream().filter(spot -> {
			SpotSummary s1 = spotSummarySupplier.apply(spot, a);
			SpotSummary s2 = spotSummarySupplier.apply(spot, b);
			return testFunction.apply(s1, s2);
		}).collect(Collectors.toSet());
	}
	
	private static boolean notOverlapping(SpotSummary s1, SpotSummary s2) {
		double x1 = s1.getMean() - s1.getStdDev();
		double y1 = s1.getMean() + s1.getStdDev();
		double x2 = s2.getMean() - s2.getStdDev();
		double y2 = s2.getMean() + s2.getStdDev();
		
		return y1 < x2 || y2 < x1;
	}
	
	private static boolean tTest(SpotSummary s1, SpotSummary s2, double p) {
		double test = TestUtils.tTest(
			asPrimitiveArray(s1.getSpotValues()), 
			asPrimitiveArray(s2.getSpotValues())
		);
		return test <= p;
	}
	
	private static boolean mannUTest(SpotSummary s1, SpotSummary s2, double p) {
		double test = new MannWhitneyUTest().mannWhitneyUTest(
			asPrimitiveArray(s1.getSpotValues()), 
			asPrimitiveArray(s2.getSpotValues())
		);
		return test <= p;
	}
	
	public static double[] asPrimitiveArray(List<Double> list) {
		double[] toret = new double[list.size()];
		for (int i = 0; i < toret.length; i++) {
			toret[i] = list.get(i);
		}
		return toret;
	}
}
