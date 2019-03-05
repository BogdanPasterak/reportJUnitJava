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
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument.Content;
import javax.xml.bind.Unmarshaller.Listener;

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
	JPanel panel;
	JScrollPane jScrollPane;

	public Program() {
		init();
	}

	private void init() {
		setTitle("Report viewer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(200, 100));
		container = getContentPane();
		//container.setPreferredSize(new Dimension(800, 500));
		panel = new JPanel(new MyLayout());
		panel.setBackground(Color.RED);
		//panel.setPreferredSize(new Dimension(300, 500));
		jScrollPane = new JScrollPane(panel);
		jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		container.add(jScrollPane);
		//container.setLayout(new MyLayout());
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
		currentFile = fileChooser.getSelectedFile();
		
		actionPerformed(new ActionEvent(load, 0, ""));
		
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
				setTitle("Report viewer : " + currentFile.getName());
				jo = ConvertXmlToJson.convert(currentFile);
				try {
					testRun = new TestRun(jo);
					// System.out.println(testRun.toString());
					//testPanel = new TestPanel(testRun, container);
					panel = new TestPanel(testRun, panel);
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