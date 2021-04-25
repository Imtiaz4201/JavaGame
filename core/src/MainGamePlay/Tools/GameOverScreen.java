package MainGamePlay.Tools;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import AbstractClass.AbstractGameScreen;
import AssetsManager.Manager;
import GameConfiguration.GameConfig;
import Levels.jungleLevels;
import MainGamePlay.jungle.jungleLevel1;

public class GameOverScreen extends AbstractGameScreen {
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private Game game;
    private Table items;
    private Label label, score, play, exit;
    private Stack stack;
    private Manager manager = Manager.managerInstance;
    private Levels.jungleLevels jungleLevels;
    private OrthographicCamera orthographicCamera;
    private float playX, playY, exitX, exitY, touchX, touchY;
    private SpriteBatch batch;

    public GameOverScreen() {
        batch = new SpriteBatch();
        jungleLevels = new jungleLevels();
        skin = manager.assetManager.get(manager.settingsSkin, Skin.class);
    }

    public Table setItems() {
        Table table = new Table();
        table.setScale(10, 10);
        table.pad(0, 0, -200, 0);
        // TODO: score label
        label = new Label("Game Over", skin, "title");
        table.add(label).row();
        play = new Label("Play Again", skin, "title");
        play.setColor(Color.GREEN);
        table.add(play).row();
        exit = new Label("Exit", skin, "title");
        exit.setColor(Color.RED);
        table.add(exit);

        return table;
    }

    @Override
    public void show() {
        orthographicCamera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.V_WIDTH, GameConfig.V_HEIGHT, orthographicCamera);
        stage = new Stage(viewport, batch);
        stack = new Stack();
        stage.addActor(stack);
        Gdx.input.setInputProcessor(stage);
        items = setItems();
        stack.add(items);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        touchX = Gdx.input.getX();
        touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
        playX = GameConfig.WIDTH / 2f - play.getWidth() + 2;
        playY = GameConfig.HEIGHT / 2f - play.getHeight() + 4;
        exitX = GameConfig.WIDTH / 2f - exit.getWidth() + 2;
        exitY = GameConfig.HEIGHT / 2f - exit.getHeight() + 4 - play.getHeight() - 8;

        if (Gdx.input.isTouched()) {
            //play again
            if (touchX > playX && touchX < playX + play.getWidth() && touchY > playY - play.getHeight() && touchY < playY) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new jungleLevel1());
                batch.dispose();
            }
            //main menu
            if (touchX > exitX && touchX < exitX + exit.getWidth() && touchY > exitY - exit.getHeight() && touchY < exitY) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new jungleLevels());
                batch.dispose();
            }
        }

        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stack.setSize(GameConfig.WIDTH, GameConfig.HEIGHT);
        orthographicCamera.setToOrtho(false, GameConfig.WIDTH, GameConfig.HEIGHT);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        skin.dispose();
        batch.dispose();
    }
}
