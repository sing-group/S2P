package es.uvigo.ei.sing.s2p.core.operations.quantification;

import java.util.stream.DoubleStream;

public interface NormalizationFactor {
	public double getNormalizationFactor(DoubleStream values);
}
