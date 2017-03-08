package es.uvigo.ei.sing.s2p.core.operations.quantification;

import static es.uvigo.ei.sing.s2p.core.operations.quantification.NormalizationUtils.getTotalProteinValue;
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

	private static final QuantificationSample normalizeSample(
		QuantificationSample sample
	) {
		double totalSampleAmount = getTotalProteinValue(sample);
		return NormalizationUtils.normalizeSample(sample, totalSampleAmount);
	}
}
