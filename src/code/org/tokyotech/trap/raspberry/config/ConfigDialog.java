	package code.org.tokyotech.trap.raspberry.config;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import code.org.tokyotech.trap.raspberry.main.CalendarPanel;
import code.org.tokyotech.trap.raspberry.main.MainFrame;
import code.org.tokyotech.trap.raspberry.task.Tag;
import code.org.tokyotech.trap.raspberry.task.Task;
import code.org.tokyotech.trap.raspberry.task.TaskManager;


/**
 * タスク設定ダイアログ
 * @author yuu
 *
 */
public class ConfigDialog extends JDialog {
	/** 単一のダイアログを表示するためのインスタンス */
	private static ConfigDialog instance = null;

	private MainFrame owner;
	private JComboBox<String>[] year = new JComboBox[2];
	private JComboBox<String>[] month = new JComboBox[2];
	private JComboBox<String>[] day = new JComboBox[2];
	private JTextField taskName;
	private JTextField tagName;
	private JTextField estimateTime;
	private JSpinner scheduledHour, scheduledMin;
	private JSpinner weight;
	private JTextArea explanation;
	private JPanel tags = new JPanel();
	
	public ConfigDialog(MainFrame owner, Calendar date, int x, int y) {
		super(owner);
		
		// 既に表示済みの場合は何もしない
		if(instance != null) {
			dispose();
			return;
		}
		instance = this;
		
		this.owner = owner;
		tags.setLayout(new FlowLayout());

        // レイアウトを設定
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(layout);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);


		gbc.gridwidth = 6;
		JPanel main = new JPanel();
		main.add(new JLabel("タスク設定"));
		layout.setConstraints(main, gbc);
		add(main);
		
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		gbc.gridy++;
		taskName = setTextWithLabel("タスク名", layout, gbc, 4);
		
		gbc.gridy++;
		tagName = setTextWithLabel("タグ", layout, gbc, 3);
		tagName.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					addTag();
			}
		});
		
		gbc.gridx = 4;
		JButton add = new JButton("追加");
		add.addActionListener(e -> { addTag(); });
		layout.setConstraints(add, gbc);
		add(add);

		gbc.gridx = 1;
		gbc.gridy++;
		gbc.gridwidth = 5;
		JScrollPane tags_s = new JScrollPane(tags);
		tags_s.setPreferredSize(new Dimension(280, 60));
		layout.setConstraints(tags_s, gbc);
		add(tags_s);
		
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 2;
		JLabel estimate = new JLabel("タグから推測される予想時間");
		layout.setConstraints(estimate, gbc);
		add(estimate);
		gbc.gridx = 2;
		estimateTime = new JTextField();
		estimateTime.setEditable(false);
		layout.setConstraints(estimateTime, gbc);
		add(estimateTime);
		
		gbc.gridy++;
		gbc.gridwidth = 1;
		setDate("開始", date, layout, gbc, 0);

		gbc.gridy++;
		gbc.gridwidth = 1;
		setDate("期限", date, layout, gbc, 1);

		gbc.gridy++;
		gbc.gridwidth = 1;
		setScheduledTime("予想時間", layout, gbc);
		
		gbc.gridy++;
		setWeight("重さ", layout, gbc);
		
		gbc.gridy++;
		explanation = setTextAreaWithLabel("説明", layout, gbc, 4);

		gbc.gridy++;
		gbc.gridx = 1;
		gbc.gridwidth = 1;
		JButton ok  = new JButton("タスクを作成");
		ok.addActionListener(e -> { createTask(); });
		layout.setConstraints(ok, gbc);
		add(ok);
		
		gbc.gridx = 3;
		gbc.gridwidth = 1;
		JButton cancel  = new JButton("キャンセル");
		cancel.addActionListener(e -> { dispose(); });
		layout.setConstraints(cancel, gbc);
		add(cancel);

		pack();
		setLocation(x - getWidth() / 2, y - getHeight() / 2);
		
		// 閉じた際にシングルトンを削除
		addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				instance = null;
			}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
		});
		
		// 表示
		setResizable(false);
		setTitle("タスク設定");
		setVisible(true);
	}
	
	/**
	 * テキストフィールドを題名付きで設置
	 * @param text 題名
	 * @param layout レイアウト
	 * @param gbc レイアウト管理
	 * @param width テキストフィールドの大きさ
	 * @return 生成したテキストフィールド
	 */
	private JTextField setTextWithLabel(String text, GridBagLayout layout, GridBagConstraints gbc, int width) {
		gbc.gridx = 0;
		JLabel name = new JLabel(text);
		layout.setConstraints(name, gbc);
		add(name);
		

		gbc.gridx = 1;
		gbc.gridwidth = width;
		JTextField nameText = new JTextField(20);
		layout.setConstraints(nameText, gbc);
		add(nameText);
		gbc.gridwidth = 1;

		return nameText;
	}

	/**
	 * テキストエリアを題名付きで設置
	 * @param text 題名
	 * @param layout レイアウト
	 * @param gbc レイアウト管理
	 * @param width テキストエリアドの大きさ
	 * @return 生成したテキストエリア
	 */
	private JTextArea setTextAreaWithLabel(String text, GridBagLayout layout, GridBagConstraints gbc, int width) {
		gbc.gridx = 0;
		JLabel name = new JLabel(text);
		layout.setConstraints(name, gbc);
		

		gbc.gridx = 1;
		gbc.gridwidth = width;
		JTextArea nameText = new JTextArea(5, 20);
		JScrollPane nameText_s = new JScrollPane(nameText);
		layout.setConstraints(nameText_s, gbc);
		add(nameText_s);			
		gbc.gridwidth = 1;
		add(name);
		
		return nameText;
	}

	/**
	 * 日付を設定
	 * @param text 日付の題名
	 * @param date 設定する日付
	 * @param layout レイアウト
	 * @param gbc レイアウト管理
	 * @param id 設定する種別
	 */
	private void setDate(String text, Calendar date, GridBagLayout layout, GridBagConstraints gbc, int id) {
		gbc.gridx = 0;
		JLabel name = new JLabel(text);
		layout.setConstraints(name, gbc);
		add(name);

		gbc.gridx = 1;
		year[id] = new JComboBox<String>();
		for(int i = date.get(Calendar.YEAR); i < 2039; ++i)
			year[id].addItem(i + "年");
		year[id].addItemListener(e -> { refleshDay(id); });
		layout.setConstraints(year[id], gbc);
		add(year[id]);

		gbc.gridx = 2;
		month[id] = new JComboBox<String>();
		for(int i = 0; i < 12; ++i)
			month[id].addItem((i+1) + "月");
		month[id].setSelectedIndex(date.get(Calendar.MONTH));
		month[id].addItemListener(e -> { refleshDay(id); });
		layout.setConstraints(month[id], gbc);
		add(month[id]);
		
		gbc.gridx = 3;
		day[id]= new JComboBox<String>();
		for(int i = 0; i < date.getActualMaximum(Calendar.DATE); ++i)
			day[id].addItem((i+1) + "日");
		day[id].setSelectedIndex(date.get(Calendar.DAY_OF_MONTH) - 1);
		layout.setConstraints(day[id], gbc);
		add(day[id]);
	}
	
	/**
	 * 日付をリフレッシュする
	 * 月及び年に応じた日付を設定する
	 * @param id リフレッシュする日付番号
	 */
	private void refleshDay(int id) {
		String y = year[id].getSelectedItem().toString();
		String m = month[id].getSelectedItem().toString();
		day[id].removeAllItems();
		try {
			int max = new Calendar.Builder().setInstant((new SimpleDateFormat("yyyy年M月").parse(y + m))).build().getActualMaximum(Calendar.DATE);
			for(int i = 0; i < max; ++i)
				day[id].addItem((i+1) + "日");		
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 予想時間を設定する
	 * @param text 予想時間の題名
	 * @param layout レイアウト
	 * @param gbc レイアウト管理
	 */
	private void setScheduledTime(String text, GridBagLayout layout, GridBagConstraints gbc) {
		gbc.gridx = 0;
		JLabel name = new JLabel(text);
		layout.setConstraints(name, gbc);
		add(name);
		
		gbc.gridx = 1;
		SpinnerNumberModel model = new SpinnerNumberModel(1.0, 0.0, 10000, 1);
		scheduledHour = new JSpinner(model);
		layout.setConstraints(scheduledHour, gbc);
		add(scheduledHour);
		
		gbc.gridx = 2;
		JLabel hour = new JLabel("時間");
		layout.setConstraints(hour, gbc);
		add(hour);

		gbc.gridx = 3;
		model = new SpinnerNumberModel(0.0, 0.0, 60.0, 1.0);
		scheduledMin = new JSpinner(model);
		layout.setConstraints(scheduledMin, gbc);
		add(scheduledMin);

		gbc.gridx = 4;
		JLabel min = new JLabel("分");
		layout.setConstraints(min, gbc);
		add(min);
	}
	
	/**
	 * 重みを設定
	 * @param text 重みの題名
	 * @param layout レイアウト
	 * @param gbc レイアウト管理
	 */
	private void setWeight(String text, GridBagLayout layout, GridBagConstraints gbc) {
		gbc.gridx = 0;
		JLabel name = new JLabel(text);
		layout.setConstraints(name, gbc);
		add(name);
		
		gbc.gridx = 1;
		SpinnerNumberModel model = new SpinnerNumberModel(1.0, 0.0, 10.0, 0.1);
		weight = new JSpinner(model);
		layout.setConstraints(weight, gbc);
		add(weight);
	}
	
	/**
	 * タグを追加する
	 */
	private void addTag() {
		String text = tagName.getText();
		// 空白は却下
		if(text.equals(""))
			return;
		// すでに同じ名前のタグがあるなら却下
		for(Component c : tags.getComponents()) 
			if(c instanceof JLabel)
				if(((JLabel)c).getText().equals(text))
					return;
		JLabel label = new JLabel(text);
		tags.add(label);
		tagName.setText("");
		pack();
		
		ArrayList<Tag> ts = new ArrayList<Tag>();
		for(Component c : tags.getComponents()) 
			if(c instanceof JLabel)
				ts.add(new Tag(((JLabel)c).getText()));
		
		estimateTime.setText(new SimpleDateFormat("H時間m分s秒").format( new Time(TaskManager.instance().getEstimatedTime(ts) + 15 * 60 * 60 * 1000L)));
	}
	
	/**
	 * タスクを生成する
	 */
	private void createTask() {
		// タスク名空白は使えない
		if(taskName.getText().equals(""))
			return;
		ArrayList<Tag> taglist = new ArrayList<Tag>();
		for(Component c : tags.getComponents()) 
			if(c instanceof JLabel)
				taglist.add(new Tag(((JLabel)c).getText()));
		try {
			Date start = new SimpleDateFormat("yyyy年M月d日H m").parse(year[0].getSelectedItem().toString() + month[0].getSelectedItem().toString() + day[0].getSelectedItem().toString() + "0 0");
			Date limit = new SimpleDateFormat("yyyy年M月d日H m").parse(year[1].getSelectedItem().toString() + month[1].getSelectedItem().toString() + day[1].getSelectedItem().toString() + "23 59");
			Time time = new Time( ((Double)scheduledHour.getValue()).intValue() * 60 * 60 * 1000L + ((Double)scheduledMin.getValue()).intValue() * 60 * 1000L + 15 * 60 * 60 * 1000L);

			Task task = new Task(taskName.getText(), taglist, start, limit, explanation.getText(), time, 0);
			TaskManager.instance().addTask(task);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		owner.reflesh();
		
		dispose();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		// 破壊時は、きっちりインスタンスもクリアする
		instance = null;
	}
}
