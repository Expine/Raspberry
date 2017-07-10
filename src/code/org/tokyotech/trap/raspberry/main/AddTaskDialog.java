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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import code.org.tokyotech.trap.raspberry.task.Tag;
import code.org.tokyotech.trap.raspberry.task.Task;
import code.org.tokyotech.trap.raspberry.task.TaskManager;

/** Author @uynet */

public class AddTaskDialog extends JFrame implements ActionListener{
	//タスクの開始日
			int StartDate = 0;
			int TextWidth = 200;
			int SpacePx  = 100; //字間
			int LineSpacePx = 20; //行間
			
			//タグのリスト
			String TagsText = "";
			
			JLabel NameLabel = new JLabel("タスク名");
			JTextField NameText = new JTextField("", 10);
			
			JLabel ExpLabel = new JLabel("説明");
			JTextField ExpText = new JTextField("", 10);
			
			JLabel LimitLabel = new JLabel("期限                                月                日");
			String Month[]= new String[12];
			String Day[] = new String[31];
			
			
			
			JComboBox LimitMonthText = new JComboBox();
			JComboBox LimitDayText = new JComboBox();
			
			JLabel WeightLabel = new JLabel("重さ");
			JTextField WeightText = new JTextField("", 10);
			
			JLabel CicleLabel = new JLabel("サイクル設定                   日");
			JTextField CicleText = new JTextField("", 10);
			
			JLabel TagLabel = new JLabel("tags");
			JTextField TagText = new JTextField("", 10);

			//現在登録されているタグのラベル
			JLabel ViewTagLabel = new JLabel("");
			
			//タグのラベルのリスト
			ArrayList<JLabel> TagLabels = new ArrayList<JLabel>();
			
			JButton AddTag = new JButton("追加");
			
			JButton Submit = new JButton("登録する");
			
			//追加するタグ群
			ArrayList<Tag> AddTags = new ArrayList<Tag>();
			int tag_num = 0;
			
			
			JLabel ErrorLabel = new JLabel(":");
			
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
		
		//タスクの期限
		//月日の配列を初期化
		for(int i = 0;i<Month.length;i++){
			Month[i] = String.valueOf(i+1);
		}
		
		for(int i = 0;i<Day.length;i++){
			Day[i] = String.valueOf(i+1);
		}
		LimitMonthText = new JComboBox(Month);
		LimitDayText = new JComboBox(Day);
	
		LimitMonthText.setBounds(SpacePx ,10 + LineSpacePx * 2,66,20);
		LimitDayText.setBounds(SpacePx + 80,10 + LineSpacePx * 2,66,20);
		LimitLabel.setBounds(10,10 + LineSpacePx * 2,400,20);
		getContentPane().add(LimitLabel);
		getContentPane().add(LimitMonthText);
		getContentPane().add(LimitDayText);
		
		//重さ
		WeightText.setBounds(SpacePx ,10 + LineSpacePx * 3,60,20);
		WeightLabel.setBounds(10,10 + LineSpacePx * 3,TextWidth,20);
		getContentPane().add(WeightLabel);
		getContentPane().add(WeightText);
		
		//サイクル設定
		CicleText.setBounds(SpacePx ,10 + LineSpacePx * 4,60,20);
		CicleLabel.setBounds(10,10 + LineSpacePx * 4,TextWidth,20);
		getContentPane().add(CicleLabel);
		getContentPane().add(CicleText);
		
		//タグ
		TagText.setBounds(SpacePx ,10 + LineSpacePx * 5,100,20);
		TagLabel.setBounds(10,10 + LineSpacePx * 5,TextWidth,20);
		getContentPane().add(TagLabel);
		getContentPane().add(TagText);
		
	
		//タグの追加
		AddTag.addActionListener(this);
		AddTag.setBounds(100 + SpacePx,10 + LineSpacePx * 5,80,20);
		getContentPane().add(AddTag);
		
		//タグの表示
			ViewTagLabel.setBounds(10 + SpacePx,10 + LineSpacePx * 6,400,20);
			getContentPane().add(ViewTagLabel);
			
		//タスクの登録
		Submit.addActionListener(this);
		Submit.setBounds(10,10 + LineSpacePx * 7,100,20);
		getContentPane().add(Submit);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("task");
		setSize(400, 230);
		setVisible(true);
		
		//エラーチェック文
		ErrorLabel.setBounds(10,10 + LineSpacePx * 8,TextWidth,20);
		getContentPane().add(ErrorLabel);
		
	}
	
	//入力チェックの正規表現
	static boolean isNumMatch(String number) {
	    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^[1-9][0-9]*$");
	    java.util.regex.Matcher matcher = pattern.matcher(number);
	    return matcher.matches();
	} 
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("");
		/*
		 TODO
		 日付のエラーチェック
		 フォームに上限を設置する
		 タグの削除
		 タスクの削除
		 その日のタスク一覧を表示する
		 カレンダーから選択するとダイアログを表示する
		 */
		if(e.getSource() == Submit){
			int error = 1;
			
	       /* addtaskを呼ぶ */
			SimpleDateFormat dateTimeFormat  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String name;
			java.util.Date limit = null;
			ArrayList<Tag> tags; //タスクに登録されたタグ
			Date start; //タスクを登録した日時
			String explanation;//タスクの説明
			Time scheduledTime; // タスクの終了予想時間 
			int oopInterval;
			 //エラーチェック
			 //..
			 
			 if(NameText.getText().length() * ExpText.getText().length() == 0){
				 error = 1;
			 }
			 else if(!isNumMatch(CicleText.getText()) || !isNumMatch(WeightText.getText()) )	 {
				 error = 2;
			 }
			 else{
				 error = 0;
			 }
			 
			 if(error == 1){
				 ErrorLabel.setText("!未入力のデータがあります!");
			 }
			 else if(error == 2){
				 ErrorLabel.setText("!入力データが不正です!");
			 }
			 else{
				 //タスクを登録する
				 try {
						limit = dateTimeFormat.parse("2017/"+LimitMonthText.getSelectedItem()+"/"+LimitDayText.getSelectedItem()+ " 15:00:00");
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						error = 2;
						//System.out.println(error);
						e1.printStackTrace();
					} //タスクの期限 
				 name = NameText.getText(); //タスクの登録名
				 tags = AddTags; //タスクに登録されたタグ
				 start = null; //タスクを登録した日時
				 explanation = ExpText.getText(); //タスクの説明
				 scheduledTime = null; // タスクの終了予想時間 
				 oopInterval = 1; // 繰り返し間隔
				 Task task = new Task(name, tags, start, limit,  explanation, scheduledTime, oopInterval);
				 TaskManager.instance().addTask(task);
				 ErrorLabel.setText("登録完了");
			 }
	    }
		if(e.getSource() == AddTag){
			//ArrayListにタグを追加
			// AddedTag .. 今から追加されるタグ
			// AddTags  .. 今までに追加されたタグの一覧
			Tag AddedTag = new Tag(TagText.getText());
			AddTags.add(AddedTag);
			TagText.setText("");
			LineSpacePx+=10;
			tag_num++;
			
			String ShowString = new String();
			for(int i=0;i<tag_num;i++){
				ShowString += AddTags.get(i).getTag();
				ShowString += "    ";
			}
			ViewTagLabel.setText(ShowString);
		}
	}
}