package reportJUnitJava;

import org.json.JSONException;
import org.json.JSONObject;

public class TestSuite {
	String name;

	public TestSuite(JSONObject jo) {
		try {
			name = jo.getString("name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "TestSuite: " + name;
	}

}
