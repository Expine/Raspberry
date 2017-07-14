package code.org.tokyotech.trap.raspberry.execute;

import java.applet.Applet;
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

import code.org.tokyotech.trap.raspberry.audio.AudioManager;
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
	private JCheckBox check;
	private Timer timer;
	private Task task;
	private int checkTime = 0;
	private int restTime = 0;
	private int workTime = 0;
	
	public ExecuteDialog(Task task){
		 this.task=task;
		 KeyManager.reset();

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

		 gbc.gridy++;
		 check = new JCheckBox("しばらくPCを使わない");
		 layout.setConstraints(check, gbc);
		 add(check);

		 setImage();
		 refleshTimeLabel();
		 pack();
		 
		 setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		 setLocationRelativeTo(null);
		 setModal(true);
		 setVisible(true);
		 
	}
	
	/**
	 * 実行、あるいは止める
	 */
	private void runOrStop() {
		/*
		if(isStop()) 
			timer.start();
		else
			timer.stop();
		*/
		setImage();
		
	}
	
	/**
	 * タスク実行中画面の画像を変更する
	 */
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

	/**
	 * タスクを終える
	 */
	private void endTask() {
		TaskManager.instance().progress(task.getID(), new Time(1000 * workTime));
		timer.stop();
		dispose();
	}
	
	/**
	 * 実行中の時間を更新する
	 */
	private void refleshTimeLabel() {
		if(!isStop() && KeyManager.getElapsedTime() > 5L * 1000000000L)
			if(check.isSelected())
				KeyManager.reset();
			else
				showDialog("進捗どうですか？", "進捗確認");
		
		if(isStop()) {
			timeLabel.setText("休憩時間 : " + restTime / 3600 + "時間"+ (restTime % 3600) / 60+"分" + restTime % 60 + "秒");
			if(restTime > checkTime + 3 ) {
				showDialog("休憩がずいぶん長いですね", "休憩確認");
				checkTime = restTime;
			}
		} else
			timeLabel.setText("作業時間 : " + workTime / 3600 + "時間"+ (workTime % 3600) / 60+"分" + workTime % 60 + "秒");
		repaint();
	}
	
	/**
	 * 停止中かを判定
	 * @return 停止中であるか
	 */
	private boolean isStop() {
		return runOrStop.getText().equals("再開");
	}
	
	/**
	 * 警告を出す
	 */
	private void showDialog(String mes, String title) {
		AudioManager.playSound();
		JOptionPane jop = new JOptionPane(mes, JOptionPane.ERROR_MESSAGE);
		JDialog dialog = jop.createDialog(null, title);
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
		dialog.setAlwaysOnTop(false);
		KeyManager.reset();
		AudioManager.stop();		
	}
}
