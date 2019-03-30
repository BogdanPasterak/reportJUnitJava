package reportJUnitJava;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONObject;

public class Start {

	File currentFile;
	JSONObject jsonObject;
	ArrayList<TestObject> testObjectList;

	private static JFileChooser fileChooser;

	private Start() {
		initFileChooser();
		if (fileChooser.showOpenDialog(null) == 0) {
			// choosing file to convert
			currentFile = fileChooser.getSelectedFile();
			// read file and transform to JSON object
			jsonObject = ConvertXmlToJson.convert(currentFile);
			
			testObjectList = ConvertJsonToTestObject.convert(jsonObject);
			
			String newFileName = currentFile.getName();
			newFileName = newFileName.substring(0, newFileName.length() - 3) + "html";
			
			
					
			System.out.println(newFileName);
		}
	}

	public static void main(String[] args) {
		new Start();
	}

	private static void initFileChooser() {
		fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
		fileChooser.setAcceptAllFileFilterUsed(false);
	}

}
