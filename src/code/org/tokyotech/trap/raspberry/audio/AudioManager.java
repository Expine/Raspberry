package code.org.tokyotech.trap.raspberry.audio;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

/**
 * 音楽を管理するクラス
 * @author yuu
 *
 */
public class AudioManager {
	public static final String SOUND_FOLDER = "./sound";
	
	private static AudioClip clip;

	/**
	 * 音を鳴らす
	 */
	public static void playSound() {
		new File(SOUND_FOLDER).mkdirs();
		File[] files = new File(SOUND_FOLDER).listFiles();
		if(files.length == 0) {
			clip = Applet.newAudioClip(AudioManager.class.getClassLoader().getResource("res/p02.wav"));
		} else {
			try {
				clip = Applet.newAudioClip(files[(int)(Math.random() * files.length)].toURI().toURL());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		clip.loop();
	}
	
	/**
	 * 音を止める
	 */
	public static void stop() {
		clip.stop();
		clip = null;
	}
}
