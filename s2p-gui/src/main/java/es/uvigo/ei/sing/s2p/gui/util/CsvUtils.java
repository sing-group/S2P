package es.uvigo.ei.sing.s2p.gui.util;

import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;

public class CsvUtils {

	public static CsvFormat csvFormat(
		es.uvigo.ei.sing.hlfernandez.input.csv.CsvFormat source
	) {
		return new CsvFormat(
			source.getColumnSeparator(),
			source.getDecimalSeparator(), 
			source.isQuoteHeaders(),
			source.getLineBreak()
		);
	}
}
