package es.uvigo.ei.sing.s2p.core.operations;

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

	private static double[] doubleArray(List<Double> spotValues) {
		double[] toret = new double[spotValues.size()];
		for (int i = 0; i < spotValues.size(); i++) {
			toret[i] = spotValues.get(i).doubleValue();
		}
		return toret;
	}
}
