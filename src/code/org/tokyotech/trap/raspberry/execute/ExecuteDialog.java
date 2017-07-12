package code.org.tokyotech.trap.raspberry.execute;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import code.org.tokyotech.trap.raspberry.task.Task;

/**
 * タスク実行のダイアログ
 * @author kakuo
 * @param task 実行するタスク
 */

public class ExecuteDialog extends JDialog implements ActionListener {

	private JLabel image;
	private JButton runOrStop;
	private JLabel working;
	private JLabel rest;

	private int worktime=0;      //進捗時間
	private String workTimeText="0時間0分0秒";
	private JLabel workTimeLabel_working;
	private JLabel workTimeLabel_rest;
	private JLabel workTimeLabel_finish;
	private CardLayout layout;
	private JPanel mainPanel;
	private Timer timer;
	private Task task;


	public ExecuteDialog(Task task){
		 this.task=task;

		 GridBagLayout layout = new GridBagLayout();
		 GridBagConstraints gbc = new GridBagConstraints();
		 setLayout(layout);
			
		 
		 gbc.fill = GridBagConstraints.BOTH;
		 gbc.insets = new Insets(5, 5, 5, 5);
		 
		 gbc.gridx = 0;
		 gbc.gridy = 0;
		 gbc.gridwidth = 2;
		 gbc.gridheight = 4;
		 image = new JLabel();
		 layout.setConstraints(image, gbc);
		 add(image);
		 setImage();
		 
		 gbc.gridx = 3;
		 runOrStop = new JButton("一時停止");
		 runOrStop.addActionListener(e -> { runOrStop(); });
		 layout.setConstraints(runOrStop, gbc);
		 add(runOrStop);		 
		 
		 gbc.gridy++;
		 JButton end = new JButton("終了");
		 end.addActionListener(e -> { endTask(); });
		 layout.setConstraints(end, gbc);
		 add(end);

		 gbc.gridy++;
		 
		 

		 gbc.gridy++;
		 
		 
		 pack();
		 
		 setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		 setLocationRelativeTo(null);
		 setModal(true);
		 setVisible(true);

	}
	
	private void runOrStop() {
		if(runOrStop.getText().equals("再開")) 
			timer.start();
		else
			timer.stop();
		setImage();
		
	}
	
	private void setImage() {
		if(runOrStop.getText().equals("再開")) {
			image.setIcon(new ImageIcon(getClass().getClassLoader().getResource("res/run.png")));
			runOrStop.setText("再開");
		} else {
			image.setIcon(new ImageIcon(getClass().getClassLoader().getResource("res/stop.png")));
			runOrStop.setText("一時停止");
		}
		
	}
	
	private void endTask() {
		
	}

	public void actionPerformed(ActionEvent e){
		String cmd=e.getActionCommand();

		if(cmd=="startworking"){layout.show(mainPanel, "working"); timer.start();}

		if(cmd=="close") 	{new CloseDialog(this, task); }


		if(cmd=="rest")
		{timer.stop();
		layout.show(mainPanel, "rest");
		workTimeLabel_rest.setText("作業時間:"+workTimeText);}

		if(cmd=="restart")	{timer.start(); layout.show(mainPanel, "working");}

		if(cmd=="finishworking")
		{timer.stop();
		 layout.show(mainPanel, "finish");
		 workTimeLabel_finish.setText("今回の作業時間:"+workTimeText);
		// TaskManager.instance().progress(task.getID(),new Time(1000*worktime));
		 }

		if(cmd=="return") {dispose();}

		if(cmd=="timer")
		{ worktime++;
		  workTimeText=worktime/3600+"時間"+ (worktime%3600)/60+"分"+worktime%60+"秒";
		  workTimeLabel_working.setText("作業時間:"+workTimeText);}

	}
}
