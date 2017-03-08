package es.uvigo.ei.sing.s2p.core.io.quantification.operations;

import static es.uvigo.ei.sing.s2p.core.entities.quantification.MascotQuantificationMethod.EMPAI;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import es.uvigo.ei.sing.s2p.core.entities.quantification.DefaultProteinQuantification;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationReplicate;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationSample;
import es.uvigo.ei.sing.s2p.core.operations.quantification.GlobalNormalizationStrategy;
import es.uvigo.ei.sing.s2p.core.operations.quantification.NoNormalizationStrategy;
import es.uvigo.ei.sing.s2p.core.operations.quantification.NormalizationStrategy;
import es.uvigo.ei.sing.s2p.core.operations.quantification.ReplicateNormalizationStrategy;
import es.uvigo.ei.sing.s2p.core.operations.quantification.SampleNormalizationStrategy;

@RunWith(Parameterized.class)
public class QuantificationNormalizationTest {

	private static final QuantificationDataset DATASET = 
		new QuantificationDataset(asList(
			new QuantificationSample("Sample 1", asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d),
					new DefaultProteinQuantification("P2", EMPAI, 2d),
					new DefaultProteinQuantification("P3", EMPAI, 3d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d),
					new DefaultProteinQuantification("P4", EMPAI, 4d),
					new DefaultProteinQuantification("P5", EMPAI, 5d)
				))
			), 1d),
			new QuantificationSample("Sample 2", asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d),
					new DefaultProteinQuantification("P3", EMPAI, 3d),
					new DefaultProteinQuantification("P4", EMPAI, 4d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P5", EMPAI, 5d),
					new DefaultProteinQuantification("P6", EMPAI, 6d),
					new DefaultProteinQuantification("P7", EMPAI, 7d)
				))
			), 1d)
		)
	);

	private static final QuantificationDataset DATASET_REPLICATE_NORMALIZATION = 
		new QuantificationDataset(asList(
			new QuantificationSample("Sample 1", asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d, 1d / 6d),
					new DefaultProteinQuantification("P2", EMPAI, 2d, 2d / 6d),
					new DefaultProteinQuantification("P3", EMPAI, 3d, 3d / 6d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d, 1d / 10d),
					new DefaultProteinQuantification("P4", EMPAI, 4d, 4d / 10d),
					new DefaultProteinQuantification("P5", EMPAI, 5d, 5d / 10d)
				))
			), 1d),
			new QuantificationSample("Sample 2", asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d, 1d / 8d),
					new DefaultProteinQuantification("P3", EMPAI, 3d, 3d / 8d),
					new DefaultProteinQuantification("P4", EMPAI, 4d, 4d / 8d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P5", EMPAI, 5d, 5d / 18d),
					new DefaultProteinQuantification("P6", EMPAI, 6d, 6d / 18d),
					new DefaultProteinQuantification("P7", EMPAI, 7d, 7d / 18d)
				))
			), 1d)
		)
	);

	private static final QuantificationDataset DATASET_SAMPLE_NORMALIZATION = 
		new QuantificationDataset(asList(
			new QuantificationSample("Sample 1", asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d, 1d / 16d),
					new DefaultProteinQuantification("P2", EMPAI, 2d, 2d / 16d),
					new DefaultProteinQuantification("P3", EMPAI, 3d, 3d / 16d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d, 1d / 16d),
					new DefaultProteinQuantification("P4", EMPAI, 4d, 4d / 16d),
					new DefaultProteinQuantification("P5", EMPAI, 5d, 5d / 16d)
				))
			), 1d),
			new QuantificationSample("Sample 2", asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d, 1d / 26d),
					new DefaultProteinQuantification("P3", EMPAI, 3d, 3d / 26d),
					new DefaultProteinQuantification("P4", EMPAI, 4d, 4d / 26d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P5", EMPAI, 5d, 5d / 26d),
					new DefaultProteinQuantification("P6", EMPAI, 6d, 6d / 26d),
					new DefaultProteinQuantification("P7", EMPAI, 7d, 7d / 26d)
				))
			), 1d)
		)
	);
	
	private static final QuantificationDataset DATASET_GLOBAL_NORMALIZATION = 
		new QuantificationDataset(asList(
			new QuantificationSample("Sample 1", asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d, 1d / 42d),
					new DefaultProteinQuantification("P2", EMPAI, 2d, 2d / 42d),
					new DefaultProteinQuantification("P3", EMPAI, 3d, 3d / 42d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d, 1d / 42d),
					new DefaultProteinQuantification("P4", EMPAI, 4d, 4d / 42d),
					new DefaultProteinQuantification("P5", EMPAI, 5d, 5d / 42d)
				))
			), 1d),
			new QuantificationSample("Sample 2", asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d, 1d / 42d),
					new DefaultProteinQuantification("P3", EMPAI, 3d, 3d / 42d),
					new DefaultProteinQuantification("P4", EMPAI, 4d, 4d / 42d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P5", EMPAI, 5d, 5d / 42d),
					new DefaultProteinQuantification("P6", EMPAI, 6d, 6d / 42d),
					new DefaultProteinQuantification("P7", EMPAI, 7d, 7d / 42d)
				))
			), 1d)
		)
	);

	
	@Parameters
	public static Collection<Object[]> data() {
		return asList(new Object[][] { 
			{ DATASET, DATASET, 						new NoNormalizationStrategy() }, 
			{ DATASET, DATASET_REPLICATE_NORMALIZATION, new ReplicateNormalizationStrategy() },
			{ DATASET, DATASET_SAMPLE_NORMALIZATION, 	new SampleNormalizationStrategy() },
			{ DATASET, DATASET_GLOBAL_NORMALIZATION, 	new GlobalNormalizationStrategy() }
		});
	}

	private QuantificationDataset input;
	private QuantificationDataset expected;
	private NormalizationStrategy normalization;

	public QuantificationNormalizationTest(QuantificationDataset input,
		QuantificationDataset expected,
		NormalizationStrategy normalization
	) {
		this.input = input;
		this.expected = expected;
		this.normalization = normalization;
	}
	
	@Test
	public void normalizationTest() {
		QuantificationDataset normalized = new QuantificationDataset(
			normalization.normalize(this.input));
		assertEquals(this.expected, normalized);
	}
}
