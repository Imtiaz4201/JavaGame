package my.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import AssetsManager.Manager;
import MainGamePlay.Tools.GameOverScreenJL0;
import StartingWindow.StartingWindow;

public class Main extends Game {
    private SpriteBatch batch; // sprite batch helps to draw texture/images
    private Manager manager = Manager.managerInstance;

    @Override
    public void create() {
        manager.load();
        manager.assetManager.finishLoading();
        this.batch = new SpriteBatch();
        setScreen(new StartingWindow(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();

    }

    public SpriteBatch getBatch() {
        return batch;
    }

}// game and application listener  class
