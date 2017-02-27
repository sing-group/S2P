package es.uvigo.ei.sing.s2p.core.operations.quantification;

import static es.uvigo.ei.sing.s2p.core.operations.quantification.NormalizationUtils.normalizeReplicate;
import static es.uvigo.ei.sing.s2p.core.operations.quantification.NormalizationUtils.getTotalProteinValue;
import static java.util.stream.Collectors.toList;

import java.util.List;

import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationReplicate;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationSample;

public class ReplicateNormalizationStrategy implements NormalizationStrategy {

	@Override
	public List<QuantificationSample> normalize(
		List<QuantificationSample> samples
	) {
		return samples.stream()
				.map(ReplicateNormalizationStrategy::normalizeSample)
				.collect(toList());
	}

	private static final QuantificationSample normalizeSample(QuantificationSample sample) {
		if(sample.getCondition().isPresent()) {
			return 	new QuantificationSample(
						sample.getName(),
						sample.getCondition().get(),
						sample.getReplicates().stream()
							.map(ReplicateNormalizationStrategy::normalize)
							.collect(toList()),
						sample.getProteinMass()
					);
		} else {
			return 	new QuantificationSample(
					sample.getName(),
					sample.getReplicates().stream()
						.map(ReplicateNormalizationStrategy::normalize)
						.collect(toList()),
					sample.getProteinMass()
				);
		}
	}
	
	private static final QuantificationReplicate normalize(QuantificationReplicate replicate) {
		return normalizeReplicate(replicate, getTotalProteinValue(replicate));
	}
}
