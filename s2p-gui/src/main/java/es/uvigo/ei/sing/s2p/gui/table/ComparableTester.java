package es.uvigo.ei.sing.s2p.gui.table;

public abstract class ComparableTester<T> implements Tester {
	protected Comparable<T> value;

	public ComparableTester() {
		this.value = null;
	}

	public ComparableTester(Comparable<T> value) {
		this.value = value;
	}

	public Comparable<T> getValue() {
		return value;
	}

	public void setValue(Comparable<T> value) {
		this.value = value;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean test(Object value) {
		if (this.value == null || !this.value.getClass().isInstance(value)) {
			return true;
		} else {
			return this.compareTo((T) value);
		}
	}
	
	protected abstract boolean compareTo(T value);
}
