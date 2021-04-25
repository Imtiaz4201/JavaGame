package my.mygdx.game.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import my.mygdx.game.Main;
import GameConfiguration.GameConfig;

public class DesktopLauncher {

    public static void main(String[] arg) {

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        // main window width and height
        config.width = GameConfig.WIDTH;
        config.height = GameConfig.HEIGHT;
        config.title = GameConfig.title;
        config.vSyncEnabled = true;
        config.forceExit = false;
        config.resizable = false;
        new LwjglApplication(new Main(), config);
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }
}// main class
