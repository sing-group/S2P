/*
 * #%L
 * S2P
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
package es.uvigo.ei.sing.s2p.aibench.operations.dialogs;

import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.inputgui.ParamProvider;
import es.uvigo.ei.aibench.workbench.inputgui.ParamsWindow;

public abstract class AbstractCsvInputDialog extends ParamsWindow {
	private static final long serialVersionUID = 1L;
	
	public static final String CSV_FORMAT = "CSV format";
	public static final String CSV_FORMAT_DESCRIPTION = 
		"<html>The CSV format of the CSV files. S2P includes two " +
		"preconfigured formats for Excel and Open/LibreOffice " +
		"applications. <br/>In case you need to specify a custom CSV format, " +
		"you can select the Custom option, which allows you to configure " +
		"the CSV format.</html>";

	protected ParamProvider getParamProvider(final Port arg0,
		final Class<?> arg1, final Object arg2
	) {
		
		if (arg0.name().equals(CSV_FORMAT)) {
			return new CsvParamProvider(arg0);
		}

		return super.getParamProvider(arg0, arg1, arg2);
	}
}
