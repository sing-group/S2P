package es.uvigo.ei.sing.s2p.core.util;

import static es.uvigo.ei.sing.math.ArrayUtils.doubleUnbox;

import java.util.List;

public class ArrayUtils {
	public static double[] doubleArray(List<Double> spotValues) {
		return doubleUnbox(spotValues.toArray(new Double[spotValues.size()]));
	}
}
