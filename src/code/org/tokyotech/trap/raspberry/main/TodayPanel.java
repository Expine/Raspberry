package code.org.tokyotech.trap.raspberry.main;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class TodayPanel extends JPanel {

	Calendar calendar = Calendar.getInstance();
	int day = calendar.get(Calendar.DATE);

	public TodayPanel() {
		setPreferredSize(new Dimension(200, 600));

		setLayout(new FlowLayout());

		JPanel panel = new JPanel();
		JLabel label = new JLabel(""+day + "日");
		panel.setPreferredSize(new Dimension(200, 30));
		panel.setBackground(Color.WHITE);
		panel.add(label);
		add(panel);

		for (int i = 0; i < 6; i++) {
			add(new TaskPanel());
		}
	}

}
