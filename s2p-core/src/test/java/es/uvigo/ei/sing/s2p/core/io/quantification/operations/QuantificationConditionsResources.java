package es.uvigo.ei.sing.s2p.core.io.quantification.operations;

import static es.uvigo.ei.sing.s2p.core.entities.quantification.MascotQuantificationMethod.EMPAI;
import static java.util.Arrays.asList;

import es.uvigo.ei.sing.s2p.core.entities.quantification.DefaultProteinQuantification;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationReplicate;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationSample;

public class QuantificationConditionsResources {

	public static final QuantificationDataset DATASET = 
		new QuantificationDataset(asList(
			new QuantificationSample("Sample 1", "ConditionA", asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d),
					new DefaultProteinQuantification("P2", EMPAI, 2d),
					new DefaultProteinQuantification("P3", EMPAI, 3d),
					new DefaultProteinQuantification("P4", EMPAI, 3d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 2d),
					new DefaultProteinQuantification("P4", EMPAI, 4d),
					new DefaultProteinQuantification("P5", EMPAI, 5d)
				))
			), 1d),
			new QuantificationSample("Sample 2", "ConditionB", asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d),
					new DefaultProteinQuantification("P3", EMPAI, 3d),
					new DefaultProteinQuantification("P4", EMPAI, 4d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 5d),
					new DefaultProteinQuantification("P4", EMPAI, 5d),
					new DefaultProteinQuantification("P5", EMPAI, 5d),
					new DefaultProteinQuantification("P6", EMPAI, 6d),
					new DefaultProteinQuantification("P7", EMPAI, 7d)
				))
			), 1d)
		)
	);	
}
