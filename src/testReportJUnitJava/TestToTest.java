package testReportJUnitJava;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class TestToTest {

	@Test
	public void testPass() {
		assertTrue(true);
	}
	
	@Ignore
	@Test
	public void testIgnore() {
		assertTrue(false);
	}

	@Test
	public void testFail() {
		assertTrue(false);
	}

}
