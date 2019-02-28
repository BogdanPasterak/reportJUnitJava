package reportJUnitJava;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestSuite {
	int level;
	String name;
	Double time;
	ArrayList<TestSuite> testSuites;
	ArrayList<TestCase> testCases;
	ArrayList<Object> undefined;
	
	public TestSuite(JSONObject jo, int level) throws JSONException {
		this.level = level;
		// matching keys and values to variables
		// all names in JSON
		JSONArray ja;
		for (String key : JSONObject.getNames(jo)) {
			switch (key) {
			case "testsuite":
				testSuites = new ArrayList<>();
				if (jo.get(key) instanceof JSONArray) {
					ja = jo.getJSONArray(key);
					for (int i = 0; i < ja.length(); i++) {
						testSuites.add(new TestSuite(ja.getJSONObject(i), level + 1));
					}
				} else if (jo.get(key) instanceof JSONObject) {
					testSuites.add(new TestSuite(jo.getJSONObject(key), level + 1));
				} else {
					if (undefined == null)
						undefined = new ArrayList<>();
					undefined.add(jo.get(key));
				}
				break;
			case "testcase":
				testCases = new ArrayList<>();
				if (jo.get(key) instanceof JSONArray) {
					ja = jo.getJSONArray(key);
					for (int i = 0; i < ja.length(); i++) {
						testCases.add(new TestCase(ja.getJSONObject(i), level + 1));
					}
				} else if (jo.get(key) instanceof JSONObject){
					testCases.add(new TestCase(jo.getJSONObject(key), level + 1));
				} else {
					if (undefined == null)
						undefined = new ArrayList<>();
					undefined.add(jo.get(key));
				}
				break;
			case "name":
				name = jo.getString(key);
				break;
			case "time":
				time = jo.getDouble(key);
				break;
			default:
				if (undefined == null)
					undefined = new ArrayList<>();
				undefined.add(jo.get(key));
				break;
			}
		}
	}
	
	@Override
	public String toString() {
		String offset = "";
		for (int i = 0; i < level; i++) {
			offset += "    ";
		}
		String s = offset + "TestSuite Object:";
		if (name != null)
			s += "\n" + offset + " Name: " + name;
		if (time != null)
			s += "\n" + offset + " Time: " + time;
		if (testSuites != null) {
			s += "\n" + offset + " TestSuites size: " + testSuites.size();
			for (TestSuite testSuite : testSuites)
				s += "\n" + testSuite.toString();
		}
		if (testCases != null) {
			s += "\n" + offset + " testCases size: " + testCases.size();
			for (TestCase testCase : testCases)
				s += "\n" + testCase.toString();
		}
		if (undefined != null) {
			s += "\n" + offset + " Undefined size: " + undefined.size();
			for (Object object : undefined) {
				s += "\n" + offset + "  Object: ";
				if (object.toString().length() > 61)
					s += object.toString().substring(0, 60);
				else
					s += object.toString();
			}
		}

		return s;
	}

}