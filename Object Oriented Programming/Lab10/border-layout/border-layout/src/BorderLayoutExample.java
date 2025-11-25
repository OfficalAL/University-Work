import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;

import javax.swing.*;
 
public class BorderLayoutExample extends JFrame {
	public BorderLayoutExample(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container pane = this.getContentPane();
		pane.setLayout(new BorderLayout());
		
		JButton buttonNorth = new JButton("NORTH");
		pane.add(buttonNorth, BorderLayout.NORTH);
		
		JButton buttonSouth = new JButton("SOUTH");
		pane.add(buttonSouth, BorderLayout.SOUTH);
		
		JButton buttonEast = new JButton("EAST");
		pane.add(buttonEast , BorderLayout.EAST);
		
		JButton buttonWest = new JButton("WEST");
		pane.add(buttonWest, BorderLayout.WEST);
		
		JButton buttonCenter = new JButton("CENTER");
		pane.add(buttonCenter, BorderLayout.CENTER);
		
		this.setSize(400, 300);
		this.setVisible(true);
	}
}