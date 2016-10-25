package es.uvigo.ei.sing.s2p.core;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestUtils {

	public static void assertMatrixEquals(Object[][] expected, Object[][] actual) {
		assertEquals(expected.length, actual.length);
		for(int i = 0; i < expected.length; i++) {
			assertArrayEquals(expected[i], actual[i]);
		}
	}
}
