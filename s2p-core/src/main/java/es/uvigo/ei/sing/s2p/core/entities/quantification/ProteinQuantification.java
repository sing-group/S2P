package es.uvigo.ei.sing.s2p.core.entities.quantification;

public interface ProteinQuantification {
	public abstract String getProtein();

	public abstract MascotQuantificationMethod getQuantificationMethod();

	public abstract double getValue();

	public abstract double getNormalizedValue();
}
