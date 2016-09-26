package es.uvigo.ei.sing.s2p.core.io.csv;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CsvFormat {
	
	private String lineBreak;
	private String columnSeparator;
	private boolean quoteHeaders;
	private char decimalSeparator = '.';
	private final DecimalFormat decimalFormatter =
		new DecimalFormat("0.0000");
	private final DecimalFormatSymbols symbols =
		new DecimalFormatSymbols(Locale.getDefault());
	
	/**
	 * Constructs a new {@code CsvFormat} instance.
	 * 
	 * @param columnSeparator the column separator.
	 * @param decimalSeparator the decimal separator.
	 * @param quoteHeaders true if headers must be quote.
	 * @param lineBreak the line break.
	 */
	public CsvFormat(String columnSeparator, char decimalSeparator,
		boolean quoteHeaders, String lineBreak
	) {
		this.columnSeparator = columnSeparator;
		this.lineBreak = lineBreak;
		this.quoteHeaders = quoteHeaders;
		this.decimalSeparator = decimalSeparator;

		this.configureFormaters();
	}
	
	private void configureFormaters() {
		if (decimalSeparator != symbols.getDecimalSeparator()) {
			symbols.setDecimalSeparator(decimalSeparator);
			this.decimalFormatter.setDecimalFormatSymbols(symbols);
		}
	}

	public String getColumnSeparator() {
		return columnSeparator;
	}

	public DecimalFormat getDecimalFormatter() {
		return decimalFormatter;
	}

	public String getLineBreak() {
		return lineBreak;
	}

	public DecimalFormatSymbols getSymbols() {
		return symbols;
	}

	public boolean isQuoteHeaders() {
		return quoteHeaders;
	}
}
