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

public class AnimatedWater extends ItemsSprite implements Disposable {
    // Box2d
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    protected PolygonShape polygonShape;

    private Manager manager = Manager.managerInstance;
    private SettingPref settingPref = SettingPref.instance;
    private Timer timer;

    public enum WaterState {
        MOVE
    }

    public WaterState currentState;
    public WaterState pastState;

    public float stateTime = 0f;
    public boolean isRunning = false;

    private jungleLevel0 jungleLevel0;
    public Animation<TextureRegion> moving;
    public Array<TextureRegion> frames;

    public AnimatedWater(jungleLevel0 jungleLevel0, float x, float y) {
        super(jungleLevel0, x, y);
        this.jungleLevel0 = jungleLevel0;
        itemBody.setActive(false);
        currentState = WaterState.MOVE;
        pastState = WaterState.MOVE;
        timer = new Timer();

        setSprite();
    }

    public void setSprite() {
        frames = new Array<>();
        for (int i = 0; i < 15; i++) {
            frames.add(new TextureRegion(jungleLevel0.getAnimatedWater().findRegion("water"), i * 128, 0, 128, 128));
        }
        moving = new Animation<>(0.10f, frames);
        frames.clear();
        setBounds(getX(), getY(), (float) 600 / GameConfig.PPM, (float) 11 / GameConfig.PPM);
    }

    @Override
    public void defineItems() {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        polygonShape = new PolygonShape();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        itemBody = world.createBody(bodyDef);
        polygonShape.setAsBox((float) 300 / GameConfig.PPM, (float) 1 / GameConfig.PPM, new Vector2(3, -.06f), 0);
        fixtureDef.shape = polygonShape;
        itemBody.createFixture(fixtureDef).setUserData("water");

    }

    @Override
    public void update(float delta) {
        stateTime += delta;
        setPosition(itemBody.getPosition().x - getWidth() / 200f,
                itemBody.getPosition().y - getHeight() / .90f);
        setRegion(getFrames(delta));
    }

    public TextureRegion getFrames(float delta) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            default:
                region = moving.getKeyFrame(stateTime, true);
        }
        stateTime = currentState == pastState ? (stateTime + delta) : 0;
        //update previous state
        pastState = currentState;
        //return the final adjusted frame
        return region;
    }

    public WaterState getState() {
        return WaterState.MOVE;
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
