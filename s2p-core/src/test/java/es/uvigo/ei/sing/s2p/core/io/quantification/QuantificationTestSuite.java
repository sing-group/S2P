package es.uvigo.ei.sing.s2p.core.io.quantification;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.uvigo.ei.sing.s2p.core.io.quantification.operations.QuantificationOperationsTestSuite;

@RunWith(Suite.class)
@SuiteClasses({
	QuantificationCsvFileLoaderTest.class,
	QuantificationCsvDatasetLoaderTest.class,
	QuantificationOperationsTestSuite.class
})
public class QuantificationTestSuite {

}
