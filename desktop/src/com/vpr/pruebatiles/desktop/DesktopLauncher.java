package com.vpr.pruebatiles.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.vpr.pruebatiles.Tiles;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 720;
		config.height = 480;
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
		new LwjglApplication(new Tiles(), config);
	}
}
