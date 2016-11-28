/*
 * #%L
 * S2P Core
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
package es.uvigo.ei.sing.s2p.core.entities;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SpotsCount {
	
	private Condition condition;
	private Map<String, Integer> spotsPresence;
	private Set<String> spots;

	public SpotsCount(Condition condition) {
		this(condition, Collections.emptySet());
	}
	
	public SpotsCount(Condition condition, Set<String> spots) {
		this.condition = condition;
		this.spots = spots;
		this.initData();
	}

	private void initData() {
		spotsPresence = new HashMap<String, Integer>();
		spots.forEach(p -> spotsPresence.put(p, 0));
		this.condition.getSamples().forEach(s -> {
			s.getSpotValues().keySet().forEach( key -> {
				spotsPresence.putIfAbsent(key, 0);
				spotsPresence.put(key, spotsPresence.get(key)+1);
			});
		});
	}
	
	public Set<String> getSpots(int presenceThreshold) {
		Set<String> spots = new HashSet<String>();
		spotsPresence.entrySet().forEach(es -> {
			if(es.getValue() >= presenceThreshold) {
				spots.add(es.getKey());
			}
		});
		
		return spots;
	}
	
	public Set<String> getSpots(int minPresenceThreshold,
		int maxPresenceThreshold
	) {
		Set<String> spots = new HashSet<String>();
		spotsPresence.entrySet().forEach(es -> {
			if(es.getValue() >= minPresenceThreshold && 
				es.getValue() <= maxPresenceThreshold
			) {
				spots.add(es.getKey());
			}
		});
		
		return spots;
	}
}
