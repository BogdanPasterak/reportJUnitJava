package reportJUnitJava;

import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONObject;

public class Start {

	File currentFile;
	JSONObject jsonObject;
	
	private static JFileChooser fileChooser;
	
	private Start() {
			initFileChooser();
			if (fileChooser.showOpenDialog(null) == 0) {
				// choosing file to convert
				currentFile = fileChooser.getSelectedFile();
				// read file and transform to JSON object
				jsonObject = ConvertXmlToJson.convert(currentFile);

				System.out.println(jsonObject);
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
