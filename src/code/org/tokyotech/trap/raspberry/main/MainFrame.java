package code.org.tokyotech.trap.raspberry.main;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import code.org.tokyotech.trap.raspberry.task.TaskManager;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 全てを乗せるメインフレーム
 * @author yuu
 * @since 2017/7/5
 */
public class MainFrame extends JFrame {
	private CalendarPanel calendar = new CalendarPanel(this);
	private TodayPanel today = new TodayPanel(this);
	
	public MainFrame() {
		setMenuBar();
		
		setTitle("Raspberry");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLayout(new FlowLayout());
		
		add(calendar);
		add(today);
		pack();
		

		addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				TaskManager.instance().saveTaskData();
			}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
		});
		setVisible(true);
	}
	
	/**
	 * メニューを設定
	 */
	private void setMenuBar() {
		JMenuBar menu = new JMenuBar();
		ArrayList<MenuItem> menus = new ArrayList<MenuItem>();
		HashMap<Character, ArrayList<MenuItemItem>> items = new HashMap<Character, ArrayList<MenuItemItem>>();
		
		// メニューを設定
		menus.add(new MenuItem(new JMenu("タスク(T)"), 'T'));
		
		for(MenuItem item : menus) {
			item.menu.setMnemonic(item.acceralator);
			item.menu.setFont(new Font("", Font.PLAIN, 12));
			menu.add(item.menu);
			menu.add(Box.createRigidArea(new Dimension(5, 1)));
		}
		
		menus.forEach(it -> { items.put(it.acceralator, new ArrayList<MenuItemItem>()); });
		
		// メニュー項目設定
		items.get('T').add(new MenuItemItem(new JMenuItem("タスクの新規作成"), KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), (e) -> {}));
		items.get('T').add(new MenuItemItem(new JMenuItem("タスクを実行する"), KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK), (e) -> {}));
		
		menus.forEach(it -> { items.get(it.acceralator).forEach(i -> { if(i.item != null) it.menu.add(i.item); else it.menu.addSeparator(); }); });
		
		setJMenuBar(menu);
	}
	
	public void reflesh() {
		calendar.reflesh();
		today.reflesh();
	}
	
	private class MenuItem {
		JMenu menu;
		char acceralator;

		private MenuItem(JMenu menu, char acceralator) {
			this.menu = menu;
			this.acceralator = acceralator;
		}
	}
	
	private class MenuItemItem {
		JMenuItem item;

		public MenuItemItem(JMenuItem item, KeyStroke key, ActionListener a) {
			this.item = item;
			item.setAccelerator(key);
			item.addActionListener(a);
		}		
	}	
}
