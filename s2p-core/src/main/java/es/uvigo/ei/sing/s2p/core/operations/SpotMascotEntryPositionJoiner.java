package es.uvigo.ei.sing.s2p.core.operations;

import java.util.HashMap;
import java.util.Map;

import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

public class SpotMascotEntryPositionJoiner {

	public static Map<String, MascotIdentifications> join(
		Map<String, String> positionToSpot, MascotIdentifications entries
	) {
		Map<String, MascotIdentifications> toret = 
			new HashMap<String, MascotIdentifications>();
		
		entries.forEach(e -> {
			String spot = positionToSpot.get(e.getPlatePosition());
			toret.putIfAbsent(spot, new MascotIdentifications());
			toret.get(spot).add(e);
		});
		return toret;
	}
}
