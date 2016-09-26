package es.uvigo.ei.sing.s2p.core.entities;

public class Pair<E, T> {

	private E first;
	private T second;

	public Pair(E first, T second) {
		this.first = first;
		this.second = second;
	}
	
	public E getFirst() {
		return first;
	}
	
	public T getSecond() {
		return second;
	}
	
	@Override
	public String toString() {
		return 	"[First: " + first.toString() + 
				", second: " + second.toString() + "]";
	}
}
