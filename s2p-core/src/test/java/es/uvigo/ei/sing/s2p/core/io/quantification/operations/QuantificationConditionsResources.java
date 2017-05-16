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
			), 2d),
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
			), 2d)
		)
	);
	
	public static final QuantificationDataset DATASET_2 = 
		new QuantificationDataset(asList(
			new QuantificationSample("Sample 1", "A", asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1.1d),
					new DefaultProteinQuantification("P2", EMPAI, 1.2d),
					new DefaultProteinQuantification("P3", EMPAI, 1.3d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1.1d),
					new DefaultProteinQuantification("P2", EMPAI, 1.2d),
					new DefaultProteinQuantification("P3", EMPAI, 1.3d)
				))
			), 1d),
			new QuantificationSample("Sample 2", "A",asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 2.1d),
					new DefaultProteinQuantification("P2", EMPAI, 2.2d),
					new DefaultProteinQuantification("P3", EMPAI, 2.3d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 2.1d),
					new DefaultProteinQuantification("P2", EMPAI, 2.2d),
					new DefaultProteinQuantification("P3", EMPAI, 2.3d)
				))
			), 1d),
			new QuantificationSample("Sample 3", "A",asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 3.1d),
					new DefaultProteinQuantification("P2", EMPAI, 3.2d),
					new DefaultProteinQuantification("P3", EMPAI, 3.3d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 3.1d),
					new DefaultProteinQuantification("P2", EMPAI, 3.2d),
					new DefaultProteinQuantification("P3", EMPAI, 3.3d)
				))
			), 1d),
			new QuantificationSample("Sample 1.B", "B", asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1.1d),
					new DefaultProteinQuantification("P2", EMPAI, 1.2d),
					new DefaultProteinQuantification("P3", EMPAI, 1.3d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 1.1d),
					new DefaultProteinQuantification("P2", EMPAI, 1.2d),
					new DefaultProteinQuantification("P3", EMPAI, 1.3d)
				))
			), 1d),
			new QuantificationSample("Sample 2.B", "B",asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 2.1d),
					new DefaultProteinQuantification("P2", EMPAI, 2.2d),
					new DefaultProteinQuantification("P3", EMPAI, 2.3d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 2.1d),
					new DefaultProteinQuantification("P2", EMPAI, 2.2d),
					new DefaultProteinQuantification("P3", EMPAI, 2.3d)
				))
			), 1d),
			new QuantificationSample("Sample 3.B", "B",asList(
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 3.1d),
					new DefaultProteinQuantification("P2", EMPAI, 3.2d),
					new DefaultProteinQuantification("P3", EMPAI, 3.3d)
				)),
				new QuantificationReplicate(asList(
					new DefaultProteinQuantification("P1", EMPAI, 3.1d),
					new DefaultProteinQuantification("P2", EMPAI, 3.2d),
					new DefaultProteinQuantification("P3", EMPAI, 3.3d)
				))
			), 1d)
		)
	);	
}
