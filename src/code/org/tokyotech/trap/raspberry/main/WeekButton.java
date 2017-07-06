package code.org.tokyotech.trap.raspberry.main;

import javax.swing.*;
import java.awt.*;

/**
 * Created by iwasetakumi on 2017/07/06.
 */
public class WeekButton extends JButton {
    public WeekButton(int weeknumber, int position){
        setText(""+weeknumber);
        setBounds(10,position,20,80);
    }

    public void step(){}
}
