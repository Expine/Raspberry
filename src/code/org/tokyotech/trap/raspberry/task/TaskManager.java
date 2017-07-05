package code.org.tokyotech.trap.raspberry.task;

import java.util.ArrayList;
import java.util.Date;

/**
 * タスクの管理を行う
 * @author yuu
 * @since 2017/7/5
 */
public class TaskManager {
	/**　シングルトン管理のインスタンス */
	private TaskManager instance = new TaskManager();
	/** タスクのリスト */
	private ArrayList<Task> tasks = new ArrayList<Task>();
	
	private TaskManager(){}

	/**
	 * インスタンス取得
	 * @return TaskManagerのインスタンス
	 */
	public TaskManager instance() {
		return instance;
	}
	
	/**
	 * タスクを追加する
	 * @param task 追加するタスク
	 */
	public void addTask(Task task) {
		tasks.add(task);
	}

	/**
	 * タスクを順次取得する
	 * @param i 取得するタスクの番号
	 * @return その番号のタスクがあるならそのタスク、そうでないならnull
	 */
	public Task getTask(int i) {
		return tasks.size()  > i ? tasks.get(i) : null;
	}
	
	/**
	 * ある日付のタスクを取得する
	 * @param d 取得する日付
	 * @return ある日付をもったタスクのリスト
	 */
	public ArrayList<Task> getTask(Date d) {
		return new ArrayList<Task>();
	}

	/**
	 * あるタグのタスクを取得する
	 * @param t 取得するタグ
	 * @return あるタグをもったタスクのリスト
	 */
	public ArrayList<Task> getTask(Tag t) {
		return new ArrayList<Task>();
	}
	
}
