package testReportJUnitJava;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class TestToTest {

	@Test
	public void testPass() {
		assertTrue(true);
	}
	
	@Test
	public void testPassWithMoreTests() {
		assertTrue(true);
		assertFalse(false);
		assertEquals(5, 5);
		assertNull(null);
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

	@Test
	public void testError() {
		//assertTrue();
	}

}
