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

import java.util.List;
import java.util.Optional;

public class QuantificationSample {
	
	private String name;
	private List<QuantificationReplicate> replicates;
	private String condition;
	private double proteinMass;

	public QuantificationSample(String name,
		List<QuantificationReplicate> replicates, double proteinMass
	) {
		this.name = name;
		this.replicates = replicates;
		this.proteinMass = proteinMass;
	}
	
	public QuantificationSample(String name, String condition, 
		List<QuantificationReplicate> replicates, double proteinMass
	) {
		this.name = name;
		this.condition = condition;
		this.replicates = replicates;
		this.proteinMass = proteinMass;
	}
	
	public String getName() {
		return name;
	}

	public Optional<String> getCondition() {
		return Optional.ofNullable(condition);
	}

	public List<QuantificationReplicate> getReplicates() {
		return replicates;
	}

	public double getProteinMass() {
		return proteinMass;
	}

	@Override
	public boolean equals(Object aThat) {
		if (aThat == null) {
			return false;
		}
		if (!(aThat instanceof QuantificationSample)) {
			return false;
		}

		QuantificationSample that = (QuantificationSample) aThat;
		return 	this.name.equals(that.name) &&
				this.proteinMass == that.proteinMass &&
				this.getCondition().equals(that.getCondition()) &&
				this.replicates.equals(that.replicates);
	}
}
