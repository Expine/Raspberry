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
	
	/**
	 * タスクデータを読み込む
	 */
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
	
	/**
	 * タスクのデータを保存する
	 */
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
		long result = 0L;
		for(Tag t : tags)
			result += calculateTime(t).getTime();
		return new Time(tags.size() == 0 ? 0 : result / tags.size());
	}	
	
	/**
	 * あるタグの推定時間を計算する
	 * @param tag 推定時間を計算するタグ
	 * @return 計算された推定時間
	 */
	private Time calculateTime(Tag tag) {
		double count = 0.0;
		double time = 0.0;
		ArrayList<WeightedTask> weightedTasks = new ArrayList<WeightedTask>();
		for(Task task : closeTasks.values())
			for(Tag t : task.getTags())
				if(t.getTag().equals(tag.getTag()))
					weightedTasks.add(new WeightedTask(task, 1.0f));
				else
					weightedTasks.add(new WeightedTask(task, 1.0f - (float)calculateLevenshteinDistance(t.getTag(), tag.getTag()) / Math.min(t.getTag().length(), tag.getTag().length())));
		for(WeightedTask wt : weightedTasks) {
			time += (double)wt.task.getElapsedTime().getTime() * wt.weight;
			count += wt.weight;
		}
		
		return new Time((long)(time / count));
	}
	
	/**
	 * レーベンシュタイン距離を求める
	 * ただし、挿入および削除は距離に含まないとする
	 * @param s1 比較対象の文字列 
	 * @param s2 比較対象の文字列 
	 * @return レーベンシュタイン距離
	 */
	private int calculateLevenshteinDistance(String s1, String s2) {
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		
		int table[][] = new int[str1.length + 1][str2.length + 1];
		for(int i = 0; i < str1.length + 1; ++i)
			table[i][0] = i;
		for(int i = 0; i < str2.length + 1; ++i)
			table[0][i] = i;
		
		int cost;
		for(int i = 1; i < str1.length + 1; ++i)
			for(int j = 1; j < str2.length + 1; ++j) {
				cost = str1[i] == str2[j] ? 0 : 1;
				//
				table[i][j] = Math.min(
						table[i - 1][j], // 挿入
				Math.min(
						table[i][j - 1], // 削除
						table[i - 1][j - 1] + cost));	// 置換
			}
		return table[str1.length][str2.length];
	}
	
	/**
	 * タスクに重みを付けたデータクラス
	 * @author yuu
	 */
	private class WeightedTask {
		/** タグ */
		private Task task;
		/** 重み */
		private float weight;
		public WeightedTask(Task task, float weight) {
			this.task = task;
			this.weight = weight;
		}		
	}
}
