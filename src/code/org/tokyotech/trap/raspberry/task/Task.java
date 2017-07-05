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
	private Date start;
	private Date limit;
	private float weight;
	private String explanation;
	private String checkPointExp;
	
	public Task(int ID, String name, ArrayList<Tag> tags, Date start, Date limit, String explanation) {
		this(ID, name, tags, start, limit, explanation, 0.0f, "");
	}

	public Task(int ID, String name, ArrayList<Tag> tags, Date start, Date limit, String explanation, float weight, String checkPointExp) {
		this.ID = ID;
		this.name = name;
		this.tags = tags;
		this.start = start;
		this.limit = limit;
		this.weight = weight;
		this.explanation = explanation;
		this.checkPointExp = checkPointExp;
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
	public Date getStart() {
		return start;
	}
	
	public Date getLimit() {
		return limit;
	}

	public float getWeight() {
		return weight;
	}

	public String getExplanation() {
		return explanation;
	}

	public String getCheckPointExp() {
		return checkPointExp;
	}	
}
