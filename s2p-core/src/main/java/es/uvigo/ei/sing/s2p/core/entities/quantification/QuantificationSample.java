package es.uvigo.ei.sing.s2p.core.entities.quantification;

import java.util.List;
import java.util.Optional;

public class QuantificationSample {
	
	private String name;
	private List<QuantificationReplicate> replicates;
	private String condition;
	private double proteinMass;

	public QuantificationSample(String name,
		List<QuantificationReplicate> replicates, double proteinMass
	) {
		this.name = name;
		this.replicates = replicates;
		this.proteinMass = proteinMass;
	}
	
	public QuantificationSample(String name, String condition, 
		List<QuantificationReplicate> replicates, double proteinMass
	) {
		this.name = name;
		this.condition = condition;
		this.replicates = replicates;
		this.proteinMass = proteinMass;
	}
	
	public String getName() {
		return name;
	}

	public Optional<String> getCondition() {
		return Optional.ofNullable(condition);
	}

	public List<QuantificationReplicate> getReplicates() {
		return replicates;
	}

	public double getProteinMass() {
		return proteinMass;
	}

	@Override
	public boolean equals(Object aThat) {
		if (aThat == null) {
			return false;
		}
		if (!(aThat instanceof QuantificationSample)) {
			return false;
		}

		QuantificationSample that = (QuantificationSample) aThat;
		return 	this.name.equals(that.name) &&
				this.proteinMass == that.proteinMass &&
				this.getCondition().equals(that.getCondition()) &&
				this.replicates.equals(that.replicates);
	}
}
