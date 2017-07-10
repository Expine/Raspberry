package code.org.tokyotech.trap.raspberry.execute;

import java.awt.CardLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import code.org.tokyotech.trap.raspberry.task.Task;

/**
 * タスクを閉じる際の確認画面
 * @author kakuo
 * @param task 閉じるタスク
 */
public class CloseDialog extends JDialog implements ActionListener{

	private CardLayout layout;
	private JPanel mainPanel;
	private Task task;


	public CloseDialog(JDialog parent, Task task){

		this.task=task;
		mainPanel=new JPanel(new CardLayout());

		JPanel askPanel=new JPanel();
		JButton ask_YesButton=new JButton("はい");
		JButton ask_NoButton=new JButton("いいえ");
		JLabel askLabel=new JLabel("このタスクを閉じてよろしいですか？");
		askPanel.add(ask_YesButton);
		askPanel.add(ask_NoButton);
		askPanel.add(askLabel);
		setButton(ask_YesButton,"try_closetask");
		setButton(ask_NoButton,"return");

		JPanel warningPanel=new JPanel();
		JButton warning_YesButton=new JButton("はい");
		JButton warning_NoButton=new JButton("いいえ");
		JLabel warningLabel=new JLabel("作業時間が予想時間を大幅に下回っています。/n本当にタスクを完了しましたか？");
		warningPanel.add(warning_YesButton);
		warningPanel.add(warning_NoButton);
		warningPanel.add(warningLabel);
		setButton(warning_YesButton,"closetask");
		setButton(warning_NoButton,"return");

		JPanel closedPanel=new JPanel();
		JButton okButton=new JButton("OK");
		JLabel closedLabel=new JLabel("タスクを閉じました。");
		closedPanel.add(okButton);
		closedPanel.add(closedLabel);
		setButton(okButton,"return");

		mainPanel.add(askPanel,"ask");
		mainPanel.add(warningPanel,"warning");
		mainPanel.add(closedPanel,"closed");

		getContentPane().add(mainPanel);
		layout=(CardLayout)mainPanel.getLayout();

		setSize(500,300);
		setLocationRelativeTo(null);
		setModal(true);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

  	public void actionPerformed(ActionEvent e){
		String cmd=e.getActionCommand();

		if(cmd=="try_closePanel"){
			if(task.getElapsedTime().getTime()<task.getScheduledTime().getTime())
			  layout.show(mainPanel,"warning");
			else layout.show(mainPanel, "closed");
		}

		if(cmd=="return")   dispose();

		if(cmd=="closetask"){layout.show(mainPanel, "closed");  }

	}

  	private void setButton(JButton button,String command){
		button.addActionListener(this);
		button.setActionCommand(command);
	}

}
