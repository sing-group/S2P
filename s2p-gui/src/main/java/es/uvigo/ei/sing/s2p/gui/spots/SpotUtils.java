/*
 * #%L
 * S2P GUI
 * %%
 * Copyright (C) 2016 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
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
package es.uvigo.ei.sing.s2p.gui.spots;

import java.util.Optional;

import es.uvigo.ei.sing.s2p.core.entities.MascotIdentifications;
import es.uvigo.ei.sing.s2p.core.entities.SpotMascotIdentifications;

public class SpotUtils {
	
	public static String spotValue(String spot,
		boolean showProteinIdentifications,
		Optional<SpotMascotIdentifications> mascotIdentifications
	) {
		StringBuilder spotValue = new StringBuilder(spot);
		if(showProteinIdentifications) {
			if(mascotIdentifications.isPresent()) {
				MascotIdentifications spotIdentifications = 
					mascotIdentifications.get().get(spot);
				if(!spotIdentifications.isEmpty()) {
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
		Optional<SpotMascotIdentifications> mascotIdentifications
	) {
		if(mascotIdentifications.isPresent()) {
			MascotIdentifications spotIdentifications = 
				mascotIdentifications.get().get(value);
			if (!spotIdentifications.isEmpty()) {
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
