package code.org.tokyotech.trap.raspberry.main;

import javax.swing.*;
import java.awt.*;

public class TodayPanel extends JPanel {
	public TodayPanel() {
		setPreferredSize(new Dimension(200,600));
		setBackground(Color.GRAY);

		setLayout(new FlowLayout());

		JPanel panel = new JPanel();
		JLabel label = new JLabel("TODAY");
		panel.setPreferredSize(new Dimension(200,30));
		panel.setBackground(Color.WHITE);
		panel.add(label);
		add(panel);

		for(int i = 0;i<6;i++){
			add(new TaskPanel());
		}
	}

	public void update(){}

	public void step(){}

	public void draw(Graphics2D g){}

}
