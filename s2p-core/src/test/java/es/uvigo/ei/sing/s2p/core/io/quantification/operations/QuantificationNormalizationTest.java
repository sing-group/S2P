/*
 * #%L
 * S2P Core
 * %%
 * Copyright (C) 2016 - 2017 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
 * 			Reboiro-Jato, Hugo López-Fernández, and Daniel Glez-Peña
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
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
import es.uvigo.ei.sing.s2p.core.operations.quantification.MedianNormalizationFactor;
import es.uvigo.ei.sing.s2p.core.operations.quantification.NoNormalizationStrategy;
import es.uvigo.ei.sing.s2p.core.operations.quantification.NormalizationFactor;
import es.uvigo.ei.sing.s2p.core.operations.quantification.NormalizationStrategy;
import es.uvigo.ei.sing.s2p.core.operations.quantification.ReplicateNormalizationStrategy;
import es.uvigo.ei.sing.s2p.core.operations.quantification.SampleNormalizationStrategy;
import es.uvigo.ei.sing.s2p.core.operations.quantification.SumNormalizationFactor;

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

	private static final QuantificationDataset DATASET_REPLICATE_NORMALIZATION_SUM = 
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

	private static final QuantificationDataset DATASET_REPLICATE_NORMALIZATION_MEDIAN = 
		new QuantificationDataset(asList(
			new QuantificationSample("Sample 1", asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d, 1d / 2d),
					new DefaultProteinQuantification("P2", EMPAI, 2d, 2d / 2d),
					new DefaultProteinQuantification("P3", EMPAI, 3d, 3d / 2d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d, 1d / 4d),
					new DefaultProteinQuantification("P4", EMPAI, 4d, 4d / 4d),
					new DefaultProteinQuantification("P5", EMPAI, 5d, 5d / 4d)
				))
			), 1d),
			new QuantificationSample("Sample 2", asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d, 1d / 3d),
					new DefaultProteinQuantification("P3", EMPAI, 3d, 3d / 3d),
					new DefaultProteinQuantification("P4", EMPAI, 4d, 4d / 3d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P5", EMPAI, 5d, 5d / 6d),
					new DefaultProteinQuantification("P6", EMPAI, 6d, 6d / 6d),
					new DefaultProteinQuantification("P7", EMPAI, 7d, 7d / 6d)
				))
			), 1d)
		)
	);

	private static final QuantificationDataset DATASET_SAMPLE_NORMALIZATION_SUM = 
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

	private static final QuantificationDataset DATASET_SAMPLE_NORMALIZATION_MEDIAN = 
		new QuantificationDataset(asList(
			new QuantificationSample("Sample 1", asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d, 1d / 2.5d),
					new DefaultProteinQuantification("P2", EMPAI, 2d, 2d / 2.5d),
					new DefaultProteinQuantification("P3", EMPAI, 3d, 3d / 2.5d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d, 1d / 2.5d),
					new DefaultProteinQuantification("P4", EMPAI, 4d, 4d / 2.5d),
					new DefaultProteinQuantification("P5", EMPAI, 5d, 5d / 2.5d)
				))
			), 1d),
			new QuantificationSample("Sample 2", asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d, 1d / 4.5d),
					new DefaultProteinQuantification("P3", EMPAI, 3d, 3d / 4.5d),
					new DefaultProteinQuantification("P4", EMPAI, 4d, 4d / 4.5d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P5", EMPAI, 5d, 5d / 4.5d),
					new DefaultProteinQuantification("P6", EMPAI, 6d, 6d / 4.5d),
					new DefaultProteinQuantification("P7", EMPAI, 7d, 7d / 4.5d)
				))
			), 1d)
		)
	);

	private static final QuantificationDataset DATASET_GLOBAL_NORMALIZATION_SUM = 
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

	private static final QuantificationDataset DATASET_GLOBAL_NORMALIZATION_MEDIAN = 
		new QuantificationDataset(asList(
			new QuantificationSample("Sample 1", asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d, 1d / 3.5d),
					new DefaultProteinQuantification("P2", EMPAI, 2d, 2d / 3.5d),
					new DefaultProteinQuantification("P3", EMPAI, 3d, 3d / 3.5d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d, 1d / 3.5d),
					new DefaultProteinQuantification("P4", EMPAI, 4d, 4d / 3.5d),
					new DefaultProteinQuantification("P5", EMPAI, 5d, 5d / 3.5d)
				))
			), 1d),
			new QuantificationSample("Sample 2", asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1d, 1d / 3.5d),
					new DefaultProteinQuantification("P3", EMPAI, 3d, 3d / 3.5d),
					new DefaultProteinQuantification("P4", EMPAI, 4d, 4d / 3.5d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P5", EMPAI, 5d, 5d / 3.5d),
					new DefaultProteinQuantification("P6", EMPAI, 6d, 6d / 3.5d),
					new DefaultProteinQuantification("P7", EMPAI, 7d, 7d / 3.5d)
				))
			), 1d)
		)
	);
	
	@Parameters
	public static Collection<Object[]> data() {
		return asList(new Object[][] { 
				{ DATASET, DATASET, 
					new NoNormalizationStrategy(),
					new SumNormalizationFactor() },
				{ DATASET, DATASET_REPLICATE_NORMALIZATION_SUM,
					new ReplicateNormalizationStrategy(),
					new SumNormalizationFactor() },
				{ DATASET, DATASET_REPLICATE_NORMALIZATION_MEDIAN,
					new ReplicateNormalizationStrategy(),
					new MedianNormalizationFactor() },
				{ DATASET, DATASET_SAMPLE_NORMALIZATION_SUM,
					new SampleNormalizationStrategy(),
					new SumNormalizationFactor() },
				{ DATASET, DATASET_SAMPLE_NORMALIZATION_MEDIAN,
					new SampleNormalizationStrategy(),
					new MedianNormalizationFactor() },
				{ DATASET, DATASET_GLOBAL_NORMALIZATION_SUM,
					new GlobalNormalizationStrategy(),
					new SumNormalizationFactor() },
				{ DATASET, DATASET_GLOBAL_NORMALIZATION_MEDIAN,
					new GlobalNormalizationStrategy(),
					new MedianNormalizationFactor() },
		});
	}

	private QuantificationDataset input;
	private QuantificationDataset expected;
	private NormalizationStrategy normalization;
	private NormalizationFactor factor;

	public QuantificationNormalizationTest(QuantificationDataset input,
		QuantificationDataset expected,
		NormalizationStrategy normalization,
		NormalizationFactor factor
	) {
		this.input = input;
		this.expected = expected;
		this.normalization = normalization;
		this.factor = factor;
	}
	
	@Test
	public void normalizationTest() {
		QuantificationDataset normalized = new QuantificationDataset(
			normalization.normalize(this.input, this.factor));
		assertEquals(this.expected, normalized);
	}
}
