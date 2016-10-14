package es.uvigo.ei.sing.s2p.core.io.samespots;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import es.uvigo.ei.sing.commons.csv.entities.CsvFormat;
import es.uvigo.ei.sing.s2p.core.entities.Pair;
import es.uvigo.ei.sing.s2p.core.entities.SameSpotsThrehold;
import es.uvigo.ei.sing.s2p.core.entities.Sample;

public class SameSpotsCsvDirectoryLoader extends AbstractSameSpotsDirectoryLoader {

	private CsvFormat format;

	private SameSpotsCsvDirectoryLoader(CsvFormat format) {
		this.format = format;
	}
	
	public static List<Sample> loadDirectory(File directory,
		SameSpotsThrehold threshold, CsvFormat format) throws IOException 
	{
		SameSpotsCsvDirectoryLoader loader = new SameSpotsCsvDirectoryLoader(format);
		return loader.load(directory, threshold);
	}
	
	protected List<File> listFiles(File directory) {
		return Arrays.asList(directory.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile() && pathname.getName().endsWith(".csv");
			}
		}));
	}

	@Override
	protected Pair<Sample, Sample> loadFile(File file,
		SameSpotsThrehold threshold) throws IOException {
		return SameSpotsCsvFileLoader.load(file, threshold, format);
	}
}

