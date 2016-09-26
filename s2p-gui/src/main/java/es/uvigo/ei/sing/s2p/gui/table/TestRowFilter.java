package es.uvigo.ei.sing.s2p.gui.table;
import javax.swing.RowFilter;

public class TestRowFilter<M, I> extends RowFilter<M, I> {
	private Tester tester;
	private int column;

	public TestRowFilter() {
		this.tester = null;
		this.column = -1;
	}

	public TestRowFilter(Tester tester, int column) {
		this.tester = tester;
		this.column = column;
	}

	public Tester getTester() {
		return tester;
	}

	public void setTester(Tester tester) {
		this.tester = tester;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	@Override
	public boolean include(RowFilter.Entry<? extends M, ? extends I> entry) {
		if (	this.tester == null || 
				this.column < 0 || 
				entry.getValueCount() <= column
		) {
			return true;
		} else {
			return this.tester.test(entry.getValue(this.column));
		}
	}
}
