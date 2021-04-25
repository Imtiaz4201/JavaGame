package ScoreBoard;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;


import java.util.Locale;

import AbstractClass.AbstractGameScreen;
import AssetsManager.Manager;
import GameConfiguration.GameConfig;
import Levels.jungleLevels;
import MenuWidget.Menu;
import Settings.SettingPref;


public class Score extends AbstractGameScreen {
    private SpriteBatch batch;
    private Stage stage;
    private Stack stack;
    private OrthographicCamera orthographicCamera;
    private Image bgImage, Logo, SF;
    private Texture bgTexture, logoTexture, SFTexture;
    private FitViewport fitViewport;
    private Label name, score, showName, showScore, showName1, showScore1, showName2, showScore2, showName3, showScore3;
    private Skin items;
    private Manager manager = Manager.managerInstance;
    private SettingPref settingPref = SettingPref.instance;
    private Sound buttonClick;
    private Music music1;
    private String nam;
    private int scores;
    private ImageTextButton back, reset;

    public Score() {
        items = manager.assetManager.get(manager.settingsSkin, Skin.class);
        bgTexture = manager.assetManager.get(manager.gameOverBG, Texture.class);
        logoTexture = manager.assetManager.get(manager.scoreLogo, Texture.class);
        SFTexture = manager.assetManager.get(manager.scoreFrame, Texture.class);
        buttonClick = manager.assetManager.get(manager.button_click, Sound.class);
    }

    public void playAudio() {
        SettingPref settingPref = SettingPref.instance;
        settingPref.loadData();
        music1 = manager.assetManager.get(manager.menuMusic, Music.class);
        if (settingPref.music == true) {
            music1.setLooping(true);
            music1.setVolume(settingPref.musicVolume);
            music1.play();
        } else if (settingPref.music == false) {
            music1.setLooping(false);
            music1.stop();
        }
    }

    public Table setBG() {
        Table table = new Table();
        bgImage = new Image(bgTexture);
        table.add(bgImage);
        return table;
    }

    public Table setLogo() {
        Table table = new Table();
        Logo = new Image(logoTexture);
        table.add(Logo).size(150, 100).padTop(-600);
        return table;
    }

    public Table setFrame() {
        Table table = new Table();
        SF = new Image(SFTexture);
        table.add(SF).size(800, 500);
        return table;
    }

    public Table setButton() {
        Table table = new Table();
        reset = new ImageTextButton("Reset", items, "default");
        reset.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (settingPref.sound == true) {
                        buttonClick.play(settingPref.soundVolume);
                    } else if (settingPref.sound = false) {
                        buttonClick.stop();
                    }
                    erasePlayerInfo();
                    resetStarColor();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        back = new ImageTextButton("Back", items, "default");
        back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(Actions.sequence(
                        Actions.alpha(0), Actions.fadeOut(0),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (settingPref.sound == true) {
                                        buttonClick.play(settingPref.soundVolume);
                                    } else if (settingPref.sound = false) {
                                        buttonClick.stop();
                                    }
                                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Menu());
                                    ownDispose();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                ));
            }
        });

        table.add(back).space(40).padBottom(-600);
        table.add(reset).padBottom(-600);
        return table;
    }

    public void erasePlayerInfo() {
        settingPref.user_name = "";
        settingPref.high_score = 0;
        settingPref.user_name1 = "";
        settingPref.high_score1 = 0;
        settingPref.user_name2 = "";
        settingPref.high_score2 = 0;
        settingPref.user_name3 = "";
        settingPref.high_score3 = 0;
        settingPref.bn = false;
        settingPref.bn1 = false;
        settingPref.bn2 = false;
        settingPref.bn3 = false;
        settingPref.block_user1 = false;
        settingPref.block_user2 = false;
        settingPref.block_user3 = false;
        settingPref.block_user4 = false;
        settingPref.score = 0;
        settingPref.saveData();
    }

    public void resetStarColor() {
        settingPref.loadData();
        settingPref.changeStar1Color = false;
        settingPref.saveData();
    }

    public Table setItems() {
        settingPref.loadData();
        Table table = new Table();
        name = new Label("Name", items, "title");
        name.setColor(Color.WHITE);
        score = new Label("Score", items, "title");
        score.setColor(Color.WHITE);
        table.add(name).space(100).padTop(-400);
        table.add(score).padTop(-400);
        return table;
    }

    public Table setPlayerDetails() {
        Table table = new Table();
        settingPref.loadData();

        showName = new Label(settingPref.user_name, items, "title");
        showScore = new Label(String.format(Locale.US, "%03d", settingPref.high_score), items, "title");
        showName.setFontScale(1);
        showScore.setFontScale(1);
        table.add(showName).pad(-280, 0, 0, 100);
        table.add(showScore).pad(-280, 50, 0, 0).row();

        showName1 = new Label(settingPref.user_name1, items, "title");
        showScore1 = new Label(String.format(Locale.US, "%03d", settingPref.high_score1), items, "title");
        showName1.setFontScale(1);
        showScore1.setFontScale(1);
        table.add(showName1).pad(-100, 0, 0, 100);
        table.add(showScore1).pad(-100, 50, 0, 0).row();

        showName2 = new Label(settingPref.user_name2, items, "title");
        showScore2 = new Label(String.format(Locale.US, "%03d", settingPref.high_score2), items, "title");
        showName2.setFontScale(1);
        showScore2.setFontScale(1);
        table.add(showName2).pad(0, 0, -50, 100);
        table.add(showScore2).pad(0, 50, -50, 0).row();

        showName3 = new Label(settingPref.user_name3, items, "title");
        showScore3 = new Label(String.format(Locale.US, "%03d", settingPref.high_score3), items, "title");
        showName3.setFontScale(1);
        showScore3.setFontScale(1);
        table.add(showName3).pad(0, 0, -200, 100);
        table.add(showScore3).pad(0, 50, -200, 0);

        return table;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        orthographicCamera = new OrthographicCamera();
        fitViewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT, orthographicCamera);
        stage = new Stage(fitViewport);
        stage.clear();
        stack = new Stack();
        Gdx.input.setInputProcessor(stage);
        stack.clear();
        stage.addActor(stack);

        Table BG = setBG();
        Table LOGO = setLogo();
        Table FRAME = setFrame();
        Table ITEMS = setItems();
        Table BUTTONS = setButton();
        Table PlayerDetails = setPlayerDetails();
        stack.add(BG);
        stack.add(LOGO);
        stack.add(FRAME);
        stack.add(ITEMS);
        stack.add(BUTTONS);
        stack.add(PlayerDetails);

        playAudio();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // to  clear window before render items
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        orthographicCamera.update();// will update the camera per frame call
        batch.setProjectionMatrix(stage.getCamera().combined);// taking camera from stage and combining to batch
        stage.act(Gdx.graphics.getDeltaTime()); // setting the delta time of screen to stage
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stack.setSize(GameConfig.WIDTH, GameConfig.HEIGHT);
        orthographicCamera.setToOrtho(false, GameConfig.WIDTH, GameConfig.HEIGHT);
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
        stage.dispose();
        bgTexture.dispose();
        logoTexture.dispose();
        music1.pause();
        buttonClick.dispose();
    }

    public void ownDispose() {
        stage.dispose();
        batch.dispose();
    }
}
