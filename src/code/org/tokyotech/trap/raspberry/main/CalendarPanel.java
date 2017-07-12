package code.org.tokyotech.trap.raspberry.main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.SimpleDateFormat;

import javax.swing.*;

import code.org.tokyotech.trap.raspberry.config.ConfigDialog;
import code.org.tokyotech.trap.raspberry.task.Task;
import code.org.tokyotech.trap.raspberry.task.TaskManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * カレンダーのパネル
 * @author yuu
 */
public class CalendarPanel extends JPanel {
	/** パネルの横幅 */
	public static final int CALENDAR_WIDTH = 700;
	/** パネルの縦幅 */
	public static final int CALENDAR_HEIGHT = 600;
	
	private static final int DAY_WIDTH = 80;
	private static final int DAY_HEIGHT = 80;

	/** 週の数 */
	private static final int WEEK = 7;
	/** 表示する週の数 */
	private static final int WEEKS_NUMBER = 6;

	/** 親のフレーム */
	private final JFrame owner;
	
	/** タスクのパネルのリスト */
	private ArrayList<PanelWithDate> taskPanelList = new ArrayList<PanelWithDate>();

    public CalendarPanel(JFrame owner) {
    	// 初期設定
    	this.owner = owner;
    	
    	// 基本情報取得
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        String[] days = {"日", "月", "火", "水", "木", "金", "土"};

        // サイズを設定
		setPreferredSize(new Dimension(CALENDAR_WIDTH, CALENDAR_HEIGHT));
        // レイアウトを設定
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(layout);

		JPanel month = new JPanel();
		month.add(new JLabel(new SimpleDateFormat("yyyy年 M月").format(now)));
		month.setBackground(Color.WHITE);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 7;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(3, 3, 3, 3);
		layout.setConstraints(month, gbc);
		add(month);
		
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		for(int i = 0; i < WEEK; ++i) {
			gbc.gridx = i;
			JPanel day = new JPanel();
			day.add(new JLabel(days[i]));
			day.setBackground(Color.WHITE);
			layout.setConstraints(day, gbc);
			add(day);
		}

		// 本日の最初の日数を設定
		Calendar proc = Calendar.getInstance();
		proc.add(Calendar.DAY_OF_MONTH, - (WEEK - proc.getMinimalDaysInFirstWeek() ) - proc.get(Calendar.DAY_OF_MONTH) + 1);
		for(int i = 0; i < WEEKS_NUMBER; ++i) {
			gbc.gridy = 2 + i;
			for(int j = 0; j < WEEK; ++j) {
				gbc.gridx = j;
				JPanel panel = getDayPanel(calendar, proc);
				layout.setConstraints(panel, gbc);
				add(panel);
				proc.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
    }
    
    /**
     * 日付パネルを取得する
     * @param today 本日の日付
     * @param d 表示する日付
     * @return 設定済みの日付パネル
     */
    private JPanel getDayPanel(Calendar today, Calendar d) {
    	// ラムダのために日付を保持する
    	CalendarPanel self = this;
    	JFrame owner = this.owner;
    	Calendar rest = (Calendar) d.clone();
    	JPanel panel = new JPanel();
    	MouseListener lis = new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
//				new AddTaskDialog();
				new ConfigDialog(self, owner, rest, e.getXOnScreen(), e.getYOnScreen());
			}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		};
		
    	panel.setPreferredSize(new Dimension(DAY_WIDTH, DAY_HEIGHT));
    	// 色を設定
    	panel.setBackground( 
    			today.get(Calendar.MONTH) != d.get(Calendar.MONTH) ? Color.GRAY :
    			d.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ? new Color(100, 100, 255) :
    			d.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ? new Color(255, 100, 100) : Color.WHITE);
    	panel.add(new JLabel(new SimpleDateFormat("d").format(d.getTime())), BorderLayout.NORTH);
    	panel.addMouseListener(lis);
    	JScrollPane task = new JScrollPane(getTaskPanel(today, d));
    	task.setOpaque(true);
    	task.setBackground(new Color(0, 0, 0, 0));
    	task.setPreferredSize(new Dimension(DAY_WIDTH - 10, DAY_HEIGHT * 2 / 3));
    	task.addMouseListener(lis);
    	panel.add(task, BorderLayout.SOUTH);
    	return panel;
    }    
    
    //TODO 未実装
    /**
     * タスクのリストが乗ったパネルを返す
     * @param today 本日の日付
     * @param d 設定する日付
     * @return
     */
    private JPanel getTaskPanel(Calendar today, Calendar d) {
    	JPanel panel = new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	panel.setBackground(new Color(0, 0, 0, 0));
    	for(Task t : TaskManager.instance().getTask(d.getTime())) {
    		JLabel task = new JLabel(t.getName());
    		panel.add(task);
    	}
    	taskPanelList.add(new PanelWithDate(panel, (Calendar)d.clone()));
    	return panel;
    }
    
    /**
     * 表示を更新する
     */
    public void reflesh() {
		for(PanelWithDate panel : taskPanelList) {
			panel.panel.removeAll();
	    	for(Task t : TaskManager.instance().getTask(panel.date.getTime())) {
	    		JLabel task = new JLabel(t.getName());
	    		panel.panel.add(task);
	    	}
		}

		owner.pack();				
		owner.repaint();
    }

    /**
     * カレンダー情報付きパネル
     * @author yuu
     *
     */
    private class PanelWithDate {
    	JPanel panel;
    	Calendar date;
		public PanelWithDate(JPanel panel, Calendar date) {
			this.panel = panel;
			this.date = date;
		}
    }
}
