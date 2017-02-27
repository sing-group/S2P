package es.uvigo.ei.sing.s2p.core.entities.quantification;

import java.util.List;
import java.util.Optional;

public class QuantificationReplicate {

	private List<ProteinQuantification> proteins;

	public QuantificationReplicate(List<ProteinQuantification> proteins) {
		this.proteins = proteins;
	}
	
	public List<ProteinQuantification> getProteins() {
		return proteins;
	}
	
	public Optional<ProteinQuantification> findProtein(String protein) {
		return 	proteins.stream()
					.filter(p -> p.getProtein().equals(protein)).findFirst();
	}

	@Override
	public boolean equals(Object aThat) {
		if (aThat == null) {
			return false;
		}
		if (!(aThat instanceof QuantificationReplicate)) {
			return false;
		}

		QuantificationReplicate that = (QuantificationReplicate) aThat;
		return proteins.equals(that.proteins);
	}
}
