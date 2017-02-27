package es.uvigo.ei.sing.s2p.core.io.quantification;

import static es.uvigo.ei.sing.s2p.core.entities.quantification.MascotQuantificationMethod.EMPAI;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.QUANTIFICATION_EMPAI_FILE;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Test;

import es.uvigo.ei.sing.s2p.core.entities.quantification.DefaultProteinQuantification;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationReplicate;

public class QuantificationCsvFileLoaderTest {

	private static final QuantificationReplicate REPLICATE =
		new QuantificationReplicate(asList(
			new DefaultProteinQuantification("Serum albumin", EMPAI, 11.46),
			new DefaultProteinQuantification("Alpha-2-macroglobulin", EMPAI, 1.78),
			new DefaultProteinQuantification("Alpha-1-antitrypsin", EMPAI, 2.41)
		)
	);

	@Test
	public void quantificationCsvFileLoaderTest() throws FileNotFoundException {
		QuantificationReplicate replicate = 
			QuantificationCsvFileLoader.load(QUANTIFICATION_EMPAI_FILE, EMPAI);
		assertEquals(REPLICATE, replicate);
	}
}
