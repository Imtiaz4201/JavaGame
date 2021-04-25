package MainGamePlay.AllSpritesItemsForJL0.Items;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;

import AssetsManager.Manager;
import GameConfiguration.GameConfig;
import MainGamePlay.Player2.PlayerClass2;
import MainGamePlay.jungle.jungleLevel0;
import Settings.SettingPref;

public class AnimatedButterFlyStill extends ItemsSprite implements Disposable {

    // box2d
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private CircleShape circleShape;

    private Manager manager = Manager.managerInstance;
    private SettingPref settingPref = SettingPref.instance;
    private Timer timer;

    private enum ButterFlyRedStillState {
        STILL
    }

    private ButterFlyRedStillState currentState;
    private ButterFlyRedStillState pastState;

    private Array<TextureRegion> frames;
    private Animation<TextureRegion> flying;

    private float stateTime = 0;
    private boolean isTurningRight = false;

    public AnimatedButterFlyStill(MainGamePlay.jungle.jungleLevel0 jungleLevel0, float x, float y) {
        super(jungleLevel0, x, y);
        itemBody.setActive(false);
        currentState = ButterFlyRedStillState.STILL;
        pastState = ButterFlyRedStillState.STILL;
        setSprite();
    }

    public void setSprite() {
        frames = new Array<>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(jungleLevel0.getButterFliesNPC().findRegion("BR"), i * 32, 0, 32, 32));
        }
        flying = new Animation<>(0.4f, frames);
        frames.clear();

        setBounds(getX(), getY(), (float) 10 / GameConfig.PPM, (float) 10 / GameConfig.PPM);
    }

    @Override
    public void defineItems() {
        bodyDef = new BodyDef();
        circleShape = new CircleShape();
        fixtureDef = new FixtureDef();

        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        itemBody = world.createBody(bodyDef);

        // body sensor
        circleShape.setRadius((float) 1 / GameConfig.PPM);
        fixtureDef.shape = circleShape;
        fixtureDef.isSensor = true;
        itemBody.createFixture(fixtureDef);
    }

    @Override
    public void update(float delta) {
        stateTime += delta;

        setPosition(itemBody.getPosition().x - getWidth() / 2f,
                itemBody.getPosition().y - getHeight() / 1.8f);
        setRegion(getFrame(delta));
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion region;

        region = flying.getKeyFrame(stateTime, true);
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

    public ButterFlyRedStillState getState() {
        return ButterFlyRedStillState.STILL;
    }

    @Override
    public void playerHit(PlayerClass2 mainPlayer) {

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
    }
}
