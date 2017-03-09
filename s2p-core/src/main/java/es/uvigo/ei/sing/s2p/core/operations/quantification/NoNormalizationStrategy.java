package es.uvigo.ei.sing.s2p.core.operations.quantification;

import java.util.List;

import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationSample;

public class NoNormalizationStrategy implements NormalizationStrategy {

	@Override
	public List<QuantificationSample> normalize(
		List<QuantificationSample> samples, NormalizationFactor factor) {
		return samples;
	}
}
