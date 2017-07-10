package code.org.tokyotech.trap.raspberry.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import code.org.tokyotech.trap.raspberry.task.Tag;
import code.org.tokyotech.trap.raspberry.task.Task;
import code.org.tokyotech.trap.raspberry.task.TaskManager;

public class AddTaskDialog extends JFrame implements ActionListener{
	//タスクの開始日
			int StartDate = 0;
			int TextWidth = 200;
			int SpacePx  = 100; //字間
			int LineSpacePx = 20; //行間
			
			JLabel NameLabel = new JLabel("タスク名");
			JTextField NameText = new JTextField("", 10);
			
			JLabel ExpLabel = new JLabel("説明");
			JTextField ExpText = new JTextField("", 10);
			
			JLabel LimitLabel = new JLabel("期限                          月             日");
			JTextField LimitMonthText = new JTextField("", 10);
			JTextField LimitDayText = new JTextField("", 10);
			
			JLabel WeightLabel = new JLabel("重さ");
			JTextField WeightText = new JTextField("5", 10);
			
			JLabel CicleLabel = new JLabel("サイクル設定");
			JTextField CicleText = new JTextField("", 10);
			
			JLabel TagLabel = new JLabel("tags");
			JTextField TagText = new JTextField("", 10);
			
			//タグのラベルのリスト
			ArrayList<JLabel> TagLabels = new ArrayList<JLabel>();
			
			JButton AddTag = new JButton("追加");
			
			JButton Submit = new JButton("登録する");
			
			//追加するタグ群
			ArrayList<Tag> AddTags = new ArrayList<Tag>();
			int tag_num = 0;
			
	AddTaskDialog(){
		
		getContentPane().setLayout(null);

		
		NameText.setBounds(SpacePx ,10 + LineSpacePx * 0,TextWidth,20);
		NameLabel.setBounds(10,10 + LineSpacePx * 0,TextWidth,20);
		getContentPane().add(NameLabel);
		getContentPane().add(NameText);
		
		ExpText.setBounds(SpacePx ,10 + LineSpacePx * 1,TextWidth,20);
		ExpLabel.setBounds(10,10 + LineSpacePx * 1,TextWidth,20);
		getContentPane().add(ExpLabel);
		getContentPane().add(ExpText);
		
		LimitMonthText.setBounds(SpacePx ,10 + LineSpacePx * 2,40,20);
		LimitDayText.setBounds(SpacePx + 60,10 + LineSpacePx * 2,40,20);
		LimitLabel.setBounds(10,10 + LineSpacePx * 2,400,20);
		getContentPane().add(LimitLabel);
		getContentPane().add(LimitMonthText);
		getContentPane().add(LimitDayText);
		
		WeightText.setBounds(SpacePx ,10 + LineSpacePx * 3,TextWidth,20);
		WeightLabel.setBounds(10,10 + LineSpacePx * 3,TextWidth,20);
		getContentPane().add(WeightLabel);
		getContentPane().add(WeightText);
		
		
		CicleText.setBounds(SpacePx ,10 + LineSpacePx * 4,TextWidth,20);
		CicleLabel.setBounds(10,10 + LineSpacePx * 4,TextWidth,20);
		getContentPane().add(CicleLabel);
		getContentPane().add(CicleText);
		
		//タグ
		TagText.setBounds(SpacePx ,10 + LineSpacePx * 5,TextWidth,20);
		TagLabel.setBounds(10,10 + LineSpacePx * 5,TextWidth,20);
		getContentPane().add(TagLabel);
		getContentPane().add(TagText);
		
		for(int i = 0;i<tag_num;i++){
			//TagLabels.get(i) = new JLabel("tags");
		}
	
		//タグの追加
		AddTag.addActionListener(this);
		AddTag.setBounds(100 + SpacePx,10 + LineSpacePx * 5,80,20);
		getContentPane().add(AddTag);
		
		
		//タスクの登録
		Submit.addActionListener(this);
		Submit.setBounds(10,10 + LineSpacePx * 6,TextWidth,20);
		getContentPane().add(Submit);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("task");
		setSize(400, 200);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		System.out.println("");
		/*
		 TODO
		 追加したタグの表示
		 入力データのパース
		 タグの削除
		 タスクの削除
		 登録のキャンセル
		 その日のタスク一覧を表示する
		 カレンダーから選択するとダイアログを表示する
		 */
		if(e.getSource() == Submit){
	       /* addtaskを呼ぶ */
			SimpleDateFormat dateTimeFormat  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			
			 String name = NameText.getText(); //タスクの登録名
			 ArrayList<Tag>  tags = null; //タスクに登録されたタグ
			 Date start = null; //タスクを登録した日時
			 java.util.Date limit = null;
			try {
				limit = dateTimeFormat.parse("2017/"+LimitMonthText.getText()+"/"+LimitDayText.getText()+ " 15:00:00");
				System.out.println("parsed");
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} //タスクの期限 
			 String explanation = ExpText.getText(); //タスクの説明
			 Time scheduledTime = null; // タスクの終了予想時間 
			 int oopInterval = 1; // 繰り返し間隔
			Task task = new Task(name, tags, start, limit,  explanation, scheduledTime, oopInterval);
			TaskManager.instance().addTask(task);
	    }
		if(e.getSource() == AddTag){
			//ArrayListにタグを追加
			Tag AddedTag = new Tag(TagText.getText());
			AddTags.add(AddedTag);
			TagText.setText("");
			tag_num++;
		}
	}
}