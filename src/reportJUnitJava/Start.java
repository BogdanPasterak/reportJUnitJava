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
			Object[] options = {"Save & Show", "Save", "Cancel"};
			int answer = JOptionPane.showOptionDialog(null,
					"           Converting ready\n       What to do with data?",
					"Test converted", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			// save
			if ( answer < 2) {
				reinitFileChooser();
				String nameFile = fileChooser.getSelectedFile().getName();
				if (fileChooser.showSaveDialog(null) == 0) {
					try (	FileWriter fw = new FileWriter(new File(nameFile));
							BufferedReader rs = new BufferedReader( new FileReader("resources/start.txt"));
							BufferedReader re = new BufferedReader( new FileReader("resources/end.txt"))){
						String line;
						// read start  html doc 
						while ((line = rs.readLine()) != null)
							fw.write(line + "\n");
						fw.write("\t<title>Test: " + nameFile + "</title>\n");
						fw.write("</head>\n<body>\n");
					} catch (Exception e) {
						System.out.println("No resource");
					}
					System.out.println("Save!");
					if (answer == 0) {
						System.out.println("Show");
					}
				}
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
