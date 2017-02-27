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
