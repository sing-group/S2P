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
package es.uvigo.ei.sing.s2p.core.io.quantification;

import static java.util.stream.Collectors.toList;
import static java.util.Arrays.asList;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.QUANTIFICATION_EMPAI_DIRECTORY;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.quantification.MascotQuantificationMethod;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationCondition;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationSample;

public class QuantificationCsvDatasetLoaderTest {

	@Test
	public void quantificationCsvDatasetLoaderTest() {
		QuantificationDataset dataset = QuantificationCsvDatasetLoader.load(
			QUANTIFICATION_EMPAI_DIRECTORY, MascotQuantificationMethod.EMPAI
		);
		assertEquals(3, dataset.size());

		List<QuantificationCondition> conditions = dataset.getConditions();
		assertEquals(3, conditions.size());
		assertEquals(
			asList("ConditionA", "ConditionB", "ConditionC"), 
			conditions.stream().map(QuantificationCondition::getName).collect(toList())
		);
		assertEquals(
			asList(3d, 2d, 1d), 
			dataset.stream().map(QuantificationSample::getProteinMass).collect(toList())
		);
	}
}
