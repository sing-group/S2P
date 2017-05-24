/*
 * #%L
 * S2P GUI
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
