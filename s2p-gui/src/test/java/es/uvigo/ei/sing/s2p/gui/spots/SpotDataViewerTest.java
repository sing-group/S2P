package es.uvigo.ei.sing.s2p.gui.spots;

import static es.uvigo.ei.sing.s2p.core.resources.TestResources.CSV_FORMAT;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.TEST_SPOT_DATA;
import static es.uvigo.ei.sing.s2p.gui.TestUtils.setNimbusLookAndFeel;
import static es.uvigo.ei.sing.s2p.gui.TestUtils.showComponent;

import java.awt.Dimension;
import java.io.IOException;

import es.uvigo.ei.sing.s2p.core.entities.SpotsData;
import es.uvigo.ei.sing.s2p.core.io.SpotsDataLoader;
import es.uvigo.ei.sing.s2p.gui.spots.SpotsDataViewer;

public class SpotDataViewerTest {
	
	public static void main(String[] args) throws IOException {
		SpotsData data = SpotsDataLoader.load(TEST_SPOT_DATA, CSV_FORMAT);
		setNimbusLookAndFeel();
		showComponent(new SpotsDataViewer(data), new Dimension(400, 400));
	}
}
