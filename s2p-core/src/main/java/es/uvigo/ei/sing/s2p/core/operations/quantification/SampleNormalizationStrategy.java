package es.uvigo.ei.sing.s2p.core.operations.quantification;

import static java.util.stream.Collectors.toList;

import java.util.List;

import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationSample;

public class SampleNormalizationStrategy implements NormalizationStrategy {

	@Override
	public List<QuantificationSample> normalize(
		List<QuantificationSample> samples
	) {
		return samples.stream()
				.map(SampleNormalizationStrategy::normalizeSample)
				.collect(toList());
	}

	private static final QuantificationSample normalizeSample(QuantificationSample sample) {
		double totalSampleAmount = NormalizationUtils.getTotalProteinValue(sample);
		if(sample.getCondition().isPresent()) {
		return 	new QuantificationSample(
					sample.getName(),
					sample.getCondition().get(),
					sample.getReplicates().stream()
						.map(r -> {return NormalizationUtils.normalizeReplicate(r, totalSampleAmount);})
						.collect(toList()),
					sample.getProteinMass()
				);
		} else {
			return 	new QuantificationSample(
					sample.getName(),
					sample.getReplicates().stream()
						.map(r -> {return NormalizationUtils.normalizeReplicate(r, totalSampleAmount);})
						.collect(toList()),
					sample.getProteinMass()
				);
		}
	}
}
