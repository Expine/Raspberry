package code.org.tokyotech.trap.raspberry.task;

import java.util.ArrayList;
import java.util.Date;

/**
 * タスクのクラス
 * @author yuu
 * @since 2017/7/5
 */
public class Task {
	private int ID;
	private String name;
	private ArrayList<Tag> tags;
	private Date date;
	public Task(int ID, String name, ArrayList<Tag> tags, Date date) {
		this.ID = ID;
		this.name = name;
		this.tags = tags;
		this.date = date;
	}
	public int getID() {
		return ID;
	}
	public String getName() {
		return name;
	}
	public ArrayList<Tag> getTags() {
		return tags;
	}
	public Date getDate() {
		return date;
	}
}
