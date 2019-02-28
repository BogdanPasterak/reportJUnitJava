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
	TestUndefined undefined;
	
	public TestSuite(JSONObject jo, int level) throws JSONException {
		this.level = level;
		// matching keys and values to variables
		// all names in JSON
		JSONArray ja;
		for (String key : JSONObject.getNames(jo)) {
			switch (key) {
			case "testsuite":
				testSuites = new ArrayList<>();
				ja = jo.getJSONArray(key);
				for (int i = 0; i < ja.length(); i++) {
					testSuites.add(new TestSuite(ja.getJSONObject(i), level + 1));
				}
				break;
//			case "testcase":
//				testCases = new ArrayList<>();
//				ja = jo.getJSONArray(key);
//				for (int i = 0; i < ja.length(); i++) {
//					testCases.add(new TestCase(ja.getJSONObject(i), level + 1));
//				}
//				break;
			case "name":
				name = jo.getString(key);
				break;
			case "time":
				time = jo.getDouble(key);
				break;
			default:
				if (undefined == null)
					undefined = new TestUndefined();
				undefined.add(jo.get(key));
				break;
			}
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "TestSuite: " + name;
	}

}