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
