package code.org.tokyotech.trap.raspberry.main;

import java.awt.FlowLayout;

import javax.swing.JFrame;

public class AddTaskDialog extends JFrame{
	AddTaskDialog(){
		getContentPane().setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("task");
		setSize(200, 100);
		setVisible(true);
	}
}