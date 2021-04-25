package Levels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;


import GameConfiguration.GameConfig;
import AbstractClass.AbstractGameScreen;
import AssetsManager.Manager;
import MenuWidget.Menu;
import Settings.SettingPref;

public class LevelLists extends AbstractGameScreen {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Stage stage;
    private FitViewport fitViewport;
    private Skin buttonSkin, BGSkin;
    private Stack stack;
    private Table table1, table2, table3;
    private ImageButton buttonJungle, buttonCity, buttonNight, buttonDesert,
            buttonExt1, buttonExt2, exitButton;
    private Image background;
    private Sound buttonClickSound;
    private Manager manager = Manager.managerInstance;
    private SettingPref settingPref = SettingPref.instance;
    private Music music1;

    private void playMusic() {
        if (settingPref.music == true) {
            music1.setLooping(true);
            music1.setVolume(settingPref.musicVolume);
            music1.play();
        } else if (settingPref.music == false) {
            music1.setLooping(false);
            music1.stop();
        }
    }

    private Table setBG() {
        table1 = new Table();
        background = new Image(BGSkin, "BG");
        table1.add(background);
        return table1;
    }

    protected Table setButtons() {
        table2 = new Table();
        table2.columnDefaults(0).padRight(20);
        table2.columnDefaults(1).padRight(30);
        table2.columnDefaults(2).padRight(40);

        buttonExt1 = new ImageButton(buttonSkin, "ext");
        table2.add(buttonExt1).colspan(2).row();
        buttonJungle = new ImageButton(buttonSkin, "jungle");
        buttonJungle.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (settingPref.sound == true) {
                    buttonClickSound.play(settingPref.soundVolume);
                } else if (settingPref.sound == false) {
                    buttonClickSound.stop();
                }
                if (settingPref.music) {
                    music1.setLooping(false);
                    music1.stop();
                }
                ((Game) Gdx.app.getApplicationListener()).setScreen(new jungleLevels());
                ownDispose();
            }
        });
        table2.add(buttonJungle);
        buttonNight = new ImageButton(buttonSkin, "night");
        buttonNight.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (settingPref.sound == true) {
                    buttonClickSound.play(settingPref.soundVolume);
                } else if (settingPref.sound == false) {
                    buttonClickSound.stop();
                }
            }
        });
        table2.add(buttonNight).row();
        buttonCity = new ImageButton(buttonSkin, "city");
        buttonCity.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (settingPref.sound == true) {
                    buttonClickSound.play(settingPref.soundVolume);
                } else if (settingPref.sound == false) {
                    buttonClickSound.stop();
                }
            }
        });
        table2.add(buttonCity);
        buttonDesert = new ImageButton(buttonSkin, "desert");
        buttonDesert.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (settingPref.sound == true) {
                    buttonClickSound.play(settingPref.soundVolume);
                } else if (settingPref.sound == false) {
                    buttonClickSound.stop();
                }
            }
        });
        table2.add(buttonDesert).row();
        buttonExt2 = new ImageButton(buttonSkin, "ext");
        table2.add(buttonExt2).colspan(2);

        return table2;
    }

    protected Table exitButton() {
        table3 = new Table();
        exitButton = new ImageButton(buttonSkin, "back");
        table3.padBottom(-600f);
        table3.padLeft(-700f);
        exitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (settingPref.sound == true) {
                    buttonClickSound.play(settingPref.soundVolume);
                } else if (settingPref.sound == false) {
                    buttonClickSound.stop();
                }
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Menu());
                ownDispose();
            }
        });
        table3.add(exitButton);

        return table3;
    }

    protected void setItems() {
        // bg
        BGSkin = manager.assetManager.get(manager.BG2Json, Skin.class);
        // button pack
        buttonSkin = manager.assetManager.get(manager.levelUiSkin, Skin.class);
        Table bg = setBG();
        Table buttons = setButtons();
        Table exitBnt = exitButton();
        stack.add(bg);
        stack.add(buttons);
        stack.add(exitBnt);
    }

    @Override
    public void show() {
        music1 = manager.assetManager.get(manager.menuMusic, Music.class);
        buttonClickSound = manager.assetManager.get(manager.button_click, Sound.class);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        fitViewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT, camera);
        stage = new Stage(fitViewport, batch);
        stack = new Stack();
        stage.clear();
        stage.addActor(stack);

        setItems();
        playMusic();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // to  clear window before render items
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(stage.getCamera().combined);
        stage.act(Gdx.graphics.getDeltaTime());
        Gdx.input.setInputProcessor(stage);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, GameConfig.WIDTH, GameConfig.HEIGHT);
        stack.setSize(GameConfig.WIDTH, GameConfig.HEIGHT);
    }

    @Override
    public void pause() {
        music1.pause();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        buttonSkin.dispose();
        BGSkin.dispose();
        buttonClickSound.dispose();
        music1.dispose();
    }

    public void ownDispose(){
        batch.dispose();
        stage.dispose();
    }
}
