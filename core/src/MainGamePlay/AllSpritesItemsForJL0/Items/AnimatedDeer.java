package MainGamePlay.AllSpritesItemsForJL0.Items;

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

public class AnimatedDeer extends ItemsSprite implements Disposable {

    private BodyDef bodyDef;
    private FixtureDef fixtureDef, fixtureDef1, fixtureDef2;
    private CircleShape circleShape;
    private PolygonShape leftWakeUpSensor, rightWakeUpSensor;

    private jungleLevel0 jungleLevel0;
    private float stateTime = 0f;

    private boolean isTurningRight;
    private boolean goLeft = false;
    private boolean goRight = false;
    private boolean makeStop = false;
    private boolean walkNow = false;
    private boolean setToDeath = false;

    private enum DeerState {
        IDLE, WALK
    }

    private DeerState currentState;
    private DeerState pastState;
    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> walk;
    private Array<TextureRegion> frames;

    private Manager manager = Manager.managerInstance;
    private SettingPref settingPref = SettingPref.instance;
    private Timer timer;

    public AnimatedDeer(jungleLevel0 jungleLevel0, float x, float y) {
        super(jungleLevel0, x, y);
        currentState = DeerState.IDLE;
        pastState = DeerState.IDLE;
        itemBody.setActive(false);
        timer = new Timer();
        setItems();

    }

    public void setItems() {
        frames = new Array<>();
        for (int i = 0; i < 10; i++) {
            frames.add(new TextureRegion(super.jungleLevel0.getAnimals().findRegion("DeerIdle"), i * 72, 0, 72, 52));
        }
        idle = new Animation<>(0.8f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(super.jungleLevel0.getAnimals().findRegion("DeerWalk"), i * 72, 0, 72, 52));
        }
        walk = new Animation<>(0.8f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        setBounds(getX(), getY(), (float) 30 / GameConfig.PPM, (float) 32 / GameConfig.PPM);
    }

    @Override
    public void defineItems() {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        circleShape = new CircleShape();

        // main body sensor
        bodyDef.bullet = true;
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        itemBody = world.createBody(bodyDef);
        circleShape.setRadius((float) 3 / GameConfig.PPM);
        circleShape.setPosition(new Vector2(0, -.02f));
        fixtureDef.shape = circleShape;
        itemBody.createFixture(fixtureDef).setUserData(this);

        // left detection sensor
        leftWakeUpSensor = new PolygonShape();
        leftWakeUpSensor.setAsBox((float) 10 / GameConfig.PPM,
                (float) 2 / GameConfig.PPM, new Vector2(-.15f, 0f), 0);
        fixtureDef1 = new FixtureDef();
        fixtureDef1.shape = leftWakeUpSensor;
        fixtureDef1.isSensor = true;
        fixtureDef1.filter.categoryBits = GameConfig.Deer_NPC_LEFT_SENSOR_BITL0;
        fixtureDef1.filter.maskBits = GameConfig.Player_BitL0 | GameConfig.NPC_Boundary_Left_BITL0 |
                GameConfig.Ground_BitL0;
        itemBody.createFixture(fixtureDef1).setUserData(this);

        // right detection sensor
        rightWakeUpSensor = new PolygonShape();
        rightWakeUpSensor.setAsBox((float) 10 / GameConfig.PPM,
                (float) 2 / GameConfig.PPM, new Vector2(.15f, 0f), 0);
        fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = rightWakeUpSensor;
        fixtureDef2.isSensor = true;
        fixtureDef2.filter.categoryBits = GameConfig.Deer_NPC_RIGHT_SENSOR_BITL0;
        fixtureDef2.filter.maskBits = GameConfig.Player_BitL0 | GameConfig.NPC_Boundary_Right_BITL0 |
                GameConfig.Ground_BitL0;
        itemBody.createFixture(fixtureDef2).setUserData(this);
    }


    @Override
    public void update(float delta) {
        stateTime += delta;

        if (walkNow) {
            setRegion(walk.getKeyFrame(stateTime, true));
        }

        setPosition(itemBody.getPosition().x - getWidth() / 2f,
                itemBody.getPosition().y - getHeight() / 3f);
        setRegion(getFrames(delta));

        if (setToDeath && !destroyed) {
            world.destroyBody(itemBody);
            destroyed = true;
        }


        if (makeStop) {
            itemBody.setLinearVelocity(new Vector2(0, 0));
        } else if (!makeStop) {
            if (goLeft) {
                itemBody.setLinearVelocity(new Vector2(0.15f, 0));
            } else if (goRight) {
                itemBody.setLinearVelocity(new Vector2(-0.15f, 0));
            }
        }

    }

    public TextureRegion getFrames(float delta) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case WALK:
                region = walk.getKeyFrame(stateTime, true);
                break;
            default:
                region = idle.getKeyFrame(stateTime, true);
        }

        if ((itemBody.getLinearVelocity().x < 0 || !isTurningRight) && region.isFlipX()) {
            region.flip(true, false);
            isTurningRight = false;
        } else if ((itemBody.getLinearVelocity().x > 0 || isTurningRight) && !region.isFlipX()) {
            region.flip(true, false);
            isTurningRight = true;
        }

        stateTime = currentState == pastState ? (stateTime + delta) : 0;
        //update previous state
        pastState = currentState;
        //return the final adjusted frame
        return region;
    }

    public DeerState getState() {
        if (walkNow == true) {
            return DeerState.WALK;
        } else {
            return DeerState.IDLE;
        }
    }

    @Override
    public void playerHit(PlayerClass2 mainPlayer) {
        // player on left
        walkNow = true;
        makeStop = false;
        goRight = false;
        goLeft = true;
    }

    @Override
    public void playerNotHit(PlayerClass2 mainPlayer) {
        // player on right
        walkNow = true;
        makeStop = false;
        goLeft = false;
        goRight = true;
    }

    @Override
    public void enemyHit(PlayerClass2 mainPlayer) {
        // stop walk animation
        walkNow = false;
        makeStop = true;
    }

    @Override
    public void destroyed() {
        setToDeath = true;
    }


    @Override
    public void dispose() {
        circleShape.dispose();
        leftWakeUpSensor.dispose();
        rightWakeUpSensor.dispose();
    }
} // NPC
