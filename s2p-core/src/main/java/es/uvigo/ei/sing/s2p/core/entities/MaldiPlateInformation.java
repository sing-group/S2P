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
