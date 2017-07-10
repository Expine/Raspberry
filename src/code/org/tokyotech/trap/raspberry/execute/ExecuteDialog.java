package code.org.tokyotech.trap.raspberry.execute;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import javax.swing.*;
import code.org.tokyotech.trap.raspberry.task.*;

/**
 * タスク実行のダイアログ
 * @author kakuo
 * @param task 実行するタスク
 */

public class ExecuteDialog extends JDialog implements ActionListener {


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
		 mainPanel=new JPanel(new CardLayout());
		 timer=new Timer(1000,this);
		 timer.setActionCommand("timer");


		JPanel selectPanel=new JPanel();                      //選択画面
		JButton startWorkButton=new JButton("タスクを始める");
		JButton closeButton=new JButton("タスクを閉じる");
		selectPanel.add(startWorkButton);
		selectPanel.add(closeButton);
		startWorkButton.addActionListener(this);
		startWorkButton.setActionCommand("workstart");
		closeButton.addActionListener(this);
		closeButton.setActionCommand("close");



		JPanel workingPanel=new JPanel();		   //作業中画面
		JButton finishButton=new JButton("終了");
		JButton restButton=new JButton("中断");
		workTimeLabel_working=new JLabel("作業時間:"+workTimeText);
		workingPanel.add(workTimeLabel_working);
		workingPanel.add(finishButton);
		workingPanel.add(restButton);
		finishButton.addActionListener(this);
		finishButton.setActionCommand("finish");
		restButton.addActionListener(this);
		restButton.setActionCommand("rest");



		JPanel restPanel=new JPanel();				//中断画面
		JButton restartButton=new JButton("再開");
		workTimeLabel_rest=new JLabel("作業時間:"+workTimeText);
		restPanel.add(workTimeLabel_rest);
		restPanel.add(restartButton);
		restartButton.addActionListener(this);
		restartButton.setActionCommand("restart");



		JPanel finishPanel=new JPanel();			   //終了画面
		workTimeLabel_finish=new JLabel("今回の作業時間:"+workTimeText);
		finishPanel.add(workTimeLabel_finish);

		mainPanel.add(selectPanel,"select");
		mainPanel.add(workingPanel,"working");
		mainPanel.add(restPanel,"rest");
		mainPanel.add(finishPanel,"finish");

		getContentPane().add(mainPanel);
		layout=(CardLayout)mainPanel.getLayout();

		setSize(500,300);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
	}

	public void actionPerformed(ActionEvent e){
		String cmd=e.getActionCommand();

		if(cmd=="workstart"){layout.show(mainPanel, "working"); timer.start();}

		if(cmd=="close") 	{new CloseDialog();}


		if(cmd=="rest")
		{timer.stop();
		layout.show(mainPanel, "rest");
		workTimeLabel_rest.setText("作業時間:"+workTimeText);}

		if(cmd=="restart")	{timer.start(); layout.show(mainPanel, "working");}

		if(cmd=="finish")
		{timer.stop();
		 layout.show(mainPanel, "finish");
		 workTimeLabel_finish.setText("今回の作業時間:"+workTimeText);
		 TaskManager.instance().progress(task.getID(),new Time(1000*worktime));}

		if(cmd=="timer")
		{ worktime++;
		  workTimeText=worktime/3600+"時間"+ (worktime%3600)/60+"分"+worktime%60+"秒";
		  workTimeLabel_working.setText("作業時間:"+workTimeText);}

	}



}
