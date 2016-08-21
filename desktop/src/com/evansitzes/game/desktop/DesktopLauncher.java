package com.evansitzes.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.evansitzes.game.Configuration;
import com.evansitzes.game.TwilightEternal;

/**
 * Created by evan on 6/6/16.
 */
public class DesktopLauncher {
	public static void main(String[] args) {
		final Configuration configuration = new Configuration();
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = configuration.title;
		cfg.width = configuration.width;
		cfg.height = configuration.height;

		new LwjglApplication(new TwilightEternal(configuration), cfg);
	}
}
