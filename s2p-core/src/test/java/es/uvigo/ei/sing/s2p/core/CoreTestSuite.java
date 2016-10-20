package es.uvigo.ei.sing.s2p.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.uvigo.ei.sing.s2p.core.entities.EntitiesTestSuite;
import es.uvigo.ei.sing.s2p.core.io.IOTestSuite;
import es.uvigo.ei.sing.s2p.core.io.samespots.SameSpotsTestSuite;
import es.uvigo.ei.sing.s2p.core.operations.OperationsTestSuite;

@RunWith(Suite.class)
@SuiteClasses({
	IOTestSuite.class,
	EntitiesTestSuite.class,
	SameSpotsTestSuite.class,
	OperationsTestSuite.class
})
public class CoreTestSuite {

}
