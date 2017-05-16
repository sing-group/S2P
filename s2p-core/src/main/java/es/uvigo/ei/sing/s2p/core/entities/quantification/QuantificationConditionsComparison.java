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
package es.uvigo.ei.sing.s2p.core.entities.quantification;

import java.util.Map;

public class QuantificationConditionsComparison {

	private QuantificationCondition condition1;
	private QuantificationCondition condition2;
	private Map<String, Double> proteinPvalues;
	private Map<String, Double> proteinQvalues;

	public QuantificationConditionsComparison(
		QuantificationCondition condition1,
		QuantificationCondition condition2,
		Map<String, Double> proteinPvalues,
		Map<String, Double> proteinQvalues
	) {
		this.condition1 = condition1;
		this.condition2 = condition2;
		this.proteinPvalues = proteinPvalues;
		this.proteinQvalues = proteinQvalues;
	}

	public QuantificationCondition getCondition1() {
		return condition1;
	}

	public QuantificationCondition getCondition2() {
		return condition2;
	}

	public Map<String, Double> getProteinPvalues() {
		return proteinPvalues;
	}

	public Map<String, Double> getProteinQvalues() {
		return proteinQvalues;
	}
	
	@Override
	public String toString() {
		return condition1 + " vs. " + condition2;
	}
}
