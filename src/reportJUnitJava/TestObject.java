package reportJUnitJava;

import java.util.ArrayList;

public class TestObject {

	private Type type;
	private int indent;
	private String preTitle;
	private String title;
	private String postTitle;
	private ArrayList<String> propertys;
	private ArrayList<String> lines;
	private boolean btnPlus;
	private boolean btnRight;
	private boolean btnDown;
	private boolean btnAll;	
	
	public TestObject (Type type,String title, int indent) {
		this.type = type;
		this.title = title;
		this.indent = indent;
	}
	
	
	@Override
	public String toString() {
		return "TestObject => {Type: " + type + ", Title: " + title + ", Indent: " + indent +
				((preTitle != null) ? ", PreTitle: " + preTitle : "") +
				((postTitle != null) ? ", PostTitle: " + postTitle : "") +
				"}";
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getIndent() {
		return indent;
	}

	public void setIndent(int indent) {
		this.indent = indent;
	}

	public String getPreTitle() {
		return preTitle;
	}

	public void setPreTitle(String preTitle) {
		this.preTitle = preTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public ArrayList<String> getPropertys() {
		return propertys;
	}

	public void addProperty(String property) {
		if (propertys == null)
			propertys = new ArrayList<>();
		propertys.add(property);
	}
	
	public ArrayList<String> getLines() {
		return lines;
	}

	public void addLine(String line) {
		if (lines == null)
			lines = new ArrayList<>();
		lines.add(line);
	}

	public boolean isBtnPlus() {
		return btnPlus;
	}

	public void setBtnPlus(boolean btnPlus) {
		this.btnPlus = btnPlus;
	}

	public boolean isBtnRight() {
		return btnRight;
	}

	public void setBtnRight(boolean btnRight) {
		this.btnRight = btnRight;
	}

	public boolean isBtnDown() {
		return btnDown;
	}

	public void setBtnDown(boolean btnDown) {
		this.btnDown = btnDown;
	}

	public boolean isBtnAll() {
		return btnAll;
	}

	public void setBtnAll(boolean btnAll) {
		this.btnAll = btnAll;
	}
	
	public static enum Type { testrun, testsuite, testcase, failure, error }
}