package es.uvigo.ei.sing.s2p.core.io;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsTestSuite;

@RunWith(Suite.class)
@SuiteClasses({
	MaldiPlateLoaderTest.class,
	MascotProjectLoaderTest.class,
	MascotCsvLoaderTest.class,
	SpotsDataLoaderTest.class,
	SameSpotsTestSuite.class
})
public class IOTestSuite {

}
