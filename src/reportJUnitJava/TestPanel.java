package reportJUnitJava;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.text.AbstractDocument.Content;

public class TestPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	TestRun testRun;
	ArrayList<TestSuite> testSuites;
	ArrayList<TestCase> testCases;
	JLabel line1, line2;
	
	public TestPanel(TestRun testRun, Container parent) {
		
		this.setLayout(new BorderLayout());
		//setSize(300,100);
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		
		this.testRun = testRun;
		//setBounds(10, 30, 400, 100);
		//setBackground(Color.YELLOW);
		//setVisible(true);
		line1 = new JLabel("<html><h1>Name: <em>" + testRun.name + "</em></h1></html>");
		//line1.setFont(new Font(line1.getName(), Font.PLAIN, 20));

		//int x = w.getWidth();
		add(line1, BorderLayout.CENTER);
		line2 = new JLabel("Project: " + testRun.project + " " + parent.getWidth());
		add(line2, BorderLayout.NORTH);
		
		repaint();
	}
	

}