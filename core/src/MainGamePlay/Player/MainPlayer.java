package MainGamePlay.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;

import AssetsManager.Manager;
import GameConfiguration.GameConfig;
import MainGamePlay.AllSprites.EnemyClass1.EnemySprite;
import MainGamePlay.jungle.jungleLevel1;
import Settings.SettingPref;
import box2dLight.PointLight;
import box2dLight.RayHandler;


public class MainPlayer extends Sprite implements Disposable{
    public Body playerBody;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private CircleShape circleShape;
    private Manager manager = Manager.managerInstance;
    private SettingPref settingPref = SettingPref.instance;
    private World world;
    protected PolygonShape Sensor;
    private Timer timer;
    private int playerHealth = 5;


    public enum PlayerState {
        Idle, Running, Jumping, Die, Attack, JumpAttack, Fall, TakingHit
    }

    public PlayerState currentState;
    public PlayerState pastState;

    private float stateTime = 0;
    public boolean isRunningRight = true;
    public boolean isTakingHit = false;
    public boolean isRunning = false;
    private float time_to_next_step = 0;
    private Sound runningSFX;

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> attacking;
    private Animation<TextureRegion> run;
    private Animation<TextureRegion> jump;
    private Animation<TextureRegion> die;
    private Animation<TextureRegion> fall;
    private Animation<TextureRegion> jumpAttack;
    private Animation<TextureRegion> takingHit;


    private boolean setToDestroy = false;
    public boolean destroyed = false;
    public PointLight tempLight;

    private  jungleLevel1 jungleLevel1;

    public MainPlayer() {
    }

    public MainPlayer(World world, jungleLevel1 jungleLevel1) {
        this.world = world;
        this.jungleLevel1 = jungleLevel1;
        currentState = PlayerState.Idle;
        pastState = PlayerState.Idle;
        timer = new Timer();
        tempLight = new PointLight(jungleLevel1.getRayHandler(), 100, Color.WHITE, 1f, 0, 0);
        tempLight.setSoftnessLength(0f);
        tempLight.attachToBody(playerBody);

        runningSFX = manager.assetManager.get(manager.runningSoundEffect, Sound.class);
        setSprite();
        setMainPlayer();

    }


    public void setSprite() {
        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(jungleLevel1.getMainPlayerAtlas().findRegion("adventurer-idle"), i * 50, 0, 50, 37));
        }
        idle = new Animation<>(0.4f, frames);
        frames.clear();


        for (int i = 0; i < 9; i++) {
            frames.add(new TextureRegion(jungleLevel1.getMainPlayerAtlas().findRegion("adventurer-bow"), i * 50, 0, 50, 37));
        }
        attacking = new Animation<>(0.20f, frames);
        frames.clear();

        for (int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(jungleLevel1.getMainPlayerAtlas().findRegion("adventurer-run"), i * 50, 0, 50, 37));
        }
        run = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(jungleLevel1.getMainPlayerAtlas().findRegion("adventurer-jump"), i * 50, 0, 50, 37));
        }
        jump = new Animation<>(0.2f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 7; i++) {
            frames.add(new TextureRegion(jungleLevel1.getMainPlayerAtlas().findRegion("adventurer-die"), i * 50, 0, 50, 37));
        }
        die = new Animation<>(0.2f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(jungleLevel1.getMainPlayerAtlas().findRegion("adventurer-fall"), i * 50, 0, 50, 37));
        }
        fall = new Animation<>(0.2f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 3; i++) {
            frames.add(new TextureRegion(jungleLevel1.getMainPlayerAtlas().findRegion("adventurer-hurt"), i * 50, 0, 50, 37));
        }
        takingHit = new Animation<>(0.2f, frames);
        frames.clear();

        setBounds(0, 0, (float) 40 / GameConfig.PPM, (float) 40 / GameConfig.PPM);
        setRegion(idle.getKeyFrame(stateTime, true));
    }


    public void update(float delta) {

        if (playerHealth <= 0) {
            setToDestroy = true;
        }

        if (isTakingHit == true) {
            setRegion(takingHit.getKeyFrame(stateTime, true));
        }
        if (setToDestroy && !destroyed) {
            setRegion(die.getKeyFrame(stateTime, true));
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    world.destroyBody(playerBody);
                    destroyed = true;
                }
            }, .5f);
            timer.start();
        }

        tempLight.setPosition(playerBody.getPosition().x, playerBody.getPosition().y);
        setPosition(playerBody.getPosition().x - getWidth() / 2, playerBody.getPosition().y - getHeight() / 3.2f);
        setRegion(getFrame(delta));
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case Jumping:
                region = jump.getKeyFrame(stateTime);
                break;
            case Fall:
                region = fall.getKeyFrame(stateTime);
                break;
            case Attack:
                region = attacking.getKeyFrame(stateTime, true);
                break;
            case Running:
                region = run.getKeyFrame(stateTime, true);
                break;
            case Die:
                region = die.getKeyFrame(stateTime);
                break;
            case TakingHit:
                region = takingHit.getKeyFrame(stateTime, true);
                break;
            default:
                region = idle.getKeyFrame(stateTime, true);
        }
        // if running left
        if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || !isRunningRight) && !region.isFlipX()) {
            region.flip(true, false);
            isRunningRight = false;

        }
        // if running right
        else if (((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || isRunningRight) && region.isFlipX())) {
            region.flip(true, false);
            isRunningRight = true;
        }

        if (currentState == pastState)
            stateTime += delta;
        else
            delta = 0;

        //update previous state
        pastState = currentState;
        //return our final adjusted frame
        return region;
    }

    public PlayerState getState() {
        if (playerBody.getLinearVelocity().y > 0) {
            return PlayerState.Jumping;
        } else if (playerBody.getLinearVelocity().y < 0) {
            return PlayerState.Fall;
        } else if (playerBody.getLinearVelocity().x != 0) {
            return PlayerState.Running;
        } else if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            return PlayerState.Attack;
        } else if (setToDestroy) {
            return PlayerState.Die;
        } else if (isTakingHit) {
            return PlayerState.TakingHit;
        } else
            return PlayerState.Idle;
    }


    public void setMainPlayer() {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        circleShape = new CircleShape();
        // main body sensor
        bodyDef.position.set((float) 32 / GameConfig.PPM, (float) 32 / GameConfig.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBody = world.createBody(bodyDef);
        circleShape.setRadius((float) 6 / GameConfig.PPM);

        fixtureDef.filter.categoryBits = GameConfig.Player_Bit;
        fixtureDef.filter.maskBits = GameConfig.Enemy_Bit |
                GameConfig.Coin_Bit |
                GameConfig.Default_Bit |
                GameConfig.Ground_Bit |
                GameConfig.Enemy_Hit_Bit;
        fixtureDef.shape = circleShape;

        playerBody.createFixture(fixtureDef).setUserData(this);

        // collusion detection
        // Sensor
        Sensor = new PolygonShape();
        Sensor.setAsBox((float) 7 / GameConfig.PPM,
                (float) 9 / GameConfig.PPM, new Vector2(0, 0.06f), 0);
        fixtureDef.shape = Sensor;
        fixtureDef.isSensor = true;
        playerBody.createFixture(fixtureDef).setUserData(this);


    }

    public float getStateTime() {
        return stateTime;
    }

    public void playerDeath() {
        isTakingHit = true;
        playerHealth -= 1;
        MainGamePlay.gameHub.destroyHealthForPlayer(playerHealth);
    }

    public void notTakingHit() {
        isTakingHit = false;
    }


    @Override
    public void dispose() {
        world.dispose();
        circleShape.dispose();
        Sensor.dispose();
    }

}// player class
