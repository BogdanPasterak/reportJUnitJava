package reportJUnitJava;

public enum Type {
	testrun,
	testsuite,
	testcase,
	failure, 
	error,
	none;
		
	public boolean isTest() {
		return this.toString().substring(0, 4).equals("test");
	}
	
}