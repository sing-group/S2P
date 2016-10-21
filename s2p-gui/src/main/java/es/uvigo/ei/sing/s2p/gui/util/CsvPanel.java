package es.uvigo.ei.sing.s2p.gui.util;

import static es.uvigo.ei.sing.s2p.gui.util.CsvUtils.csvFormat;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;

public class CsvPanel extends es.uvigo.ei.sing.hlfernandez.input.csv.CsvPanel {
	private static final long serialVersionUID = 1L;

	public CsvFormat getConvertedCsvFormat() {
		return csvFormat(super.getCsvFormat());
	}
}
