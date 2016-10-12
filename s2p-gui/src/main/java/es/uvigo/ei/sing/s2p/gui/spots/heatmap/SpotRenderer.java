package es.uvigo.ei.sing.s2p.gui.spots.heatmap;

import static java.util.stream.Collectors.joining;
import es.uvigo.ei.sing.s2p.core.entities.MascotEntry;
import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;

public class SpotRenderer {
	public static enum IdentificationsMode {
		BEST_SCORE("Best Mascot score"), 
		ALL("All identifications"), 
		NONE("None");

		private String description;

		IdentificationsMode(String description) {
			this.description = description;
		}

		@Override
		public String toString() {
			return description;
		}
	};

	private IdentificationsMode mode;
	private boolean showSpot;
	
	public SpotRenderer(IdentificationsMode mode, boolean showSpot) {
		this.mode = mode;
		this.showSpot = showSpot;
	}
	
	public String getSpotValue(String spot,
		MascotIdentifications spotIdentifications
	) {
		StringBuilder sb = new StringBuilder();
		
		if (mode.equals(IdentificationsMode.BEST_SCORE)) {
			if(spotIdentifications.isEmpty()) {
				sb.append("N/A");
			} else { 
				sb.append(spotIdentifications.getFirst().getTitle());
			}
		} else if (mode.equals(IdentificationsMode.ALL)) {
			if(spotIdentifications.isEmpty()) {
				sb.append("N/A");
			} else {
				sb.append(
					spotIdentifications.stream()
						.map(MascotEntry::getTitle)
						.distinct()
						.collect(joining(", "))
						.toString()
				);
			}
		}
		
		if(showSpot) {
			boolean onlySpot = 	mode.equals(IdentificationsMode.NONE);

			sb
				.append(onlySpot ? "": " (")
				.append(spot)
				.append(onlySpot ? "":  ")");
		}
		
		return sb.toString();
	}
	
}