/*
 * #%L
 * S2P Core
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
package es.uvigo.ei.sing.s2p.core.io.samespots;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import es.uvigo.ei.sing.s2p.core.entities.Pair;
import es.uvigo.ei.sing.s2p.core.entities.SameSpotsThrehold;
import es.uvigo.ei.sing.s2p.core.entities.Sample;

public class SameSpotsDirectoryLoader extends AbstractSameSpotsDirectoryLoader {

	private SameSpotsDirectoryLoader() {
	}
	
	public static List<Sample> loadDirectory(File directory,
		SameSpotsThrehold threshold) throws IOException
	{
		SameSpotsDirectoryLoader loader = new SameSpotsDirectoryLoader();
		return loader.load(directory, threshold);
	}

	protected List<File> listFiles(File directory) {
		return Arrays.asList(directory.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile() && pathname.getName().endsWith(".htm");
			}
		}));
	}

	@Override
	protected Pair<Sample, Sample> loadFile(File file,
		SameSpotsThrehold threshold) throws IOException 
	{
		return SameSpotsFileLoader.load(file, threshold);
	}
}
