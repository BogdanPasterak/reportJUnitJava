package reportJUnitJava;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.JSONException;
import org.json.JSONObject;

public class Program extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JFileChooser fileChooser;
	FileNameExtensionFilter fileFilter;
	File currentFile;
	//JLabel label;
	JSONObject jo;
	TestRun testRun;
	JButton start;
	
	public Program() {
		init();
	}

	private void init() {
		setTitle("Report viewer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setMinimumSize(new Dimension(300, 200));
		setVisible(true);
		start = new JButton("Start");
		add(start);
		start.addActionListener(this);

		fileChooser = new JFileChooser();
		fileFilter = new FileNameExtensionFilter("XML Files", "xml");
		fileChooser.addChoosableFileFilter(fileFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		currentFile = fileChooser.getSelectedFile();
		
		actionPerformed(new ActionEvent(start, 0, ""));
		

	}

	public static void main(String[] args) {
		new Program();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) {
			// stop choise; hardcode
			//Integer returnVal = fileChooser.showOpenDialog(this);
			// stop choise; hardcode
			if (true) { //returnVal == JFileChooser.APPROVE_OPTION) {
				// stop choise; hardcode
				//currentFile = fileChooser.getSelectedFile();
				currentFile = new File("TestRep2P2I2F1E.xml");
				// This is where a real application would open the file.
				setTitle("Report viewer : " + currentFile.getName());
				jo = ConvertXmlToJson.convert(currentFile);
				String newFileName = currentFile.getName();
				newFileName = newFileName.substring(0, newFileName.length() - 3) + "html";
				//System.out.println(newFileName);
				//					 new File(newFileName)
				try (FileWriter fw = new FileWriter(FileDescriptor.out)){
					testRun = new TestRun(jo);
					
					ArrayList<TestObject> list = ConvertJsonToTestObject.convert(jo);
					
					//File htmlfile = new File("example.html");
					for(TestObject to : list)
						fw.write(to.toString());
					
					//Desktop.getDesktop().browse(htmlfile.toURI());
					// System.out.println(testRun.toString());
					//testPanel = new TestPanel(testRun, container);
					pack();
					this.dispose();
				} catch (ReportJUnitException | JSONException | NullPointerException | IOException e1) {
					this.setTitle("Report viewer");
					//label.setForeground(Color.RED);
					//label.setText("Error: " + e1.getMessage());
				}
			}

		}

	}

}