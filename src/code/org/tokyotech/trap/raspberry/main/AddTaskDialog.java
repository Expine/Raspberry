package code.org.tokyotech.trap.raspberry.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import code.org.tokyotech.trap.raspberry.task.Task;
import code.org.tokyotech.trap.raspberry.task.TaskManager;

public class AddTaskDialog extends JFrame implements ActionListener{
	AddTaskDialog(){
		getContentPane().setLayout(new FlowLayout());

		JLabel NameLabel = new JLabel("タスク名");
		JTextField NameText = new JTextField("", 10);
		getContentPane().add(NameLabel,BorderLayout.WEST);
		getContentPane().add(NameText ,BorderLayout.EAST);
		
		JLabel WeightLabel = new JLabel("重さ");
		JTextField WeightText = new JTextField("5", 10);
		
		
		getContentPane().add(WeightLabel,BorderLayout.WEST);
		getContentPane().add(WeightText ,BorderLayout.EAST);
		
		
		JButton Submit = new JButton("OK");
		Submit.addActionListener(this);
		getContentPane().add(Submit,BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("task");
		setSize(200, 200);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
       /* addtaskを呼ぶ */
		Task task = new Task("name", null, null, null, "title", null, getDefaultCloseOperation());
		TaskManager.instance().addTask(task);
    }
}