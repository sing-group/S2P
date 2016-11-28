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
