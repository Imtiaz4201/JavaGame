package MainGamePlay.Player2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
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
import Levels.jungleLevels;
import MainGamePlay.AllSpritesItemsForJL0.Items.ItemsSprite;
import MainGamePlay.gameHub;
import MainGamePlay.jungle.jungleLevel0;
import Settings.SettingPref;
import box2dLight.PointLight;


public class PlayerClass2 extends Sprite implements Disposable {
    public Body playerBody;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef, fixtureDef1;
    private CircleShape circleShape;
    private Manager manager = Manager.managerInstance;
    private SettingPref settingPref = SettingPref.instance;
    private World world;
    protected PolygonShape Sensor;
    private Timer timer;
    private int playerHealth = 5;
    private TextureRegion region;

    public enum PlayerState {
        Idle, Running, Jumping, Die, Attack, JumpAttack, Fall, TakingHit
    }

    public PlayerState currentState;
    public PlayerState pastState;


    private float stateTime = 0;
    public boolean isRunningRight = true;
    public boolean isTakingHit = false;
    private boolean make_camera_shake = false;
    public boolean stop_camera_shake = false;
    private float time_to_next_step = 0;


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
    public boolean makePlayerIdle = false;
    public boolean callGameOverScreen = false;
    public boolean playerOnGround = false;
    public boolean playerNotOnWater = false;
    public boolean callGameOverText = false;
    private boolean pushBack = false;
    private boolean playerWalking = false;
    public boolean turnLeft;
    public boolean turnRight;
    public PointLight tempLight;
    private jungleLevel0 jungleLevel0;

    public PlayerClass2() {
    }

    public PlayerClass2(World world, jungleLevel0 jungleLevel0) {
        this.world = world;
        this.jungleLevel0 = jungleLevel0;
        currentState = PlayerState.Idle;
        pastState = PlayerState.Idle;
        timer = new Timer();

        setSprite();
        setMainPlayer();

    }


    public void setSprite() {
        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(jungleLevel0.getMainPlayerAtlas().findRegion("adventurer-idle"), i * 50, 0, 50, 37));
        }
        idle = new Animation<>(0.4f, frames);
        frames.clear();


        for (int i = 0; i < 9; i++) {
            frames.add(new TextureRegion(jungleLevel0.getMainPlayerAtlas().findRegion("adventurer-bow"), i * 50, 0, 50, 37));
        }
        attacking = new Animation<>(0.06f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(jungleLevel0.getMainPlayerAtlas().findRegion("adventurer-run"), i * 50, 0, 50, 37));
        }
        run = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(jungleLevel0.getMainPlayerAtlas().findRegion("adventurer-jump"), i * 50, 0, 50, 37));
        }
        jump = new Animation<>(0.2f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 7; i++) {
            frames.add(new TextureRegion(jungleLevel0.getMainPlayerAtlas().findRegion("adventurer-die"), i * 50, 0, 50, 37));
        }
        die = new Animation<>(0.2f, frames);
        frames.clear();

        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(jungleLevel0.getMainPlayerAtlas().findRegion("adventurer-fall"), i * 50, 0, 50, 37));
        }
        fall = new Animation<>(0.2f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 3; i++) {
            frames.add(new TextureRegion(jungleLevel0.getMainPlayerAtlas().findRegion("adventurer-hurt"), i * 50, 0, 50, 37));
        }
        takingHit = new Animation<>(0.2f, frames);
        frames.clear();

        setBounds(0, 0, (float) 35 / GameConfig.PPM, (float) 35 / GameConfig.PPM);
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
            stop_camera_shake = true;
            setRegion(die.getKeyFrame(stateTime, false));
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    world.destroyBody(playerBody);
                    destroyed = true;
                    callGameOverScreen = true;
                }
            }, 1.5f);
            timer.start();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerWalking = true;
        } else {
            playerWalking = false;
        }
        if (playerWalking && playerOnGround) {
            time_to_next_step -= delta;
            if (time_to_next_step < 0) {
                if (settingPref.sfx) {
                    jungleLevel0.getPlayerWalingSound().play(settingPref.sfxVolume);
                } else if (!settingPref.sfx) {
                    jungleLevel0.getPlayerWalingSound().stop();
                }
                while (time_to_next_step < 0) {
                    time_to_next_step += .30;
                }
            }
        } else {
            time_to_next_step = 0;
        }

        setPosition(playerBody.getPosition().x - getWidth() / 2, playerBody.getPosition().y - getHeight() / 4f);
        setRegion(getFrame(delta));
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();
        region = new TextureRegion();

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
        if ((Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || !isRunningRight) && !region.isFlipX()) {
            isRunningRight = false;
            region.flip(true, false);
            turnLeft = true;
            turnRight = false;
        }
        // if running right
        else if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || isRunningRight) && region.isFlipX()) {
            isRunningRight = true;
            region.flip(true, false);
            turnRight = true;
            turnLeft = false;

        }

        if (currentState == pastState) {
            stateTime += delta;
        } else {
            delta = 0;
        }
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
        } else if (playerBody.getLinearVelocity().x != 0 && !makePlayerIdle) {
            return PlayerState.Running;
        } else if (Gdx.input.isKeyPressed(Input.Keys.F) && MainGamePlay.gameHub.getTotalArrowAmount() != 0) {
            return PlayerState.Attack;
        } else if (setToDestroy) {
            return PlayerState.Die;
        } else if (isTakingHit) {
            return PlayerState.TakingHit;
        } else {
            return PlayerState.Idle;
        }
    }


    public void setMainPlayer() {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        circleShape = new CircleShape();
        // main body sensor
        bodyDef.position.set((float) 32 / GameConfig.PPM, (float) 32 / GameConfig.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBody = world.createBody(bodyDef);
        circleShape.setRadius((float) 1 / GameConfig.PPM);
        circleShape.setPosition(new Vector2(0, -.04f));
        fixtureDef.shape = circleShape;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 4;
        playerBody.createFixture(fixtureDef);

        // collusion detection
        // Sensor
        Sensor = new PolygonShape();
        fixtureDef1 = new FixtureDef();
        Sensor.setAsBox((float) 6 / GameConfig.PPM,
                (float) 9 / GameConfig.PPM, new Vector2(0, 0.03f), 0);
        fixtureDef1.shape = Sensor;
        //fixtureDef1.isSensor = true;
        fixtureDef1.filter.categoryBits = GameConfig.Player_BitL0;
        fixtureDef1.filter.maskBits = GameConfig.Enemy_BitL0 |
                GameConfig.Coin_BitL0 |
                GameConfig.Default_BitL0 |
                GameConfig.Ground_BitL0 |
                GameConfig.Enemy_Hit_BitL0 |
                GameConfig.Star_BitL0 |
                GameConfig.Spring_BitL0 |
                GameConfig.ENEMY_LEFT_SENSOR_BITL0 |
                GameConfig.ENEMY_RIGHT_SENSOR_BITL0 |
                GameConfig.Ground_ENEMY_LEFT_SENSOR_BITL0 |
                GameConfig.Ground_ENEMY_RIGHT_SENSOR_BITL0 |
                GameConfig.ENEMY_WAKE_UP_BITL0 |
                GameConfig.Deer_NPC_LEFT_SENSOR_BITL0 |
                GameConfig.Deer_NPC_RIGHT_SENSOR_BITL0 |
                GameConfig.Fox_NPC_LEFT_SENSOR_BITL0 |
                GameConfig.Fox_NPC_RIGHT_SENSOR_BITL0 |
                GameConfig.Bird_BITL0;
        playerBody.createFixture(fixtureDef1).setUserData(this);
    }

    public float getStateTime() {
        return stateTime;
    }

    public Body getPlayerBody() {
        return playerBody;
    }

    public void playerDeath() {
        if (!stop_camera_shake) {
            isTakingHit = true;
            playerHealth -= 1;
            playerOnGround = true;
            MainGamePlay.gameHub.destroyHealthForPlayer(playerHealth);
        }
    }

    public void oneHitDeath() {
        MainGamePlay.gameHub.destroyHealthForPlayer(-5);
        if (!destroyed) {
            setToDestroy = true;
        }
    }

    public void recursiveDeath_call() {
        if (!destroyed && playerHealth > 0 && pushBack) {
            isTakingHit = true;
            playerOnGround = true;
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    if (!stop_camera_shake) {
                        playerHealth -= 1;
                        MainGamePlay.gameHub.destroyHealthForPlayer(playerHealth);
                        recursiveDeath_call();
                    }
                }
            }, .8f);
            timer.start();
        }
    }


    public void notTakingHit() {
        isTakingHit = false;
    }

    public void notOnGroundLayer() {
        makePlayerIdle = true;
    }

    public void onGroundLayer() {
        makePlayerIdle = false;
    }

    public void playerOnGroundNow() {
        playerOnGround = true;
    }

    public void playerInAir() {
        playerOnGround = false;
    }

    public void setPlayerOnWater() {
        playerNotOnWater = true;
    }

    public void shakeCamera() {
        make_camera_shake = true;
    }

    public void stopShakeCamera() {
        make_camera_shake = false;
    }

    public void push_back() {
        pushBack = true;
    }

    public void dont_push_back() {
        pushBack = false;
    }

    public boolean getCameraShake() {
        return make_camera_shake;
    }

    public void changeStar1Color() {
        settingPref.changeStar1Color = true;
        settingPref.saveData();
    }

    @Override
    public void dispose() {
        world.dispose();
        circleShape.dispose();
        Sensor.dispose();
    }

} // player class
