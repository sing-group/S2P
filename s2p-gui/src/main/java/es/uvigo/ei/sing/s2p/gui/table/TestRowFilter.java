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
