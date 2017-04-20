package es.uvigo.ei.sing.s2p.aibench.operations;

import static es.uvigo.ei.sing.s2p.aibench.util.PortConfiguration.EXTRAS_HTML_FILES;
import static es.uvigo.ei.sing.s2p.core.io.MascotProjectLoader.load;

import java.io.File;
import java.io.IOException;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

@Operation(
	name = "Associate Mascot identifications from a HTM Mascot report file", 
	description = "Associates Mascot identifications from a HTM Mascot report file with a Maldi plate."
)
public class AssociateMascotIdentificationsFromReport
	extends AbstractAssociateMascotIdentifications {

	private File htmlFile;

	@Port(
		direction = Direction.INPUT, 
		name = "Protein identifications", 
		description = "A HTM file containing the Mascot report.",
		order = 1,
		extras = EXTRAS_HTML_FILES,
		validateMethod = "validateHtmlFile"
	)
	public void setHtmlFile(File htmlFile) {
		this.htmlFile = htmlFile;
	}

	public void validateHtmlFile(File htmlFile) {
		try {
			load(htmlFile, 0, false);
		} catch (IOException e) {
			throw new IllegalArgumentException("Can't load " + htmlFile.getAbsolutePath());
		}
	}

	@Override
	protected MascotIdentifications getMascotIdentifications() throws IOException {
		return load(htmlFile, minimumMS, removeDuplicates);
	}
}
