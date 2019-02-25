package reportJUnitJava;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Program extends JFrame implements ActionListener {
	JMenuBar menuBar;
	JMenu file;
	JMenuItem load;
	JFileChooser fileChooser;
	FileNameExtensionFilter fileFilter;
	File currentFile;
	JLabel label;
	

	public Program() {

		init();
	}

	private void init() {
		setTitle("Report viewer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		//setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setLayout(null);
		// Menu
		menuBar = new JMenuBar();
		file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		menuBar.add(file);
		
		load = new JMenuItem("Load");
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		load.addActionListener(this);
		file.add(load);
		
		setJMenuBar(menuBar);
		
		fileChooser = new JFileChooser();
		fileFilter = new FileNameExtensionFilter("XML Files", "xml");
		fileChooser.addChoosableFileFilter(fileFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		
		
		
		label = new JLabel("tu");
		label.setBounds(20, 30, 500, 55);
		add(label);
		
//		JButton button = new JButton("click");
//		button.setBounds(165, 135, 115, 55);
//		button.addActionListener(this);
//
//		// adding button on frame
//		add(button);
		setVisible(true);

	}

	public static void main(String[] args) {
		new Program();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == load){
			Integer returnVal = fileChooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				currentFile = fileChooser.getSelectedFile();
	            //This is where a real application would open the file.
	            this.setTitle("Report viewer : " + currentFile.getName());
	        } else {
	            this.setTitle("Report viewer");
	        }
			
		}
		
	}
	
	

}
