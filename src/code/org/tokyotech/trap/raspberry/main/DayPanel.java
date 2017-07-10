package code.org.tokyotech.trap.raspberry.main;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

/**
 * Created by iwasetakumi on 2017/07/06.
 */
public class DayPanel extends JPanel {

    Calendar calendar = Calendar.getInstance();
    protected static int date;
    int day = calendar.get(Calendar.DATE);

    public DayPanel(int date,int x,int y,int width,int height){
        super();
        this.date = date;
        setBounds(x,y,width,height);
        setBackground(Color.WHITE);
        JLabel label = new JLabel("" + date);
        add(label);

        if(date == day){
            setBackground(Color.BLUE);
        }
    }

}
