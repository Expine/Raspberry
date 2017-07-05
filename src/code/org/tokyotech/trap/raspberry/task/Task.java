package code.org.tokyotech.trap.raspberry.task;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 * タスクのクラス
 * @author yuu
 * @since 2017/7/5
 */
public class Task {
	static int startID = 0;
	private int ID;
	private String name;
	private ArrayList<Tag> tags;
	private Date start;
	private Date limit;
	private float weight;
	private String explanation;
	private String checkPointExp;
	
	private Time elapsedTime;
	private Time scheduledTime;
	
	public Task(String name, ArrayList<Tag> tags, Date start, Date limit, String explanation, Time elapsedTime, Time scheduledTime) {
		this(name, tags, start, limit, explanation, elapsedTime, scheduledTime, 0.0f, "");
	}

	public Task(String name, ArrayList<Tag> tags, Date start, Date limit, String explanation, Time elapsedTime, Time scheduledTime, float weight, String checkPointExp) {
		this.ID = startID++;
		this.name = name;
		this.tags = tags;
		this.start = start;
		this.limit = limit;
		this.weight = weight;
		this.explanation = explanation;
		this.checkPointExp = checkPointExp;
		this.elapsedTime = elapsedTime;
		this.scheduledTime = scheduledTime;
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

	public Time getElapsedTime() {
		return elapsedTime;
	}

	public Time getScheduledTime() {
		return scheduledTime;
	}	
}
