package code.org.tokyotech.trap.raspberry.main;

import java.awt.*;

import javax.swing.*;

import java.util.Calendar;

public class CalendarPanel extends JPanel {

    public enum chapter{
        MONTH,WEEK,DAY;
    }

    public CalendarPanel() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        int[] calendarday = new int[42];
        int count = 0;
        int gridX,gridY;
        gridX = 0;

        count = setDateArray(year,month,day,calendarday,count);
        int weekcount = count / 7;

		setPreferredSize(new Dimension(700, 600));

		setLayout(null);

		JPanel panel1 = new JPanel();
		JLabel tuki = new JLabel(""+(month+1) + "月");
		panel1.setBounds(50,10,620,20);
		panel1.setBackground(Color.WHITE);
		panel1.add(tuki);
		add(panel1);

		JPanel panel2 = new JPanel();
		JLabel week = new JLabel("日　　　　　　月　　　　　　火　　　　　　水　　　　　　木　　　　　　金　　　　　　土");
		panel2.setBounds(50,40,620,20);
		panel2.setBackground(Color.WHITE);
		panel2.add(week);
		add(panel2);

        for(int i = 0;i < 6;i++) {
            add(new WeekButton(i + 1, 70 + i * 90));
        }

        for(int i = 0;i < weekcount;i++){
            gridY = 70+i*90;
                for (int j = i * 7; j < i * 7 + 7; j++) {
                        if (calendarday[j] > 35) {
                            add(new DayPanel(calendarday[j] - 35,50+90*gridX, gridY, 80, 80));
                            gridX++;
                            if(gridX > 6){
                                gridX = 0;
                            }
                        }else {
                            add(new DayPanel(calendarday[j],50+90*gridX, gridY, 80, 80));
                            gridX++;
                            if(gridX > 6){
                                gridX = 0;
                            }
                        }
                }

        }
	}

	protected int setDateArray(int year, int month, int day, int[] calendarday, int count){
        Calendar calendar = Calendar.getInstance();

        calendar.set(year,month,1);
        int startweek = calendar.get(Calendar.DAY_OF_WEEK);

        calendar.set(year,month,0);
        int beforemonthlastday = calendar.get(Calendar.DATE);

        calendar.set(year,month + 1,0);
        int thismonthlastday = calendar.get(Calendar.DATE);

        for(int i = startweek - 2;i >= 0;i--){
            calendarday[count++] = beforemonthlastday - i  + 35;
        }

        for(int i = 1;i <= thismonthlastday;i++){
            calendarday[count++] = i;
        }

        int nextmonthday = 1;
        while(count % 7 != 0){
            calendarday[count++] =35 + nextmonthday++;
        }
        return count;
    }

}
