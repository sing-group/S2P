/*
 * #%L
 * S2P
 * %%
 * Copyright (C) 2016 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
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
package es.uvigo.ei.sing.s2p.aibench.datatypes;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.uvigo.ei.aibench.core.datatypes.annotation.Datatype;
import es.uvigo.ei.aibench.core.datatypes.annotation.Property;
import es.uvigo.ei.aibench.core.datatypes.annotation.Structure;
import es.uvigo.ei.sing.s2p.core.entities.SameSpotsThrehold;
import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.util.FileUtils;

@Datatype(
	structure = Structure.COMPLEX,
	namingMethod = "getName",
	renameable = true ,
	clipboardClassName = "SameSpot sample analysis",
	autoOpen = true
)
public class SameSpotsSampleRepeatedMeasuresDatatype {

	private List<Sample> samples;
	private File file;
	private SameSpotsThrehold threshold;
	private Map<Sample, String> conditions;

	public SameSpotsSampleRepeatedMeasuresDatatype(File file,
			SameSpotsThrehold threshold, List<Sample> samples
	) {
		this.file = file;
		this.threshold = threshold;
		this.samples = samples;
		createSamplesConditions();
	}

	private void createSamplesConditions() {
		this.conditions = new HashMap<Sample, String>();
		for (Sample s : samples) {
			this.conditions.put(s, s.getName());
			s.setName(FileUtils.removeExtension(this.file) + " " + s.getName());
		}
	}

	public String getName() {
		return file.getName();
	}

	@Property(name = "File")
	public String getFile() {
		return file.getAbsolutePath();
	}

	@Property(name = "p-value")
	public double getPValue() {
		return threshold.getP();
	}

	@Property(name = "fold")
	public double getFold() {
		return threshold.getFold();
	}

	public List<Sample> getSamples() {
		return samples;
	}

	public Map<Sample, String> getConditions() {
		return conditions;
	}
}
