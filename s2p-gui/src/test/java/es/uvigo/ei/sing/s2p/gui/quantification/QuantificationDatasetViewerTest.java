package es.uvigo.ei.sing.s2p.gui.quantification;

import static es.uvigo.ei.sing.s2p.core.resources.TestResources.QUANTIFICATION_EMPAI_DIRECTORY;
import static es.uvigo.ei.sing.s2p.gui.TestUtils.setNimbusLookAndFeel;
import static es.uvigo.ei.sing.s2p.gui.TestUtils.showComponent;

import java.awt.Dimension;
import java.io.IOException;

import es.uvigo.ei.sing.s2p.core.entities.quantification.MascotQuantificationMethod;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;
import es.uvigo.ei.sing.s2p.core.io.quantification.QuantificationCsvDatasetLoader;
import es.uvigo.ei.sing.s2p.core.operations.quantification.ReplicateNormalizationStrategy;

public class QuantificationDatasetViewerTest {
	
	public static void main(String[] args) throws IOException {
		QuantificationDataset dataset = QuantificationCsvDatasetLoader.load(
			QUANTIFICATION_EMPAI_DIRECTORY, MascotQuantificationMethod.EMPAI,
			new ReplicateNormalizationStrategy()
		);
		setNimbusLookAndFeel();
		showComponent(new QuantificationDatasetViewer(dataset), new Dimension(400, 400));
	}
}
