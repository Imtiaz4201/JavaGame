package Levels;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import AbstractClass.AbstractGameScreen;
import AssetsManager.Manager;
import GameConfiguration.GameConfig;
import Levels.StartingLevelAnimation.Jungle.JL0;
import MainGamePlay.Tools.GameOverScreenJL0;
import MainGamePlay.jungle.jungleLevel0;
import MainGamePlay.jungle.jungleLevel1;
import Settings.SettingPref;


public class jungleLevels extends AbstractGameScreen {

    private OrthographicCamera orthographicCamera;
    private FitViewport fitViewport;
    private Stack stack;
    private Stage stage;
    private Sound buttonClickSound;
    private SpriteBatch batch;
    private Manager manager = Manager.managerInstance;
    private SettingPref settingPref = SettingPref.instance;
    private Skin buttonSkin, BG;
    private ImageTextButton imageTextButton1, imageTextButton2, imageTextButton3,
            imageTextButton4, imageTextButton5, imageTextButton6, nextLevel;
    private Table table1, table2, table3, table4;
    private Image s1, s2, s3, bg;
    private ImageButton back;
    private Music jungleMusic;
    private String lock_level = "Locked";

    private void playMusic() {
        if (settingPref.music == true) {
            jungleMusic.setVolume(settingPref.musicVolume);
            jungleMusic.setLooping(true);
            jungleMusic.play();
        } else if (settingPref.music == false) {
            jungleMusic.setLooping(false);
            jungleMusic.stop();
        }
    }

    protected Table setBG() {
        table1 = new Table();
        bg = new Image(BG, "jBG");
        table1.add(bg);
        return table1;
    }

    protected Table setButtons() {
        settingPref.loadData();
        table2 = new Table();
        table2.columnDefaults(0).padRight(20);
        table2.columnDefaults(1).padRight(30);
        table2.columnDefaults(2).padRight(40);
        table2.left().top();

        imageTextButton1 = new ImageTextButton("1", buttonSkin, "level");
        table2.add(imageTextButton1);
        imageTextButton1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (settingPref.sound == true) {
                    buttonClickSound.play(settingPref.soundVolume);
                } else if (settingPref.sound == false) {
                    buttonClickSound.stop();
                }
                if (settingPref.music) {
                    jungleMusic.setLooping(false);
                    jungleMusic.stop();
                }

                ((Game) Gdx.app.getApplicationListener()).setScreen(new JL0());
                ownDispose();
            }
        });

        if (settingPref.high_score >= 20 || settingPref.high_score1 >= 20 || settingPref.high_score2 >= 20 ||
                settingPref.high_score3 >= 20) {
            lock_level = "2";
        }

        imageTextButton2 = new ImageTextButton(lock_level, buttonSkin, "level");
        imageTextButton2.setColor(Color.WHITE);
        table2.add(imageTextButton2);
        if (settingPref.high_score >= 20 || settingPref.high_score1 >= 20 || settingPref.high_score2 >= 20 ||
                settingPref.high_score3 >= 20) {
            imageTextButton2.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    if (settingPref.sound == true) {
                        buttonClickSound.play(settingPref.soundVolume);
                    } else if (settingPref.sound == false) {
                        buttonClickSound.stop();
                    }
                    if (settingPref.music) {
                        jungleMusic.setLooping(false);
                        jungleMusic.stop();
                    }
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new jungleLevel1());
                    ownDispose();
                }
            });
        }

        imageTextButton3 = new ImageTextButton("", buttonSkin, "level");
        table2.add(imageTextButton3);
        imageTextButton4 = new ImageTextButton("", buttonSkin, "level");
        table2.add(imageTextButton4);
        imageTextButton5 = new ImageTextButton("", buttonSkin, "level");
        table2.add(imageTextButton5);
        imageTextButton6 = new ImageTextButton("", buttonSkin, "level");
        table2.add(imageTextButton6).row();
        s1 = new Image(buttonSkin, "star");
        settingPref.loadData();
        if (settingPref.changeStar1Color) {
            s1.setColor(Color.RED);
        } else {
            s1.setColor(Color.CLEAR);
        }
        table2.add(s1);
        s2 = new Image(buttonSkin, "star");
        s2.setColor(Color.CLEAR);
        table2.add(s2);
        return table2;
    }

    protected Table setNextLevel() {
        table3 = new Table();
        nextLevel = new ImageTextButton("00", buttonSkin, "nextLevel");
        nextLevel.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        table3.bottom().right();
        table3.add(nextLevel);
        return table3;
    }

    protected Table setBack() {
        table4 = new Table();
        back = new ImageButton(buttonSkin, "back");
        back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (settingPref.sound == true) {
                    buttonClickSound.play(settingPref.soundVolume);
                } else if (settingPref.sound == false) {
                    buttonClickSound.stop();
                }
                jungleMusic.stop();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelLists());
                ownDispose();
            }
        });
        table4.padBottom(-600f);
        table4.padLeft(-700f);
        table4.add(back);
        return table4;
    }

    protected void setItems() {
        buttonSkin = manager.assetManager.get(manager.subLevelUiSkin, Skin.class);
        BG = manager.assetManager.get(manager.BG3Json, Skin.class);
        buttonClickSound = manager.assetManager.get(manager.button_click, Sound.class);
        Table background = setBG();
        Table button = setButtons();
        Table backP = setBack();
        Table nextL = setNextLevel();
        stack.add(background);
        stack.add(button);
        stack.add(nextL);
        stack.add(backP);
    }

    @Override
    public void show() {
        buttonClickSound = manager.assetManager.get(manager.button_click, Sound.class);
        jungleMusic = manager.assetManager.get(manager.jungleSoundEffect, Music.class);
        batch = new SpriteBatch();
        orthographicCamera = new OrthographicCamera();
        fitViewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT, orthographicCamera);
        stage = new Stage(fitViewport, batch);
        stack = new Stack();
        stage.clear();
        stack.clear();
        stage.addActor(stack);

        setItems();
        playMusic();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // to  clear window before render items
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        orthographicCamera.update();
        batch.setProjectionMatrix(stage.getCamera().combined);
        stage.act(Gdx.graphics.getDeltaTime());
        Gdx.input.setInputProcessor(stage);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        orthographicCamera.setToOrtho(false, GameConfig.WIDTH, GameConfig.HEIGHT);
        stack.setSize(GameConfig.WIDTH, GameConfig.HEIGHT);
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
        batch.dispose();
        stage.dispose();
        buttonClickSound.dispose();
        buttonSkin.dispose();
        jungleMusic.dispose();
    }

    public void ownDispose() {
        batch.dispose();
        stage.dispose();
    }

} // jungle levels ui design class

