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
package es.uvigo.ei.sing.s2p.aibench.operations.dialogs;

import static es.uvigo.ei.sing.s2p.gui.util.CsvUtils.csvFormat;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import es.uvigo.ei.aibench.core.CoreUtils;
import es.uvigo.ei.aibench.core.ParamSpec;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.inputgui.AbstractParamProvider;
import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.s2p.gui.util.CsvPanel;

class CsvParamProvider extends AbstractParamProvider {
	private CsvPanel csvPanel = new CsvPanel();
	private Port arg0;

	public CsvParamProvider(final Port arg0) {
		this.arg0 = arg0;
		this.csvPanel.addCsvListener(e -> {
			SwingUtilities.invokeLater(() -> {
				this.setChanged();
				this.notifyObservers();
			});
		});
	}

	public JComponent getComponent() {
		return this.csvPanel;
	}

	public ParamSpec getParamSpec() throws IllegalArgumentException {
		List<CsvFormat> params = new LinkedList<>();
		params.add(csvFormat(csvPanel.getCsvFormat()));

		return CoreUtils.createParams(params)[0];
	}

	public Port getPort() {
		return arg0;
	}

	@Override
	public boolean isValidValue() {
		return this.csvPanel.isValidFormat();
	}
};