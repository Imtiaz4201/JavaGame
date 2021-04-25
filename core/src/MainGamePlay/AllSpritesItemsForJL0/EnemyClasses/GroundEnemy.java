package MainGamePlay.AllSpritesItemsForJL0.EnemyClasses;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
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

public class GroundEnemy extends EnemySpritesJL0 implements Disposable {

    // Box2d
    private BodyDef bodyDef;
    private FixtureDef fixtureDef, fixtureDef1, fixtureDef2, fixtureDef3, fixtureDef4;
    private CircleShape circleShape;
    protected PolygonShape BSensor, hitSensor, leftWakeUpSensor, rightWakeUpSensor;

    private Manager manager = Manager.managerInstance;
    private SettingPref settingPref = SettingPref.instance;
    private Timer timer;

    private Vector2 position = new Vector2();
    private Vector2 direction = new Vector2();
    private float distance = 0;
    private float maxDistance = 0;
    private float time_to_next_step = 0;

    public enum EnemyState {
        ATTACK, IDLE, DEATH, WALK, HUT
    }

    public EnemyState currentState;
    public EnemyState pastState;

    private float stateTime = 0;
    private Sound runningSFX;
    private boolean isStaticBody = false;

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> attacking;
    private Animation<TextureRegion> walk;
    private Animation<TextureRegion> takingHit;
    private Animation<TextureRegion> death;
    private Array<TextureRegion> frames;

    private boolean isAttacking = false;
    private boolean isTurningRight = true;
    private boolean setToDestroy = false;
    private boolean Dead = false;
    private boolean playerDeath = false;
    private boolean goLeft = false;
    private boolean goRight = false;
    private boolean makeEnemyStop = false;
    private boolean enemyFinished = false;

    public GroundEnemy(jungleLevel0 jungleLevel0, float x, float y) {
        super(jungleLevel0, x, y);
        enemyBody.setActive(false);
        currentState = EnemyState.IDLE;
        pastState = EnemyState.IDLE;
        timer = new Timer();

        position.x = x;
        position.y = y;
        direction.x = -.3f;
        direction.y = 0;
        maxDistance = 2f;

        setSprite();
    }

    public void playWolfAttackSound(float delta) {
        time_to_next_step -= delta;
        if (time_to_next_step < 0) {
            if (settingPref.sfx) {
                jungleLevel0.getWolfAttackSound().play(settingPref.sfxVolume);
            } else if (!settingPref.sfx) {
                jungleLevel0.getWolfAttackSound().stop();
            }
            while (time_to_next_step < 0) {
                time_to_next_step += .60;
            }
        } else {
            time_to_next_step = 0;
        }
    }

    public void setSprite() {
        frames = new Array<>();

        for (int i = 0; i < 3; i++) {
            frames.add(new TextureRegion(super.jungleLevel0.getEnemy2Atlas().findRegion("Hidel"), i * 48, 0, 48, 48));
        }
        idle = new Animation<>(0.4f, frames);
        frames.clear();

        for (int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(jungleLevel0.getEnemy2Atlas().findRegion("Hattak"), i * 48, 0, 48, 48));
        }
        attacking = new Animation<>(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(jungleLevel0.getEnemy2Atlas().findRegion("Hwalk"), i * 48, 0, 48, 48));
        }
        walk = new Animation<>(0.2f, frames, Animation.PlayMode.LOOP);
        frames.clear();


        for (int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(jungleLevel0.getEnemy2Atlas().findRegion("Hdeath"), i * 48, 0, 48, 48));
        }
        death = new Animation<>(0.5f, frames);
        frames.clear();

        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(jungleLevel0.getEnemy2Atlas().findRegion("Hhurt"), i * 48, 0, 48, 48));
        }
        takingHit = new Animation<>(0.2f, frames);
        frames.clear();

        setBounds(getX(), getY(), (float) 30 / GameConfig.PPM, (float) 30 / GameConfig.PPM);
    }

    @Override
    public void defineEnemy() {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        circleShape = new CircleShape();

        // main body sensor
        bodyDef.bullet = true;
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        enemyBody = world.createBody(bodyDef);
        circleShape.setRadius((float) 3 / GameConfig.PPM);
        fixtureDef.shape = circleShape;
        enemyBody.createFixture(fixtureDef);

        // left detection sensor
        leftWakeUpSensor = new PolygonShape();
        leftWakeUpSensor.setAsBox((float) 30 / GameConfig.PPM,
                (float) 2 / GameConfig.PPM, new Vector2(-.4f, 0f), 0);
        fixtureDef3 = new FixtureDef();
        fixtureDef3.shape = leftWakeUpSensor;
        fixtureDef3.isSensor = true;
        fixtureDef3.filter.categoryBits = GameConfig.Ground_ENEMY_LEFT_SENSOR_BITL0;
        fixtureDef3.filter.maskBits = GameConfig.Player_BitL0;
        enemyBody.createFixture(fixtureDef3).setUserData(this);

        // right detection sensor
        rightWakeUpSensor = new PolygonShape();
        rightWakeUpSensor.setAsBox((float) 30 / GameConfig.PPM,
                (float) 2 / GameConfig.PPM, new Vector2(.4f, 0f), 0);
        fixtureDef4 = new FixtureDef();
        fixtureDef4.shape = rightWakeUpSensor;
        fixtureDef4.isSensor = true;
        fixtureDef4.filter.categoryBits = GameConfig.Ground_ENEMY_RIGHT_SENSOR_BITL0;
        fixtureDef4.filter.maskBits = GameConfig.Player_BitL0;
        enemyBody.createFixture(fixtureDef4).setUserData(this);

        // body sensor
        BSensor = new PolygonShape();
        BSensor.setAsBox((float) 5 / GameConfig.PPM, (float) 3 / GameConfig.PPM, new Vector2(0, 0f), 0);
        fixtureDef1 = new FixtureDef();
        fixtureDef1.shape = BSensor;
        fixtureDef1.filter.categoryBits = GameConfig.Enemy_BitL0;
        fixtureDef1.filter.maskBits = GameConfig.Default_BitL0 | GameConfig.PlatForm_BitL0 | GameConfig.Player_BitL0 | GameConfig.Ground_BitL0;
        enemyBody.createFixture(fixtureDef1).setUserData(this);

        // hit sensor
        hitSensor = new PolygonShape();
        fixtureDef2 = new FixtureDef();
        hitSensor.setAsBox((float) 7 / GameConfig.PPM, (float) .5 / GameConfig.PPM, new Vector2(0f, 0f), 0);
        fixtureDef2.shape = hitSensor;
        fixtureDef2.isSensor = true;
        enemyBody.createFixture(fixtureDef2).setUserData("SCORPION");
    }

    public void update(float delta) {
        stateTime += delta;
        stateTimer += delta;
        if (isAttacking && !playerDeath) {
            setRegion(attacking.getKeyFrame(stateTime, true));
        }

        if (setToDestroy && !destroyed) {
            enemyFinished = true;
            Dead = true;
            isStaticBody = true;
            world.destroyBody(enemyBody);
            destroyed = true;
        } else if (!destroyed) {
            setPosition(enemyBody.getPosition().x - getWidth() / 2.5f,
                    enemyBody.getPosition().y - getHeight() / 5f);
            setRegion(walk.getKeyFrame(stateTime, true));
        }
        //movement of enemy body
        if (isStaticBody) {
            enemyBody.setLinearVelocity(new Vector2(0, 0));
        } else {
            if (makeEnemyStop) {
                enemyBody.setLinearVelocity(new Vector2(0, 0));
            } else if (!makeEnemyStop) {
                if (goLeft && !isStaticBody) {
                    enemyBody.setLinearVelocity(new Vector2(-0.15f, 0));
                } else if (goRight && !isStaticBody) {
                    enemyBody.setLinearVelocity(new Vector2(0.15f, 0));
                }
            }
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
            case WALK:
                region = walk.getKeyFrame(stateTime, true);
                break;
            case HUT:
                region = takingHit.getKeyFrame(stateTime);
                break;
            case DEATH:
                region = death.getKeyFrame(stateTime);
                break;
            default:
                region = idle.getKeyFrame(stateTime, true);
        }
        if ((enemyBody.getLinearVelocity().x < 0 || !isTurningRight) && region.isFlipX()) {
            region.flip(true, false);
            isTurningRight = false;
        } else if ((enemyBody.getLinearVelocity().x > 0 || isTurningRight) && !region.isFlipX()) {
            region.flip(true, false);
            isTurningRight = true;
        }

        stateTime = currentState == pastState ? (stateTime + delta) : 0;
        //update previous state
        pastState = currentState;
        //return the final adjusted frame
        return region;

    }

    public EnemyState getState() {
        settingPref.loadData();
        if (isAttacking == true) {
            return EnemyState.ATTACK;
        } else if (enemyBody.getLinearVelocity().x != 0) {
            return EnemyState.WALK;
        } else if (setToDestroy == true) {
            return EnemyState.DEATH;
        } else
            return EnemyState.IDLE;
    }


    @Override
    public void playerHit(PlayerClass2 mainPlayer) {
        setToDestroy = true;
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
        isStaticBody = true;
    }

    @Override
    public void detachStaticBody(PlayerClass2 mainPlayer) {
        isStaticBody = false;
    }

    @Override
    public void playerDetectionON(PlayerClass2 mainPlayer) {

    }

    @Override
    public void playerDetectionOff(PlayerClass2 mainPlayer) {
        makeEnemyStop = true;
    }

    @Override
    public void playerOnLeft(PlayerClass2 mainPlayer) {
        if (!enemyFinished) {
            makeEnemyStop = false;
            goRight = false;
            goLeft = true;
        }
    }

    @Override
    public void playerOnRight(PlayerClass2 mainPlayer) {
        if (!enemyFinished) {
            makeEnemyStop = false;
            goLeft = false;
            goRight = true;
        }
    }

    @Override
    public void dispose() {
        circleShape.dispose();
        BSensor.dispose();
        hitSensor.dispose();
        world.dispose();
        leftWakeUpSensor.dispose();
        rightWakeUpSensor.dispose();
    }
} // enemy class (ground)
