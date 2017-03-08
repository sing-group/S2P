package es.uvigo.ei.sing.s2p.core.operations.quantification;

public enum Normalization {
	REPLICATE	("Replicate", 			new ReplicateNormalizationStrategy()),
	SAMPLE		("Sample", 				new SampleNormalizationStrategy()	),
	GLOBAL		("Global", 				new GlobalNormalizationStrategy()	),
	NO			("No normalization",	new NoNormalizationStrategy()		);

	private String name;
	private NormalizationStrategy strategy;

	Normalization(String name, NormalizationStrategy strategy) {
		this.name = name;
		this.strategy = strategy;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public NormalizationStrategy getStrategy() {
		return strategy;
	}
}
