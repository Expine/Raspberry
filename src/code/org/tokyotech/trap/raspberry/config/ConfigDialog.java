	package code.org.tokyotech.trap.raspberry.config;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;

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


/**
 * タスク設定ダイアログ
 * @author yuu
 *
 */
public class ConfigDialog extends JDialog {
	/** 単一のダイアログを表示するためのインスタンス */
	private static ConfigDialog instance = null;
	
	private JComboBox<String> box;
	private JTextField tagName;
	private JPanel tags = new JPanel();
	
	public ConfigDialog(JFrame owner, Calendar date, int x, int y) {
		super(owner);

		// 既に表示済みの場合は何もしない
		if(instance != null) {
			dispose();
			return;
		}
		instance = this;
		
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
		setTextWithLabel("タスク名", layout, gbc, 4);
		
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
		
		gbc.gridy++;
		gbc.gridwidth = 1;
		setDate("期限", date, layout, gbc);
		
		gbc.gridy++;
		setWeight("重さ", layout, gbc);
		
		gbc.gridy++;
		setTextAreaWithLabel("説明", layout, gbc, 4);

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

	private void setTextAreaWithLabel(String text, GridBagLayout layout, GridBagConstraints gbc, int width) {
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
	}

	private void setDate(String text, Calendar date, GridBagLayout layout, GridBagConstraints gbc) {
		gbc.gridx = 0;
		JLabel name = new JLabel(text);
		layout.setConstraints(name, gbc);
		add(name);

		gbc.gridx = 1;
		JComboBox<String> month = new JComboBox<String>();
		for(int i = 0; i < 12; ++i)
			month.addItem((i+1) + "月");
		month.setSelectedIndex(date.get(Calendar.MONTH));
		layout.setConstraints(month, gbc);
		add(month);
		
		gbc.gridx = 2;
		box = new JComboBox<String>();
		for(int i = 0; i < date.getActualMaximum(Calendar.DATE); ++i)
			box.addItem((i+1) + "日");
		box.setSelectedIndex(date.get(Calendar.DAY_OF_MONTH) - 1);
		layout.setConstraints(box, gbc);
		add(box);
	}
	
	private void setWeight(String text, GridBagLayout layout, GridBagConstraints gbc) {
		gbc.gridx = 0;
		JLabel name = new JLabel(text);
		layout.setConstraints(name, gbc);
		add(name);
		
		gbc.gridx = 1;
		SpinnerNumberModel model = new SpinnerNumberModel(1.0, 0.0, 10.0, 0.1);
		JSpinner spinner = new JSpinner(model);
		layout.setConstraints(spinner, gbc);
		add(spinner);
	}
	
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
	}
}
