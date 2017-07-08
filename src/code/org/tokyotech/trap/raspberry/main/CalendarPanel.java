package code.org.tokyotech.trap.raspberry.main;

import java.awt.Dimension;

import javax.swing.JPanel;

public class CalendarPanel extends JPanel {
	public CalendarPanel() {
		setPreferredSize(new Dimension(800, 600));
		
		//タスクが発生したら呼ばれる
		AddTaskDialog dlg = new AddTaskDialog();
	}
}
