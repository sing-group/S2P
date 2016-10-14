package es.uvigo.ei.sing.s2p.core.io.samespots;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	SameSpotsLoaderTest.class,
	SameSpotsCsvLoaderTest.class,
	SameSpotsWriterTest.class
})
public class SameSpotsTestSuite {

}
