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
