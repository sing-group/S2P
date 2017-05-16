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
package es.uvigo.ei.sing.s2p.core.operations.quantification.comparison;

import static es.uvigo.ei.sing.s2p.core.util.CombinationUtils.allPairs;
import static es.uvigo.ei.sing.s2p.core.util.StatisticsTestsUtils.correct;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.exception.NumberIsTooSmallException;

import es.uvigo.ei.sing.math.statistical.corrections.FDRCorrection;
import es.uvigo.ei.sing.s2p.core.entities.Pair;
import es.uvigo.ei.sing.s2p.core.entities.quantification.ProteinSummary;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationCondition;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationConditionSummary;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationConditionsComparison;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationConditionsComparisons;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationConditionsSummary;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;
import es.uvigo.ei.sing.s2p.core.operations.quantification.QuantificationConditionsSummarizer;

public class QuantificationsConditionsTest {

	private final static ProteinSummaryTest defaultProteinComparison = 
		new NormalizationProteinSummaryTest();
	
	/**
	 * Creates all possible comparisons between the conditions in
	 * {@code dataset}. For each pair of conditions, a t-test is performed for
	 * each protein in order to compare the normalized values in all samples of
	 * each condition.
	 * 
	 * @param dataset a {@code QuantificationDataset}.
	 * @return a {@code QuantificationConditionsComparisons}.
	 */
	public static QuantificationConditionsComparisons compare(
		QuantificationDataset dataset
	) {
		return compare(dataset, defaultProteinComparison);
	}

	/**
	 * Creates all possible comparisons between the conditions in
	 * {@code dataset}. For each pair of conditions, a t-test is performed for
	 * each protein using the specified {@code ProteinSummaryTest}.
	 * 
	 * @param dataset a {@code QuantificationDataset}.
	 * @param proteinComparison a {@code ProteinSummaryTest}.
	 * @return a {@code QuantificationConditionsComparisons}.
	 */
	public static QuantificationConditionsComparisons compare(
		QuantificationDataset dataset, ProteinSummaryTest proteinComparison
	) {
		return compare(QuantificationConditionsSummarizer.summary(dataset), proteinComparison);
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
		QuantificationConditionsSummary summary) {
		return compare(summary, defaultProteinComparison);
	}

	/**
	 * Creates all possible comparisons between the conditions in
	 * {@code dataset}. For each pair of conditions, a t-test is performed for
	 * each protein using the specified {@code ProteinSummaryTest}.
	 * 
	 * @param summary a {@code QuantificationConditionsSummary}.
	 * @param proteinComparison a {@code ProteinSummaryTest}.
	 * @return a {@code QuantificationConditionsComparisons}.
	 */	
	public static QuantificationConditionsComparisons compare(
		QuantificationConditionsSummary summary, 
		ProteinSummaryTest proteinComparison
	) {
		return new QuantificationConditionsComparisons(
			compare(allPairs(summary.getConditions()), summary, proteinComparison));
	}

	private static Collection<QuantificationConditionsComparison> compare(
		List<Pair<QuantificationCondition, 
		QuantificationCondition>> conditionPairs, 
		QuantificationConditionsSummary summary,
		ProteinSummaryTest proteinComparison
	) {
		List<QuantificationConditionsComparison> comparisons = new LinkedList<>();
		conditionPairs.forEach(cP -> 
			comparisons.add(compare(cP, summary, proteinComparison))
		);
		return comparisons;
	}

	private static QuantificationConditionsComparison compare(
		Pair<QuantificationCondition, QuantificationCondition> cP, 
		QuantificationConditionsSummary summary, 
		ProteinSummaryTest proteinComparison
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
				double tTest = proteinComparison.test(firstProteinSummary, secondProteinSummary);
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
