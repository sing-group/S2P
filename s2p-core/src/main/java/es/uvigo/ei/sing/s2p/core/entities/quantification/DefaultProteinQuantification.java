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

public class DefaultProteinQuantification implements ProteinQuantification {

	private String protein;
	private MascotQuantificationMethod method;
	private Double value;
	private Double normalizedValue;

	public DefaultProteinQuantification(String protein,
		MascotQuantificationMethod method, Double value
	) {
		this(protein, method, value, value);
	}

	public DefaultProteinQuantification(String protein,
		MascotQuantificationMethod method, Double value,
		Double normalizedValue
	) {
		this.protein = protein;
		this.method = method;
		this.value = value;
		this.normalizedValue = normalizedValue;
	}

	@Override
	public String getProtein() {
		return this.protein;
	}

	@Override
	public MascotQuantificationMethod getQuantificationMethod() {
		return this.method;
	}

	@Override
	public double getValue() {
		return this.value;
	}

	@Override
	public double getNormalizedValue() {
		return this.normalizedValue;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb
			.append(protein)
			.append(", ")
			.append(method.toString())
			.append(", ")
			.append(value)
			.append(", ")
			.append(normalizedValue);
		return sb.toString();
	}

	@Override
	public boolean equals(Object aThat) {
		if (aThat == null) {
			return false;
		}
		if (!(aThat instanceof DefaultProteinQuantification)) {
			return false;
		}

		DefaultProteinQuantification that = (DefaultProteinQuantification) aThat;
		return 	protein.equals(that.protein) &&
				method.equals(that.method) &&
				value.equals(that.value) &&
				normalizedValue.equals(that.normalizedValue);
	}
}
