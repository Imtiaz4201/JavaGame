package MainGamePlay.jungle;

import MainGamePlay.AllSpritesItemsForJL0.EnemyClasses.EnemySpritesJL0;
import MainGamePlay.AllSpritesItemsForJL0.Items.ItemsSprite;
import MainGamePlay.Tools.GameOverScreenJL0;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import AbstractClass.AbstractGameScreen;
import AssetsManager.Manager;
import GameConfiguration.GameConfig;
import MainGamePlay.AllSpritesItemsForJL0.CoinClasses.CoinSpriteJL0;
import MainGamePlay.AllSpritesItemsForJL0.HazardsClasses.HazardSpriteJL0;
import MainGamePlay.Player2.PlayerBowClass2;
import MainGamePlay.Player2.PlayerClass2;
import MainGamePlay.Tools.ParticalEffets.CameraShake;
import MainGamePlay.gameHub;
import MainGamePlay.worldObjects.Box2dWorldObjCreatorForJL0;
import MainGamePlay.worldObjects.WordObjectsListenerJL0;
import Settings.SettingPref;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class jungleLevel0 extends AbstractGameScreen {

    private OrthographicCamera orthographicCamera;
    // tile map
    private TiledMap tileMap;
    private OrthoCachedTiledMapRenderer orthogonalTiledMapRenderer;

    private FitViewport fitViewport;
    private SpriteBatch spriteBatch;
    private MainGamePlay.gameHub gameHub;
    private PlayerClass2 playerClass2;
    private TextureAtlas mainPlayerAtlas, enemy2Atlas, hazardsSprite, groundObjects,
            coinAtlas, playerArrow, woodenLog, animatedWater, animatedFire, flyingEnemy, Springs, cloud, hiddenSnake,
            animals, duckNPC, butterFliesNPC, BirdNPC;
    private Manager manager = Manager.managerInstance;
    private Box2dWorldObjCreatorForJL0 box2dWorldObjCreatorForJL0;

    // Box2d
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer; // provides graphical  re-presentation of fixture and body
    private Array<PlayerBowClass2> playerWeapons;

    private Music levelAmbientMusic;
    private Sound arrowSound, wolfAttackSound, playerWalingSound;
    private SettingPref settingPref = SettingPref.instance;
    private Timer timer;


    public jungleLevel0() {
        spriteBatch = new SpriteBatch();
        playerWeapons = new Array<>();
        timer = new Timer();


        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                MainGamePlay.gameHub.callControlInstruction();
            }
        }, 1f);
        timer.start();
    }

    public void playLevelAmbientMusic() {
        if (settingPref.music) {
            levelAmbientMusic.setLooping(true);
            levelAmbientMusic.setVolume(settingPref.musicVolume);
            levelAmbientMusic.play();
        } else if (!settingPref.music) {
            levelAmbientMusic.setLooping(false);
            levelAmbientMusic.stop();
        }
    }

    public void playArrowSound() {
        if (settingPref.sfx) {
            arrowSound.play(settingPref.sfxVolume);
        } else if (!settingPref.sfx) {
            arrowSound.stop();
        }
    }

    public void keyboardInput(float delta) {
        // to move the main player

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !(playerClass2.playerBody.getLinearVelocity().y > 0) && playerClass2.playerOnGround) {
            playerClass2.playerBody.applyLinearImpulse(new Vector2(0, 2f), playerClass2.playerBody.getWorldCenter(), true);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && !playerClass2.playerNotOnWater && playerClass2.playerBody.getLinearVelocity().x >= -1) {
            playerClass2.playerBody.applyLinearImpulse(new Vector2(-0.08f, 0), playerClass2.playerBody.getWorldCenter(), true);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !playerClass2.playerNotOnWater && playerClass2.playerBody.getLinearVelocity().x <= 1) {
            playerClass2.playerBody.applyLinearImpulse(new Vector2(0.08f, 0), playerClass2.playerBody.getWorldCenter(), true);

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F) && MainGamePlay.gameHub.getTotalArrowAmount() != 0) {
            playArrowSound();
            shoot();
            MainGamePlay.gameHub.subArrow(1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            if (MainGamePlay.gameHub.getTotalArrowAmount() == 0) {
                MainGamePlay.gameHub.callArrowFinishedMessage();
            }
        }
    }

    public void update(float delta) {
        keyboardInput(delta);
        playLevelAmbientMusic();
        orthographicCamera.position.x = playerClass2.playerBody.getPosition().x; // move game camera according to player movement
        world.step(1 / 60f, 6, 2);

        // update main player
        playerClass2.update(delta);

        // update player weapon
        for (PlayerBowClass2 Weapon : playerWeapons) {
            Weapon.update(delta);
            if (Weapon.isDestroyed()) {
                playerWeapons.removeValue(Weapon, true);
                playerWeapons.clear();
            }
        }

        // update coin
        for (CoinSpriteJL0 coinSpriteJL0 : box2dWorldObjCreatorForJL0.getCoin1Definitions()) {
            coinSpriteJL0.update(delta);
        }

        // update star
        for (CoinSpriteJL0 coinSpriteJL0 : box2dWorldObjCreatorForJL0.getStarDefinitionJL0s()) {
            coinSpriteJL0.update(delta);
        }

        // update spike type1
        for (HazardSpriteJL0 hazardsSprite : box2dWorldObjCreatorForJL0.getSpikeType1JL0()) {
            hazardsSprite.update(delta);
        }

        // update ground enemy
        for (EnemySpritesJL0 enemySpritesJL0 : box2dWorldObjCreatorForJL0.getEnemyClass1()) {
            enemySpritesJL0.update(delta);
        }

        // update flying enemy
        for (EnemySpritesJL0 enemySpritesJL0 : box2dWorldObjCreatorForJL0.getFlyingEnemies()) {
            enemySpritesJL0.update(delta);
        }

        // update moving wooden log
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getWoodenLog()) {
            itemsSprite.update(delta);
        }
        // update animated water
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getAnimatedWaters()) {
            itemsSprite.update(delta);
        }

        // update animated fire
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getAnimatedFires()) {
            itemsSprite.update(delta);
        }

        // update animated spring jumper
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getSpringJumpers()) {
            itemsSprite.update(delta);
        }

        // update animated blade
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getBlades()) {
            itemsSprite.update(delta);
        }

        // update hidden snake
        for (EnemySpritesJL0 enemySpritesJL0 : box2dWorldObjCreatorForJL0.getHiddenSnakes()) {
            enemySpritesJL0.update(delta);
        }

        // update animated deer
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getAnimatedDeers()) {
            itemsSprite.update(delta);
        }

        // update animated rabbits
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getAnimatedRabbits()) {
            itemsSprite.update(delta);
        }

        // update animated butterflies P
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getAnimatedButterflyPurples()) {
            itemsSprite.update(delta);
        }

        // update animated butterflies R
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getAnimatedButterFlyStills()) {
            itemsSprite.update(delta);
        }

        // update animated birds
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getAnimatedBirdNPCS()) {
            itemsSprite.update(delta);
        }

    }

    @Override
    public void show() {
        tileMap = new TmxMapLoader().load("GamePlayAssets/jungleMaps/jungleLevel0/JL0.tmx");
        orthogonalTiledMapRenderer = new OrthoCachedTiledMapRenderer(tileMap, (float) 1 / GameConfig.PPM);
        orthogonalTiledMapRenderer.setBlending(true);
        orthographicCamera = new OrthographicCamera();
        fitViewport = new FitViewport((float) GameConfig.V_WIDTH / GameConfig.PPM,
                (float) GameConfig.V_HEIGHT / GameConfig.PPM, orthographicCamera);

        gameHub = new gameHub(spriteBatch);

        world = new World(new Vector2(0, -4.5f), true);// for world object velocity
        box2DDebugRenderer = new Box2DDebugRenderer();
        box2DDebugRenderer.setDrawBodies(false);
        orthographicCamera.position.set(fitViewport.getWorldWidth() / 2, fitViewport.getWorldHeight() / 2, 0);

        // loading sprites
        mainPlayerAtlas = manager.assetManager.get(manager.playerPack, TextureAtlas.class);
        hazardsSprite = manager.assetManager.get(manager.hazards, TextureAtlas.class);
        groundObjects = manager.assetManager.get(manager.groundObjects, TextureAtlas.class);
        coinAtlas = manager.assetManager.get(manager.coins, TextureAtlas.class);
        playerArrow = manager.assetManager.get(manager.playerArrow, TextureAtlas.class);
        enemy2Atlas = manager.assetManager.get(manager.enemy2Pack, TextureAtlas.class);
        animatedFire = manager.assetManager.get(manager.animatedFire, TextureAtlas.class);
        woodenLog = manager.assetManager.get(manager.woodLog, TextureAtlas.class);
        animatedWater = manager.assetManager.get(manager.animatedWater, TextureAtlas.class);
        flyingEnemy = manager.assetManager.get(manager.flyingEnemy, TextureAtlas.class);
        Springs = manager.assetManager.get(manager.springJumper, TextureAtlas.class);
        cloud = manager.assetManager.get(manager.cloud, TextureAtlas.class);
        hiddenSnake = manager.assetManager.get(manager.hiddenSnake, TextureAtlas.class);
        animals = manager.assetManager.get(manager.AnimalsPack, TextureAtlas.class);
        duckNPC = manager.assetManager.get(manager.animatedDuck, TextureAtlas.class);
        butterFliesNPC = manager.assetManager.get(manager.ButterfliesNPC, TextureAtlas.class);
        BirdNPC = manager.assetManager.get(manager.BirdNPC, TextureAtlas.class);
        // load Audio
        arrowSound = manager.assetManager.get(manager.arrowSound, Sound.class);
        levelAmbientMusic = manager.assetManager.get(manager.jLevel0, Music.class);
        wolfAttackSound = manager.assetManager.get(manager.wolfAttackSound, Sound.class);
        playerWalingSound = manager.assetManager.get(manager.runningSoundEffect, Sound.class);

        // word object ContactListener
        world.setContactListener(new WordObjectsListenerJL0());

        // for all elements in world map
        box2dWorldObjCreatorForJL0 = new Box2dWorldObjCreatorForJL0(this);

        // for Player
        playerClass2 = new PlayerClass2(world, this); // main player class object

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 187, 249, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!playerClass2.stop_camera_shake) {
            // to make the camera shake
            if (playerClass2.getCameraShake()) {
                CameraShake.shake(.01f, .01f);
            } else if (!playerClass2.getCameraShake()) {
                orthographicCamera.position.set(fitViewport.getWorldWidth() / 2, fitViewport.getWorldHeight() / 2, 0);
            }

            if (CameraShake.getRumbleTimeLeft() > 0) {
                CameraShake.tick(delta);
                orthographicCamera.translate(CameraShake.getPos());
            }
        }

        update(delta);
        // tilemap
        orthogonalTiledMapRenderer.render();
        orthogonalTiledMapRenderer.setView(orthographicCamera);
        // camera
        orthographicCamera.update();
        spriteBatch.setProjectionMatrix(gameHub.stage.getCamera().combined);

        // stage
        gameHub.stage.act(Gdx.graphics.getDeltaTime());
        gameHub.stage.draw();

        box2DDebugRenderer.render(world, orthographicCamera.combined);
        spriteBatch.setProjectionMatrix(orthographicCamera.combined);
        spriteBatch.begin();
        // render player
        if (!playerClass2.destroyed) {
            playerClass2.draw(spriteBatch);
        }
        // render player weapon
        for (PlayerBowClass2 Weapon : playerWeapons) {
            if (!Weapon.isDestroyed()) {
                Weapon.draw(spriteBatch);
            }
        }

        // render ground enemy
        for (EnemySpritesJL0 enemySpritesJL0 : box2dWorldObjCreatorForJL0.getEnemyClass1()) {
            if (!enemySpritesJL0.destroyed) {
                enemySpritesJL0.draw(spriteBatch);
            }
            if (enemySpritesJL0.getX() < playerClass2.getX() + 300f / GameConfig.PPM) {
                enemySpritesJL0.enemyBody.setActive(true);
            }
        }

        // render flying enemy
        for (EnemySpritesJL0 enemySpritesJL0 : box2dWorldObjCreatorForJL0.getFlyingEnemies()) {
            if (!enemySpritesJL0.destroyed) {
                enemySpritesJL0.draw(spriteBatch);
            }
            if (enemySpritesJL0.getX() < playerClass2.getX() + 300f / GameConfig.PPM) {
                enemySpritesJL0.enemyBody.setActive(true);
            }
        }

        // render hidden snake
        for (EnemySpritesJL0 enemySpritesJL0 : box2dWorldObjCreatorForJL0.getHiddenSnakes()) {
            if (!enemySpritesJL0.destroyed) {
                enemySpritesJL0.draw(spriteBatch);
            }
            if (enemySpritesJL0.getX() < playerClass2.getX() + 300f / GameConfig.PPM) {
                enemySpritesJL0.enemyBody.setActive(true);
            }
        }

        // render coins
        for (CoinSpriteJL0 coinSpriteJL0 : box2dWorldObjCreatorForJL0.getCoin1Definitions()) {
            if (!coinSpriteJL0.destroyed) {
                coinSpriteJL0.draw(spriteBatch);
            }
            if (playerClass2.getX() > coinSpriteJL0.getX()) {
                coinSpriteJL0.coinBody.setActive(true);
            }
        }

        // render star
        for (CoinSpriteJL0 coinSpriteJL0 : box2dWorldObjCreatorForJL0.getStarDefinitionJL0s()) {
            if (!coinSpriteJL0.destroyed) {
                coinSpriteJL0.draw(spriteBatch);
            }
            if (playerClass2.getX() > coinSpriteJL0.getX()) {
                coinSpriteJL0.coinBody.setActive(true);
            }
        }

        // render spike type1
        for (HazardSpriteJL0 hazardsSprite : box2dWorldObjCreatorForJL0.getSpikeType1JL0()) {
            hazardsSprite.draw(spriteBatch);
            if (hazardsSprite.getX() < playerClass2.getX() + 150f / GameConfig.PPM) {
                hazardsSprite.hazardBody.setActive(true);
            }
        }

        // render moving wooden log
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getWoodenLog()) {
            itemsSprite.draw(spriteBatch);
        }

        // render animated water
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getAnimatedWaters()) {
            itemsSprite.draw(spriteBatch);
            if (itemsSprite.getX() < playerClass2.getX() + 150f / GameConfig.PPM) {
                itemsSprite.itemBody.setActive(true);
            }
        }

        // render animated fire
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getAnimatedFires()) {
            itemsSprite.draw(spriteBatch);
            if (itemsSprite.getX() < playerClass2.getX() + 300f / GameConfig.PPM) {
                itemsSprite.itemBody.setActive(true);
            }
        }

        // render animated spring jumper
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getSpringJumpers()) {
            itemsSprite.draw(spriteBatch);
            if (itemsSprite.getX() < playerClass2.getX() + 300f / GameConfig.PPM) {
                itemsSprite.itemBody.setActive(true);
            }
        }

        // render animated blade
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getBlades()) {
            itemsSprite.draw(spriteBatch);
            if (itemsSprite.getX() < playerClass2.getX() + 300f / GameConfig.PPM) {
                itemsSprite.itemBody.setActive(true);
            }
        }

        // render animated deers
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getAnimatedDeers()) {
            if (!itemsSprite.destroyed) {
                itemsSprite.draw(spriteBatch);
            }
            if (itemsSprite.getX() < playerClass2.getX() + 300f / GameConfig.PPM) {
                itemsSprite.itemBody.setActive(true);
            }
        }

        // render animated rabbits
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getAnimatedRabbits()) {
            if (!itemsSprite.destroyed) {
                itemsSprite.draw(spriteBatch);
            }
            if (itemsSprite.getX() < playerClass2.getX() + 300f / GameConfig.PPM) {
                itemsSprite.itemBody.setActive(true);
            }
        }

        // render animated butterflies P
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getAnimatedButterflyPurples()) {
            if (!itemsSprite.destroyed) {
                itemsSprite.draw(spriteBatch);
            }
            if (itemsSprite.getX() < playerClass2.getX() + 300f / GameConfig.PPM) {
                itemsSprite.itemBody.setActive(true);
            }
        }

        // render animated butterflies R
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getAnimatedButterFlyStills()) {
            if (!itemsSprite.destroyed) {
                itemsSprite.draw(spriteBatch);
            }
            if (itemsSprite.getX() < playerClass2.getX() + 300f / GameConfig.PPM) {
                itemsSprite.itemBody.setActive(true);
            }
        }

        // render animated birds
        for (ItemsSprite itemsSprite : box2dWorldObjCreatorForJL0.getAnimatedBirdNPCS()) {
            if (!itemsSprite.destroyed) {
                itemsSprite.draw(spriteBatch);
            }
            if (itemsSprite.getX() < playerClass2.getX() + 300f / GameConfig.PPM) {
                itemsSprite.itemBody.setActive(true);
            }
        }
        spriteBatch.end();

        if (gameOver()) {
            MainGamePlay.gameHub.callGameOverDialog();
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    levelAmbientMusic.setLooping(false);
                    levelAmbientMusic.stop();
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverScreenJL0());
                }
            }, 2f);
            MainGamePlay.gameHub.saveScore();
            timer.start();
        }

        if (playerClass2.callGameOverText) {
            MainGamePlay.gameHub.callGameOverDialog();
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    levelAmbientMusic.setLooping(false);
                    levelAmbientMusic.stop();
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverScreenJL0());
                }
            }, 2f);
            MainGamePlay.gameHub.saveScore();
            timer.start();
        }

        if (MainGamePlay.gameHub.getExitGame()) {
            levelAmbientMusic.setLooping(false);
            levelAmbientMusic.stop();
            MainGamePlay.gameHub.resetExitGame();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverScreenJL0());
            MainGamePlay.gameHub.saveScore();
        }
    }

    public boolean gameOver() {
        if (playerClass2.destroyed) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void resize(int width, int height) {
        //orthographicCamera.setToOrtho(false, fitViewport.getWorldWidth()/2, fitViewport.getWorldHeight()/10);
        fitViewport.update(GameConfig.WIDTH, GameConfig.HEIGHT);

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

    public void shoot() {
        playerWeapons.add(new PlayerBowClass2(this, playerClass2.playerBody.getPosition().x, playerClass2.playerBody.getPosition().y, playerClass2.isRunningRight));
    }

    public World getWorld() {
        return world;
    }

    public TiledMap getTiledMap() {
        return tileMap;
    }

    public TextureAtlas getMainPlayerAtlas() {
        return mainPlayerAtlas;
    }

    public TextureAtlas getPlayerArrow() {
        return playerArrow;
    }

    public TextureAtlas getCoinAtlas() {
        return coinAtlas;
    }

    public TextureAtlas getHazardsSprite() {
        return hazardsSprite;
    }

    public TextureAtlas getEnemy2Atlas() {
        return enemy2Atlas;
    }

    public TextureAtlas getWoodenLog() {
        return woodenLog;
    }

    public TextureAtlas getAnimatedWater() {
        return animatedWater;
    }

    public TextureAtlas getAnimatedFire() {
        return animatedFire;
    }

    public TextureAtlas getFlyingEnemy() {
        return flyingEnemy;
    }

    public TextureAtlas getSprings() {
        return Springs;
    }

    public TextureAtlas getCloud() {
        return cloud;
    }

    public TextureAtlas getHiddenSnake() {
        return hiddenSnake;
    }

    public Sound getWolfAttackSound() {
        return wolfAttackSound;
    }

    public Sound getPlayerWalingSound() {
        return playerWalingSound;
    }

    public TextureAtlas getAnimals() {
        return animals;
    }

    public TextureAtlas getDuckNPC() {
        return duckNPC;
    }

    public TextureAtlas getButterFliesNPC() {
        return butterFliesNPC;
    }

    public TextureAtlas getBirdNPC() {
        return BirdNPC;
    }

    @Override
    public void dispose() {
        MainGamePlay.gameHub.stage.dispose();
        world.dispose();
        mainPlayerAtlas.dispose();
        groundObjects.dispose();
        playerArrow.dispose();
        arrowSound.dispose();
        enemy2Atlas.dispose();
        animatedWater.dispose();
        animatedFire.dispose();
        woodenLog.dispose();
        Springs.dispose();
        flyingEnemy.dispose();
        cloud.dispose();
        hiddenSnake.dispose();
        levelAmbientMusic.dispose();
        animals.dispose();
        duckNPC.dispose();
        BirdNPC.dispose();

    }
}
