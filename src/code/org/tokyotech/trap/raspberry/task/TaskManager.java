package code.org.tokyotech.trap.raspberry.task;

import java.sql.Time;
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
	
	private TaskManager(){
		for(int i = 0; i < 10; ++i) {
			Task t = new Task("Name" + i, new ArrayList<Tag>(), new Date(1000), new Date(2000), "Exp" + i, new Time(0), new Time(2000));
			tasks.add(t);
		}
	}

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
	 * ある日付を含むタスクを取得する
	 * @param d 取得する日付
	 * @return ある日付をもったタスクのリスト
	 */
	public ArrayList<Task> getTask(Date d) {
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task : tasks)
			if(task.getStart().after(d) && task.getLimit().before(d))
				result.add(task);		
		return result;
	}

	/**
	 * あるタグのタスクを取得する
	 * @param t 取得するタグ
	 * @return あるタグをもったタスクのリスト
	 */
	public ArrayList<Task> getTask(Tag t) {
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task : tasks)
			if(task.getTags().contains(t))
				result.add(task);		
		return result;
	}
	
}
