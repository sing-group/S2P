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
package es.uvigo.ei.sing.s2p.core.operations;

import java.util.HashSet;
import java.util.Set;

public class StringSetComparison {

	private Set<String> set1;
	private Set<String> set2;
	private Set<String> intersection;
	
	private int set1Size;
	private int set2Size;
	private int intersectionSize;
	
	public StringSetComparison(Set<String> set1, Set<String> set2) {
		this.set1 = set1;
		this.set2 = set2;
		
		this.initData();
	}
	
	private void initData() {
		intersection = new HashSet<String>(this.set1);
		intersection.retainAll(this.set2);

		this.intersectionSize = this.intersection.size();
		this.set1Size = set1.size();
		this.set2Size = set2.size();
	}
	
	public Set<String> getSet1() {
		return set1;
	}
	
	public Set<String> getSet1Unique() {
		return unique(set1);
	}
	
	private Set<String> unique(Set<String> set) {
		Set<String> unique = new HashSet<String>(set);
		unique.removeAll(intersection);
		
		return unique;
	}

	public Set<String> getSet2() {
		return set2;
	}
	
	public Set<String> getSet2Unique() {
		return unique(set2);
	}
	
	public Set<String> getIntersection() {
		return intersection;
	}

	public int getSet1Size() {
		return set1Size;
	}
	
	public int getSet1UniqueSize() {
		return set1Size - intersectionSize;
	}
	
	public int getSet2Size() {
		return set2Size;
	}
	
	public int getSet2UniqueSize() {
		return set2Size - intersectionSize;
	}
	
	public int getIntersectionSize() {
		return intersectionSize;
	}
}
