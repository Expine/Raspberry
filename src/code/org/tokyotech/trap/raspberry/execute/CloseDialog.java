package code.org.tokyotech.trap.raspberry.execute;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import code.org.tokyotech.trap.raspberry.task.Task;
import code.org.tokyotech.trap.raspberry.task.TaskManager;

/**
 * タスクを閉じる際の確認画面
 * @author kakuo
 * @param task 閉じるタスク
 */
public class CloseDialog extends JDialog {

	private CardLayout layout;
	private JPanel mainPanel;



	public CloseDialog(JDialog parent, Task task){

		super(parent);
		
		mainPanel=new JPanel(new CardLayout());

		JPanel askPanel=new JPanel();
		
		JButton ask_YesButton=new JButton("はい");
		ask_YesButton.addActionListener(new ActionListener(){					//押されたらタスクをクローズ、または警告
			@Override public void actionPerformed(ActionEvent e){try_closetask(task);}});
		
		JButton ask_NoButton=new JButton("いいえ");
		ask_NoButton.addActionListener(new ActionListener(){					//押されたらダイアログを閉じる
			@Override public void actionPerformed(ActionEvent e){dispose();}});
		
		JLabel askLabel=new JLabel("このタスクを閉じてよろしいですか？");
		askPanel.add(askLabel);
		askPanel.add(ask_YesButton);
		askPanel.add(ask_NoButton);
		
		

		JPanel warningPanel=new JPanel();
		
		JButton warning_YesButton=new JButton("はい");
		warning_YesButton.addActionListener(new ActionListener(){					//押されたらタスクを（強制的に）クローズ
			@Override public void actionPerformed(ActionEvent e){closetask(task);}});
		
		JButton warning_NoButton=new JButton("いいえ");
		warning_NoButton.addActionListener(new ActionListener(){					//押されたらダイアログを閉じる
			@Override public void actionPerformed(ActionEvent e){dispose();}});
		
		JLabel warningLabel=new JLabel("作業時間が予想時間を下回っています。\n本当にタスクを完了しましたか？");
		warningPanel.add(warningLabel);
		warningPanel.add(warning_YesButton);
		warningPanel.add(warning_NoButton);
		
		

		JPanel closedPanel=new JPanel();
		JButton okButton=new JButton("OK");
		okButton.addActionListener(new ActionListener(){					//押されたらダイアログを閉じる
			@Override public void actionPerformed(ActionEvent e){dispose();}});
		
		JLabel closedLabel=new JLabel("タスクを閉じました。");
		closedPanel.add(closedLabel);
		closedPanel.add(okButton);
	

		mainPanel.add(askPanel,"ask");
		mainPanel.add(warningPanel,"warning");
		mainPanel.add(closedPanel,"closed");

		getContentPane().add(mainPanel);
		layout=(CardLayout)mainPanel.getLayout();

		setSize(700,300);
		setLocationRelativeTo(null);
		setModal(true);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

  

  	private void try_closetask(Task task){						//作業時間が予測時間を下回っていれば警告画面、そうでなければタスクをクローズ
			if(task.getElapsedTime().getTime()<task.getScheduledTime().getTime() )
			  layout.show(mainPanel,"warning");
			else {closetask(task);}
  	}
  	
  	
  	private void closetask(Task task){								//タスクをクローズ					
  		layout.show(mainPanel, "closed"); TaskManager.instance().closeTask(task.getID());
  	}
  	
  
}
