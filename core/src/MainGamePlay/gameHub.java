package MainGamePlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Locale;

import AssetsManager.Manager;
import GameConfiguration.GameConfig;
import MainGamePlay.AllSpritesItemsForJL0.Items.ItemsSprite;
import MainGamePlay.Player2.PlayerClass2;
import Settings.SettingPref;

public class gameHub implements Disposable {
    public static Stage stage;
    private FitViewport fitViewport;
    private OrthographicCamera orthographicCamera;
    private static Integer totalCoinCollected;
    private static Integer totalArrowAmount;
    private Table table;
    private static Label totalCoins, totalArrow;
    private static Image life1, life2, life3, life4, life5, arrowAmmo;
    private Manager manager = Manager.managerInstance;
    private static SettingPref settingPref = SettingPref.instance;
    private static Skin hubSkin1, hubSkin2, controlDialog;
    private ImageTextButton menuOption;
    private Texture texture;
    private static Timer timer;
    private static boolean exitGame = false;


    public gameHub(SpriteBatch batch) {
        totalCoinCollected = 0;
        totalArrowAmount = 5;

        orthographicCamera = new OrthographicCamera();
        fitViewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT, orthographicCamera);
        stage = new Stage(fitViewport, batch);
        Gdx.input.setInputProcessor(stage);
        hubSkin1 = manager.assetManager.get(manager.lifeSkin, Skin.class);
        hubSkin2 = manager.assetManager.get(manager.settingsSkin, Skin.class);
        texture = manager.assetManager.get(manager.arrowAmmo, Texture.class);
        controlDialog = manager.assetManager.get(manager.ControlInfo, Skin.class);
        Table hubItems = setHubItems();
        stage.addActor(hubItems);

        timer = new Timer();

    }

    public gameHub() {

    }


    private Table setHubItems() {
        table = new Table();
        table.top();
        table.columnDefaults(0).padRight(5);
        table.setFillParent(true); // table will fill the full screen
        arrowAmmo = new Image(texture);
        totalArrow = new Label(String.format(Locale.US, "%02d", totalArrowAmount), hubSkin2, "title");
        life1 = new Image(hubSkin1, "life");
        life2 = new Image(hubSkin1, "life");
        life3 = new Image(hubSkin1, "life");
        life4 = new Image(hubSkin1, "life");
        life5 = new Image(hubSkin1, "life");
        totalCoins = new Label(String.format(Locale.US, "%03d", totalCoinCollected), hubSkin2, "title");
        totalCoins.setColor(Color.WHITE);
        menuOption = new ImageTextButton("Menu", hubSkin2, "default");
        menuOption.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                inGameMenu();
            }
        });

        table.add(arrowAmmo);
        table.add(totalArrow).space(20);
        table.add(life1);
        table.add(life2);
        table.add(life3);
        table.add(life4);
        table.add(life5).padRight(50);
        table.add(totalCoins).padRight(20);
        table.add(menuOption);

        return table;
    }

    public static class menuDialog extends Dialog {
        // in game options

        {
            button("Settings", "st");
            button("Resume", "re");
            button("Exit", "exit");
        }

        @Override
        protected void result(Object object) {


        }

        public menuDialog(String title, Skin skin) {
            super(title, skin);
        }

        public menuDialog(String title, Skin skin, String windowStyleName) {
            super(title, skin, windowStyleName);
        }

        public menuDialog(String title, WindowStyle windowStyle) {
            super(title, windowStyle);
        }
    }

    public void inGameMenu() {
        final Dialog dialog = new Dialog("", hubSkin2, "small");
        dialog.setPosition(200, 200);
        dialog.setSize(500f, 500f);
        final TextButton exit = new TextButton("Exit", hubSkin2, "default");
        final TextButton resume = new TextButton("Resume", hubSkin2, "default");
        dialog.add(exit);
        dialog.add(resume);
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                exitGame = true;
            }
        });
        resume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
                dialog.cancel();
            }
        });
        dialog.show(stage);
    }

    public static boolean getExitGame() {
        return exitGame;
    }

    public static void resetExitGame() {
        exitGame = false;
    }

    public static class GameOver extends Dialog {

        public GameOver(String title, Skin skin) {
            super(title, skin);
        }

        public GameOver(String title, Skin skin, String windowStyleName) {
            super(title, skin, windowStyleName);
        }

        public GameOver(String title, WindowStyle windowStyle) {
            super(title, windowStyle);
        }
    }

    public static void callArrowFinishedMessage() {
        final ArrowFinishedMessage arrowFinishedMessage = new ArrowFinishedMessage("", controlDialog, "arrowFinished");
        stage.addActor(arrowFinishedMessage);
        arrowFinishedMessage.setSize(300, 150);
        arrowFinishedMessage.setPosition(300, 260);

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                arrowFinishedMessage.hide();
            }
        }, .5f);
        timer.start();
    }

    public static class ArrowFinishedMessage extends Dialog {

        @Override
        protected void result(Object object) {

        }

        public ArrowFinishedMessage(String title, Skin skin) {
            super(title, skin);
        }

        public ArrowFinishedMessage(String title, Skin skin, String windowStyleName) {
            super(title, skin, windowStyleName);
        }

        public ArrowFinishedMessage(String title, WindowStyle windowStyle) {
            super(title, windowStyle);
        }
    }

    private static class GameOverDialog extends Dialog {

        public GameOverDialog(String title, Skin skin) {
            super(title, skin);
        }

        public GameOverDialog(String title, Skin skin, String windowStyleName) {
            super(title, skin, windowStyleName);
        }

        public GameOverDialog(String title, WindowStyle windowStyle) {
            super(title, windowStyle);
        }
    }

    public static void callControlInstruction() {
        final ControlInstruction controlInstruction = new ControlInstruction("", controlDialog, "ControllInfo");
        stage.addActor(controlInstruction);
        controlInstruction.setSize(500, 400);
        controlInstruction.setPosition(250, 100);

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                controlInstruction.hide();
            }
        }, 5f);
        timer.start();
    }


    public static class ControlInstruction extends Dialog {

        public ControlInstruction(String title, Skin skin) {
            super(title, skin);
        }

        public ControlInstruction(String title, Skin skin, String windowStyleName) {
            super(title, skin, windowStyleName);
        }

        public ControlInstruction(String title, WindowStyle windowStyle) {
            super(title, windowStyle);
        }
    }


    public static void addScore(int score) {
        totalCoinCollected += score;
        settingPref.addScore(score);
        settingPref.saveData();
        totalCoins.setText(String.format(Locale.US, "%03d", totalCoinCollected));
    }

    public static void saveScore() {
        // to save score
        settingPref.loadData();
//        if (!settingPref.user_name.equals("")) {
//            if (totalCoinCollected > settingPref.high_score) {
//                settingPref.high_score = totalCoinCollected;
//            }
//        }
        if (settingPref.block_user1) {
            if (totalCoinCollected > settingPref.high_score) {
                settingPref.high_score = totalCoinCollected;
            }

        } else if (settingPref.block_user2) {
            if (totalCoinCollected > settingPref.high_score1) {
                settingPref.high_score1 = totalCoinCollected;
            }
        } else if (settingPref.block_user3) {
            if (totalCoinCollected > settingPref.high_score2) {
                settingPref.high_score2 = totalCoinCollected;
            }
        } else if (settingPref.block_user4) {
            if (totalCoinCollected > settingPref.high_score2) {
                settingPref.high_score2 = totalCoinCollected;
            }
        }
        settingPref.saveData();
    }

    public static void subArrow(int subArrow) {
        totalArrowAmount -= subArrow;
        totalArrow.setText(String.format(Locale.US, "%02d", totalArrowAmount));
    }

    public static void addArrow(int addArrow) {
        totalArrowAmount += addArrow;
        totalArrow.setText(String.format(Locale.US, "%02d", totalArrowAmount));
    }

    public static void destroyHealthForPlayer(int num) {
        if (num == 4) {
            life5.setVisible(false);
        } else if (num == 3) {
            life4.setVisible(false);
        } else if (num == 2) {
            life3.setVisible(false);
        } else if (num == 1) {
            life2.setVisible(false);
        } else if (num == 0) {
            life1.setVisible(false);
        } else if (num == -5) {
            life1.setVisible(false);
            life2.setVisible(false);
            life3.setVisible(false);
            life4.setVisible(false);
            life5.setVisible(false);
        }
    }

    public static void callGameOverDialog() {
        final GameOverDialog gameOverDialog = new GameOverDialog("", controlDialog, "GameOverMsg");
        stage.addActor(gameOverDialog);
        gameOverDialog.setSize(300, 200);
        gameOverDialog.setPosition(250, 400);

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                gameOverDialog.hide();
            }
        }, 1f);
        timer.start();
    }

    public static Integer getTotalArrowAmount() {
        return totalArrowAmount;
    }

    @Override
    public void dispose() {
        stage.dispose();
        hubSkin1.dispose();
        hubSkin2.dispose();
        texture.dispose();
    }

}
