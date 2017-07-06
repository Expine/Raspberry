package code.org.tokyotech.trap.raspberry.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * タスクの管理を行う
 * @author yuu
 * @since 2017/7/5
 */
public class TaskManager {
	/**　シングルトン管理のインスタンス */
	private static TaskManager instance = new TaskManager();
	/** タスクのリスト */
	private HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();
	/** クローズするタスクのリスト */
	private HashMap<Integer, Task> closeTasks = new HashMap<Integer, Task>();
	
	private TaskManager(){
//		loadTaskData();
		for(int i = 0; i < 10; ++i) {
			Task t = new Task("Name" + i, new ArrayList<Tag>(), new Date(1000), new Date(2000), "Exp" + i, new Time(2000), 0);
			tasks.put(t.getID(), t);
		}
	}
	
	private void loadTaskData() {		
		// Check file
		if(!(new File("./config").exists()))
			new File("./config").mkdirs();

		String line = null;
		try {
			if(new File("./config/.task").exists()) {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./config/.task"));
				tasks = (HashMap<Integer, Task>)ois.readObject();		
				ois.close();				
			}

			if(new File("./config/.old").exists()) {
				ObjectInputStream ois =new ObjectInputStream(new FileInputStream("./config/.old"));
				closeTasks = (HashMap<Integer, Task>)ois.readObject();		
				ois.close();				
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void saveTaskData() {
		// Check file
		if(!(new File("./config").exists()))
			new File("./config").mkdirs();

		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./config/.task"));
			oos.writeObject(tasks);
			oos.close();
			oos = new ObjectOutputStream(new FileOutputStream("./config/.old"));
			oos.writeObject(closeTasks);
			oos.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * インスタンス取得
	 * @return TaskManagerのインスタンス
	 */
	public static TaskManager instance() {
		return instance;
	}
	
	/**
	 * タスクを追加する
	 * @param task 追加するタスク
	 */
	public void addTask(Task task) {
		tasks.put(task.getID(), task);
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
		for(Task task : tasks.values())
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
		for(Task task : tasks.values())
			if(task.getTags().contains(t))
				result.add(task);		
		return result;
	}

	/**
	 * 進捗してタスクに時間を追加する
	 * @param ID 追加するタスクのID
	 * @param time 進捗した時間
	 */
	public void progress(int ID, Time time) {
		tasks.get(ID).addTime(time);
	}
	
	/**
	 * タスクをクローズする
	 * @param ID
	 */
	public void closeTask(int ID) {
		Task task = tasks.get(ID);
		tasks.remove(task);
		closeTasks.put(task.getID(), task);
	}

	/**
	 * タグからかかりそうな時間を推定する
	 * @return 推定時間
	 */
	public Time getEstimatedTime(ArrayList<Tag> tags) {
		return new Time(1000);
	}	
}
