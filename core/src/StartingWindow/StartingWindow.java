package StartingWindow;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;

import AbstractClass.AbstractGameScreen;
import AssetsManager.Manager;
import GameConfiguration.GameConfig;
import MenuWidget.Menu;
import Settings.SettingPref;
import my.mygdx.game.Main;

public class StartingWindow extends AbstractGameScreen {

    private Main main;
    private Menu menu;
    private Music startingMusic;
    private OrthographicCamera orthographicCamera;
    private FitViewport fitViewport;
    private Pixmap cursor;

    private static final int FRAME_COLS = 6, FRAME_ROWS = 10;
    Animation<TextureRegion> walkAnimation;
    Texture imageSprite;
    SpriteBatch spriteBatch;
    private Manager manager = Manager.managerInstance;
    private SettingPref settingPref = SettingPref.instance;
    float stateTime = 0;

    public StartingWindow(Main main) {
        this.main = main;
    }

    @Override
    public void show() {
        orthographicCamera = new OrthographicCamera();
        fitViewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT, orthographicCamera);
        cursor = new Pixmap(Gdx.files.internal("Backgrounds/cursor.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursor, 0, 0));

        imageSprite = manager.assetManager.get(manager.startingAppAnimation, Texture.class);
        startingMusic = manager.assetManager.get(manager.starting_music, Music.class);
        TextureRegion[][] tmp = TextureRegion.split(imageSprite,
                imageSprite.getWidth() / FRAME_COLS,
                imageSprite.getHeight() / FRAME_ROWS);

        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        walkAnimation = new Animation<TextureRegion>(.15f, walkFrames);
        spriteBatch = new SpriteBatch();
        playMusic();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, 0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);
        spriteBatch.end();

        if (stateTime > walkAnimation.getAnimationDuration()) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Menu());
            if (settingPref.music) {
                startingMusic.setLooping(false);
                startingMusic.stop();
            }
            dispose();
        }
    }

    public void playMusic() {
        settingPref.loadData();
        if (settingPref.music) {
            startingMusic.setLooping(true);
            startingMusic.setVolume(settingPref.musicVolume);
            startingMusic.play();
        } else if (!settingPref.music) {
            startingMusic.setLooping(false);
            startingMusic.stop();
        }
    }

    @Override
    public void resize(int width, int height) {

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
        spriteBatch.dispose();
        cursor.dispose();
        startingMusic.dispose();
    }
}
