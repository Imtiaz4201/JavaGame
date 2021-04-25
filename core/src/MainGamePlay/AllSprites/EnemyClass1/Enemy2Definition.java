package MainGamePlay.AllSprites.EnemyClass1;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import GameConfiguration.GameConfig;
import MainGamePlay.Player.MainPlayer;
import MainGamePlay.jungle.jungleLevel1;

public class Enemy2Definition extends EnemySprite implements Disposable {

    public enum EnemyState {
        Flying, Die
    }

    private EnemyState currentState;
    private EnemyState pastState;
    private float stateTime = 0;
    private boolean isTurningRight = false;
    private boolean setToDestroy = false;

    private Array<TextureRegion> frames;
    private Animation<TextureRegion> flying;
    private Animation<TextureRegion> die;

    private Vector2 pos = new Vector2();
    private Vector2 dir = new Vector2();
    private float dist = 0;
    private float maxDist = 0;


    // box2d
    private BodyDef bodyDef;
    private FixtureDef fixtureDef, fixtureDef2;
    private CircleShape circleShape;
    private PolygonShape polygonShape, polygonShape2;

    public Enemy2Definition(jungleLevel1 jungleLevel1, float x, float y) {
        super(jungleLevel1, x, y);
        enemyBody.setActive(false);
        currentState = EnemyState.Flying;
        pastState = EnemyState.Flying;

        pos.x = x;
        pos.y = y;
        dir.x = -.4f;
        dir.y = 0;
        maxDist = 3f;

        setSprite();

    }

    public void setSprite() {
        frames = new Array<>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(jungleLevel1.getFlyingEnemy().findRegion("bf"), i * 16, 0, 16, 24));
        }
        flying = new Animation<>(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(jungleLevel1.getFlyingEnemy().findRegion("bDead"), i * 16, 0, 16, 24));
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
        enemyBody.createFixture(fixtureDef);

        // taking hit
        polygonShape = new PolygonShape();
        polygonShape.setAsBox((float) 6 / GameConfig.PPM, (float) 5 / GameConfig.PPM, new Vector2(0, 0.04f), 0);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = GameConfig.Enemy_Bit;
        fixtureDef.filter.maskBits = GameConfig.Default_Bit | GameConfig.Player_Bit;
        enemyBody.createFixture(fixtureDef).setUserData(this);

        // attacking sensor
        polygonShape2 = new PolygonShape();
        polygonShape2.setAsBox((float) 5 / GameConfig.PPM, (float) 0.1 / GameConfig.PPM, new Vector2(0, 0.06f), 0);
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = polygonShape2;
        fixtureDef2.isSensor = true;
        fixtureDef2.filter.categoryBits = GameConfig.Enemy_Hit_Bit;
        fixtureDef2.filter.maskBits = GameConfig.Default_Bit | GameConfig.Player_Bit | GameConfig.Ground_Bit;
        enemyBody.createFixture(fixtureDef2).setUserData("flyingEnemy");
    }

    @Override
    public void update(float delta) {
        stateTime += delta;

        if (setToDestroy && !destroyed) {
            world.destroyBody(enemyBody);
            destroyed = true;
        }

        dist += dir.len() * delta;
        if (dist > maxDist) {
            dir.scl(-1);
            dist = 0;
        }

        enemyBody.setLinearVelocity(dir);

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

    public EnemyState getState() {
        return EnemyState.Flying;
    }

    @Override
    public void playerHit(MainPlayer mainPlayer) {
        setToDestroy = true;
    }

    @Override
    public void playAttackAnimation(MainPlayer mainPlayer) {

    }

    @Override
    public void dontPlayAttackAnimation(MainPlayer mainPlayer) {

    }

    @Override
    public void enemyHit() {

    }

    @Override
    public void dispose() {
        circleShape.dispose();
        polygonShape.dispose();
    }
} // flying enemy (bat)
