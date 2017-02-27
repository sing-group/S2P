package es.uvigo.ei.sing.s2p.core.util;

import java.util.Map;
import java.util.logging.Logger;

import es.uvigo.ei.sing.math.statistical.corrections.Correction;

public class StatisticsTestsUtils {
	
	private static final Logger LOGGER = 
		Logger.getLogger(StatisticsTestsUtils.class.getName());

	public static <K> Map<K, Double> correct(Correction correction,
		Map<K, Double> pValues
	) {
		try {
			return 	es.uvigo.ei.sing.math.statistical.StatisticsTestsUtils
					.correct(correction, pValues);
		} catch (InterruptedException e) {
			LOGGER.warning("Correction execution interrupted");
			throw new RuntimeException(e);
		}
	}
}
