package testReportJUnitJava;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestToTest.class, TestWithAllTestsPass.class })
public class AllTestsFalseAndPass {

}
