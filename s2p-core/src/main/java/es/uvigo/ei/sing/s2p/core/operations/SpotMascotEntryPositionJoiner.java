package es.uvigo.ei.sing.s2p.core.operations;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;

public class SpotMascotEntryPositionJoiner {

	public static Map<String, List<MascotEntry>> join(
		Map<String, String> positionToSpot, List<MascotEntry> entries
	) {
		Map<String, List<MascotEntry>> toret = 
			new HashMap<String, List<MascotEntry>>();
		
		entries.forEach(e -> {
			String spot = positionToSpot.get(e.getPlatePosition());
			toret.putIfAbsent(spot, new LinkedList<MascotEntry>());
			toret.get(spot).add(e);
		});
		return toret;
	}
}
