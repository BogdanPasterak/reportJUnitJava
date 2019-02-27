package reportJUnitJava;

import org.json.JSONException;
import org.json.JSONObject;

public class TestRun {
	String name;
	String project;
	Integer tests;
	Integer started;
	Integer failures;
	Integer errors;
	Integer ignored;
	TestSuite testsuite;
	TestUndefined undefined;

	public TestRun(JSONObject jo) {
		// checking the correctness of the object
		if (jo == null || ! jo.has("testrun"))
			try {
				throw new Exception("Inproper JSON Object");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		try {
			// get dependency
			JSONObject jod = jo.getJSONObject("testrun");
			// matching keys and values ​​to variables
			// all names in JSON
			for (String key : JSONObject.getNames(jod)) {
				switch (key) {
				case "testsuite":
					testsuite = new TestSuite();	//jod.getString("project");
					break;
				case "name":
					name = jod.getString(key);		break;
				case "project":
					project = jod.getString(key);	break;
				case "tests":
					tests = jod.getInt(key);		break;
				case "started":
					started = jod.getInt(key);		break;
				case "failures":
					failures = jod.getInt(key);		break;
				case "errors":
					errors = jod.getInt(key);		break;
				case "ignored":
					ignored = jod.getInt(key);		break;
				default:
					if (undefined == null)
						undefined = new TestUndefined();
					undefined.add(jod.get(key));
					break;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private String withNull(Object s) {
		if (s == null)
			return "\n";
		return s.toString() + "\n";
	}

	@Override
	public String toString() {

		return "TestRun Object:\n" + "Name: " + withNull(name) + "Project: " + withNull(project) + "Tests: "
				+ withNull(tests) + "Started: " + withNull(started) + "Failures: " + withNull(failures) + "Errors: "
				+ withNull(errors) + "Ignored: " + withNull(ignored) + "TestSuite: " + withNull(testsuite)
				+ "Undefined: " + withNull(undefined);
	}


}
