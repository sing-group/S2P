package es.uvigo.ei.sing.s2p.core.operations.quantification;

import static es.uvigo.ei.sing.s2p.core.util.StatisticsTestsUtils.correct;
import static es.uvigo.ei.sing.s2p.core.util.ArrayUtils.doubleArray;
import static es.uvigo.ei.sing.s2p.core.util.CombinationUtils.allPairs;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.stat.inference.TestUtils;

import es.uvigo.ei.sing.math.statistical.corrections.FDRCorrection;
import es.uvigo.ei.sing.s2p.core.entities.Pair;
import es.uvigo.ei.sing.s2p.core.entities.quantification.ProteinSummary;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationCondition;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationConditionSummary;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationConditionsComparison;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationConditionsComparisons;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationConditionsSummary;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;

public class QuantificationsConditionsTest {
	
	/**
	 * Creates all possible comparisons between the conditions in
	 * {@code dataset}. For each pair of conditions, a t-test is performed for
	 * each protein in order to compare the normalized values in all samples of
	 * each condition.
	 * 
	 * @param summary a {@code QuantificationDataset}.
	 * @return a {@code QuantificationConditionsComparisons}.
	 */
	public static QuantificationConditionsComparisons compare(
		QuantificationDataset dataset
	) {
		return compare(QuantificationConditionsSummarizer.summary(dataset));
	}

	/**
	 * Creates all possible comparisons between the conditions in
	 * {@code summary}. For each pair of conditions, a t-test is performed for
	 * each protein in order to compare the normalized values in all samples of
	 * each condition.
	 * 
	 * @param summary a {@code QuantificationConditionsSummary}.
	 * @return a {@code QuantificationConditionsComparisons}.
	 */
	public static QuantificationConditionsComparisons compare(
		QuantificationConditionsSummary summary
	) {
		return new QuantificationConditionsComparisons(
			compare(allPairs(summary.getConditions()), summary));
	}

	private static Collection<QuantificationConditionsComparison> compare(
		List<Pair<QuantificationCondition, 
		QuantificationCondition>> conditionPairs, 
		QuantificationConditionsSummary summary
	) {
		List<QuantificationConditionsComparison> comparisons = new LinkedList<>();
		conditionPairs.forEach(cP -> comparisons.add(compare(cP, summary)));
		return comparisons;
	}

	private static QuantificationConditionsComparison compare(
		Pair<QuantificationCondition, QuantificationCondition> cP, 
		QuantificationConditionsSummary summary
	) {
		Set<String> proteins = cP.getFirst().getProteins();
		proteins.addAll(cP.getSecond().getProteins());
		QuantificationConditionSummary firstSummary = summary.get(cP.getFirst());
		QuantificationConditionSummary secondSummary = summary.get(cP.getSecond());
		Map<String, Double> proteinTest = new HashMap<>();

		proteins.forEach(p -> {
			ProteinSummary firstProteinSummary = firstSummary.get(p);
			ProteinSummary secondProteinSummary = secondSummary.get(p);
			try {
				double tTest = TestUtils.tTest(
					doubleArray(firstProteinSummary.getNormalizedProteinValues()),
					doubleArray(secondProteinSummary.getNormalizedProteinValues())
				);
				proteinTest.put(p, tTest);
			} catch(NumberIsTooSmallException e) {
				
			}
		});

		Map<String, Double> correctedProteinTest = 
			correct(new FDRCorrection(), proteinTest);

		return new QuantificationConditionsComparison(
			cP.getFirst(), cP.getSecond(), proteinTest, correctedProteinTest);
	}
}
