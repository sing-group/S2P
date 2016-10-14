package es.uvigo.ei.sing.s2p.core.operations;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.SpotSummary;

public class SpotSummaryOperations {

	public static Set<String> findNotOverlapingSpots(Set<String> spots, Condition a,
		Condition b,
		BiFunction<String, Condition, SpotSummary> spotSummarySupplier
	) {
		return spots.stream().filter(spot -> {
			SpotSummary s1 = spotSummarySupplier.apply(spot, a);
			SpotSummary s2 = spotSummarySupplier.apply(spot, b);
			return notOverlapping(s1, s2);
		}).collect(Collectors.toSet());
	}
	
	private static boolean notOverlapping(SpotSummary s1, SpotSummary s2) {
		double x1 = s1.getMean() - s1.getStdDev();
		double y1 = s1.getMean() + s1.getStdDev();
		double x2 = s2.getMean() - s2.getStdDev();
		double y2 = s2.getMean() + s2.getStdDev();
		
		return y1 < x2 || y2 < x1;
	}
}
