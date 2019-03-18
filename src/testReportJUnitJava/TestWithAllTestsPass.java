package testReportJUnitJava;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestWithAllTestsPass {

	@Test
	public void testTrue() {
		assertTrue(true);
	}

	@Test
	public void testFalse() {
		assertFalse(false);
	}

	@Test
	public void testEquals() {
		assertEquals(2, 2);
	}

	@Test
	public void testNull() {
		assertNull(null);
	}

	@Test
	public void testNotNull() {
		assertNotNull(2);
	}

}
