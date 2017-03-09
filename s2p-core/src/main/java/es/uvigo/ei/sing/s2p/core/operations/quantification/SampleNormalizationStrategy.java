package es.uvigo.ei.sing.s2p.core.operations.quantification;

import static java.util.stream.Collectors.toList;

import java.util.List;

import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationSample;

public class SampleNormalizationStrategy implements NormalizationStrategy {

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
		double totalSampleAmount = factor.getNormalizationFactor(
			NormalizationUtils.getProteinValues(sample));

		return NormalizationUtils.normalizeSample(sample, totalSampleAmount);
	}
}
