package code.org.tokyotech.trap.raspberry.main;

import code.org.tokyotech.trap.raspberry.task.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

/**
 * Created by iwasetakumi on 2017/07/06.
 */
public class DayPanel extends JPanel implements ActionListener {

    Calendar calendar = Calendar.getInstance();
    protected static int date;
    int day = calendar.get(Calendar.DATE);
    String taskname;
    public Task task;

    public DayPanel(int date,int x,int y,int width,int height){
        super();
        this.date = date;
        setBounds(x,y,width,height);
        setBackground(Color.WHITE);
        JLabel label = new JLabel("" + date);
        add(label);

        JButton AddTask = new JButton(taskname);
        AddTask.setPreferredSize(new Dimension(width,height-30));
        AddTask.addActionListener(this);
        add(AddTask);

        if(date == day){
            setBackground(Color.GRAY);
        }

        if(task != null) {
            taskname = task.getName();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new AddTaskDialog();
    }
}
