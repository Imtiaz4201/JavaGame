package MainGamePlay.AllSpritesItemsForJL0.HazardsClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Disposable;

import GameConfiguration.GameConfig;
import MainGamePlay.Player2.PlayerClass2;
import MainGamePlay.jungle.jungleLevel0;

public class SpikeType1JL0 extends HazardSpriteJL0 implements Disposable {
    private TextureRegion region;
    // Box2d
    private BodyDef bodyDef;
    private PolygonShape polygonShape;
    private FixtureDef fixtureDef;


    public SpikeType1JL0(MainGamePlay.jungle.jungleLevel0 jungleLevel0, float x, float y) {
        super(jungleLevel0, x, y);
        this.jungleLevel0 = jungleLevel0;
        hazardBody.setActive(false);
        setSprite();
    }


    public void setSprite() {
        region = new TextureRegion(jungleLevel0.getHazardsSprite().findRegion("spikes1"), 0, 0, 32, 32);
        setBounds(getX(), getY() - 10, (float) 25 / GameConfig.PPM, (float) 10 / GameConfig.PPM);
        setRegion(region);
    }

    @Override
    public void defineHazards() {
        bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        hazardBody = world.createBody(bodyDef);
        polygonShape = new PolygonShape();
        polygonShape.setAsBox((float) 9 / GameConfig.PPM, (float) .5 / GameConfig.PPM, new Vector2(0, -.1f), 0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        hazardBody.createFixture(fixtureDef).setUserData("spike1JL0");
    }

    @Override
    public void update(float delta) {
        setPosition(hazardBody.getPosition().x - getWidth() / 2f,
                hazardBody.getPosition().y - getHeight() / .80f);
    }

    @Override
    public void playerHit(PlayerClass2 mainPlayer) {

    }

    @Override
    public void hazardHit(HazardSpriteJL0 hazardsSprite) {

    }

    @Override
    public void dispose() {
        polygonShape.dispose();
    }
}
