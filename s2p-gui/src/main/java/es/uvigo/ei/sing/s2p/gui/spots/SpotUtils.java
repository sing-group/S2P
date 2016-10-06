package es.uvigo.ei.sing.s2p.gui.spots;

import java.util.Map;
import java.util.Optional;

import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

public class SpotUtils {
	
	public static String spotValue(String spot,
		boolean showProteinIdentifications,
		Optional<Map<String, MascotIdentifications>> mascotIdentifications
	) {
		StringBuilder spotValue = new StringBuilder(spot);
		if(showProteinIdentifications) {
			if(mascotIdentifications.isPresent()) {
				MascotIdentifications spotIdentifications = 
					mascotIdentifications.get().get(spot);
				if(spotIdentifications != null) {
					spotValue
						.append(" (")
						.append(spotIdentifications.get(0).getTitle())
						.append(")");
				}
			}
		}
		return spotValue.toString();
	}
	
	public static String spotTooltip(String value,
		Optional<Map<String, MascotIdentifications>> mascotIdentifications
	) {
		if(mascotIdentifications.isPresent()) {
			MascotIdentifications spotIdentifications = 
				mascotIdentifications.get().get(value);
			if(spotIdentifications != null) {
				StringBuilder tooltip = new StringBuilder();
				tooltip
					.append(spotIdentifications.get(0).getTitle())
					.append(" (MS=")
					.append(spotIdentifications.get(0).getMascotScore())
					.append(")");
				if(spotIdentifications.size() > 1) {
					tooltip.append(" [...]");
				}
				return tooltip.toString();
			}
		}
		return value;
	}
}
