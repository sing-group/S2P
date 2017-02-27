package es.uvigo.ei.sing.s2p.core.entities.quantification;

public class DefaultProteinQuantification implements ProteinQuantification {

	private String protein;
	private MascotQuantificationMethod method;
	private Double value;
	private Double normalizedValue;

	public DefaultProteinQuantification(String protein,
		MascotQuantificationMethod method, Double value
	) {
		this(protein, method, value, value);
	}

	public DefaultProteinQuantification(String protein,
		MascotQuantificationMethod method, Double value,
		Double normalizedValue
	) {
		this.protein = protein;
		this.method = method;
		this.value = value;
		this.normalizedValue = normalizedValue;
	}

	@Override
	public String getProtein() {
		return this.protein;
	}

	@Override
	public MascotQuantificationMethod getQuantificationMethod() {
		return this.method;
	}

	@Override
	public double getValue() {
		return this.value;
	}

	@Override
	public double getNormalizedValue() {
		return this.normalizedValue;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb
			.append(protein)
			.append(", ")
			.append(method.toString())
			.append(", ")
			.append(value)
			.append(", ")
			.append(normalizedValue);
		return sb.toString();
	}

	@Override
	public boolean equals(Object aThat) {
		if (aThat == null) {
			return false;
		}
		if (!(aThat instanceof DefaultProteinQuantification)) {
			return false;
		}

		DefaultProteinQuantification that = (DefaultProteinQuantification) aThat;
		return 	protein.equals(that.protein) &&
				method.equals(that.method) &&
				value.equals(that.value) &&
				normalizedValue.equals(that.normalizedValue);
	}
}
