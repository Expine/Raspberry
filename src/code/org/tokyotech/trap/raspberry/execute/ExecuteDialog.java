package code.org.tokyotech.trap.raspberry.execute;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;

import javax.swing.*;
import code.org.tokyotech.trap.raspberry.task.Task;
import code.org.tokyotech.trap.raspberry.task.TaskManager;

/**
 * タスク実行のダイアログ
 * @author kakuo
 * @param task 実行するタスク
 */

public class ExecuteDialog extends JDialog {

	private ImageIcon[] runOrStopImage = new ImageIcon[2];
	private JLabel image;
	private JButton runOrStop;
	private JLabel timeLabel;
	private Timer timer;
	private Task task;
	private int restTime = 0;
	private int workTime = 0;
	
	public ExecuteDialog(Task task){
		 this.task=task;

		 GridBagLayout layout = new GridBagLayout();
		 GridBagConstraints gbc = new GridBagConstraints();
		 setLayout(layout);
		 
		 timer = new Timer(1000, e -> { if(isStop()) restTime++; else workTime++; refleshTimeLabel(); });
		 timer.start();
		 runOrStopImage[0] = new ImageIcon(getClass().getClassLoader().getResource("res/run.png"));
		 runOrStopImage[1] = new ImageIcon(getClass().getClassLoader().getResource("res/stop.png"));
			
		 
		 gbc.fill = GridBagConstraints.BOTH;
		 gbc.insets = new Insets(5, 5, 5, 5);
		 
		 gbc.gridx = 0;
		 gbc.gridy = 0;
		 gbc.gridwidth = 2;
		 gbc.gridheight = 4;
		 image = new JLabel();
		 layout.setConstraints(image, gbc);
		 add(image);
		 
		 gbc.gridx = 3;
		 gbc.gridwidth = 1;
		 gbc.gridheight = 1;
		 runOrStop = new JButton("再開");
		 runOrStop.addActionListener(e -> { runOrStop(); });
		 layout.setConstraints(runOrStop, gbc);
		 add(runOrStop);		 
		 
		 gbc.gridy++;
		 JButton end = new JButton("終了");
		 end.addActionListener(e -> { endTask(); });
		 layout.setConstraints(end, gbc);
		 add(end);

		 gbc.gridy++;
		 timeLabel = new JLabel();
		 layout.setConstraints(timeLabel, gbc);
		 add(timeLabel);		 
		 
		 setImage();
		 refleshTimeLabel();
		 pack();
		 
		 setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		 setLocationRelativeTo(null);
		 setModal(true);
		 setVisible(true);
		 
		 KeyManager.reset();

	}
	
	private void runOrStop() {
		/*
		if(isStop()) 
			timer.start();
		else
			timer.stop();
		*/
		setImage();
		
	}
	
	private void setImage() {
		if(isStop()) {
			image.setIcon(runOrStopImage[0]);
			runOrStop.setText("一時停止");
		} else {
			image.setIcon(runOrStopImage[1]);
			runOrStop.setText("再開");
		}
		repaint();
	}
	
	private void endTask() {
		TaskManager.instance().progress(task.getID(), new Time(1000 * workTime));
		timer.stop();
		dispose();
	}
	
	private void refleshTimeLabel() {
		System.out.println(KeyManager.getElapsedTime());
		if(KeyManager.getElapsedTime() > 60L * 1000000000L) {
			JOptionPane jop = new JOptionPane("進捗どうですか？", JOptionPane.ERROR_MESSAGE);
			JDialog dialog = jop.createDialog(null, "進捗確認");
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
			dialog.setAlwaysOnTop(false);
			KeyManager.reset();
		}
		
		if(isStop())
			timeLabel.setText("休憩時間 : " + restTime / 3600 + "時間"+ (restTime % 3600) / 60+"分" + restTime % 60 + "秒");
		else
			timeLabel.setText("作業時間 : " + workTime / 3600 + "時間"+ (workTime % 3600) / 60+"分" + workTime % 60 + "秒");
		repaint();
	}
	
	private boolean isStop() {
		return runOrStop.getText().equals("再開");
	}
}
