package code.org.tokyotech.trap.raspberry.main;

import java.awt.Dimension;

import javax.swing.JPanel;

import java.util.Calendar;

public class CalendarPanel extends JPanel {

	Calendar calendar = Calendar.getInstance();

	int year = calendar.get(Calendar.YEAR);
	int month = calendar.get(Calendar.MONTH);
	int day = calendar.get(Calendar.DATE);

	public CalendarPanel() {
		setPreferredSize(new Dimension(600, 600));

	}
}
