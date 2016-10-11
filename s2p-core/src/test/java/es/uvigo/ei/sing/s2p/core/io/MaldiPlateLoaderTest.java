package es.uvigo.ei.sing.s2p.core.io;

import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MALDI_PLATE_FILE;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.MALDI_PLATE_FORMAT;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

public class MaldiPlateLoaderTest {
	
	@Test
	public void maldiPlateLoaderTest() throws IOException {
		Map<String, String> mpl = MaldiPlateLoader
			.importCsv(MALDI_PLATE_FILE, MALDI_PLATE_FORMAT)
			.asMap();
		assertEquals(4, mpl.size());
		assertEquals(mpl.get("A1"), "140");
		assertEquals(mpl.get("A2"), "200");
		assertEquals(mpl.get("B2"), "45");
		assertEquals(mpl.get("C1"), "50");
	}
}
