/*
 * #%L
 * S2P Core
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
package es.uvigo.ei.sing.s2p.core.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import es.uvigo.ei.sing.s2p.core.entities.Pair;

public class CombinationUtils {

	public static <T> List<Pair<T,T>> allPairs(Set<T> set){ 
		List<Pair<T,T>> toret = new LinkedList<>();
		List<T> elements = new LinkedList<>(set);
		for(int i = 0; i < elements.size() - 1; i++) {
			T elementA = elements.get(i);
			for(int j = i+1; j < elements.size(); j++) {
				T elementB = elements.get(j);
				toret.add(new Pair<T, T>(elementA, elementB));
			}
		}
		return toret;
	}
}
