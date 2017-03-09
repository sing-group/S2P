package es.uvigo.ei.sing.s2p.core.operations.quantification;

import static es.uvigo.ei.sing.s2p.core.operations.quantification.NormalizationUtils.normalizeReplicate;
import static java.util.stream.Collectors.toList;

import java.util.List;

import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationReplicate;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationSample;

public class ReplicateNormalizationStrategy implements NormalizationStrategy {

	@Override
	public List<QuantificationSample> normalize(
		List<QuantificationSample> samples, NormalizationFactor factor
	) {
		return samples.stream()
				.map(s -> normalizeSample(s, factor))
				.collect(toList());
	}

	private static final QuantificationSample normalizeSample(
		QuantificationSample sample, NormalizationFactor factor
	) {
		if(sample.getCondition().isPresent()) {
			return 	new QuantificationSample(
						sample.getName(),
						sample.getCondition().get(),
						sample.getReplicates().stream()
							.map(r -> normalize(r, factor))
							.collect(toList()),
						sample.getProteinMass()
					);
		} else {
			return 	new QuantificationSample(
					sample.getName(),
					sample.getReplicates().stream()
						.map(r -> normalize(r, factor))
						.collect(toList()),
					sample.getProteinMass()
				);
		}
	}
	
	private static final QuantificationReplicate normalize(
	QuantificationReplicate replicate, NormalizationFactor factor
	) {
		double totalProteinValue = factor.getNormalizationFactor(
			NormalizationUtils.getProteinValues(replicate));

		return normalizeReplicate(replicate, totalProteinValue);
	}
}
