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
public class CloseDialog extends JDialog implements ActionListener{

	private CardLayout layout;
	private JPanel mainPanel;
	private Task task;
	private JDialog parent;


	public CloseDialog(JDialog parent, Task task){

		super(parent);
		this.parent=parent;
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
		closedPanel.add(closedLabel);
		closedPanel.add(okButton);
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

		if(cmd=="try_closetask"){
			if(task.getElapsedTime().getTime()<task.getScheduledTime().getTime() )
			  layout.show(mainPanel,"warning");
			else {layout.show(mainPanel, "closed"); TaskManager.instance().closeTask(task.getID());}

		}

		if(cmd=="return")   parent.dispose();

		if(cmd=="closetask"){layout.show(mainPanel, "closed"); TaskManager.instance().closeTask(task.getID()); }

	}

  	private void setButton(JButton button,String command){
		button.addActionListener(this);
		button.setActionCommand(command);
	}

}
