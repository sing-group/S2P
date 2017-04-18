package es.uvigo.ei.sing.s2p.gui.quantification;

import static es.uvigo.ei.sing.s2p.core.resources.TestResources.QUANTIFICATION_EMPAI_DIRECTORY;
import static es.uvigo.ei.sing.s2p.gui.TestUtils.setNimbusLookAndFeel;
import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showComponent;

import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;

import es.uvigo.ei.sing.s2p.core.entities.quantification.MascotQuantificationMethod;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;
import es.uvigo.ei.sing.s2p.core.io.quantification.QuantificationCsvDatasetLoader;
import es.uvigo.ei.sing.s2p.core.operations.quantification.ReplicateNormalizationStrategy;
import es.uvigo.ei.sing.s2p.core.operations.quantification.SumNormalizationFactor;
import es.uvigo.ei.sing.s2p.core.operations.quantification.comparison.ComparisonMode;

public class QuantificationDatasetViewerTest {

	public static void main(String[] args) throws IOException {
		setNimbusLookAndFeel();
		JComponent viewer = new QuantificationDatasetViewer(testDataset(),
			ComparisonMode.PROTEIN_MASS);
		showComponent(viewer, JFrame.MAXIMIZED_BOTH);
	}

	private static QuantificationDataset testDataset() {
		return QuantificationCsvDatasetLoader.load(
				QUANTIFICATION_EMPAI_DIRECTORY, 
				MascotQuantificationMethod.EMPAI,
				new ReplicateNormalizationStrategy(), 
				new SumNormalizationFactor()
			);
	}
}
