package es.uvigo.ei.sing.s2p.core.io.quantification;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import es.uvigo.ei.sing.s2p.core.entities.quantification.DefaultProteinQuantification;
import es.uvigo.ei.sing.s2p.core.entities.quantification.MascotQuantificationMethod;
import es.uvigo.ei.sing.s2p.core.entities.quantification.ProteinQuantification;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationReplicate;

public class QuantificationCsvFileLoader {
	
	private static final String PROTEIN_TITLE_DELIMITER = " OS";

	public static QuantificationReplicate load(File quantificationEmpaiFile,
		MascotQuantificationMethod method
	) throws FileNotFoundException {
		return new QuantificationReplicate(
			extractProteinQuantifications(quantificationEmpaiFile, method)
		);
	}

	private static List<ProteinQuantification> extractProteinQuantifications(
		File quantificationEmpaiFile, MascotQuantificationMethod method
	) throws FileNotFoundException {
		Scanner scanner = new Scanner(quantificationEmpaiFile);
		List<ProteinQuantification> proteins = new LinkedList<>();
		
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line.contains(method.getMethod())) {
				proteins.add(parseProtein(line, method));
			}
		}
		scanner.close();

		return proteins;
	}

	private static ProteinQuantification parseProtein(String line,
		MascotQuantificationMethod method
	) {
		String[] lineSplit = line.split(",");
		return 	new DefaultProteinQuantification(
				parseProteinName(lineSplit[2]),
				method,
				Double.valueOf(lineSplit[lineSplit.length-1])
			);
	}

	private static String parseProteinName(String proteinTitle) {
		return proteinTitle.contains(PROTEIN_TITLE_DELIMITER) ? 
			proteinTitle.substring(1, proteinTitle.indexOf(PROTEIN_TITLE_DELIMITER)) : 
			proteinTitle.substring(1);
	}
}
