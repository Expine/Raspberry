package code.org.tokyotech.trap.raspberry.main;

import code.org.tokyotech.trap.raspberry.execute.ExecuteDialog;
import code.org.tokyotech.trap.raspberry.task.Task;
import code.org.tokyotech.trap.raspberry.task.TaskManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

/**
 * Created by iwasetakumi on 2017/07/06.
 */
public class TaskPanel extends JPanel implements ActionListener {

    public Task task;

    public TaskPanel(Task task){
        setPreferredSize(new Dimension(180,100));
        setBackground(Color.WHITE);

        setLayout(null);

        JButton executebutton = new JButton();
        executebutton.setBounds(10,50,160,30);
        executebutton.addActionListener(this);
        add(executebutton);

        JLabel namelabel = new JLabel(task.getName());
        namelabel.setBounds(10,0,160,30);
        add(namelabel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new ExecuteDialog(null);
    }
}
