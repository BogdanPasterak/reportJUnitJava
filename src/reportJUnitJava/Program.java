package reportJUnitJava;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Program extends JFrame {

	public Program() {

		init();
	}

	private void init() {
		setTitle("Report viewer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setLayout(null);
		setVisible(true);
		
		JButton button = new JButton("click");

		button.setBounds(165, 135, 115, 55);

		// adding button on frame
		add(button);

	}

	public static void main(String[] args) {
		new Program();
	}
	
	

}
