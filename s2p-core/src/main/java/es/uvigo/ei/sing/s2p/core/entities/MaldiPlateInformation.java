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
package es.uvigo.ei.sing.s2p.core.entities;

import java.io.Serializable;
import java.util.Optional;

public class MaldiPlateInformation  implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIELD_USER = "User";
	public static final String FIELD_DATE = "Date";
	public static final String FIELD_SAMPLE_TYPE = "Type of sample";
	public static final String FIELD_MALDI_TARGET = "Type of MALDI target";
	public static final String FIELD_MASCOT_EXPERIMENT = "Mascot experiment";
	
	public enum MaldiTarget {
		GSP("Ground Steel Peptides"), 
		NPs("NPs/Organics"), 
		PS("Polished Steel"), 
		AC400("AnchorChip 400/384TF"), 
		AC800("AnchorChip 800/384TF");
		
		private String description;

		MaldiTarget(String description) {
			this.description = description;
		}
		
		@Override
		public String toString() {
			return description;
		}
	
	};
	
	private String user 			= "";
	private String date				= "";
	private String sampleType		= "";
	private MaldiTarget maldiTarget	= null;
	private String mascotExperiment	= "";
	
	public MaldiPlateInformation() { }
	
	public String getUser() {
		return user;
	}

	public String getDate() {
		return date;
	}

	public String getSampleType() {
		return sampleType;
	}

	public Optional<MaldiTarget> getMaldiTarget() {
		return Optional.ofNullable(maldiTarget);
	}

	public String getMascotExperiment() {
		return mascotExperiment;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public void setMaldiTarget(MaldiTarget maldiTarget) {
		this.maldiTarget = maldiTarget;
	}

	public void setMascotExperiment(String mascotExperiment) {
		this.mascotExperiment = mascotExperiment;
	}
}
