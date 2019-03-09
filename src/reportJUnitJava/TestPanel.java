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
	private TestCase testCase;
	JPanel cover;
	
	public TestPanel(TestRun testRun, Container parent) {
		
		indent = 0;
		this.testRun = testRun;
		buildPanel(testRun.getName(), "Project: " + testRun.getProject());

		
		parent.add(cover);
		
		new TestPanel(testRun.getTestSuite(), parent);
		
	}
	
	public TestPanel(TestSuite testSuite, Container parent) {
		indent = testSuite.getLevel();
		testSuites = new ArrayList<>();
		testSuites.add(testSuite);
		buildPanel(testSuite.getName(), null);
		
		parent.add(cover);
		
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
		buildPanel(testCase.getName(), testCase.getClassname());
		
		parent.add(cover);
		
	}
	
	 private void buildPanel(String title, String smalLine) {

		 this.setLayout(new BorderLayout());
		 this.setBorder(BorderFactory.createRaisedBevelBorder());

		 this.setBounds(10, getY(), getWidth(), getHeight());
		 this.add(new JLabel("<html><h2>Name: <em>" + title + "</em></h2></html>"), BorderLayout.NORTH);
		 if (smalLine != null)
			 this.add(new JLabel(smalLine), BorderLayout.CENTER);
		 
		cover = new JPanel();
		cover.setBackground(Color.GRAY);
		cover.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 400));
		cover.add(this);
		 
	 }
	
	public int getIndent(){
		return indent;
	}
	

}