package es.uvigo.ei.sing.s2p.core.io.quantification.operations;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.uvigo.ei.sing.s2p.core.io.quantification.operations.QuantificationNormalizationTest;

@RunWith(Suite.class)
@SuiteClasses({
	QuantificationNormalizationTest.class,
	QuantificationConditionsSummarizerTest.class,
	QuantificationConditionsComparisonTest.class
})
public class QuantificationOperationsTestSuite {

}
