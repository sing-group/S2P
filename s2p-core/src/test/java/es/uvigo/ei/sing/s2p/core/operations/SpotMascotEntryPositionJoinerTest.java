package es.uvigo.ei.sing.s2p.core.operations;

import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MALDI_PLATE_FILE;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MASCOT_PROJECT;
import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.io.MaldiPlateLoader;
import es.uvigo.ei.sing.s2p.core.io.MascotProjectLoader;

public class SpotMascotEntryPositionJoinerTest {

	@Test
	public void spotMascotEntryPositionJoinerTest() throws IOException {
		MascotIdentifications entries = MascotProjectLoader.load(MASCOT_PROJECT);
		Map<String, String> posToSpot = MaldiPlateLoader.load(MALDI_PLATE_FILE);
		
		Map<String, MascotIdentifications> join = 
			SpotMascotEntryPositionJoiner.join(posToSpot, entries);
		
		assertEquals(1, join.get("45").size());
	}
}
