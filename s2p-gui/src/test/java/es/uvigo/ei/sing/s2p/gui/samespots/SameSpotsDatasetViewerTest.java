package es.uvigo.ei.sing.s2p.gui.samespots;

import static es.uvigo.ei.sing.s2p.core.resources.TestResources.SAMESPOTS_DIRECTORY;
import static es.uvigo.ei.sing.s2p.gui.TestUtils.setNimbusLookAndFeel;
import static es.uvigo.ei.sing.s2p.gui.TestUtils.showComponent;

import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;

import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsDirectoryLoader;
import es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsFileLoader;

public class SameSpotsDatasetViewerTest {
	
	public static void main(String[] args) throws IOException {
		List<Sample> samples = SameSpotsDirectoryLoader.load(
			SAMESPOTS_DIRECTORY, SameSpotsFileLoader.DEFAULT_THRESHOLD);
		
		setNimbusLookAndFeel();
		showComponent(new SameSpotsDatasetViewer(samples), JFrame.MAXIMIZED_BOTH);
	}
}
