package es.uvigo.ei.sing.s2p.core.io;

import static es.uvigo.ei.sing.s2p.core.operations.MascotIdentificationsOperations.removeDuplicates;
import static java.lang.Integer.valueOf;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

public class MascotProjectLoader {
	
	private static final String TITLE_DELIMITER = " OS";
	
	private static final int INDEX_TITLE 		= 0;
	private static final int INDEX_POS_ON_SCOUT = 4;
	private static final int INDEX_MASCOT_SCORE = 5;
	private static final int INDEX_DIFFERENCE 	= 6;
	private static final int INDEX_MS_COVERAGE 	= 7;
	private static final int INDEX_PROTEIN_MW 	= 8;
	private static final int INDEX_METHOD	 	= 10;
	private static final int INDEX_PI_VALUE 	= 11;
	private static final int INDEX_ACCESSION 	= 13;
	
	public static MascotIdentifications load(File file) throws IOException {
		return load(file, false);
	}

	public static MascotIdentifications load(File file, boolean removeDuplicates)
		throws IOException 
	{
		return load(file, Integer.MIN_VALUE, removeDuplicates);
	}
	
	public static MascotIdentifications load(File file, int minScore,
		boolean removeDuplicates) throws IOException 
	{
		MascotIdentifications toret = new MascotIdentifications();
		Map<Integer, String> lastColumnValues = new HashMap<Integer, String>();
		
		Document doc = Jsoup.parse(file, "UTF-8");
		Elements trs = doc.select("tr");
		for (Element tr : trs.subList(2, trs.size())) {
			Elements tds = tr.select("td");
			MascotEntry entry = createMascotEntry(tds, toret, lastColumnValues);

			if(entry.getMascotScore() > minScore) {
				toret.add(entry);
			}
		}
		
		return removeDuplicates ? removeDuplicates(toret) : toret;
	}

	private static MascotEntry createMascotEntry(Elements tds,
		MascotIdentifications toret, Map<Integer, String> lastColumnValues
	) {
		return new MascotEntry(
			columnValue(INDEX_TITLE, tds, lastColumnValues),
			columnValue(INDEX_POS_ON_SCOUT, tds, lastColumnValues),
			valueOf(columnValue(INDEX_MASCOT_SCORE, tds, lastColumnValues)),
			valueOf(columnValue(INDEX_DIFFERENCE, tds, lastColumnValues)),
			valueOf(columnValue(INDEX_MS_COVERAGE, tds, lastColumnValues)),
			Double.valueOf(columnValue(INDEX_PROTEIN_MW, tds, lastColumnValues)),
			columnValue(INDEX_METHOD, tds, lastColumnValues),
			Double.valueOf(columnValue(INDEX_PI_VALUE, tds, lastColumnValues)),
			columnValue(INDEX_ACCESSION, tds, lastColumnValues)
		);
	}
	
	private static String columnValue(int column, Elements tds,
		Map<Integer, String> lastColumnValues
	) {
		String text = tds.get(column).html();
		
		if(text.equals("&nbsp;")) {
			text = lastColumnValues.get(column);
		}
		
		if(column == INDEX_TITLE && text.contains(TITLE_DELIMITER)) {
			text = text.substring(0, text.indexOf(TITLE_DELIMITER));
		}
		
		lastColumnValues.put(column, text);
		return text;
	}
}
