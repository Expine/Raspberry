package code.org.tokyotech.trap.raspberry.main;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

/**
 * Created by iwasetakumi on 2017/07/06.
 */
public class DayPanel extends JPanel {

    protected static int date;

    public DayPanel(int date,int x,int y,int width,int height){
        super();
        this.date = date;
        setBounds(x,y,width,height);
        setBackground(Color.WHITE);
        JLabel label = new JLabel("" + date);
        add(label);
        //add(new TaskPanel());
    }

    public void step(){}
}
