package Settings;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AddListenerAction;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import AssetsManager.Manager;
import GameConfiguration.GameConfig;
import AbstractClass.AbstractGameScreen;
import Settings.SettingPref;
import MenuWidget.Menu;
import AssetsManager.Manager.*;

public class SettingOptions extends AbstractGameScreen {
    private Stage stage;
    private Skin BGSkin, itemsSkin;
    private OrthographicCamera orthographicCamera;
    private Stack stack;
    private FitViewport fitViewport;
    private SpriteBatch batch;
    private Table table;
    private Image BGImage;
    private Pixmap cursor;
    private Label audioLabel, buttonsLabel, soundText, musicText, sfxText;
    private CheckBox soundCheck, musicCheck, sfxCheck;
    private Slider soundSlider, musicSlider, sfxSlider;
    private ImageTextButton saveButton, creditButton, exitButton, loginButton;
    private TextField textField;
    private Menu menu;
    private Manager manager = Manager.managerInstance;
    private Sound buttonClick, dialogOpen, dialogClose;
    private SettingPref settingPref = SettingPref.instance;
    private boolean saveName = false;
    private String UserName = "";
    private boolean stop = true;

    protected Table setBG() {
        BGImage = new Image(BGSkin, "BG");
        table = new Table();
        table.add(BGImage);
        return table;
    }

    protected Table setItems() {
        // items settings
        table = new Table();
        table.columnDefaults(0).padRight(20);
        table.columnDefaults(1).padRight(30);
        table.columnDefaults(2).padRight(40);

        audioLabel = new Label("Audio", itemsSkin, "default");
        table.add(audioLabel).colspan(3);
        table.row().padBottom(10f);

        soundCheck = new CheckBox("", itemsSkin, "default");
        soundText = new Label("Sound", itemsSkin, "default");
        soundSlider = new Slider(0.0f, 1.0f, 0.1f, false, itemsSkin, "default-horizontal");
        table.row().padBottom(10f);
        table.add(soundText);
        table.add(soundCheck);
        table.add(soundSlider);

        musicText = new Label("Music", itemsSkin, "default");
        musicCheck = new CheckBox("", itemsSkin, "default");
        musicSlider = new Slider(0.0f, 1.0f, 0.1f, false, itemsSkin, "default-horizontal");
        table.row().padBottom(10f);
        table.add(musicText);
        table.add(musicCheck);
        table.add(musicSlider);

        sfxText = new Label("SFX", itemsSkin, "default");
        sfxCheck = new CheckBox("", itemsSkin, "default");
        sfxSlider = new Slider(0.0f, 1.0f, 0.1f, false, itemsSkin, "default-horizontal");
        table.row().padBottom(10f);
        table.add(sfxText);
        table.add(sfxCheck);
        table.add(sfxSlider);

        buttonsLabel = new Label("Button", itemsSkin, "default");
        table.row().padBottom(10);
        table.add(buttonsLabel).colspan(3);

        saveButton = new ImageTextButton("Save", itemsSkin, "default");
        saveButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (settingPref.sound == true) {
                    buttonClick.play(settingPref.soundVolume);
                } else if (settingPref.sound == false) {
                    buttonClick.stop();
                }
                saveValue();
            }
        });
        table.row();
        table.add(saveButton);

        creditButton = new ImageTextButton("Credit", itemsSkin, "default");
        creditButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (settingPref.sound == true) {
                    dialogOpen.play(settingPref.soundVolume);
                } else if (settingPref.sound == false) {
                    dialogOpen.stop();
                }
                callCreditClass();
            }
        });
        table.add(creditButton);

        loginButton = new ImageTextButton("Login", itemsSkin, "default");
        loginButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (settingPref.sound == true) {
                    dialogOpen.play(settingPref.soundVolume);
                } else if (settingPref.sound == false) {
                    dialogOpen.stop();
                }
                callLoginClass();
            }
        });
        table.add(loginButton);

        exitButton = new ImageTextButton("Exit", itemsSkin, "default");
        exitButton.setColor(1, 0, 0, 1);
        exitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(Actions.sequence(
                        Actions.fadeOut(0),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                if (settingPref.sound == true) {
                                    buttonClick.play(settingPref.soundVolume);
                                } else if (settingPref.sound == false) {
                                    buttonClick.stop();
                                }
                                stop = true;
                                ((Game) Gdx.app.getApplicationListener()).setScreen(new Menu());
                                ownDispose();
                            }
                        })
                ));
            }
        });
        table.row();
        table.add(exitButton).colspan(3);

        return table;
    }

    protected void setSkin() {

        // pack for background
        BGSkin = manager.assetManager.get(manager.BG2Json, Skin.class);
        // pack for items
        itemsSkin = manager.assetManager.get(manager.settingsSkin, Skin.class);

        stack = new Stack();
        stage.clear(); // clear stage b4 displaying items
        stack.clear();
        stage.addActor(stack);

        // adding item func to reference
        Table backGroundImageTable = setBG();
        Table itemsTable = setItems();

        //adding table to stack
        stack.add(backGroundImageTable);
        stack.add(itemsTable);
    }

    private void loadValue() {
        SettingPref settingPref = SettingPref.instance;
        settingPref.loadData();
        musicCheck.setChecked(settingPref.music);
        musicSlider.setValue(settingPref.musicVolume);
        soundCheck.setChecked(settingPref.sound);
        soundSlider.setValue(settingPref.soundVolume);
        sfxCheck.setChecked(settingPref.sfx);
        sfxSlider.setValue(settingPref.sfxVolume);
    }

    private void saveValue() {
        SettingPref settingPref = SettingPref.instance;
        settingPref.music = musicCheck.isChecked();
        settingPref.musicVolume = musicSlider.getValue();
        settingPref.soundVolume = soundSlider.getValue();
        settingPref.sound = soundCheck.isChecked();
        settingPref.sfx = sfxCheck.isChecked();
        settingPref.sfxVolume = sfxSlider.getValue();
        settingPref.saveData();
    }


    @Override
    public void show() {

        buttonClick = manager.assetManager.get(manager.button_click, Sound.class);
        dialogOpen = manager.assetManager.get(manager.dialogOpen, Sound.class);
        dialogClose = manager.assetManager.get(manager.dialogClose, Sound.class);
        cursor = new Pixmap(Gdx.files.internal("Backgrounds/cursor.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursor, 0, 0));

        batch = new SpriteBatch();
        orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, GameConfig.WIDTH, GameConfig.HEIGHT);// setting the width/height of camera
        fitViewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT, orthographicCamera);
        stage = new Stage(fitViewport, batch);
        Gdx.input.setInputProcessor(stage);
        stage.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeIn(1)));

        setSkin();
        loadValue();
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


    protected void callCreditClass() {
        Credit credit = new Credit("", itemsSkin, "small");
        credit.setCloseSound(dialogClose);
        credit.setSize(500, 600);
        credit.setPosition(250, 200);
        stage.addActor(credit);
    }

    protected void callLoginClass() {
        final Dialog dialog = new Dialog("", itemsSkin, "small");
        dialog.setPosition(200, 200);
        dialog.setSize(500f, 500f);
        final TextButton okButton = new TextButton("OK", itemsSkin, "default");
        final TextButton closeButton = new TextButton("Close", itemsSkin, "default");
        final TextField nameField = new TextField("", itemsSkin, "default");
        nameField.setMaxLength(8);
        dialog.add(nameField).left().row();
        dialog.add(okButton);
        dialog.add(closeButton);
        nameField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                UserName = nameField.getText();
            }
        });
        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (settingPref.sound == true) {
                    dialogClose.play(settingPref.soundVolume);
                } else if (settingPref.sound == false) {
                    dialogClose.stop();
                }
                settingPref.loadData();
                if (!settingPref.bn) {
                    settingPref.user_name = UserName;
                    settingPref.bn = true;
                    settingPref.block_user1 = true;
                    stop = false;
                    settingPref.saveData();
                    dialog.hide();
                    dialog.cancel();
                } else if (!settingPref.bn1 && settingPref.bn == true && stop) {
                    settingPref.user_name1 = UserName;
                    settingPref.bn1 = true;
                    settingPref.block_user2 = true;
                    settingPref.block_user1 = false;
                    stop = false;
                    settingPref.saveData();
                    dialog.hide();
                    dialog.cancel();
                } else if (!settingPref.bn2 && settingPref.bn == true && settingPref.bn1 == true && stop) {
                    settingPref.user_name2 = UserName;
                    settingPref.bn2 = true;
                    settingPref.block_user3 = true;
                    settingPref.block_user1 = false;
                    settingPref.block_user2 = false;
                    stop = false;
                    settingPref.saveData();
                    dialog.hide();
                    dialog.cancel();
                } else if (!settingPref.bn3 && settingPref.bn == true && settingPref.bn1 == true && settingPref.bn2 == true &&
                        stop) {
                    settingPref.user_name3 = UserName;
                    settingPref.bn3 = true;
                    settingPref.block_user4 = true;
                    settingPref.block_user1 = false;
                    settingPref.block_user2 = false;
                    settingPref.block_user3 = false;
                    stop = false;
                    settingPref.saveData();
                    dialog.hide();
                    dialog.cancel();
                }

            }
        });
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (settingPref.sound == true) {
                    dialogClose.play(settingPref.soundVolume);
                } else if (settingPref.sound == false) {
                    dialogClose.stop();
                }
                dialog.hide();
                dialog.cancel();
            }
        });
        dialog.show(stage);
    }

    public static class Credit extends Dialog {
        // dialog box to display the creator of the game
        private SettingPref settingPref = SettingPref.instance;
        private Sound closeSound;

        {
            text("Programme: Imtiaz Ahmad\nDesign: Annoor Mahdi Anan");
            button("Close", "cls");
        }

        protected void setCloseSound(Sound close) {
            closeSound = close;
        }

        protected Sound getCloseSound() {
            return closeSound;
        }

        @Override
        protected void result(Object object) {
            super.result(object);
            if (object.equals("cls")) {
                if (settingPref.sound == true) {
                    getCloseSound().play(settingPref.soundVolume);
                } else if (settingPref.sound == false) {
                    getCloseSound().stop();
                }
            }
        }

        public Credit(String title, Skin skin) {
            super(title, skin);
        }

        public Credit(String title, Skin skin, String windowStyleName) {
            super(title, skin, windowStyleName);
        }

        public Credit(String title, WindowStyle windowStyle) {
            super(title, windowStyle);
        }
    }


    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        cursor.dispose();
        BGSkin.dispose();
        itemsSkin.dispose();
        buttonClick.dispose();
        dialogOpen.dispose();
        dialogClose.dispose();
    }

    public void ownDispose() {
        batch.dispose();
        stage.dispose();
        cursor.dispose();
    }

} // setting options class
