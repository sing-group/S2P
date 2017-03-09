package es.uvigo.ei.sing.s2p.core.operations.quantification;

import static java.util.stream.Collectors.toList;

import java.util.List;

import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationSample;

public class GlobalNormalizationStrategy implements NormalizationStrategy {

	@Override
	public List<QuantificationSample> normalize(
		List<QuantificationSample> samples, NormalizationFactor factor
	) {
		double totalSamplesAmount = factor.getNormalizationFactor(
			NormalizationUtils.getProteinValues(samples));

		return samples.stream()
				.map(s -> NormalizationUtils.normalizeSample(s, totalSamplesAmount))
				.collect(toList());
	}
}
