package es.uvigo.ei.sing.s2p.core.operations.quantification;

import java.util.List;

import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationSample;

public interface NormalizationStrategy {
	public List<QuantificationSample> normalize(List<QuantificationSample> samples);
}
