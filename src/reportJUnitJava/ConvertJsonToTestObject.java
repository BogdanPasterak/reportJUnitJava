package reportJUnitJava;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import reportJUnitJava.TestObject.Type;

public class ConvertJsonToTestObject {

	public static ArrayList<TestObject> convert(JSONObject jo) throws JSONException{
		ArrayList<TestObject> list = new ArrayList<>();
		
		// test
		//list.add(new TestObject(TestObject.Type.testrun, "Name: TestReport", 0));
		getTestObject(jo, 0, list);
		
		return list;
	}

	private static boolean getTestObject(JSONObject jo, int indent, ArrayList<TestObject> list) throws JSONException {
		//System.out.println(jo);
		String name;
		boolean fail = false;
		
		// TestRun
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
				fail = getTestObject(tr, 1, list);
			
			if (fail)
				to.setFail();

			return fail;
			
		} 
		// TestSuite
		else if (jo.has("testsuite")) {
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
					fail = getTestObject(ts, indent + 1, list);
				else if (ts.get("testsuite") instanceof JSONArray) {
					for (int index = 0; index < ts.getJSONArray("testsuite").length(); index++) {
						JSONObject temp = new JSONObject();
						temp.put("testsuite", ts.getJSONArray("testsuite").getJSONObject(index));
						fail = getTestObject(temp, indent + 1, list) || fail;
					}
				}
					to.setChildren(ts.getJSONArray("testsuite").length());
			} else if (ts.has("testcase")) {
				if (ts.get("testcase") instanceof JSONObject)
					fail = getTestObject(ts, indent + 1, list);
				else if (ts.get("testcase") instanceof JSONArray) {
					for (int index = 0; index < ts.getJSONArray("testcase").length(); index++) {
						JSONObject temp = new JSONObject();
						temp.put("testcase", ts.getJSONArray("testcase").getJSONObject(index));
						fail = getTestObject(temp, indent + 1, list) || fail;
					}
				}
			}
			// if some child is fail this is also fail 
			if (fail)
				to.setFail();

			return fail;
		}
		// TestCase
		else if (jo.has("testcase")) {
			JSONObject tc = jo.getJSONObject("testcase");
			name = "Name: " + tc.getString("name");
			// create object
			TestObject to = new TestObject(TestObject.Type.testcase, name, indent);
			// class name
			if (tc.has("classname"))
				to.setPreTitle("ClassName: " + tc.getString("classname"));
			// children
			if (tc.has("failure") || tc.has("error"))
				to.setChildren(1);
			// property time in ms
			if (tc.has("time"))
				to.addProperty("Time: " + (String.valueOf((int)(tc.getDouble("time") * 1000 ))) + " ms");
			if (tc.has("ignored"))
				to.addProperty("Ignored: " + String.valueOf(tc.getBoolean("ignored")));
			
			// buttons
			to.setBtnPlus();
			if (to.getChildren() > 0)
				to.setBtnDown();
						
			// add object
			list.add(to);
			
			// add failure or error
			if (to.getChildren() > 0) {
				fail = getTestObject(tc, indent + 1, list);
			}
			
			if (fail)
				to.setFail();
			
			return fail;
		}
		else if (jo.has("failure") || jo.has("error")) {
			String fe;
			boolean failure = (jo.has("failure")) ? true : false;
			
			if (failure) {
				fe = jo.getString("failure");
				name = "Failure";
			} else {
				fe = jo.getString("error");
				name = "Error";
			}
			String[] lines = null;
			if (fe.contains("&#13;"))
				lines = fe.split("&#13;");
			else if (fe.contains("\t"))
				lines = fe.split("\t");
			else {
				System.out.println(fe);
				if (fe.charAt(23) == 'r') {
					System.out.println(fe.substring(23, 31));
					
					for (int i = 23; i < 31; i++) {
						System.out.println((int)fe.charAt(i));
					}
				}
			}
			// create object
			TestObject.Type type = (failure) ? TestObject.Type.failure : TestObject.Type.error;
			TestObject to = new TestObject(type, name, indent);
			if (lines != null) {
				to.setPostTitle(lines[0]);
				for (int i = 1; i < lines.length; i++)
					to.addLine(lines[i]);
			}
			to.setFail();
			
			// add object
			list.add(to);
			return true;
			
		}
		
		System.err.println("Error !! Dont machet type.");
		return fail;

	}
}