package MenuWidget;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Random;

import GameConfiguration.GameConfig;
import Settings.SettingOptions;
import Levels.LevelLists;
import AbstractClass.AbstractGameScreen;
import ScoreBoard.Score;
import AssetsManager.Manager;
import Settings.SettingPref;

public class Menu extends AbstractGameScreen {
    private SpriteBatch batch;
    private TextureAtlas buttonAtlasPack, dialogAtlasPack, cloudsAtlasPAck, planeAtlasPack;
    private TextButton.TextButtonStyle textButtonStyle;
    private Stage stage, stage2;
    private Skin BGSkin, buttonSkin, dialogSkin, cloudsSkin, planesSkin; // A skin is a collection of resources which are used to style and display UI widgets/like css
    private Table table;
    private Stack stack;
    private Image MenuBG, c1, c2, c3, c4, c5, plane1;
    private ImageButton playButton, exitButton, settingButton, scoreButton;
    private BitmapFont blackFont, redFont;
    private Label label;
    private Label.LabelStyle labelStyle;
    private OrthographicCamera menuOrthoGraphicCamera;
    private FitViewport menuViewPort;
    private Sound buttonClick;
    private Music music1;
    private Timer timer;
    private Pixmap cursor;
    private Manager manager = Manager.managerInstance;
    private SettingPref settingPref = SettingPref.instance;

    protected Table setBGImage() {
        // setting BG image to Image class and returning Table
        MenuBG = new Image(BGSkin, "menuBG");
        table = new Table();
        return table;
    }

    protected void animatedClouds() {
        // animated cloud1
        c1 = new Image(cloudsSkin, "cloud1");
        c1.setPosition(GameConfig.WIDTH, GameConfig.HEIGHT - 500);
        float pos_x_c1 = Gdx.graphics.getWidth() - 1200;
        float pos_y_c1 = Gdx.graphics.getHeight() - c1.getHeight();
        c1.addAction(Actions.forever(Actions.sequence(
                Actions.moveTo(pos_x_c1, pos_y_c1, 10f),
                Actions.fadeOut(0),
                Actions.moveTo(GameConfig.WIDTH, GameConfig.HEIGHT - 500, 1),
                Actions.fadeIn(1)
        )));

        //animated cloud2
        c2 = new Image(cloudsSkin, "cloud2");
        c2.setPosition(GameConfig.WIDTH, GameConfig.HEIGHT - 700);
        float pos_x_c2 = Gdx.graphics.getWidth() - 1200;
        float pos_y_c2 = Gdx.graphics.getHeight() - c2.getHeight();
        c2.addAction(Actions.forever(Actions.sequence(
                Actions.moveTo(pos_x_c2, pos_y_c2, 17f),
                Actions.fadeOut(0),
                Actions.moveTo(GameConfig.WIDTH, GameConfig.HEIGHT - 750, 1),
                Actions.fadeIn(1)
        )));

        //animated cloud 3
        c3 = new Image(cloudsSkin, "cloud3");
        c3.setPosition(GameConfig.WIDTH, GameConfig.HEIGHT - 400);
        float pos_x_c3 = Gdx.graphics.getWidth() - 1200;
        float pos_y_c3 = Gdx.graphics.getHeight() - c3.getHeight();
        c3.addAction(Actions.forever(Actions.sequence(
                Actions.moveTo(pos_x_c3, pos_y_c3, 15f),
                Actions.fadeOut(0),
                Actions.moveTo(GameConfig.WIDTH, GameConfig.HEIGHT - 450, 1),
                Actions.fadeIn(1)
        )));

        //animated cloud4
        c4 = new Image(cloudsSkin, "cloud4");
        c4.setPosition(GameConfig.WIDTH, GameConfig.HEIGHT - 750);
        float pos_x_c4 = Gdx.graphics.getWidth() - 1200;
        float pos_y_c4 = Gdx.graphics.getHeight() - c4.getHeight();
        c4.addAction(Actions.forever(Actions.sequence(
                Actions.moveTo(pos_x_c4, pos_y_c4, 15f),
                Actions.fadeOut(0),
                Actions.moveTo(GameConfig.WIDTH, GameConfig.HEIGHT - 700, 1),
                Actions.fadeIn(1)
        )));


        //animated cloud5
        c5 = new Image(cloudsSkin, "cloud5");
        c5.setPosition(GameConfig.WIDTH, GameConfig.HEIGHT - 300);
        float pos_x_c5 = Gdx.graphics.getWidth() - 1200;
        float pos_y_c5 = Gdx.graphics.getHeight() - c5.getHeight();
        c5.addAction(Actions.forever(Actions.sequence(
                Actions.moveTo(pos_x_c5, pos_y_c5, 15f),
                Actions.fadeOut(0),
                Actions.moveTo(GameConfig.WIDTH, GameConfig.HEIGHT - 400, 1),
                Actions.fadeIn(1)
        )));

        stage2.addActor(c1);
        stage2.addActor(c2);
        stage2.addActor(c3);
        stage2.addActor(c4);
        stage2.addActor(c5);

    }

    protected void animtedPlanes() {
        String[] planes = {"p1", "p2", "p3", "p4"};
        Random random_plane = new Random();
        int rp = random_plane.nextInt(4);
        int random_height = random_plane.nextInt(300) + 300;

        plane1 = new Image(planesSkin
                , planes[rp]);
        plane1.setPosition(GameConfig.WIDTH, GameConfig.HEIGHT - random_height);
        float pos_x_p1 = Gdx.graphics.getWidth() - 1200;
        float pos_y_p1 = Gdx.graphics.getHeight() - plane1.getHeight();
        plane1.addAction(Actions.forever(Actions.sequence(
                Actions.moveTo(pos_x_p1, pos_y_p1, 10f)
        )));

        stage2.addActor(plane1);

    }


    protected Table message() {
        labelStyle = new Label.LabelStyle();// to apply font style to label
        labelStyle.font = blackFont;
        labelStyle.fontColor = Color.RED;
        label = new Label("BETA-v05", labelStyle);
        table = new Table();
        return table;
    }

    protected Table controlButtonSet1() {
        // TODO: still has bugs
        playButton = new ImageButton(buttonSkin, "start");
        playButton.getImage().setFillParent(true);
        playButton.getImage().setScaling(Scaling.stretch);
        playButton.addAction(
                Actions.moveTo(200, 350, 2f)
        );
        playButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(
                        Actions.sequence(Actions.fadeOut(0),
                                Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            if (settingPref.sound == true) {
                                                buttonClick.play(settingPref.soundVolume);
                                            } else if (settingPref.sound = false) {
                                                buttonClick.stop();
                                            }
                                            if (settingPref.music) {
                                                music1.setLooping(false);
                                                music1.stop();
                                            }
                                            ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelLists());
                                            ownDispose();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                })
                        )
                );
            }

        });

        exitButton = new ImageButton(buttonSkin, "buttonExit");
        exitButton.addAction(
                Actions.moveTo(330, 250, 2f)
        );
        exitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (settingPref.sound == true) {
                        buttonClick.play(settingPref.soundVolume);
                    } else if (settingPref.sound = false) {
                        buttonClick.stop();
                    }
                    callExitDialog();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        scoreButton = new ImageButton(buttonSkin, "score");
        scoreButton.addAction(
                Actions.moveTo(450, 350, 2f)
        );
        scoreButton.addListener(new ClickListener() {
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
                                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Score());
                                    ownDispose();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                ));
            }
        });

        table = new Table();
        table.padBottom(-150f);
        table.add(playButton).size(200f, 100f);
        table.add(scoreButton).size(200f, 100f);
        table.row().colspan(2);
        table.add(exitButton).size(200f, 100f).center();
        table.center();
        table.setFillParent(true);
        ;
        table.setBounds(0, 0, 100f, 100f);
        return table;
    }

    protected Table controlButtonSet2() {
        settingButton = new ImageButton(buttonSkin, "setting");
        settingButton.addAction(
                Actions.moveTo(335, 100, 2f)
        );
        settingButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(
                        Actions.sequence(
                                Actions.fadeOut(0),
                                Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            if (settingPref.sound == true) {
                                                buttonClick.play(settingPref.soundVolume);
                                            } else if (settingPref.sound = false) {
                                                buttonClick.stop();
                                            }
                                            ((Game) Gdx.app.getApplicationListener()).setScreen(new SettingOptions());
                                            ownDispose();
                                        } catch (Exception e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                })
                        )
                );
            }

        });

        table = new Table();
        table.add(settingButton).size(200f, 150f);
        table.setBounds(0, 0, 100f, 100f);
        return table;
    }


    protected void setMenuItems() {
        // pack for BG
        BGSkin = manager.assetManager.get(manager.BG4Json, Skin.class);
        // pack for button
        buttonAtlasPack = new TextureAtlas(Gdx.files.internal("MenuSkinItems/Images/buttonImages/buttonImageSkin.pack"));
        final FileHandle button = Gdx.files.internal("MenuSkinItems/button.json");
        buttonSkin = new Skin(button, buttonAtlasPack);
        blackFont = new BitmapFont(Gdx.files.internal("Fonts/text.fnt"));

        // pack for dialog
        dialogAtlasPack = new TextureAtlas(Gdx.files.internal("MenuSkinItems/Images/DialogImages/exitDialogSkin.pack"));
        final FileHandle dialog = Gdx.files.internal("MenuSkinItems/Dialog.json");
        dialogSkin = new Skin(dialog, dialogAtlasPack);

        // pack for clouds
        cloudsAtlasPAck = new TextureAtlas(Gdx.files.internal("MenuSkinItems/Images/Items/cloudsPack.pack"));
        final FileHandle items = Gdx.files.internal("MenuSkinItems/items.json");
        cloudsSkin = new Skin(items, cloudsAtlasPAck);

        //pack for planes
        planeAtlasPack = new TextureAtlas(Gdx.files.internal("MenuSkinItems/Images/Items/planeSkin.pack"));
        final FileHandle planes = Gdx.files.internal("MenuSkinItems/plane.json");
        planesSkin = new Skin(planes, planeAtlasPack);

        stack = new Stack();// adding all table in the stack
        stack.clear();
        stage.addActor(stack);// adding stack to stage as actor

        // references of functions
        Table backGroundLayerTable = setBGImage();
        Table buttonTable1 = controlButtonSet1();
        Table buttonTable2 = controlButtonSet2();


        Table messageTable = message();
        messageTable.padBottom(-650f);
        messageTable.padRight(-800f);

        // adding buttonImages/BGImages/Images to table
        backGroundLayerTable.add(MenuBG);
        messageTable.add(label);


        //adding table to stack
        stack.add(backGroundLayerTable);
        stack.add(messageTable);
        stack.add(buttonTable1);
        stack.add(buttonTable2);

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

    @Override
    public void show() { // show() func will be called when the screen is shown
        buttonClick = manager.assetManager.get(manager.button_click, Sound.class);
        cursor = new Pixmap(Gdx.files.internal("Backgrounds/cursor.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursor, 0, 0));
        batch = new SpriteBatch();
        menuOrthoGraphicCamera = new OrthographicCamera();
        menuViewPort = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT, menuOrthoGraphicCamera); // viewport adjusts the images according tho  the screen
        stage = new Stage(menuViewPort, batch);
        stage2 = new Stage(menuViewPort, batch);
        stage.clear();
        stage2.clear();
        Gdx.input.setInputProcessor(stage); // make stage capable of getting events
        playAudio();
        setMenuItems();
        animatedClouds();
        timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                animtedPlanes();
            }
        }, 10, 30);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // to  clear window before render items
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        menuOrthoGraphicCamera.update();
        batch.setProjectionMatrix(stage.getCamera().combined); // combining camera with stage
        stage.act(Gdx.graphics.getDeltaTime());// update stage;
        stage.draw();

        batch.setProjectionMatrix(stage2.getCamera().combined);
        stage2.act(Gdx.graphics.getDeltaTime());
        stage2.draw();
    }

    @Override
    public void resize(int width, int height) {
        stack.setSize(GameConfig.WIDTH, GameConfig.HEIGHT);
        menuOrthoGraphicCamera.setToOrtho(false, GameConfig.WIDTH, GameConfig.HEIGHT);
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

    protected void callExitDialog() {
        // to call the inner class
        ExitDialog exitDialog = new ExitDialog("", dialogSkin);
        exitDialog.setSound(buttonClick);
        stage.addActor(exitDialog);
        exitDialog.setSize(500, 300);
        exitDialog.setPosition(250, 200);
    }


    private class ExitDialog extends Dialog {
        // inner class for exit dialog

        private Sound sound;

        {
            // static method
            text("Do you want to Exit Game?");
            button("Exit", "exit");
            button("Close", "close");
        }

        protected Sound getSound() {
            return sound;
        }

        public void setSound(Sound sound) {
            this.sound = sound;
        }

        @Override
        protected void result(Object object) {
            // this func will be called if any action is pressed
            try {
                if (object.equals("exit")) {
                    if (settingPref.sound == true) {
                        getSound().play(settingPref.soundVolume);
                    } else if (settingPref.sound = false) {
                        getSound().stop();
                    }
                    Gdx.app.exit();

                } else if (object.equals("close")) {
                    if (settingPref.sound == true) {
                        getSound().play(settingPref.soundVolume);
                    } else if (settingPref.sound = false) {
                        getSound().stop();
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
            super.result(object);
        }

        public ExitDialog(String title, Skin skin) {
            super(title, skin);
        }

        public ExitDialog(String title, Skin skin, String windowStyleName) {
            super(title, skin, windowStyleName);
        }

        public ExitDialog(String title, WindowStyle windowStyle) {
            super(title, windowStyle);
        }

    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        stage2.dispose();
        blackFont.dispose();
        redFont.dispose();
        dialogAtlasPack.dispose();
        buttonAtlasPack.dispose();
        cursor.dispose();
        BGSkin.dispose();
        dialogSkin.dispose();
        buttonSkin.dispose();
        cloudsSkin.dispose();
        cloudsAtlasPAck.dispose();
        planeAtlasPack.dispose();
        planesSkin.dispose();
        music1.dispose();
        buttonClick.dispose();
    }

    public void ownDispose() {
        batch.dispose();
        stage.dispose();
        stage2.dispose();
        blackFont.dispose();
        cursor.dispose();
    }


}// Menu class for game
