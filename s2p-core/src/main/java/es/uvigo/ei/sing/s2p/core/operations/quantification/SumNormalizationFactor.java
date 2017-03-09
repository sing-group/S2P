package es.uvigo.ei.sing.s2p.core.operations.quantification;

import java.util.stream.DoubleStream;

public class SumNormalizationFactor implements NormalizationFactor {

	@Override
	public double getNormalizationFactor(DoubleStream values) {
		return values.sum();
	}
}
