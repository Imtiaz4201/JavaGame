package MainGamePlay.AllSpritesItemsForJL0.EnemyClasses;

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
import MainGamePlay.AllSprites.EnemyClass1.Enemy2Definition;
import MainGamePlay.Player2.PlayerClass2;
import MainGamePlay.jungle.jungleLevel0;
import Settings.SettingPref;

public class FlyingEnemy extends EnemySpritesJL0 implements Disposable {

    // box2d
    private BodyDef bodyDef;
    private FixtureDef fixtureDef, fixtureDef2;
    private CircleShape circleShape;
    private PolygonShape polygonShape, polygonShape2;

    private Manager manager = Manager.managerInstance;
    private SettingPref settingPref = SettingPref.instance;
    private Timer timer;

    private Vector2 position = new Vector2();
    private Vector2 direction = new Vector2();
    private float distance = 0;
    private float maxDistance = 0;


    public enum FlyingEnemyState {
        Flying, Die
    }

    public FlyingEnemyState currentState;
    public FlyingEnemyState pastState;

    private Array<TextureRegion> frames;
    private Animation<TextureRegion> flying;
    private Animation<TextureRegion> die;

    private float stateTime = 0;
    private boolean isTurningRight = false;
    private boolean setToDestroy = false;

    public FlyingEnemy(jungleLevel0 jungleLevel0, float x, float y) {
        super(jungleLevel0, x, y);
        enemyBody.setActive(false);
        currentState = FlyingEnemyState.Flying;
        pastState = FlyingEnemyState.Flying;

        position.x = x;
        position.y = y;
        direction.x = -.3f;
        direction.y = 0;
        maxDistance = 2f;

        setSprite();
    }

    public void setSprite() {
        frames = new Array<>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(jungleLevel0.getFlyingEnemy().findRegion("bf"), i * 16, 0, 16, 24));
        }
        flying = new Animation<>(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(jungleLevel0.getFlyingEnemy().findRegion("bDead"), i * 16, 0, 16, 24));
        }
        die = new Animation<>(0.1f, frames);
        frames.clear();

        setBounds(getX(), getY(), (float) 22 / GameConfig.PPM, (float) 22 / GameConfig.PPM);
    }

    @Override
    public void defineEnemy() {
        bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        enemyBody = world.createBody(bodyDef);

        // body sensor
        circleShape = new CircleShape();
        circleShape.setRadius((float) 1 / GameConfig.PPM);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.isSensor = true;
        enemyBody.createFixture(fixtureDef);

        // taking hit
        polygonShape = new PolygonShape();
        polygonShape.setAsBox((float) 6 / GameConfig.PPM, (float) 5 / GameConfig.PPM, new Vector2(0, 0.04f), 0);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = GameConfig.Enemy_BitL0;
        fixtureDef.filter.maskBits = GameConfig.Default_BitL0 | GameConfig.Player_BitL0;
        enemyBody.createFixture(fixtureDef).setUserData(this);

        // attacking sensor
        polygonShape2 = new PolygonShape();
        polygonShape2.setAsBox((float) 5 / GameConfig.PPM, (float) 0.1 / GameConfig.PPM, new Vector2(0, 0.05f), 0);
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = polygonShape2;
        fixtureDef2.isSensor = true;
        enemyBody.createFixture(fixtureDef2).setUserData("flyingEnemy");
    }

    @Override
    public void update(float delta) {
        stateTime += delta;

        if (setToDestroy && !destroyed) {
            world.destroyBody(enemyBody);
            destroyed = true;
        }

        distance += direction.len() * delta;
        if (distance > maxDistance) {
            direction.scl(-1);
            distance = 0;
        }

        enemyBody.setLinearVelocity(direction);

        setPosition(enemyBody.getPosition().x - getWidth() / 2f,
                enemyBody.getPosition().y - getHeight() / 1.8f);
        setRegion(getFrame(delta));

    }


    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case Die:
                region = die.getKeyFrame(stateTime);
                break;
            default:
                region = flying.getKeyFrame(stateTime, true);
        }

        if ((enemyBody.getLinearVelocity().x < 0 || !isTurningRight) && !region.isFlipX()) {
            region.flip(true, false);
            isTurningRight = false;
        } else if ((enemyBody.getLinearVelocity().x > 0 || isTurningRight) && region.isFlipX()) {
            region.flip(true, false);
            isTurningRight = true;
        }

        if (currentState == pastState)
            stateTime += delta;
        else
            stateTime = 0;

        //update previous state
        pastState = currentState;
        //return the final adjusted frame
        return region;
    }

    public FlyingEnemyState getState() {
        return FlyingEnemyState.Flying;
    }

    @Override
    public void playerHit(PlayerClass2 mainPlayer) {
        setToDestroy = true;
    }

    @Override
    public void playAttackAnimation(PlayerClass2 mainPlayer) {

    }

    @Override
    public void dontPlayAttackAnimation(PlayerClass2 mainPlayer) {

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

    }

    @Override
    public void playerDetectionOff(PlayerClass2 mainPlayer) {

    }

    @Override
    public void playerOnLeft(PlayerClass2 mainPlayer) {

    }

    @Override
    public void playerOnRight(PlayerClass2 mainPlayer) {

    }

    @Override
    public void dispose() {
        circleShape.dispose();
        polygonShape.dispose();
    }
}
