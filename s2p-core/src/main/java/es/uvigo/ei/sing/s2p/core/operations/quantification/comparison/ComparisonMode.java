package es.uvigo.ei.sing.s2p.core.operations.quantification.comparison;

public enum ComparisonMode {
	NORMALIZED_QUANTIFICATION(
		"Normalized quantification",
		"normalized protein quantification",
		new NormalizationProteinSummaryTest()
	),
	PROTEIN_MASS(
		"Protein mass",
		"protein mass",
		new MassProteinSummaryTest()
	);

	private String name;
	private String label;
	private ProteinSummaryTest test;

	ComparisonMode(String name, String label, ProteinSummaryTest test) {
		this.name = name;
		this.test = test;
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public ProteinSummaryTest getTest() {
		return test;
	}

	@Override
	public String toString() {
		return getName();
	}

	public String getLabel() {
		return this.label;
	}
};