package reportJUnitJava;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
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
	private static int count = 10;
	private int indent;
	private TestRun testRun;
	private TestSuite testSuite;
	private TestCase testCase;
	
	public TestPanel(TestRun testRun, Container parent) {
		
		indent = 0;
		this.testRun = testRun;
		buildPanel(testRun.getName(), "Project: " + testRun.getProject());

		
		parent.add(this);
		
		new TestPanel(testRun.getTestSuite(), parent);
		
	}
	
	public TestPanel(TestSuite testSuite, Container parent) {
		indent = testSuite.getLevel();
		this.testSuite = testSuite;
		buildPanel(testSuite.getName(), null);
		
		parent.add(this);
		
		if (testSuite.getTestSuites() != null) {
			for (TestSuite testS : testSuite.getTestSuites()) {
				new TestPanel(testS, parent);
			}
		} else if (testSuite.getTestCases() != null) {
			for (TestCase testC : testSuite.getTestCases()) {
				new TestPanel(testC, parent);
			}
		}
			
	}
	
	public TestPanel(TestCase testCase, Container parent) {
		indent = testCase.getLevel();
		this.testCase = testCase;
		buildPanel(testCase.getName(), "Classname: " + testCase.getClassname());
		
		parent.add(this);
		
	}
	
	 private void buildPanel(String title, String smalLine) {

		 setLayout(new BorderLayout());
		 setBorder(BorderFactory.createRaisedBevelBorder());

		 //this.setBounds(10, getY(), getWidth(), getHeight());
		 add(new JLabel("<html><h2>Name: <em>" + title + "</em></h2></html>"), BorderLayout.CENTER);
		 if (smalLine != null)
			 add(new JLabel(smalLine), BorderLayout.NORTH);
		 
		 if (testSuite != null) {
			 int child = 0;
			 if (testSuite.getTestSuites() != null)
				 child = testSuite.getTestSuites().size();
			 else if (testSuite.getTestCases() != null)
				 child = testSuite.getTestCases().size();
			 add(new JLabel(String.valueOf(child)), BorderLayout.SOUTH);
		 }
		 
		 setSize(getPreferredSize());
		 setLocation(new Point(10 + indent * 50, count));
		 count += getHeight() + 10;
		 
	 }
	
	public int getIndent(){
		return indent;
	}

	public static int getCount() {
		return count;
	}
	

	

}