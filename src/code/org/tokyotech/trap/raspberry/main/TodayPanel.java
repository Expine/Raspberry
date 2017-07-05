package code.org.tokyotech.trap.raspberry.main;

import javax.swing.JPanel;
import java.awt.*;

public class TodayPanel extends JPanel {
	public TodayPanel() {
		setPreferredSize(new Dimension(200,600));
		setBackground(Color.DARK_GRAY);

		setLayout(new FlowLayout());

		//add(new TaskPanel());
	}
}
