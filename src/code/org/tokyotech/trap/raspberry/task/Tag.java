package code.org.tokyotech.trap.raspberry.task;

import java.io.Serializable;

/**
 * タグのクラス
 * @author yuu
 * @since 2017/7/5
 */
public class Tag implements Serializable{
	private String tag;

	public Tag(String tag) {
		this.tag = tag;
	}
	
	public String getTag() {
		return tag;
	}
}
