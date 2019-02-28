package reportJUnitJava;

import org.json.JSONObject;

public class TestCase {
	int level;

	public TestCase(JSONObject jsonObject, int level) {
		this.level = level;
	}
	
	@Override
	public String toString() {
		String offset = "";
		for (int i = 0; i < level; i++) {
			offset += "    ";
		}
		String s = offset + "TestSuite Object:";
		
		return s;
	}

}
