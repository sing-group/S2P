package es.uvigo.ei.sing.s2p.core.operations.quantification;

public enum NormalizationFactorEnum {
	SUM			("Sum", 		new SumNormalizationFactor()),
	MEDIAN		("Median",		new MedianNormalizationFactor()	);

	public static final String DEFAULT_FACTOR = "Sum";

	private String name;
	private NormalizationFactor factor;

	NormalizationFactorEnum(String name, NormalizationFactor factor) {
		this.name = name;
		this.factor = factor;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public NormalizationFactor getNormalizationFactor() {
		return factor;
	}
}
