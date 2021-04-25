package MainGamePlay.AllSprites.EnemyClass1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;

import GameConfiguration.GameConfig;
import MainGamePlay.Player.MainPlayer;
import MainGamePlay.jungle.jungleLevel1;

public class EnemyDefinition extends EnemySprite implements Disposable {
    private Fixture fixture;

    public enum enemyState {
        Idle, Attacking, Walk, Hit
    }

    private enemyState currentState;
    private enemyState pastState;
    private boolean isTurningRight = true;
    private float stateTime = 0;
    private MainPlayer mainPlayer;
    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> attack;
    private Animation<TextureRegion> walk;
    private Animation<TextureRegion> hit;
    private Timer timer;
    private int enemyHealth = 3;

    private CircleShape circleShape;
    private PolygonShape polygonShape,polygonShape2;

    private boolean attacking = false;
    private boolean setToDestroy = false;

    public EnemyDefinition(jungleLevel1 jungleLevel1, float x, float y) {
        super(jungleLevel1, x, y);
        enemyBody.setActive(false);
        timer = new Timer();
        mainPlayer = new MainPlayer();
        currentState = enemyState.Idle;
        pastState = enemyState.Idle;
        setSprite();

    }

    public void setSprite() {
        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(jungleLevel1.getEnemy1Atlas().findRegion("idle"), i * 64, 0, 64, 64));
        }
        idle = new Animation<>(0.2f, frames);
        frames.clear();

        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(jungleLevel1.getEnemy1Atlas().findRegion("attack"), i * 64, 0, 64, 64));
        }
        attack = new Animation<>(0.15f, frames);
        frames.clear();

        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(jungleLevel1.getEnemy1Atlas().findRegion("walk"), i * 64, 0, 64, 64));
        }
        walk = new Animation<>(0.15f, frames);
        frames.clear();

        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(jungleLevel1.getEnemy1Atlas().findRegion("hit"), i * 64, 0, 64, 64));
        }
        hit = new Animation<>(0.1f, frames);
        frames.clear();


        setBounds(getX(), getY(), (float) 40 / GameConfig.PPM, (float) 40 / GameConfig.PPM);
    }

    @Override
    public void update(float delta) {
        stateTime += delta;
        if (attacking == true) {
            setRegion(attack.getKeyFrame(stateTime, true));
        }
        // condition when the enemy will destroy
        if (enemyHealth <= 0) {
            setToDestroy = true;
        }
        if (setToDestroy && !destroyed) {
            setRegion(hit.getKeyFrame(stateTime, true));
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    world.destroyBody(enemyBody);
                    destroyed = true;
                }
            }, 1);
            timer.start();

        } else if (!destroyed) {
            setPosition(enemyBody.getPosition().x - getWidth() / 1.3f,
                    enemyBody.getPosition().y - getHeight() / 1.9f);
            setRegion(walk.getKeyFrame(stateTime, true));
        }

        enemyBody.setLinearVelocity(vector2);
        setPosition(enemyBody.getPosition().x - getWidth() / 1.5f,
                enemyBody.getPosition().y - getHeight() / 3f);
        setRegion(getFrames(delta));
    }


    public TextureRegion getFrames(float delta) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case Attacking:
                region = attack.getKeyFrame(stateTime, true);
                break;
            case Walk:
                region = walk.getKeyFrame(stateTime, true);
                break;
            case Hit:
                region = hit.getKeyFrame(stateTime);
                break;
            default:
                region = idle.getKeyFrame(stateTime, true);
        }
        if ((enemyBody.getLinearVelocity().x < 0 || !isTurningRight) && !region.isFlipX()) {
            region.flip(true, false);
            isTurningRight = false;
            setRegion(walk.getKeyFrame(stateTime, true));
        } else if ((enemyBody.getLinearVelocity().x > 0 || isTurningRight) && region.isFlipX()) {
            region.flip(true, false);
            isTurningRight = true;
            setRegion(walk.getKeyFrame(stateTime, true));
        }

        stateTime = currentState == pastState ? (stateTime + delta) : 0;
        //update previous state
        pastState = currentState;
        //return the final adjusted frame
        return region;

    }

    public enemyState getState() {

        if (attacking == true) {
            return enemyState.Attacking;
        } else if (setToDestroy == true) {
            return enemyState.Hit;
        } else
            return enemyState.Idle;
    }


    @Override
    public void defineEnemy() {

        // enemy main body sensor
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        enemyBody = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        circleShape = new CircleShape();
        circleShape.setRadius((float) 6 / GameConfig.PPM);
        fixtureDef.shape = circleShape;
        enemyBody.createFixture(fixtureDef);

        // taking hit sensor
        polygonShape = new PolygonShape();
        FixtureDef fixtureDef1 = new FixtureDef();
        polygonShape.setAsBox((float) 9 / GameConfig.PPM, (float) 10 / GameConfig.PPM, new Vector2(0, 0.04f), 0);
        fixtureDef1.shape = polygonShape;
        fixtureDef1.isSensor = true;
        fixtureDef1.filter.categoryBits = GameConfig.Enemy_Bit;
        fixtureDef1.filter.maskBits = GameConfig.Default_Bit | GameConfig.Player_Bit;
        enemyBody.createFixture(fixtureDef1).setUserData(this);

        // attacking sensor
        polygonShape2 = new PolygonShape();
        polygonShape2.setAsBox((float) 7 / GameConfig.PPM, (float) 0.1 / GameConfig.PPM, new Vector2(0, 0.06f), 0);
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = polygonShape2;
        fixtureDef2.isSensor = true;
        fixtureDef2.filter.categoryBits = GameConfig.Enemy_Hit_Bit;
        fixtureDef2.filter.maskBits = GameConfig.Default_Bit | GameConfig.Player_Bit | GameConfig.Ground_Bit;
        enemyBody.createFixture(fixtureDef2).setUserData("enemyG1");
    }


    @Override
    public void playerHit(MainPlayer mainPlayer) {
        enemyHealth -= 1;
    }

    @Override
    public void playAttackAnimation(MainPlayer mainPlayer) {
        attacking = true;
    }

    @Override
    public void dontPlayAttackAnimation(MainPlayer mainPlayer) {
        attacking = false;
    }

    @Override
    public void enemyHit() {

    }

    @Override
    public void dispose() {
        circleShape.dispose();
        polygonShape.dispose();
        polygonShape2.dispose();

    }
}
