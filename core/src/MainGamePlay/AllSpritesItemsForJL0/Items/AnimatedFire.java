package MainGamePlay.AllSpritesItemsForJL0.Items;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
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

public class AnimatedFire extends ItemsSprite implements Disposable {

    // Box2d
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    protected PolygonShape polygonShape;

    private Manager manager = Manager.managerInstance;
    private SettingPref settingPref = SettingPref.instance;
    private Timer timer;

    public enum FireState {
        BURN
    }

    public FireState currentState;
    public FireState pastState;

    public float stateTime = 0f;
    public boolean isRunning = false;
    private jungleLevel0 jungleLevel0;
    public Animation<TextureRegion> burning;
    public Array<TextureRegion> frames;

    public AnimatedFire(jungleLevel0 jungleLevel0, float x, float y) {
        super(jungleLevel0, x, y);
        this.jungleLevel0 = jungleLevel0;
        itemBody.setActive(false);
        currentState = FireState.BURN;
        pastState = FireState.BURN;
        timer = new Timer();

        setSprite();
    }

    public void setSprite() {
        frames = new Array<>();
        for (int i = 0; i < 15; i++) {
            frames.add(new TextureRegion(jungleLevel0.getAnimatedFire().findRegion("fire_column_medium"), i * 45, 0, 45, 90));
        }
        burning = new Animation<>(0.20f, frames);
        frames.clear();
        setBounds(getX(), getY(), (float) 30 / GameConfig.PPM, (float) 60 / GameConfig.PPM);
    }

    @Override
    public void defineItems() {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        polygonShape = new PolygonShape();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        itemBody = world.createBody(bodyDef);
        polygonShape.setAsBox((float) 9 / GameConfig.PPM, (float) 20 / GameConfig.PPM, new Vector2(0, 0), 0);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        itemBody.createFixture(fixtureDef).setUserData("fire");
    }

    @Override
    public void update(float delta) {
        stateTime += delta;
        setPosition(itemBody.getPosition().x - getWidth() / 2f,
                itemBody.getPosition().y - getHeight() / 3.9f);
        setRegion(getFrames(delta));
    }

    public TextureRegion getFrames(float delta) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            default:
                region = burning.getKeyFrame(stateTime, true);
        }
        stateTime = currentState == pastState ? (stateTime + delta) : 0;
        //update previous state
        pastState = currentState;
        //return the final adjusted frame
        return region;
    }

    public FireState getState() {
        return FireState.BURN;
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
        polygonShape.dispose();
    }
}
