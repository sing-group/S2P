/*
 * #%L
 * S2P Core
 * %%
 * Copyright (C) 2016 - 2017 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
 * 			Reboiro-Jato, Hugo López-Fernández, and Daniel Glez-Peña
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
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
