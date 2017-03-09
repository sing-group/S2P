package es.uvigo.ei.sing.s2p.core.operations.quantification;

import java.util.stream.DoubleStream;

import org.apache.commons.math3.stat.descriptive.rank.Median;

public class MedianNormalizationFactor implements NormalizationFactor {

	@Override
	public double getNormalizationFactor(DoubleStream values) {
		return new Median().evaluate(values.toArray());
	}
}
