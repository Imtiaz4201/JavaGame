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

public class AnimatedBirdNPC extends ItemsSprite implements Disposable {

    // box2d
    private BodyDef bodyDef;
    private FixtureDef fixtureDef, fixtureDef2;
    private CircleShape circleShape;
    private PolygonShape polygonShape;

    private Manager manager = Manager.managerInstance;
    private SettingPref settingPref = SettingPref.instance;
    private Timer timer;

    private enum BirdState {
        STILL, FLY
    }

    private BirdState currentState;
    private BirdState pastState;

    private Array<TextureRegion> frames;
    private Animation<TextureRegion> flying;
    private Animation<TextureRegion> still;
    private boolean playerContact = false;

    private float stateTime = 0;
    private boolean isTurningRight = false;

    public AnimatedBirdNPC(MainGamePlay.jungle.jungleLevel0 jungleLevel0, float x, float y) {
        super(jungleLevel0, x, y);
        itemBody.setActive(false);
        currentState = BirdState.STILL;
        pastState = BirdState.STILL;
        setSprite();
    }

    public void setSprite() {
        frames = new Array<>();
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(jungleLevel0.getBirdNPC().findRegion("birdS"), i * 16, 0, 16, 16));
        }
        still = new Animation<>(1f, frames);
        frames.clear();

        for (int i = 0; i < 10; i++) {
            frames.add(new TextureRegion(jungleLevel0.getBirdNPC().findRegion("birdFly"), i * 16, 0, 16, 16));
        }
        flying = new Animation<>(0.1f, frames);
        frames.clear();

        setBounds(getX(), getY(), (float) 10 / GameConfig.PPM, (float) 10 / GameConfig.PPM);
    }

    @Override
    public void defineItems() {
        bodyDef = new BodyDef();
        circleShape = new CircleShape();
        fixtureDef = new FixtureDef();
        polygonShape = new PolygonShape();

        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        itemBody = world.createBody(bodyDef);

        // body sensor
        circleShape.setRadius((float) 1 / GameConfig.PPM);
        fixtureDef.shape = circleShape;
        fixtureDef.isSensor = true;
        itemBody.createFixture(fixtureDef);

        // contact sensor
        polygonShape = new PolygonShape();
        fixtureDef2 = new FixtureDef();
        polygonShape.setAsBox((float) 10 / GameConfig.PPM, (float) 60 / GameConfig.PPM, new Vector2(0f, -.10f), 0);
        fixtureDef2.shape = polygonShape;
        fixtureDef2.isSensor = true;
        fixtureDef2.filter.categoryBits = GameConfig.Bird_BITL0;
        fixtureDef2.filter.maskBits = GameConfig.Player_BitL0;
        itemBody.createFixture(fixtureDef2).setUserData(this);
    }

    @Override
    public void update(float delta) {

        if (playerContact) {
            stateTime += delta;
        }

        if (playerContact) {
            setRegion(flying.getKeyFrame(stateTime, true));
        }
        if (playerContact) {
            itemBody.setLinearVelocity(new Vector2(-0.15f, 0));
        }
        if (playerContact && !destroyed) {
            if (stateTime > 80f) {
                world.destroyBody(itemBody);
                destroyed = true;
            }
        }
        setPosition(itemBody.getPosition().x - getWidth() / 2f,
                itemBody.getPosition().y - getHeight() / 1.8f);
        setRegion(getFrame(delta));
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case FLY:
                region = flying.getKeyFrame(stateTime, true);
                break;
            default:
                region = still.getKeyFrame(stateTime, true);
        }

        if ((itemBody.getLinearVelocity().x < 0 || !isTurningRight) && region.isFlipX()) {
            region.flip(true, false);
            isTurningRight = false;
        } else if ((itemBody.getLinearVelocity().x > 0 || isTurningRight) && !region.isFlipX()) {
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

    public BirdState getState() {
        if (itemBody.getLinearVelocity().x != 0) {
            return BirdState.FLY;
        } else
            return BirdState.STILL;
    }

    @Override
    public void playerHit(PlayerClass2 mainPlayer) {
        playerContact = true;
    }

    @Override
    public void playerNotHit(PlayerClass2 mainPlayer) {

    }

    @Override
    public void enemyHit(PlayerClass2 mainPlayer) {

    }

    @Override
    public void destroyed() {

    }

    @Override
    public void dispose() {
        circleShape.dispose();
        polygonShape.dispose();
    }
}
