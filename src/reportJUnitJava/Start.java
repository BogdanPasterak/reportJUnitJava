package reportJUnitJava;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Start {

	File currentFile;
	
	private static JFileChooser fileChooser;
	
	private Start() {
		initFileChooser();		
		if (fileChooser.showOpenDialog(null) == 0 ) {
			currentFile = fileChooser.getSelectedFile();

			System.out.println(currentFile.getName());
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
