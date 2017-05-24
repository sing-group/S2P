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

import static es.uvigo.ei.sing.s2p.core.io.quantification.operations.QuantificationConditionsResources.DATASET;
import static es.uvigo.ei.sing.s2p.core.io.quantification.operations.QuantificationConditionsResources.DATASET_2;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.quantification.ProteinSummary;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationCondition;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationConditionSummary;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationConditionsSummary;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;
import es.uvigo.ei.sing.s2p.core.operations.quantification.GlobalNormalizationStrategy;
import es.uvigo.ei.sing.s2p.core.operations.quantification.QuantificationConditionsSummarizer;
import es.uvigo.ei.sing.s2p.core.operations.quantification.SumNormalizationFactor;

public class QuantificationConditionsSummarizerTest {
	
	@Test
	public void spotSummaryTest() throws IOException {
		QuantificationConditionsSummary summary =
			QuantificationConditionsSummarizer.summary(DATASET);
		assertEquals(2, summary.keySet().size());
		
		QuantificationCondition first = DATASET.getConditions().get(0);
		QuantificationConditionSummary firstConditionSummary = summary.get(first);
		assertEquals(7, firstConditionSummary.keySet().size());

		ProteinSummary p1Summary = firstConditionSummary.get("P1");
		assertEquals(2, 	p1Summary.getTotalReplicates());
		assertEquals(2, 	p1Summary.getNumReplicates());
		assertEquals(1.5d, 	p1Summary.getProteinValueMean(), 0.0);
		assertEquals(1.5d, 	p1Summary.getNormalizedProteinValueMean(), 0.0);
		assertEquals(3d, 	p1Summary.getProteinMassValueMean(), 0.0);
	
		ProteinSummary p2Summary = firstConditionSummary.get("P2");
		assertEquals(2,  p2Summary.getTotalReplicates());
		assertEquals(1,  p2Summary.getNumReplicates());
		assertEquals(2d, p2Summary.getProteinValueMean(), 0.0);
		assertEquals(2d, p2Summary.getNormalizedProteinValueMean(), 0.0);
		assertEquals(4d, p2Summary.getProteinMassValueMean(), 0.0);
	}
	
	@Test
	public void testEqualRsdWithGlobalNormalization() {
		QuantificationDataset dataset = new QuantificationDataset(
			new GlobalNormalizationStrategy().normalize(
				DATASET_2, new SumNormalizationFactor()
			)
		);

		QuantificationConditionsSummary summary =
			QuantificationConditionsSummarizer.summary(dataset);

		summary.entrySet().forEach(e -> {
			e.getValue().entrySet().forEach(qS -> {
				ProteinSummary proteinSummary = qS.getValue();
				assertEquals(
					proteinSummary.getNormalizedProteinValueRsd(),
					proteinSummary.getProteinValueRsd(),
					0.0001d
				);
				assertEquals(
					proteinSummary.getProteinMassValueRsd(),
					proteinSummary.getProteinMassValueRsd(),
					0.0001d
				);
			});
		});
	}
}
