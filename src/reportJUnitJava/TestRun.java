package reportJUnitJava;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class TestRun {
	private String name;
	private String project;
	private Integer tests;
	private Integer started;
	private Integer failures;
	private Integer errors;
	private Integer ignored;
	private TestSuite testSuite;
	private ArrayList<Object> undefined;

	public TestRun(JSONObject jo) throws ReportJUnitException, JSONException {
		// checking the correctness of the object
		if (jo == null || !jo.has("testrun"))
			throw new ReportJUnitException("Inproper JSON Object");
		// get dependency
		JSONObject jod = jo.getJSONObject("testrun");
		// matching keys and values to variables
		// all names in JSON
		for (String key : JSONObject.getNames(jod)) {
			switch (key) {
			case "testsuite":
				testSuite = new TestSuite(jod.getJSONObject(key), 1);
				break;
			case "name":
				name = jod.getString(key);
				break;
			case "project":
				project = jod.getString(key);
				break;
			case "tests":
				tests = jod.getInt(key);
				break;
			case "started":
				started = jod.getInt(key);
				break;
			case "failures":
				failures = jod.getInt(key);
				break;
			case "errors":
				errors = jod.getInt(key);
				break;
			case "ignored":
				ignored = jod.getInt(key);
				break;
			default:
				if (undefined == null)
					undefined = new ArrayList<>();
				undefined.add(jod.get(key));
				break;
			}
		}
	}
	
	public TestSuite getTestSuite() {
		return testSuite;
	}

	public String getName() {
		return name;
	}

	public String getProject() {
		return project;
	}

	public Integer getTests() {
		return tests;
	}

	public Integer getStarted() {
		return started;
	}

	public Integer getFailures() {
		return failures;
	}

	public Integer getErrors() {
		return errors;
	}

	public Integer getIgnored() {
		return ignored;
	}

	public ArrayList<Object> getUndefined() {
		return undefined;
	}

	@Override
	public String toString() {
		String s = "TestRun Object:";
		if (name != null)
			s += "\n Name: " + name;
		if (project != null)
			s += "\n Project: " + project;
		if (tests != null)
			s += "\n Tests: " + tests;
		if (started != null)
			s += "\n Started: " + started;
		if (failures != null)
			s += "\n Failures: " + failures;
		if (errors != null)
			s += "\n Errors: " + errors;
		if (ignored != null)
			s += "\n Ignored: " + ignored;
		if (testSuite != null)
			s += "\n" + testSuite;
		if (undefined != null) {
			s += "\n Undefined size: " + undefined.size();
			for (Object object : undefined) {
				s += "\n  Object: ";
				if (object.toString().length() > 61)
					s += object.toString().substring(0, 60);
				else
					s += object.toString();
			}
		}

		return s;
	}

}
