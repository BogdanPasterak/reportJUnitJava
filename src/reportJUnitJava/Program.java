package reportJUnitJava;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument.Content;

import org.json.JSONException;
import org.json.JSONObject;

public class Program extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JMenuBar menuBar;
	JMenu file;
	JMenuItem load;
	JFileChooser fileChooser;
	FileNameExtensionFilter fileFilter;
	File currentFile;
	//JLabel label;
	JSONObject jo;
	TestRun testRun;
	TestPanel testPanel;
	Container container;

	public Program() {
		init();
	}

	private void init() {
		setTitle("Report viewer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 500));
		container = getContentPane();
		container.setLayout(new FlowLayout());
		// Menu
		menuBar = new JMenuBar();
		file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		menuBar.add(file);

		load = new JMenuItem("Load");
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		load.addActionListener(this);
		file.add(load);

		this.setJMenuBar(menuBar);

		fileChooser = new JFileChooser();
		fileFilter = new FileNameExtensionFilter("XML Files", "xml");
		fileChooser.addChoosableFileFilter(fileFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

		//label = new JLabel("to");
		//label.setBounds(0, 0, 580, 20);
		// label.setMinimumSize(new Dimension(WIDTH, 20) );
		// label.setBackground(Color.WHITE);
		//label.setOpaque(true);
		// label.setForeground(Color.RED);
		// label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//add(label);

		// JButton button = new JButton("click");
		// button.setBounds(165, 135, 115, 55);
		// button.addActionListener(this);
		//
		// // adding button on frame
		// add(button);
		setVisible(true);

	}

	public static void main(String[] args) {
		new Program();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == load) {
			Integer returnVal = fileChooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				currentFile = fileChooser.getSelectedFile();
				// This is where a real application would open the file.
				this.setTitle("Report viewer : " + currentFile.getName());
				jo = ConvertXmlToJson.convert(currentFile);
				try {
					testRun = new TestRun(jo);
					// System.out.println(testRun.toString());
					testPanel = new TestPanel(testRun, container);
					container.add(testPanel);
					//testPanel.repaint();
					pack();
				} catch (ReportJUnitException | JSONException | NullPointerException e1) {
					this.setTitle("Report viewer");
					//label.setForeground(Color.RED);
					//label.setText("Error: " + e1.getMessage());
				}
			}

		}

	}

}