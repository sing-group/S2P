package es.uvigo.ei.sing.s2p.core.io.quantification;

import static java.lang.Double.valueOf;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import es.uvigo.ei.sing.s2p.core.entities.quantification.MascotQuantificationMethod;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationDataset;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationReplicate;
import es.uvigo.ei.sing.s2p.core.entities.quantification.QuantificationSample;
import es.uvigo.ei.sing.s2p.core.operations.quantification.DefaultNormalizationFactor;
import es.uvigo.ei.sing.s2p.core.operations.quantification.NoNormalizationStrategy;
import es.uvigo.ei.sing.s2p.core.operations.quantification.NormalizationFactor;
import es.uvigo.ei.sing.s2p.core.operations.quantification.NormalizationStrategy;

public class QuantificationCsvDatasetLoader {
	public static final String PROTEIN_MASSES_FILE = "protein-mass.txt";
	
	private static final Logger LOGGER = Logger.getLogger(
		QuantificationCsvDatasetLoader.class.getName());

	public static QuantificationDataset load(File quantificationDirectory,
		MascotQuantificationMethod quantificationMethod 
	) {
		return load(quantificationDirectory, quantificationMethod, 
			new NoNormalizationStrategy(), new DefaultNormalizationFactor());
	}
	public static QuantificationDataset load(File quantificationDirectory,
		MascotQuantificationMethod quantificationMethod, 
		NormalizationStrategy normalizationStrategy, NormalizationFactor factor
	) {
		return new QuantificationDataset(
			normalizationStrategy.normalize(loadDirectory(
			requireDirectory(quantificationDirectory),
			quantificationMethod, false
		), factor));
	}

	private static File requireDirectory(File quantificationDirectory) {
		if (!quantificationDirectory.isDirectory()) {
			throw new IllegalArgumentException(
				"quantificationDirectory must be a directory");
		}
		return quantificationDirectory;
	}

	private static List<QuantificationSample> loadDirectory(File directory, 
		MascotQuantificationMethod quantificationMethod, 
		boolean useDirectoryNameAsCondition
	) {
		if (containsCsvs(directory)) {
			return loadCsvs(directory, quantificationMethod, 
				useDirectoryNameAsCondition);
		} else {
			List<QuantificationSample> samples = new LinkedList<>();
			for (File subdir : directory.listFiles(n -> n.isDirectory())) {
				samples.addAll(loadCsvs(subdir, quantificationMethod, true));
			}
			return samples;
		}
	}

	private static boolean containsCsvs(File directory) {
		return directory
				.listFiles(n -> n.getName().endsWith(".csv")).length > 0;
	}

	private static List<QuantificationSample> loadCsvs(File directory,
		MascotQuantificationMethod quantificationMethod,
		boolean useDirectoryNameAsCondition
	) {
		Properties sampleProteinMasses = loadSampleProteinMass(directory);
		Map<String, List<QuantificationReplicate>> sampleToReplicates = 
			createReplicatesMap(directory, quantificationMethod);
		
		return 	new LinkedList<>(
			sampleToReplicates.entrySet().stream()
			.map(e ->  {
				double sampleProteinMass = 
					valueOf(sampleProteinMasses.getProperty(e.getKey(), "0")
					.toString()).doubleValue();

				if (useDirectoryNameAsCondition) {
					return new QuantificationSample(e.getKey(),
						directory.getName(), e.getValue(), sampleProteinMass);
				} else {
					return new QuantificationSample(e.getKey(), e.getValue(), 
						sampleProteinMass);
				}
			})
			.collect(toList())
		);
	}

	private static Properties loadSampleProteinMass(File directory) {
		Properties sampleProteinMasses = new Properties();
		try {
			sampleProteinMasses.load(
				new FileInputStream(findProteinMassesFile(directory))
			);
		} catch (IOException e) {
			LOGGER.info("Protein masses file not found at "
				+ directory.getAbsolutePath()
				+ ". A mass value of 0 will be used for all samples");
		}
		return sampleProteinMasses;
	}

	private static File findProteinMassesFile(File directory)
			throws FileNotFoundException {
		for (File f : directory.listFiles()) {
			if (f.getName().toLowerCase().equals(PROTEIN_MASSES_FILE)) {
				return f;
			}
		}
		throw new FileNotFoundException();
	}

	private static Map<String, List<QuantificationReplicate>> createReplicatesMap(
		File directory, MascotQuantificationMethod quantificationMethod
	) {
		Map<String, List<QuantificationReplicate>> sampleToReplicates = new HashMap<>();

		for (File f : directory.listFiles(n -> n.getName().endsWith(".csv"))) {
			String name = sampleName(f);
			sampleToReplicates.putIfAbsent(name, new LinkedList<>());

			try {
				sampleToReplicates.get(name).add(
					QuantificationCsvFileLoader.load(f, quantificationMethod)
				);
			} catch (FileNotFoundException e) {
				throw new IllegalStateException(e);
			}
		};

		return sampleToReplicates;
	}	

	private static String sampleName(File f) {
		String name = f.getName();
		if(name.contains(".")) {
			return name.substring(0, name.indexOf("."));
		} else {
			return name;
		}
	}
}
