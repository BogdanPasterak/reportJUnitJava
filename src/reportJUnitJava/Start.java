package reportJUnitJava;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Start {

	private static JFileChooser fileChooser;
	
	public static void main(String[] args) {
		initFileChooser();
	}

	private static void initFileChooser() {
		fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		Integer returnVal = fileChooser.showOpenDialog(null);
		File currentFile = fileChooser.getSelectedFile();

		System.out.println(currentFile.getName());
	}

}
