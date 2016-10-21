package es.uvigo.ei.sing.s2p.aibench.operations;

import static es.uvigo.ei.sing.s2p.core.io.MascotProjectLoader.load;

import java.io.File;
import java.io.IOException;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.sing.s2p.aibench.datatypes.MascotIdentificationsDatatype;

@Operation(
	name = "Import Mascot identifications", 
	description = "Imports Mascot identifications from a .HTM file."
)
public class ImportMascotIdentifications {
	private File htmlFile;
	private int minimumMS;
	private boolean removeDuplicates;

	@Port(
		direction = Direction.INPUT, 
		name = "Protein identifications", 
		description = "A .HTM file containing the Mascot report.",
		order = 1
	)
	public void setHtmlFile(File htmlFile) {
		this.htmlFile = htmlFile;
	}
	
	@Port(
		direction = Direction.INPUT, 
		name = "Minimum Mascot Score", 
		description = "Entries within a lower Mascot Score are not loaded.",
		order = 2,
		defaultValue = "0"
	)
	public void setMinimumMascotScore(int minimumMS) {
		this.minimumMS = minimumMS;
	}
	
	@Port(
		direction = Direction.INPUT, 
		name = "Remove duplicates", 
		description = "Check this option to remove duplicated entries.",
		order = 3,
		defaultValue = "true"
	)
	public void setRemoveDuplicates(boolean removeDuplicates) {
		this.removeDuplicates = removeDuplicates;
	}
	
	@Port(
		direction = Direction.OUTPUT, 
		order = 4
	)
	public MascotIdentificationsDatatype loadData() throws IOException {
		return new MascotIdentificationsDatatype(
					load(htmlFile, minimumMS, removeDuplicates), 
				htmlFile, minimumMS);
	}
}
