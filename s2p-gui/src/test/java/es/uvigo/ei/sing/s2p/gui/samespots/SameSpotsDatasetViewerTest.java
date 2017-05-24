/*
 * #%L
 * S2P GUI
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
package es.uvigo.ei.sing.s2p.gui.samespots;

import static org.sing_group.gc4s.demo.DemoUtils.showComponent;
import static es.uvigo.ei.sing.s2p.core.resources.TestResources.SAMESPOTS_DIRECTORY;
import static es.uvigo.ei.sing.s2p.gui.TestUtils.setNimbusLookAndFeel;
import static java.awt.Frame.MAXIMIZED_BOTH;

import java.io.IOException;
import java.util.List;

import es.uvigo.ei.sing.s2p.core.entities.Sample;
import es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsDirectoryLoader;
import es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsFileLoader;

public class SameSpotsDatasetViewerTest {
	
	public static void main(String[] args) throws IOException {
		List<Sample> samples = SameSpotsDirectoryLoader.loadDirectory(
			SAMESPOTS_DIRECTORY, SameSpotsFileLoader.DEFAULT_THRESHOLD);
		
		setNimbusLookAndFeel();
		showComponent(new SameSpotsDatasetViewer(samples), MAXIMIZED_BOTH);
	}
}
