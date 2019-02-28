package reportJUnitJava;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestCase {
	int level;
	String name;
	String classname;
	Double time;
	Boolean ignored;
	String error;
	String failure;
	ArrayList<Object> undefined;

	public TestCase(JSONObject jo, int level) throws JSONException {
		this.level = level;
		// matching keys and values to variables
		// all names in JSON
//		JSONArray ja;
		for (String key : JSONObject.getNames(jo)) {
			switch (key) {
//			case "testsuite":
//				testSuites = new ArrayList<>();
//				if (jo.get(key) instanceof JSONArray) {
//					ja = jo.getJSONArray(key);
//					for (int i = 0; i < ja.length(); i++) {
//						testSuites.add(new TestSuite(ja.getJSONObject(i), level + 1));
//					}
//				} else if (jo.get(key) instanceof JSONObject) {
//					testSuites.add(new TestSuite(jo.getJSONObject(key), level + 1));
//				} else {
//					if (undefined == null)
//						undefined = new ArrayList<>();
//					undefined.add(jo.get(key));
//				}
//				break;
			case "name":
				name = jo.getString(key);
				break;
			case "classname":
				classname = jo.getString(key);
				break;
			case "error":
				error = jo.getString(key);
				break;
			case "failure":
				failure = jo.getString(key);
				break;
			case "time":
				time = jo.getDouble(key);
				break;
			case "ignored":
				ignored = jo.getBoolean(key);
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
		if (classname != null)
			s += "\n" + offset + " ClassName: " + classname;
		if (time != null)
			s += "\n" + offset + " Time: " + time;
		if (ignored != null)
			s += "\n" + offset + " Ignored: " + ignored;
		if (error != null) {
			int index = error.indexOf("\t");
			if (index > 0)
				s += "\n" + offset + " Error: " + error.substring(0, index);
			else 
				s += "\n" + offset + " Error: " + error;
		}
		if (failure != null) {
			int index = failure.indexOf("\t");
			if (index > 0)
				s += "\n" + offset + " Failure: " + failure.substring(0, index);
			else 
				s += "\n" + offset + " Failure: " + failure;
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
