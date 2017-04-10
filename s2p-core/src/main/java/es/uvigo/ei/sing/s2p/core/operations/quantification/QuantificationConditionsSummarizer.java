package es.uvigo.ei.sing.s2p.core.operations.quantification;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import es.uvigo.ei.sing.s2p.core.entities.quantification.ProteinQuantification;
import es.uvigo.ei.sing.s2p.core.entities.quantification.ProteinSummary;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationCondition;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationConditionSummary;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationConditionsSummary;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;

public class QuantificationConditionsSummarizer {

	/**
	 * Creates a {@code QuantificationConditionsSummary} containing all proteins
	 * in {@code data}.
	 * 
	 * @param data a {@code QuantificationDataset}.
	 * @return a {@code QuantificationConditionsSummary}
	 */
	public static QuantificationConditionsSummary summary(
		QuantificationDataset data
	) {
		return summary(data, data.getProteins());
	}
	
	/**
	 * Creates a {@code QuantificationConditionsSummary} containing only
	 * information about the proteins in the list {@code proteins}.
	 * 
	 * @param data a {@code QuantificationDataset}.
	 * @param proteins the list of proteins to summarize.
	 * @return a {@code QuantificationConditionsSummary}
	 */
	public static QuantificationConditionsSummary summary(
		QuantificationDataset data, Set<String> proteins
	) {
		QuantificationConditionsSummary summary = new QuantificationConditionsSummary();
		data.getConditions().forEach(c -> {
			summary.put(c, conditionSummary(proteins, c));
		});

		return summary;
	}

	private static QuantificationConditionSummary conditionSummary(
		Set<String> proteins, QuantificationCondition c
	) {
		QuantificationConditionSummary summary = new QuantificationConditionSummary();
		proteins.forEach(protein -> {
			summary.put(protein, proteinSummary(protein, c));
		});

		return summary;
	}

	private static ProteinSummary proteinSummary(String protein,
		QuantificationCondition c
	) {
		List<Double> proteinValues = new LinkedList<Double>();
		List<Double> proteinMassValues = new LinkedList<Double>();
		List<Double> proteinNormalizedValues = new LinkedList<Double>();
		
		AtomicInteger replicatesCount = new AtomicInteger(0);
		c.getSamples().forEach(s -> {
			double proteinMass = s.getProteinMass();
			s.getReplicates().forEach(r -> {
				replicatesCount.set(replicatesCount.get() + 1);
				Optional<ProteinQuantification> pQ = r.findProtein(protein);
				if (pQ.isPresent()) {
					double normalizedValue = pQ.get().getNormalizedValue();
					double value = pQ.get().getValue();
					double massValue = pQ.get().getNormalizedValue() * proteinMass;
					if (!Double.isNaN(normalizedValue) && !Double.isNaN(value)) {
						proteinNormalizedValues.add(normalizedValue);
						proteinValues.add(value);
						proteinMassValues.add(massValue);
					}
				}
			});
		});

		return new ProteinSummary(
			replicatesCount.intValue(), proteinValues, proteinNormalizedValues, proteinMassValues);
	}
}
