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

public class TodayPanel extends JPanel {
	
	private static final int TODAY_WIDTH = 300;
	private static final int TODAY_HEIGHT = 600;
	
	private MainFrame owner;
	private JPanel tasks;

	Calendar calendar = Calendar.getInstance();
	int day = calendar.get(Calendar.DATE);

	public TodayPanel(MainFrame owner) {
		this.owner = owner;
		
		setPreferredSize(new Dimension(TODAY_WIDTH, TODAY_HEIGHT));
		setLayout(new FlowLayout());

		JPanel panel = new JPanel();
		JLabel label = new JLabel(day + "日");
		panel.setPreferredSize(new Dimension(200, 30));
		panel.setBackground(Color.WHITE);
		panel.add(label);
		add(panel);
		
		tasks = new JPanel();
		tasks.setLayout(new BoxLayout(tasks, BoxLayout.Y_AXIS));
		setTasks();
		JScrollPane main = new JScrollPane(tasks);
		main.setPreferredSize(new Dimension(TODAY_WIDTH, TODAY_HEIGHT - 50));
		add(main);
	}
	
	private void setTasks() {
		tasks.removeAll();
		for(Task t : TaskManager.instance().getTask(Calendar.getInstance().getTime())) {
			JPanel task = new JPanel();
			GridBagLayout layout = new GridBagLayout();
			GridBagConstraints gbc = new GridBagConstraints();
			task.setLayout(layout);

			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.insets = new Insets(5, 5, 5, 5);
			JLabel name = new JLabel(t.getName());
			layout.setConstraints(name, gbc);
			task.add(name);
			
			gbc.gridy = 2;
			gbc.gridwidth = 3;
			JTextArea exp = new JTextArea(5, 20);
			JScrollPane exp_s = new JScrollPane(exp);
			exp.setText(t.getExplanation());
			exp.setEditable(false);
			layout.setConstraints(exp_s, gbc);
			task.add(exp_s);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.gridwidth = 1;
			JButton run = new JButton("実行");
			layout.setConstraints(run, gbc);
			task.add(run);
			
			gbc.gridx = 1;
			JButton close = new JButton("閉じる");
			layout.setConstraints(close, gbc);
			task.add(close);
			
			tasks.add(task);
		}		
	}
	
	public void reflesh() {
		setTasks();
	}

}
