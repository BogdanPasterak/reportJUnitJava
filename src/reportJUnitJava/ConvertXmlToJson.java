package reportJUnitJava;

import java.io.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class ConvertXmlToJson {
	public static JSONObject convert(File file) {
		JSONObject jo;
		
		try (BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(
						new FileInputStream(file) , "UTF-8"))){
			
			StringBuilder responseStrBuilder = new StringBuilder();

			String inputStr;
			while ((inputStr = bufferedReader.readLine()) != null) {
				responseStrBuilder.append(inputStr);
			}

			jo = XML.toJSONObject(responseStrBuilder.toString());
			
			return jo;
						
		} catch (IOException | JSONException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
}
