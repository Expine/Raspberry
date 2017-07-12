package code.org.tokyotech.trap.raspberry.execute;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import code.org.tokyotech.trap.raspberry.task.Task;
import code.org.tokyotech.trap.raspberry.task.TaskManager;

/**
 * タスク実行のダイアログ
 * @author kakuo
 * @param task 実行するタスク
 */

public class ExecuteDialog extends JDialog  {


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
		 timer=new Timer(1000,new ActionListener(){								//1秒ごとにupdateTimerを呼び出すよう設定
			 @Override public void actionPerformed (ActionEvent e){	updateTimer();}});



		JPanel selectPanel=new JPanel();      //選択画面

		JButton startWorkButton=new JButton("タスクを始める");
		startWorkButton.addActionListener(new ActionListener(){					//押されたらworkingPanelを表示
			@Override public void actionPerformed(ActionEvent e){showWorkingPanel();}});
		
		JButton closeButton=new JButton("タスクを閉じる");
		 ExecuteDialog thisDialog=this;
		closeButton.addActionListener(new ActionListener(){							//押されたらcloseDialogを表示
			@Override public void actionPerformed(ActionEvent e){new CloseDialog(thisDialog,task);}});

		JButton returnButton=new JButton("戻る");
		returnButton.addActionListener(new ActionListener(){					//押されたらダイアログを閉じる
			@Override public void actionPerformed(ActionEvent e){dispose();}});

		selectPanel.add(returnButton);
		selectPanel.add(startWorkButton);
		selectPanel.add(closeButton);




		JPanel workingPanel=new JPanel();		   //作業中画面

		JButton finishButton=new JButton("終了");
		finishButton.addActionListener(new ActionListener(){					//押されたらfinishPanelを表示
			@Override public void actionPerformed(ActionEvent e){showFinishPanel();}});

		JButton restButton=new JButton("中断");
		restButton.addActionListener(new ActionListener(){					//押されたらrestPanelを表示
			@Override public void actionPerformed(ActionEvent e){showRestPanel();}});

		workTimeLabel_working=new JLabel("作業時間:"+workTimeText);
		workingPanel.add(workTimeLabel_working);
		workingPanel.add(finishButton);
		workingPanel.add(restButton);



		JPanel restPanel=new JPanel();				//中断画面
		JButton restartButton=new JButton("再開");
		restartButton.addActionListener(new ActionListener(){					//押されたらworkingPanelを表示
			@Override public void actionPerformed(ActionEvent e){showWorkingPanel();}});
		workTimeLabel_rest=new JLabel("作業時間:"+workTimeText);
		restPanel.add(workTimeLabel_rest);
		restPanel.add(restartButton);



		JPanel finishPanel=new JPanel();			   //終了画面
		JButton okButton=new JButton("OK");
		okButton.addActionListener(new ActionListener(){					//押されたらダイアログを閉じる
			@Override public void actionPerformed(ActionEvent e){dispose();}});
		workTimeLabel_finish=new JLabel("今回の作業時間:"+workTimeText);

		finishPanel.add(workTimeLabel_finish);
		finishPanel.add(okButton);



		mainPanel.add(selectPanel,"select");
		mainPanel.add(workingPanel,"working");
		mainPanel.add(restPanel,"rest");
		mainPanel.add(finishPanel,"finish");

		getContentPane().add(mainPanel);
		layout=(CardLayout)mainPanel.getLayout();

		setSize(500,300);
		setLocationRelativeTo(null);
		setModal(true);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}



	private void showWorkingPanel(){													//作業中画面を表示し、タイマーをスタート
		layout.show(mainPanel, "working"); timer.start();
	}

	private void showRestPanel(){														//中断画面を表示し、タイマーを止める
		timer.stop();
		layout.show(mainPanel, "rest");
		workTimeLabel_rest.setText("作業時間:"+workTimeText);
	}

	private void showFinishPanel(){													//タイマーを止め、作業時間を表示
		timer.stop();
		 layout.show(mainPanel, "finish");
		 workTimeLabel_finish.setText("今回の作業時間:"+workTimeText);
		// TaskManager.instance().progress(task.getID(),new Time(1000*worktime));
	}

	private void updateTimer(){															//作業時間の更新
		 worktime++;
		  workTimeText=worktime/3600+"時間"+ (worktime%3600)/60+"分"+worktime%60+"秒";
		  workTimeLabel_working.setText("作業時間:"+workTimeText);
	}


}
