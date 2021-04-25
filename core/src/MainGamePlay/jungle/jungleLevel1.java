package MainGamePlay.jungle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import MainGamePlay.AllSprites.CoinsClass.CoinSprite;
import MainGamePlay.AllSprites.EnemyClass1.EnemySprite;
import MainGamePlay.AllSprites.GroundAndPlatform.GroundObjectSprite;
import MainGamePlay.AllSprites.Hazards.HazardsSprite;
import MainGamePlay.Player.PlayerWeapon;
import MainGamePlay.Tools.GameOverScreen;
import MainGamePlay.gameHub;
import MainGamePlay.Player.MainPlayer;
import MainGamePlay.worldObjects.Box2dWorldObjectCreator;
import MainGamePlay.worldObjects.WorldObjectsListenerJL1;
import Settings.SettingPref;
import box2dLight.RayHandler;

public class jungleLevel1 extends AbstractGameScreen {

    private OrthographicCamera orthographicCamera;
    private TiledMap tiledMap;
    private OrthoCachedTiledMapRenderer orthogonalTiledMapRenderer;
    private FitViewport fitViewport;
    private SpriteBatch batch;
    private MainGamePlay.gameHub gameHub;
    private MainPlayer mainPlayer;
    private TextureAtlas mainPlayerAtlas, enemy1Atlas, flyingEnemy, hazardsSprite, groundObjects,
            coinAtlas, torchAtlas, playerArrow;
    private Manager manager = Manager.managerInstance;
    private Box2dWorldObjectCreator box2dWorldObjectCreator;
    // Box2d
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer; // provides grephical  re-presentation of fixture and body
    private Array<PlayerWeapon> playerWeapons;

    private RayHandler rayHandler;
    private Sound arrowSound;
    private SettingPref settingPref = SettingPref.instance;
    private Timer timer;

    public jungleLevel1() {
        batch = new SpriteBatch();
        playerWeapons = new Array<>();
        rayHandler = new RayHandler(getWorld());
        rayHandler.setAmbientLight(.2f); // for ambient color
        timer = new Timer();
    }

    public void playArrowSound() {
        if (settingPref.sfx == true) {
            arrowSound.play(settingPref.sfxVolume);
        } else if (settingPref.sfx == false) {
            arrowSound.stop();
        }
    }

    public void keyboardInput(float delta) {
        // to move the main player
        if (!mainPlayer.destroyed) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !(mainPlayer.playerBody.getLinearVelocity().y > 0)) {
                mainPlayer.playerBody.applyLinearImpulse(new Vector2(0, 2.5f), mainPlayer.playerBody.getWorldCenter(), true);

            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)
                    && mainPlayer.playerBody.getLinearVelocity().x >= -0.5f) {
                mainPlayer.playerBody.applyLinearImpulse(new Vector2(-0.1f, 0), mainPlayer.playerBody.getWorldCenter(), true);

            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)
                    && mainPlayer.playerBody.getLinearVelocity().x >= 0.5f) {
                mainPlayer.playerBody.applyLinearImpulse(new Vector2(0.1f, 0), mainPlayer.playerBody.getWorldCenter(), true);

            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        playArrowSound();
                        shoot();
                    }
                }, 1.1f);
                timer.start();
            }
        }
    }


    public void update(float delta) {
        keyboardInput(delta);
        orthographicCamera.position.x = mainPlayer.playerBody.getPosition().x; // move game camera according to player movement
        world.step(1 / 60f, 6, 2);

        // update main player
        mainPlayer.update(delta);
        rayHandler.update();
        rayHandler.setCombinedMatrix(orthographicCamera);
        // update enemy1
        for (EnemySprite enemySprite : box2dWorldObjectCreator.getEnemy1()) {
            enemySprite.update(delta);
        }
        // update flying enemy
        for (EnemySprite enemySprite : box2dWorldObjectCreator.getFlyingEnemies()) {
            enemySprite.update(delta);
        }
        // update animated hazards
        for (HazardsSprite hazardsSprite : box2dWorldObjectCreator.getAnimatedHazards()) {
            hazardsSprite.update(delta);
        }
        // update ground object
        for (GroundObjectSprite groundObjectSprite : box2dWorldObjectCreator.getGroundObjectDefinitions()) {
            groundObjectSprite.update(delta);
        }
        // update torch object
        for (GroundObjectSprite groundObjectSprite : box2dWorldObjectCreator.getTorchDefinitions()) {
            groundObjectSprite.update(delta);
        }
        // update player weapon

        for (PlayerWeapon Weapon : playerWeapons) {
            Weapon.update(delta);
            if (Weapon.isDestroyed()) {
                playerWeapons.removeValue(Weapon, true);
                playerWeapons.clear();
            }
        }

        // update coin
        for (CoinSprite coinSprite : box2dWorldObjectCreator.getCoin1Definitions()) {
            coinSprite.update(delta);
        }
        // update diamond
        for (CoinSprite coinSprite : box2dWorldObjectCreator.getDiamondDefinition()) {
            coinSprite.update(delta);
        }
        // update spike type1
        for (HazardsSprite hazardsSprite : box2dWorldObjectCreator.getSpikeType1()) {
            hazardsSprite.update(delta);
        }

    }

    @Override
    public void show() {
        tiledMap = new TmxMapLoader().load("GamePlayAssets/jungleMaps/level1.tmx");
        orthogonalTiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap, (float) 1 / GameConfig.PPM);
        orthogonalTiledMapRenderer.setBlending(true);
        orthographicCamera = new OrthographicCamera();
        fitViewport = new FitViewport((float) GameConfig.V_WIDTH / GameConfig.PPM,
                (float) GameConfig.V_HEIGHT / GameConfig.PPM, orthographicCamera);

        gameHub = new gameHub(batch);

        world = new World(new Vector2(0, -4), true);// for world object velocity
        box2DDebugRenderer = new Box2DDebugRenderer();
        orthographicCamera.position.set(fitViewport.getWorldWidth() / 2, fitViewport.getWorldHeight() / 2, 0);
        // need to create bodyDef then body and fit bodyDef into body then shape then fixture and fit shape into fixture
        // then need to fit fixture into world body

        // loading sprites
        mainPlayerAtlas = manager.assetManager.get(manager.playerPack, TextureAtlas.class);
        enemy1Atlas = manager.assetManager.get(manager.enemy1Pack, TextureAtlas.class);
        flyingEnemy = manager.assetManager.get(manager.flyingEnemy, TextureAtlas.class);
        hazardsSprite = manager.assetManager.get(manager.hazards, TextureAtlas.class);
        groundObjects = manager.assetManager.get(manager.groundObjects, TextureAtlas.class);
        coinAtlas = manager.assetManager.get(manager.coins, TextureAtlas.class);
        torchAtlas = manager.assetManager.get(manager.torchLight, TextureAtlas.class);
        playerArrow = manager.assetManager.get(manager.playerArrow, TextureAtlas.class);
        // load Audio
        arrowSound = manager.assetManager.get(manager.arrowSound, Sound.class);

        // for Player
        mainPlayer = new MainPlayer(world, this); // main player class object

        world.setContactListener(new WorldObjectsListenerJL1());

        // for all elements in world map
        box2dWorldObjectCreator = new Box2dWorldObjectCreator(this);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        // tilemap
        orthogonalTiledMapRenderer.render();
        orthogonalTiledMapRenderer.setView(orthographicCamera);
        // camera
        orthographicCamera.update();
        batch.setProjectionMatrix(gameHub.stage.getCamera().combined);

        // stage
        gameHub.stage.act(Gdx.graphics.getDeltaTime());
        gameHub.stage.draw();

        box2DDebugRenderer.render(world, orthographicCamera.combined);
        batch.setProjectionMatrix(orthographicCamera.combined);
        rayHandler.render();
        batch.begin();
        // render player
        if (!mainPlayer.destroyed) {
            mainPlayer.draw(batch);
        }
        // render enemy1
        for (EnemySprite enemySprite : box2dWorldObjectCreator.getEnemy1()) {
            if (!enemySprite.destroyed) {
                enemySprite.draw(batch);
            }
            if (enemySprite.getX() < mainPlayer.getX() + 150f / GameConfig.PPM) {
                enemySprite.enemyBody.setActive(true);
            }
        }
        // render flying enemy
        for (EnemySprite enemySprite : box2dWorldObjectCreator.getFlyingEnemies()) {
            if (!enemySprite.destroyed) {
                enemySprite.draw(batch);
            }
            if (enemySprite.getX() < mainPlayer.getX() + 150f / GameConfig.PPM) {
                enemySprite.enemyBody.setActive(true);
            }
        }
        // render animated hazards
        for (HazardsSprite hazardsSprite : box2dWorldObjectCreator.getAnimatedHazards()) {
            hazardsSprite.draw(batch);
        }
        // render ground object
        for (GroundObjectSprite groundObjectSprite : box2dWorldObjectCreator.getGroundObjectDefinitions()) {
            groundObjectSprite.draw(batch);
        }
        // render torch object
        for (GroundObjectSprite groundObjectSprite : box2dWorldObjectCreator.getTorchDefinitions()) {
            groundObjectSprite.draw(batch);
        }
        // render player weapon
        for (PlayerWeapon Weapon : playerWeapons) {
            if (!Weapon.isDestroyed()) {
                Weapon.draw(batch);
            }
        }
        // render coins
        for (CoinSprite coinSprite : box2dWorldObjectCreator.getCoin1Definitions()) {
            if (!coinSprite.destroyed) {
                coinSprite.draw(batch);
            }
            if (mainPlayer.getX() > coinSprite.getX()) {
                coinSprite.coinBody.setActive(true);
            }
        }
        // render spike type1
        for (HazardsSprite hazardsSprite : box2dWorldObjectCreator.getSpikeType1()) {
            hazardsSprite.draw(batch);
        }
        // render diamond
        for (CoinSprite coinSprite : box2dWorldObjectCreator.getDiamondDefinition()) {
            if (!coinSprite.destroyed) {
                coinSprite.draw(batch);
            }
            if (mainPlayer.getX() > coinSprite.getX()) {
                coinSprite.coinBody.setActive(true);
            }
        }

        batch.end();

        if (gameOver()) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverScreen());
            batch.dispose();
            tiledMap.dispose();
            box2DDebugRenderer.dispose();
        }

    }

    public boolean gameOver() {
        if (mainPlayer.currentState == MainPlayer.PlayerState.Die && mainPlayer.getStateTime() > 3 || mainPlayer.destroyed) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void resize(int width, int height) {
        fitViewport.update(width, height + 100);
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

    public TextureAtlas getMainPlayerAtlas() {
        return mainPlayerAtlas;
    }

    public TextureAtlas getEnemy1Atlas() {
        return enemy1Atlas;
    }

    public TextureAtlas getFlyingEnemy() {
        return flyingEnemy;
    }

    public TextureAtlas getHazardsSprite() {
        return hazardsSprite;
    }

    public TextureAtlas getGroundObjects() {
        return groundObjects;
    }

    public TextureAtlas getCoinAtlas() {
        return coinAtlas;
    }

    public TextureAtlas getTorchAtlas() {
        return torchAtlas;
    }

    public TextureAtlas getPlayerArrow() {
        return playerArrow;
    }

    public World getWorld() {
        return world;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public RayHandler getRayHandler() {
        return rayHandler;
    }

    public void shoot() {
        playerWeapons.add(new PlayerWeapon(this, mainPlayer.playerBody.getPosition().x, mainPlayer.playerBody.getPosition().y, mainPlayer.isRunningRight));
    }

    @Override
    public void dispose() {
        batch.dispose();
        gameHub.stage.dispose();
        tiledMap.dispose();
        box2DDebugRenderer.dispose();
        world.dispose();
        mainPlayerAtlas.dispose();
        flyingEnemy.dispose();
        hazardsSprite.dispose();
        coinAtlas.dispose();
        rayHandler.dispose();
        groundObjects.dispose();
        torchAtlas.dispose();
        playerArrow.dispose();
        arrowSound.dispose();

    }

} // jungle level-1 class
