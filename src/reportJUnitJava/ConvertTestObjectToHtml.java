package reportJUnitJava;

public class ConvertTestObjectToHtml {
	private static int count = 0;

	public static String convert(TestObject to) {
		String s = "";
		
		String type = to.getType().toString();
		type = (type.charAt(0) == 't') ? "t" + type.substring(4, 5) : "fe";
		String nr = (count < 100) ? "0" + ((count < 10) ? "0": "" ) : "";
		nr += String.valueOf(count);
		count++;
		String hidden = (count > 1) ? " hidden" : "";
		String color = (to.isFail()) ? " false" : ""; 
		
		if (type.equals("tr"))
			s += "\t<!-- TestRun -->\n";
		else if (type.equals("ts"))
			s += "\t<!-- TestSuite -->\n";
		else if (type.equals("tc"))
			s += "\t<!-- TestCase -->\n";
		else if (type.equals("fe"))
			s += "\t<!-- False or Error -->\n";
		// object div
		s += "\t<div class=\"tp " + type + " flow-row" + hidden + "\" id=\"tp" + nr +
				"\" offset=\"" + String.valueOf(to.getIndent()) + "\">\n";
		// offset divs
		for (int i = 0; i < to.getIndent(); i++)
			s += "\t\t<div class=\"offset\"></div>\n";
		// main div
		s += "\t\t<div class=\"main" + color + "\">\n";
		// preTitle
		if (to.getPreTitle() != null)
			s += "\t\t\t<h4>" + to.getPreTitle() + "</h4>\n";
		// title
		s += "\t\t\t<h3>" + to.getTitle() + "</h3>\n";
		// preTitle
		if (to.getPostTitle() != null)
			s += "\t\t\t<h4>" + to.getPostTitle() + "</h4>\n";
		// bottom buttons
		if (to.isBtnDown() || to.isBtnAll()) {
			s += "\t\t\t<div class=\"flow-row\">\n";
			s += "\t\t\t\t<button class=\"btn-details\">V</button>\n";
			if (to.isBtnAll()) {
				s += "\t\t\t\t<p></p>\n";
				s += "\t\t\t\t<p>" + to.getChildren() + "</p>\n";
				s += "\t\t\t\t<p></p>\n";
				s += "\t\t\t\t<button class=\"btn-details-all\">VVV</button>\n";
			}
			s += "\t\t\t</div>\n";
		}
		// errors lines
		if (to.getLines() != null)
			for(String line : to.getLines())
				s += "\t\t\t<p>" + line + "</p>\n";
		// end main
		s += "\t\t</div>\n";
		// expand
		if (!type.equals("fe")) {
			s += "\t\t<div class=\"expand\">\n";
			s += "\t\t\t<button class=\"btn-expand\">+</button>\n";
			if (to.isBtnRight()) {
				s += "\t\t\t<br/>\n";
				s += "\t\t\t<button class=\"btn-expand-all-nested\">&gt;</button>\n";
			}
			s += "\t\t</div>\n";
			s += "\t\t<div class=\"extra hidden\">\n";
			for(String property : to.getPropertys()) {
				s += "\t\t\t<div class=\"cover\">\n";
				s += "\t\t\t\t<p>" + property + "</p>\n";
				s += "\t\t\t</div>\n";
			}
			s += "\t\t</div>\n";
		}
		s += "\t</div>\n";
		
		return s;
	}
}
