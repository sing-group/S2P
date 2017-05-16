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
package es.uvigo.ei.sing.s2p.gui.quantification;

import static es.uvigo.ei.sing.s2p.core.resources.TestResources.QUANTIFICATION_EMPAI_DIRECTORY;
import static es.uvigo.ei.sing.s2p.gui.TestUtils.setNimbusLookAndFeel;
import static org.sing_group.gc4s.demo.DemoUtils.showComponent;

import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;

import es.uvigo.ei.sing.s2p.core.entities.quantification.MascotQuantificationMethod;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;
import es.uvigo.ei.sing.s2p.core.io.quantification.QuantificationCsvDatasetLoader;
import es.uvigo.ei.sing.s2p.core.operations.quantification.ReplicateNormalizationStrategy;
import es.uvigo.ei.sing.s2p.core.operations.quantification.SumNormalizationFactor;
import es.uvigo.ei.sing.s2p.core.operations.quantification.comparison.ComparisonMode;

public class QuantificationDatasetViewerTest {

	public static void main(String[] args) throws IOException {
		setNimbusLookAndFeel();
		JComponent viewer = new QuantificationDatasetViewer(testDataset(),
			ComparisonMode.PROTEIN_MASS);
		showComponent(viewer, JFrame.MAXIMIZED_BOTH);
	}

	private static QuantificationDataset testDataset() {
		return QuantificationCsvDatasetLoader.load(
				QUANTIFICATION_EMPAI_DIRECTORY, 
				MascotQuantificationMethod.EMPAI,
				new ReplicateNormalizationStrategy(), 
				new SumNormalizationFactor()
			);
	}
}
