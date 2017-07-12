package code.org.tokyotech.trap.raspberry.main;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import code.org.tokyotech.trap.raspberry.execute.ExecuteDialog;
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
			gbc.gridwidth = 2;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.insets = new Insets(5, 5, 5, 5);
			JLabel name = new JLabel(t.getName());
			layout.setConstraints(name, gbc);
			task.add(name);
			
			gbc.gridx = 0;
			gbc.gridy++;
			JLabel procLabel = new JLabel("進捗時間");
			layout.setConstraints(procLabel, gbc);
			task.add(procLabel);

			gbc.gridx = 1;
			gbc.gridwidth = 1;
			JLabel proctime = new JLabel(new SimpleDateFormat("H時間m分s秒").format(t.getElapsedTime()));
			layout.setConstraints(proctime, gbc);
			task.add(proctime);

			gbc.gridx = 0;
			gbc.gridy++;
			JLabel scheduledLabel = new JLabel("予想時間");
			layout.setConstraints(scheduledLabel, gbc);
			task.add(scheduledLabel);

			gbc.gridx = 1;
			gbc.gridwidth = 1;
			JLabel scheduledtime = new JLabel(new SimpleDateFormat("H時間m分s秒").format(t.getScheduledTime()));
			layout.setConstraints(scheduledtime, gbc);
			task.add(scheduledtime);

			gbc.gridx = 0;
			gbc.gridy++;
			gbc.gridwidth = 1;
			JButton run = new JButton("実行");
			run.addActionListener(e -> { new ExecuteDialog(t); owner.reflesh(); });
			layout.setConstraints(run, gbc);
			task.add(run);
			
			gbc.gridx = 1;
			JButton close = new JButton("閉じる");
			close.addActionListener(e -> { TaskManager.instance().closeTask(t.getID()); owner.reflesh(); });
			layout.setConstraints(close, gbc);
			task.add(close);

			gbc.gridx = 0;
			gbc.gridy++;
			gbc.gridwidth = 3;
			JTextArea exp = new JTextArea(5, 20);
			JScrollPane exp_s = new JScrollPane(exp);
			exp.setText(t.getExplanation());
			exp.setEditable(false);
			layout.setConstraints(exp_s, gbc);
			task.add(exp_s);
			
						tasks.add(task);
		}		
	}
	
	public void reflesh() {
		setTasks();
		owner.pack();				
		owner.repaint();		
	}

}
