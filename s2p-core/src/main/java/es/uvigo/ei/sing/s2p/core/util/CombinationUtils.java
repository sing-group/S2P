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
