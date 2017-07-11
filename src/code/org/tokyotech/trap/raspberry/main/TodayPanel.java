package code.org.tokyotech.trap.raspberry.main;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import code.org.tokyotech.trap.raspberry.task.Tag;
import code.org.tokyotech.trap.raspberry.task.Task;
import code.org.tokyotech.trap.raspberry.task.TaskManager;
import com.sun.prism.j2d.print.J2DPrinterJob;

public class TodayPanel extends JPanel {

	Calendar calendar = Calendar.getInstance();
	int day = calendar.get(Calendar.DATE);

	public TodayPanel() {
		setPreferredSize(new Dimension(200, 600));

		JPanel panel = new JPanel();
		JLabel label = new JLabel(""+day + "日");
		panel.setPreferredSize(new Dimension(200,30));
		panel.setBackground(Color.WHITE);
		panel.add(label);
		add(panel);

		JPanel scroll = new JPanel();
		JScrollPane jScrollPane = new JScrollPane(scroll);
		jScrollPane.setPreferredSize(new Dimension(200,700));

		Task t1 = new Task("Name", new ArrayList<Tag>(), calendar.getTime(), new Date(calendar.getTimeInMillis() + 10000L), "Exp", new Time(2000), 0);
		TaskManager.instance().addTask(t1);

		for(Task t : TaskManager.instance().getTask(Calendar.getInstance().getTime())) {
			System.out.print(t);
			scroll.add(new TaskPanel(t));
		}

		/*SpringLayout layout = new SpringLayout();
		scroll.setLayout(layout);*/

		add(jScrollPane);
	}

}
