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
	private int indent;
	private TestRun testRun;
	private ArrayList<TestSuite> testSuites;
	private ArrayList<TestCase> testCases;
	private JLabel line1, line2;
	private static final String STARTH2 = "<html><h2>Name: <em>";
	private static final String STOPH2 = "</em></h2></html>";
	
	public TestPanel(TestRun testRun, Container parent) {
		
		indent = 0;
		this.testRun = testRun;
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		
		this.testRun = testRun;
		line1 = new JLabel(STARTH2 + testRun.name + STOPH2);
		add(line1, BorderLayout.CENTER);
		line2 = new JLabel("Project: " + testRun.project);
		add(line2, BorderLayout.NORTH);
		parent.add(this);
		parent.add(new TestPanel(testRun.getTestSuite(), parent));
		
		//repaint();
		//setMaximumSize(new Dimension(300,getHeight()));
	}
	
	public TestPanel(TestSuite testSuite, Container parent) {
		indent = testSuite.getLevel();
		testSuites = new ArrayList<>();
		testSuites.add(testSuite);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		
		line1 = new JLabel(STARTH2 + testSuite.name + STOPH2);
		add(line1, BorderLayout.CENTER);
		line2 = new JLabel("Time: " + testSuite.time.toString());
		add(line2, BorderLayout.NORTH);
		
	}
	
	public int getIndent(){
		return indent;
	}
	

}