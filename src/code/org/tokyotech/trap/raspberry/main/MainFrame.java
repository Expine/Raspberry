package code.org.tokyotech.trap.raspberry.main;

import javax.swing.JFrame;
import java.awt.*;

/**
 * 全てを乗せるメインフレーム
 * @author yuu
 * @since 2017/7/5
 */
public class MainFrame extends JFrame {
	public MainFrame() {
		setTitle("Raspberry");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setBounds(300,100,0,0);

		setLayout(new FlowLayout());
		
		add(new CalendarPanel());
		add(new TodayPanel());
		pack();
		
		setVisible(true);
	}
}
