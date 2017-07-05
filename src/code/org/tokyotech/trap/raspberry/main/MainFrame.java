package code.org.tokyotech.trap.raspberry.main;

import javax.swing.JFrame;

/**
 * 全てを乗せるメインフレーム
 * @author yuu
 * @since 2017/7/5
 */
public class MainFrame extends JFrame {
	public MainFrame() {
		setTitle("Raspberry");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		add(new CalendarPanel());
		pack();
		
		setVisible(true);
	}
}
