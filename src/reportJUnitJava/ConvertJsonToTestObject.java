package reportJUnitJava;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConvertJsonToTestObject {

	public static ArrayList<TestObject> convert(JSONObject jo) throws JSONException{
		ArrayList<TestObject> list = new ArrayList<>();
		
		// test
		//list.add(new TestObject(TestObject.Type.testrun, "Name: TestReport", 0));
		getTestObject(jo, 0, list);
		
		return list;
	}

	private static void getTestObject(JSONObject jo, int indent, ArrayList<TestObject> list) throws JSONException {
		//System.out.println(jo);
		String name;
		
		if (jo.has("testrun")) {
			JSONObject tr = jo.getJSONObject("testrun");
			name = "Name: " + tr.getString("name");
			// create object
			TestObject to = new TestObject(TestObject.Type.testrun, name, 0);
			// children - must have 1
			if (tr.has("testsuite"))
				to.setChildren(1);
			// project name
			if (tr.has("project"))
				to.setPreTitle("Project: " + tr.getString("project"));
			// propertys
			if (tr.has("tests"))
				to.addProperty("Tests: " + String.valueOf(tr.getInt("tests")));
			if (tr.has("started"))
				to.addProperty("Started: " + String.valueOf(tr.getInt("started")));
			if (tr.has("ignored"))
				to.addProperty("Ignored: " + String.valueOf(tr.getInt("ignored")));
			if (tr.has("failures"))
				to.addProperty("Failures: " + String.valueOf(tr.getInt("failures")));
			if (tr.has("errors"))
				to.addProperty("Errors: " + String.valueOf(tr.getInt("errors")));
			// buttons
			to.setBtnPlus();
			to.setBtnRight();
			to.setBtnDown();
			to.setBtnAll();
			
			// add object
			list.add(to);
			
			// recursive add testsuite
			if (tr.has("testsuite") && tr.get("testsuite") instanceof JSONObject)
				getTestObject(tr, 1, list);
			
		} else if (jo.has("testsuite")) {
			JSONObject ts = jo.getJSONObject("testsuite");
			name = "Name: " + ts.getString("name");
			// create object
			TestObject to = new TestObject(TestObject.Type.testsuite, name, indent);
			// children
			if (ts.has("testsuite")) {
				if (ts.get("testsuite") instanceof JSONObject)
					to.setChildren(1);
				else if (ts.get("testsuite") instanceof JSONArray)
					to.setChildren(ts.getJSONArray("testsuite").length());
			} else if (ts.has("testcase")) {
				if (ts.get("testcase") instanceof JSONObject)
					to.setChildren(1);
				else if (ts.get("testcase") instanceof JSONArray)
					to.setChildren(ts.getJSONArray("testcase").length());
			}
			// property time in ms
			if (ts.has("time"))
				to.addProperty("Time: " + (String.valueOf((int)(ts.getDouble("time") * 1000 ))) + " ms");
			
			// buttons
			to.setBtnPlus();
			to.setBtnRight();
			to.setBtnDown();
			to.setBtnAll();
			
			// add object
			list.add(to);

			// recursive add testsuite or testcase
			if (ts.has("testsuite")) {
				if (ts.get("testsuite") instanceof JSONObject)
					getTestObject(ts, indent + 1, list);
				else if (ts.get("testsuite") instanceof JSONArray) {
					for (int index = 0; index < ts.getJSONArray("testsuite").length(); index++) {
						JSONObject temp = new JSONObject();
						temp.put("testsuite", ts.getJSONArray("testsuite").getJSONObject(index));
						getTestObject(temp, indent + 1, list);
					}
				}
					to.setChildren(ts.getJSONArray("testsuite").length());
			} else if (ts.has("testcase")) {
				if (ts.get("testcase") instanceof JSONObject)
					to.setChildren(1);
				else if (ts.get("testcase") instanceof JSONArray)
					to.setChildren(ts.getJSONArray("testcase").length());
			}

			//System.out.println("Suite");
		}
	}
}