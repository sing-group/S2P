package es.uvigo.ei.sing.s2p.aibench.datatypes;

import java.util.List;

import es.uvigo.ei.aibench.core.datatypes.annotation.Datatype;
import es.uvigo.ei.aibench.core.datatypes.annotation.Structure;
import es.uvigo.ei.sing.s2p.aibench.views.VennDiagramDesigner;
import es.uvigo.ei.sing.vda.core.VennDiagramDesign;
import es.uvigo.ei.sing.vda.core.entities.NamedRSet;

@Datatype(
	structure = Structure.SIMPLE, 
	namingMethod = "getName", 
	renameable = true,
	clipboardClassName = "Venn Diagram design",
	autoOpen = true
)
public class VennDiagramDesignDatatype extends VennDiagramDesign {
	private static final long serialVersionUID = 1L;
	private static int INSTANCES = 0;
	private int instanceNumber;
	
	public VennDiagramDesignDatatype() {
		this(VennDiagramDesigner.DEFAULT_DESIGN.getSets());
	}
	
	public VennDiagramDesignDatatype(List<NamedRSet<String>> sets) {
		super(sets);
		this.instanceNumber = ++INSTANCES;
	}
	
	public String getName() {
		return "Design " + this.instanceNumber;
	}
}
