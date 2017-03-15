package es.uvigo.ei.sing.s2p.core.entities.quantification;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class QuantificationCondition {

	private String name;
	private List<QuantificationSample> samples;
	
	public QuantificationCondition(String name, List<QuantificationSample> samples) {
		this.name = name;
		this.samples = samples;
	}
	
	public String getName() {
		return name;
	}
	
	public List<QuantificationSample> getSamples() {
		return samples;
	}

	public Set<String> getProteins() {
		return QuantificationDataset.getProteins(this.samples.stream());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.samples);
	}

	@Override
	public String toString() {
		return this.getName();
	}

	@Override
	public boolean equals(Object aThat) {
		if (aThat == null) {
			return false;
		}
		if (!(aThat instanceof QuantificationCondition)) {
			return false;
		}

		QuantificationCondition that = (QuantificationCondition) aThat;
		return 	this.name.equals(that.name) &&
				this.samples.equals(that.samples);
	}
}
