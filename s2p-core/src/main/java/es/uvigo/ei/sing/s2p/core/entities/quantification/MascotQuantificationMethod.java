package es.uvigo.ei.sing.s2p.core.entities.quantification;

public enum MascotQuantificationMethod {
	EMPAI("emPAI");

	private String method;

	MascotQuantificationMethod(String method) {
		this.method = method;
	}

	public String getMethod() {
		return this.method;
	}

	@Override
	public String toString() {
		return getMethod();
	}
}
