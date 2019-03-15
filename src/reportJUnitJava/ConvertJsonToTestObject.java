package reportJUnitJava;

import java.util.ArrayList;
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
		System.out.println(jo);
		String name;
		
		if (jo.has("testrun")) {
			JSONObject tr = jo.getJSONObject("testrun");
			name = "Name: " + tr.getString("name");
			TestObject to = new TestObject(TestObject.Type.testrun, name, 0);
			if (tr.has("project"))
				to.setPreTitle("Project: " + tr.getString("project"));
			
			// add object
			list.add(to);
			
		}
	}
}