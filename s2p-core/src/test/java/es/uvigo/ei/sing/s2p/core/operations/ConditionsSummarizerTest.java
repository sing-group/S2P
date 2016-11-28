/*
 * #%L
 * S2P Core
 * %%
 * Copyright (C) 2016 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
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
package es.uvigo.ei.sing.s2p.core.operations;

import static es.uvigo.ei.sing.s2p.core.resources.TestResources.CSV_FORMAT;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.TEST_SPOT_DATA;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.Condition;
import es.uvigo.ei.sing.s2p.core.entities.ConditionSummary;
import es.uvigo.ei.sing.s2p.core.entities.ConditionsSummary;
import es.uvigo.ei.sing.s2p.core.entities.SpotSummary;
import es.uvigo.ei.sing.s2p.core.entities.SpotsData;
import es.uvigo.ei.sing.s2p.core.io.SpotsDataLoader;

public class ConditionsSummarizerTest {

	@Test
	public void spotSummaryTest() throws IOException {
		SpotsData data = SpotsDataLoader.load(TEST_SPOT_DATA, CSV_FORMAT);
		ConditionsSummary summary = ConditionsSummarizer.summary(data.getSpots(), data);
		
		Condition first = data.getConditions().get(0);
		ConditionSummary firstConditionSummary = summary.get(first);

		SpotSummary spotSummary = firstConditionSummary.get("P1");
		assertEquals(2, spotSummary.getTotalSamples());
		assertEquals(2, spotSummary.getNumSamples());
		assertEquals(150.5d, spotSummary.getMean(), 0.0);
		assertEquals(70.00d, spotSummary.getStdDev(), 0.01);
	}
}
