package Levels.StartingLevelAnimation.Jungle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;


import AbstractClass.AbstractGameScreen;
import AssetsManager.Manager;
import GameConfiguration.GameConfig;
import MainGamePlay.jungle.jungleLevel0;
import Settings.SettingPref;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

public class JL0 extends AbstractGameScreen {
    private OrthographicCamera orthographicCamera;
    private FitViewport fitViewport;
    private Stage stage;
    private Texture texture;
    private Image image;
    private Manager manager = Manager.managerInstance;
    private SettingPref settingPref = SettingPref.instance;

    public JL0() {
        texture = manager.assetManager.get(manager.JL0img, Texture.class);
        image = new Image(texture);
    }

    @Override
    public void show() {
        orthographicCamera = new OrthographicCamera();
        fitViewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT, orthographicCamera);
        stage = new Stage(fitViewport);
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new jungleLevel0());
                stage.dispose();
            }
        };

        image.setOrigin(32, 32);
        image.setPosition(stage.getWidth() / 2 - 32, stage.getHeight() + 32);

        image.addAction(Actions.sequence(alpha(0), scaleTo(.1f, .1f),
                parallel(fadeIn(2f, Interpolation.pow2),
                        scaleTo(2f, 2f, 2.5f, Interpolation.pow5),
                        moveTo(stage.getWidth() / 2 - 300, stage.getHeight() / 2 - 50, 2f, Interpolation.swing)),
                delay(1.5f), fadeOut(1.25f), run(transitionRunnable)));

        stage.addActor(image);

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthographicCamera.update();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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
        stage.dispose();
        texture.dispose();
    }
}
