package com.bmhs.gametitle;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.bmhs.gametitle.game.utils.GameHandler;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1280, 768);
		config.setForegroundFPS(60);
		config.setTitle("TileGame");
		new Lwjgl3Application(new GameHandler(), config);
	}
}
/////////