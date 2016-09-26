package es.uvigo.ei.sing.s2p.core.io;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;

public class MascotProjectLoader {
	
	private static final String TITLE_DELIMITER = " OS";
	
	private static final int INDEX_TITLE 		= 0;
	private static final int INDEX_POS_ON_SCOUT = 4;
	private static final int INDEX_MASCOT_SCORE = 5;
	private static final int INDEX_DIFFERENCE 	= 6;
	private static final int INDEX_MS_COVERAGE 	= 7;
	private static final int INDEX_ACCESSION 	= 13;
	
	public static List<MascotEntry> load(File file) throws IOException {
		List<MascotEntry> toret = new LinkedList<MascotEntry>();
		Map<Integer, String> lastColumnValues = new HashMap<Integer, String>();
		
		Document doc = Jsoup.parse(file, "UTF-8");
		Elements trs = doc.select("tr");
		for (Element tr : trs.subList(2, trs.size())) {
			Elements tds = tr.select("td");
			toret.add(createMascotEntry(tds, toret, lastColumnValues));
		}
		return toret;
	}

	private static MascotEntry createMascotEntry(Elements tds,
		List<MascotEntry> toret, Map<Integer, String> lastColumnValues
	) {
		return new MascotEntry(
			columnValue(INDEX_TITLE, tds, lastColumnValues),
			columnValue(INDEX_POS_ON_SCOUT, tds, lastColumnValues),
			Integer.valueOf(columnValue(INDEX_MASCOT_SCORE, tds, lastColumnValues)),
			Integer.valueOf(columnValue(INDEX_DIFFERENCE, tds, lastColumnValues)),
			Integer.valueOf(columnValue(INDEX_MS_COVERAGE, tds, lastColumnValues)),
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
