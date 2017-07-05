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
	/** 次に登録するタスクのID */
	static int startID = 0;
	/**　タスクのID */
	private int ID;
	/** タスクの登録名 */
	private String name;
	/** タスクに登録されたタグ*/
	private ArrayList<Tag> tags;
	/** タスクを登録した日時 */
	private Date start;
	/** タスクの期限 */
	private Date limit;
	/* タスクの説明 */
	private String explanation;
	/* タスクの進捗時間 */
	private Time elapsedTime;
	/* タスクの終了予想時間 */
	private Time scheduledTime;

	/** 一つのタスクの重み*/
	private float weight;
	private String checkPointExp;
	
	
	private int loopInterval;
	
	public Task(String name, ArrayList<Tag> tags, Date start, Date limit, String explanation, Time elapsedTime, Time scheduledTime, int loopInterval) {
		this(name, tags, start, limit, explanation, elapsedTime, scheduledTime, loopInterval, 1.0f, "");
	}

	public Task(String name, ArrayList<Tag> tags, Date start, Date limit, String explanation, Time elapsedTime, Time scheduledTime, int loopInterval, float weight, String checkPointExp) {
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
		this.loopInterval = loopInterval;
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
	
	public int getLoopInterval() {
		return loopInterval;
	}
	
	public void addTime(Time t) {
		elapsedTime.setTime(elapsedTime.getTime() + t.getTime());
	}
}
