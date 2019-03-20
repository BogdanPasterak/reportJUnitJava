package reportJUnitJava;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ConvertJsonToTestObject {
	//public static final String[] TYPE = { "testrun", "testsuite", "testcase", "failure", "error" };

	public static ArrayList<TestObject> convert(JSONObject jo) throws JSONException{
		ArrayList<TestObject> list = new ArrayList<>();
		
		// test
		//list.add(new TestObject(TestObject.Type.testrun, "Name: TestReport", 0));
		//getTestObject(jo, 0, list);
				
		if (jo.has(Type.testrun.toString()) && jo.get(Type.testrun.toString()) instanceof JSONObject)
			buildListTestObject(Type.testrun, jo.getJSONObject(Type.testrun.toString()), 0, list);
		
		return list;
		
	}

	private static boolean buildListTestObject(Type type, JSONObject jo, int indent, ArrayList<TestObject> list) throws JSONException {
		boolean fail = false; // if fail colour red
		String typeInS;
		Type typeIn;
		boolean array = false;
		
		// name is almost always name
		String name;
		if (type.isTest())
			name = "Name: " +  jo.getString("name");
		else if (type == Type.failure)
			name = "Failure";
		else if (type == Type.error)
			name = "Error";
		else
			name = "Unknown";
		
		// check nested Type
		if (jo.has(Type.testsuite.toString()))
			typeIn = Type.testsuite;
		else if (jo.has(Type.testcase.toString()))
			typeIn = Type.testcase;
		else if (jo.has(Type.failure.toString()))
			typeIn = Type.failure;
		else if (jo.has(Type.error.toString()))
			typeIn = Type.error;
		else
			typeIn = Type.none;

		typeInS = typeIn.toString();

		if (typeIn != Type.none)
			array = jo.get(typeInS) instanceof JSONArray;
		

		// create object
		TestObject to = new TestObject(type, name, indent);
		
		// check and count children
		if (type == Type.testrun || typeIn == Type.failure || typeIn == Type.error)	// testrun  or testcase with failure or error
			to.setChildren(1);
		else if (typeIn == Type.testsuite || typeIn == Type.testcase ) {			// testsuite with testsuite or testcase
			if (! array)
				to.setChildren(1);
			else
				to.setChildren(jo.getJSONArray(typeInS).length());
		}
		
		
		// propertys
		if (jo.has("tests"))
			to.addProperty("Tests: " + String.valueOf(jo.getInt("tests")));
		if (jo.has("started"))
			to.addProperty("Started: " + String.valueOf(jo.getInt("started")));
		if (jo.has("ignored")) 
			to.addProperty("Ignored: " + jo.get("ignored").toString());
		if (jo.has("failures"))
			to.addProperty("Failures: " + String.valueOf(jo.getInt("failures")));
		if (jo.has("errors"))
			to.addProperty("Errors: " + String.valueOf(jo.getInt("errors")));
		if (jo.has("time"))
			to.addProperty("Time: " + (String.valueOf((int)(jo.getDouble("time") * 1000 ))) + " ms");
		// Pre title
		if (jo.has("classname"))
			to.setPreTitle("ClassName: " + jo.getString("classname"));
		if (jo.has("project"))
			to.setPreTitle("Project: " + jo.getString("project"));
		// error or failure
		if (jo.has("details")) {
			String fe = jo.getString("details");
			String[] lines = new String[1];
			
			if (fe.contains("&#13;"))
				lines = fe.split("&#13;");
			else if (fe.contains("\t"))
				lines = fe.split("\t");
			else {
				System.err.println("I can not divide messages in error / failure");
				lines[0] = fe;
			}
			// create lines message
			to.setPostTitle(lines[0]);
			for (int i = 1; i < lines.length; i++)
				to.addLine(lines[i]);
			
			fail = true;
		}

		// buttons
		if (type.isTest()) {
			to.setBtnPlus();
			if (to.getChildren() > 0) {
				to.setBtnDown();
				if (to.getChildren() > 1 || type == Type.testrun) {
					to.setBtnAll();
					to.setBtnRight();
				} else if (type != Type.testcase) 
					to.setBtnRight();
			}	
		}
		
		list.add(to);
		
		// build dependent test object
		if (to.getChildren() > 0) {
			if (typeIn == Type.testsuite || typeIn == Type.testcase) {
				if (array)
					for (int index = 0; index < jo.getJSONArray(typeInS).length(); index++)
						fail = buildListTestObject(typeIn, jo.getJSONArray(typeInS).getJSONObject(index), indent + 1, list) || fail;
				else
					fail = buildListTestObject(typeIn, jo.getJSONObject(typeInS), indent + 1, list);
			} else if (typeIn == Type.failure || typeIn == Type.error) {
				JSONObject temp = new JSONObject().put("details", jo.getString(typeInS));
				fail = buildListTestObject(typeIn, temp, indent + 1, list);
			}
		}

		// set color if any child is fail
		if (fail)
			to.setFail();
		
		return fail;
	}

}