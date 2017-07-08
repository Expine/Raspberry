package code.org.tokyotech.trap.raspberry.execute;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * グローバルフックを行うクラス
 * @author yuu
 *
 */
public class KeyManager implements NativeKeyListener{
	private static long elapsedTime = 0L;
	
	/**
	 * グローバルフックを登録する
	 */
	public static void register() {
		if (!GlobalScreen.isNativeHookRegistered()) {
            try {
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException e) {
                e.printStackTrace();
            }
        }		
        final Logger jNativeHookLogger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        if (jNativeHookLogger.getLevel() != Level.OFF) {
                jNativeHookLogger.setLevel(Level.OFF);
        }
	
		GlobalScreen.addNativeKeyListener(new KeyManager());		
		
		new Thread(() -> {
			long beg;
			while(true) {				
				beg = System.nanoTime();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				elapsedTime += System.nanoTime() - beg;
			}
		}).start();;
	}
	
	/**
	 * キーが押されていない時間を返す
	 * @return キーが押されていない時間
	 */
	public long getElapsedTime() {
		return elapsedTime;
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		elapsedTime = 0;
	}
}
