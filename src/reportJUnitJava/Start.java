package reportJUnitJava;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONObject;

public class Start {

	File currentFile;
	static FileNameExtensionFilter extension;
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
			
			String html = "";
			for(TestObject testObject : testObjectList) {
				//fw.write(to.toString() + "\n");
				html += ConvertTestObjectToHtml.convert(testObject);;
			}
			JOptionPane.showMessageDialog(null, "infoMessage", "InfoBox: " + "titleBar", JOptionPane.INFORMATION_MESSAGE);
			
			reinitFileChooser();
			if (fileChooser.showOpenDialog(null) == 0) {
				System.out.println(html);
			}
						
			//System.out.println(newFileName);
		}
	}

	public static void main(String[] args) {
		new Start();
	}

	private static void initFileChooser() {
		fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
		fileChooser.setDialogTitle("Open file to convert");
		extension = new FileNameExtensionFilter("XML Files", "xml");
		fileChooser.addChoosableFileFilter(extension);
		fileChooser.setAcceptAllFileFilterUsed(false);
	}

	private void reinitFileChooser() {
		String newFileName = currentFile.getName();
		newFileName = newFileName.substring(0, newFileName.length() - 3) + "html";
		currentFile = new File(newFileName);
		fileChooser.setDialogTitle("Seve converted file");
		fileChooser.setSelectedFile(currentFile);
		fileChooser.removeChoosableFileFilter(extension);
		extension = new FileNameExtensionFilter("HTML Files", "html");
		fileChooser.addChoosableFileFilter(extension);
	}

}
