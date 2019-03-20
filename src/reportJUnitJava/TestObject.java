package reportJUnitJava;

import java.util.ArrayList;

public class TestObject {
	//public static final String[] TYPE = { "testrun", "testsuite", "testcase", "failure", "error" };

	private Type type;
	private int indent;
	private int children;
	private String preTitle;
	private String title;
	private String postTitle;
	private ArrayList<String> propertys;
	private ArrayList<String> lines;
	private boolean btnPlus;
	private boolean btnRight;
	private boolean btnDown;
	private boolean btnAll;	
	private boolean fail;
	
	public TestObject (Type type,String title, int indent) {
		this.type = type;
		this.title = title;
		this.indent = indent;
	}
	
	
	@Override
	public String toString() {
		String s = "TestObject => ";
		for (int i = 0; i < indent; i++)
			s += "  ";
		return s + "{Type: " + type + ", Title: \"" + title + "\", Indent: " + indent +
				((children > 0) ? ", Children: " + children : "") +
				((preTitle != null) ? ", PreTitle: \"" + preTitle + "\"" : "") +
				((postTitle != null) ? ", PostTitle: \"" + postTitle + "\"" : "") +
				((propertys != null) ? ", Propertys No: " + propertys.size() : "") +
				((lines != null) ? ", Lines No: " + lines.size() : "") +
				((btnPlus || btnRight || btnDown || btnAll) ? ", Buttons: " : "") +
				((btnPlus) ? "+" : "") + ((btnRight) ? ">" : "") +
				((btnDown) ? "V" : "") + ((btnAll) ? "A" : "") +
				((fail) ? ", Fail" : "") + "}";
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

	public int getChildren() {
		return children;
	}

	public void setChildren(int children) {
		this.children = children;
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

	public void setBtnPlus() {
		this.btnPlus = true;
	}

	public boolean isBtnRight() {
		return btnRight;
	}

	public void setBtnRight() {
		this.btnRight = true;
	}

	public boolean isBtnDown() {
		return btnDown;
	}

	public void setBtnDown() {
		this.btnDown = true;
	}

	public boolean isBtnAll() {
		return btnAll;
	}

	public void setBtnAll() {
		this.btnAll = true;
	}
	
	public boolean isFail() {
		return fail;
	}

	public void setFail() {
		this.fail = true;
	}

}