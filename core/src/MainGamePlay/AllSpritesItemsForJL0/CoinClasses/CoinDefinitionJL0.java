package MainGamePlay.AllSpritesItemsForJL0.CoinClasses;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import AssetsManager.Manager;
import GameConfiguration.GameConfig;
import MainGamePlay.Player2.PlayerClass2;
import MainGamePlay.jungle.jungleLevel0;
import Settings.SettingPref;

public class CoinDefinitionJL0 extends CoinSpriteJL0 implements Disposable {

    private jungleLevel0 jungleLevel0;

    private enum CoinStateForJL0 {
        RotatingL, RotatingR
    }

    private CoinStateForJL0 currentState;
    private CoinStateForJL0 pastState;
    private Animation<TextureRegion> rotate;
    private Array<TextureRegion> frames;
    private float stateTime = 0;
    private Sound coinSFX;
    private SettingPref settingPref = SettingPref.instance;
    private Manager manager = Manager.managerInstance;

    // Box2d physics
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private CircleShape circleShape;
    private MainGamePlay.gameHub gameHub;

    public CoinDefinitionJL0(jungleLevel0 jungleLevel0, float x, float y) {
        super(jungleLevel0, x, y);
        this.jungleLevel0 = jungleLevel0;
        coinSFX = manager.assetManager.get(manager.coinSound, Sound.class);
        setCoinSprite();
    }

    private void setCoinSprite() {
        frames = new Array<>();
        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(jungleLevel0.getCoinAtlas().findRegion("coin"), i * 16, 0, 16, 16));
        }
        rotate = new Animation<TextureRegion>(0.2f, frames);
        frames.clear();
        setBounds(getX(), getY(), (float) 20 / GameConfig.PPM, (float) 20 / GameConfig.PPM);
    }

    public void playCoinSFX() {
        if (settingPref.sfx == true) {
            coinSFX.play(settingPref.sfxVolume);
        } else if (settingPref.sfx == false) {
            coinSFX.stop();
        }
    }

    @Override
    public void defineCoin() {
        bodyDef = new BodyDef();
        circleShape = new CircleShape();
        fixtureDef = new FixtureDef();

        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        coinBody = world.createBody(bodyDef);
        circleShape.setRadius((float) 6 / GameConfig.PPM);
        fixtureDef.shape = circleShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = GameConfig.Coin_BitL0;
        fixtureDef.filter.maskBits = GameConfig.Player_BitL0;
        coinBody.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void update(float delta) {
        stateTime += delta;

        // condition when the coin will destroy
        if (setToDestroy && !destroyed) {
            playCoinSFX();
            world.destroyBody(coinBody);
            destroyed = true;
        }
        setPosition(coinBody.getPosition().x - getWidth() / 2f,
                coinBody.getPosition().y - getHeight() / 1.9f);
        setRegion(getFrames(delta));

    }

    public TextureRegion getFrames(float delta) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            default:
                region = rotate.getKeyFrame(stateTime, true);
        }
        stateTime = currentState == pastState ? (stateTime + delta) : 0;
        //update previous state
        pastState = currentState;
        //return the final adjusted frame
        return region;
    }

    public CoinStateForJL0 getState() {
        return CoinStateForJL0.RotatingL;
    }

    @Override
    public void playerHit(PlayerClass2 mainPlayer) {
        setToDestroy = true;
        MainGamePlay.gameHub.addScore(1); // will increase the amount of coin when get touched
    }

    @Override
    public void callGameOverScreen(PlayerClass2 mainPlayer) {

    }

    @Override
    public void dispose() {
        circleShape.dispose();
        coinSFX.dispose();
    }
}
