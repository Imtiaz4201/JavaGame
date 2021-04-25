package MainGamePlay.AllSprites.CoinsClass;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import GameConfiguration.GameConfig;
import Levels.jungleLevels;
import MainGamePlay.Player.MainPlayer;
import MainGamePlay.jungle.jungleLevel1;

public class DiamondDefinition extends CoinSprite implements Disposable {
    private jungleLevel1 jungleLevel1;

    private Animation<TextureRegion> diamondAnimation;
    private Array<TextureRegion> frames;
    private float stateTime = 0;

    private jungleLevels jungleLevels;

    // Box2d physics
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private CircleShape circleShape;

    public DiamondDefinition(MainGamePlay.jungle.jungleLevel1 jungleLevel1, float x, float y) {
        super(jungleLevel1, x, y);
        this.jungleLevel1 = jungleLevel1;
        jungleLevels = new jungleLevels();
        setSprite();
    }

    public void setSprite() {
        frames = new Array<>();
        for (int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(jungleLevel1.getCoinAtlas().findRegion("diamond"), i * 16, 0, 16, 16));
        }
        diamondAnimation = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        setBounds(getX(), getY(), (float) 20 / GameConfig.PPM, (float) 20 / GameConfig.PPM);
    }

    @Override
    public void defineCoin() {
        bodyDef = new BodyDef();
        circleShape = new CircleShape();
        fixtureDef = new FixtureDef();

        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;
        coinBody = world.createBody(bodyDef);
        circleShape.setRadius((float) 6 / GameConfig.PPM);
        fixtureDef.shape = circleShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = GameConfig.Coin_Bit;
        fixtureDef.filter.maskBits = GameConfig.Default_Bit | GameConfig.Player_Bit;
        coinBody.createFixture(fixtureDef).setUserData(this);

    }

    @Override
    public void update(float delta) {
        stateTime += delta;
        // condition when the coin will destroy
        if (setToDestroy && !destroyed) {
            world.destroyBody(coinBody);
            destroyed = true;
        }
        setPosition(coinBody.getPosition().x - getWidth() / 2f,
                coinBody.getPosition().y - getHeight() / 1.9f);
        setRegion(getFrames(delta));
    }

    public TextureRegion getFrames(float delta) {
        TextureRegion region;
        region = diamondAnimation.getKeyFrame(stateTime, true);
        return region;
    }

    @Override
    public void playerHit(MainPlayer mainPlayer) {
        setToDestroy = true;
    }

    @Override
    public void dispose() {
        circleShape.dispose();
    }
}
