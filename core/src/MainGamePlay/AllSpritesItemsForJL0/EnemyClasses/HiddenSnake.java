package MainGamePlay.AllSpritesItemsForJL0.EnemyClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;

import AssetsManager.Manager;
import GameConfiguration.GameConfig;
import MainGamePlay.Player2.PlayerClass2;
import MainGamePlay.jungle.jungleLevel0;
import Settings.SettingPref;

public class HiddenSnake extends EnemySpritesJL0 implements Disposable {

    // Box2d
    private BodyDef bodyDef;
    private FixtureDef fixtureDef, fixtureDef1, fixtureDef2, fixtureDef3, fixtureDef4, fixtureDef5;
    private CircleShape circleShape;
    protected PolygonShape attackSensor, hitSensor, LeftSensor, RightSensor, wakeUpSensor, attackToPlayerSensor;

    private Manager manager = Manager.managerInstance;
    private SettingPref settingPref = SettingPref.instance;
    private Timer timer;
    private Music snakeHissingSound;

    private enum HiddenSnakeState {
        IDLE, ATTACK, WAKEUP, HIDDEN
    }

    private HiddenSnakeState currentState;
    private HiddenSnakeState pastState;

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> attacking;
    private Animation<TextureRegion> wakeUP;
    private Animation<TextureRegion> hidden;
    private Array<TextureRegion> frames;

    private boolean isAttacking = false;
    private boolean setToDestroy = false;
    private boolean playIdleAnim = false;
    private boolean playerDeath = false;
    private boolean playerLeavingSensorZone;
    private boolean turnLeft;
    private boolean turnRight;
    private boolean playSnakeHissingSound = false;

    private float stateTime = 0;
    private float life = 2;

    public HiddenSnake(MainGamePlay.jungle.jungleLevel0 jungleLevel0, float x, float y) {
        super(jungleLevel0, x, y);
        enemyBody.setActive(false);
        currentState = HiddenSnakeState.IDLE;
        pastState = HiddenSnakeState.IDLE;
        timer = new Timer();
        setSprite();
        snakeHissingSound = manager.assetManager.get(manager.snakeHissingSound, Music.class);

    }

    public void setSprite() {
        frames = new Array<>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(super.jungleLevel0.getHiddenSnake().findRegion("hit"), i * 128, 0, 128, 128));
        }
        attacking = new Animation<>(0.2f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(jungleLevel0.getHiddenSnake().findRegion("idle"), i * 128, 0, 128, 128));
        }
        idle = new Animation<>(0.5f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(jungleLevel0.getHiddenSnake().findRegion("appear"), i * 128, 0, 128, 128));
        }
        wakeUP = new Animation<>(0.2f, frames);
        frames.clear();

        for (int i = 0; i < 1; i++) {
            frames.add(new TextureRegion(jungleLevel0.getHiddenSnake().findRegion("appear1"), i * 128, 0, 128, 128));
        }
        hidden = new Animation<>(0, frames);
        frames.clear();

        setBounds(0, 0, (float) 35 / GameConfig.PPM, (float) 35 / GameConfig.PPM);
        setRegion(hidden.getKeyFrame(stateTime));
    }

    public void playSnakeHissingSound() {
        if (settingPref.sfx) {
            snakeHissingSound.setLooping(true);
            snakeHissingSound.setVolume(settingPref.sfxVolume);
            snakeHissingSound.play();
        } else if (!settingPref.sfx) {
            snakeHissingSound.setLooping(false);
            snakeHissingSound.stop();
        }
    }

    public void playSnakeAttackSound() {

    }

    @Override
    public void defineEnemy() {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        circleShape = new CircleShape();

        // main body sensor
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        enemyBody = world.createBody(bodyDef);
        circleShape.setRadius((float) 3 / GameConfig.PPM);
        fixtureDef.shape = circleShape;
        enemyBody.createFixture(fixtureDef);

        // wake up sensor
        wakeUpSensor = new PolygonShape();
        fixtureDef4 = new FixtureDef();
        wakeUpSensor.setAsBox((float) 100 / GameConfig.PPM,
                (float) 40 / GameConfig.PPM, new Vector2(0f, .05f), 0);
        fixtureDef4.shape = wakeUpSensor;
        fixtureDef4.isSensor = true;
        fixtureDef4.filter.categoryBits = GameConfig.ENEMY_WAKE_UP_BITL0;
        fixtureDef4.filter.maskBits = GameConfig.Player_BitL0;
        enemyBody.createFixture(fixtureDef4).setUserData(this);


        //  left sensor
        LeftSensor = new PolygonShape();
        fixtureDef1 = new FixtureDef();
        LeftSensor.setAsBox((float) 40 / GameConfig.PPM,
                (float) 2 / GameConfig.PPM, new Vector2(-.5f, 0f), 0);
        fixtureDef1.shape = LeftSensor;
        fixtureDef1.isSensor = true;
        fixtureDef1.filter.categoryBits = GameConfig.ENEMY_LEFT_SENSOR_BITL0;
        fixtureDef1.filter.maskBits = GameConfig.Player_BitL0;
        enemyBody.createFixture(fixtureDef1).setUserData(this);

        //  right sensor
        RightSensor = new PolygonShape();
        fixtureDef2 = new FixtureDef();
        RightSensor.setAsBox((float) 40 / GameConfig.PPM,
                (float) 2 / GameConfig.PPM, new Vector2(.5f, 0f), 0);
        fixtureDef2.shape = RightSensor;
        fixtureDef2.isSensor = true;
        fixtureDef2.filter.categoryBits = GameConfig.ENEMY_RIGHT_SENSOR_BITL0;
        fixtureDef2.filter.maskBits = GameConfig.Player_BitL0;
        enemyBody.createFixture(fixtureDef2).setUserData(this);

        // body sensor
        attackSensor = new PolygonShape();
        fixtureDef3 = new FixtureDef();
        attackSensor.setAsBox((float) 10 / GameConfig.PPM, (float) 6 / GameConfig.PPM, new Vector2(0, 0f), 0);
        fixtureDef3.shape = attackSensor;
        fixtureDef3.filter.categoryBits = GameConfig.Enemy_BitL0;
        fixtureDef3.filter.maskBits = GameConfig.Default_BitL0 | GameConfig.Player_BitL0;
        fixtureDef3.isSensor = true;
        enemyBody.createFixture(fixtureDef3).setUserData(this);

        // attack sensor
        attackToPlayerSensor = new PolygonShape();
        fixtureDef5 = new FixtureDef();
        attackToPlayerSensor.setAsBox((float) 9 / GameConfig.PPM, (float) 2 / GameConfig.PPM, new Vector2(0, -.01f), 0);
        fixtureDef5.shape = attackToPlayerSensor;
        fixtureDef5.isSensor = true;
        enemyBody.createFixture(fixtureDef5).setUserData("HSAttack");
    }

    @Override
    public void update(float delta) {
        stateTime += delta;

        if (playSnakeHissingSound) {
            playSnakeHissingSound();
        }
        if (!playSnakeHissingSound) {
            snakeHissingSound.stop();
        }

        if (playerDetectionSensor) {
            playIdleAnim = true;
            setRegion(wakeUP.getKeyFrame(stateTime));
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    playerDetectionSensor = false;
                }
            }, .5f);
            timer.start();
        }

        if (playerLeavingSensorZone) {
            playIdleAnim = false;
            setRegion(hidden.getKeyFrame(stateTime));
        }

        if (playIdleAnim) {
            setRegion(idle.getKeyFrame(stateTime, true));
        }

        if (isAttacking && !playerDeath) {
            setRegion(attacking.getKeyFrame(stateTime, true));
        }

        if (life == 0) {
            setToDestroy = true;
        }

        if (setToDestroy && !destroyed) {
            world.destroyBody(enemyBody);
            destroyed = true;
            snakeHissingSound.setLooping(false);
            snakeHissingSound.stop();
        } else if (!destroyed) {
            setPosition(enemyBody.getPosition().x - getWidth() / 2.5f,
                    enemyBody.getPosition().y - getHeight() / 5f);
        }

        setPosition(enemyBody.getPosition().x - getWidth() / 2.5f,
                enemyBody.getPosition().y - getHeight() / 5f);
        setRegion(getFrames(delta));
    }

    public TextureRegion getFrames(float delta) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case ATTACK:
                region = attacking.getKeyFrame(stateTime, true);
                break;
            case WAKEUP:
                region = wakeUP.getKeyFrame(stateTime);
                break;
            case IDLE:
                region = idle.getKeyFrame(stateTime, true);
                break;
            default:
                region = hidden.getKeyFrame(stateTime);
        }
        if (turnRight && !region.isFlipX()) {
            region.flip(true, false);

        } else if (turnLeft && region.isFlipX()) {
            region.flip(true, false);

        }

        stateTime = currentState == pastState ? (stateTime + delta) : 0;
        //update previous state
        pastState = currentState;
        //return the final adjusted frame
        return region;

    }

    public HiddenSnakeState getState() {
        if (isAttacking) {
            return HiddenSnakeState.ATTACK;
        } else if (playerDetectionSensor) {
            return HiddenSnakeState.WAKEUP;
        } else if (playIdleAnim) {
            return HiddenSnakeState.IDLE;
        } else
            return HiddenSnakeState.HIDDEN;
    }


    @Override
    public void playerHit(PlayerClass2 mainPlayer) {
        life -= 1;
    }

    @Override
    public void playAttackAnimation(PlayerClass2 mainPlayer) {
        playerDeath = mainPlayer.destroyed;
        isAttacking = true;
    }

    @Override
    public void dontPlayAttackAnimation(PlayerClass2 mainPlayer) {
        isAttacking = false;
    }

    @Override
    public void enemyHit() {

    }

    @Override
    public void attachStaticBody(PlayerClass2 mainPlayer) {

    }

    @Override
    public void detachStaticBody(PlayerClass2 mainPlayer) {

    }

    @Override
    public void playerDetectionON(PlayerClass2 mainPlayer) {
        playSnakeHissingSound = true;
        playerLeavingSensorZone = false;
        playerDetectionSensor = true;
    }

    @Override
    public void playerDetectionOff(PlayerClass2 mainPlayer) {
        playSnakeHissingSound = false;
        playerLeavingSensorZone = true;
    }

    @Override
    public void playerOnLeft(PlayerClass2 mainPlayer) {
        turnRight = false;
        turnLeft = true;
    }

    @Override
    public void playerOnRight(PlayerClass2 mainPlayer) {
        turnLeft = false;
        turnRight = true;
    }

    @Override
    public void dispose() {
        attackSensor.dispose();
        wakeUpSensor.dispose();
        LeftSensor.dispose();
        RightSensor.dispose();
        hitSensor.dispose();
        attackToPlayerSensor.dispose();
        snakeHissingSound.dispose();
    }
}
