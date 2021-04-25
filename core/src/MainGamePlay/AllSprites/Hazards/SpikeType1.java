package MainGamePlay.AllSprites.Hazards;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Disposable;

import GameConfiguration.GameConfig;
import MainGamePlay.Player.MainPlayer;
import MainGamePlay.jungle.jungleLevel1;

public class SpikeType1 extends HazardsSprite implements Disposable {

    private jungleLevel1 jungleLevel1;

    private TextureRegion region;

    // Box2d
    private BodyDef bodyDef;
    private PolygonShape polygonShape;
    private FixtureDef fixtureDef;


    public SpikeType1(MainGamePlay.jungle.jungleLevel1 jungleLevel1, float x, float y) {
        super(jungleLevel1, x, y);
        this.jungleLevel1 = jungleLevel1;
        setSprite();
    }

    public void setSprite() {
        region = new TextureRegion(jungleLevel1.getHazardsSprite().findRegion("spikes1"), 0, 0, 32, 32);
        setBounds(getX(), getY() - 10, (float) 30 / GameConfig.PPM, (float) 20 / GameConfig.PPM);
        setRegion(region);
    }

    @Override
    public void defineHazards() {
        bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;
        hazardBody = world.createBody(bodyDef);
        polygonShape = new PolygonShape();
        polygonShape.setAsBox((float) 11.5 / GameConfig.PPM, (float) 8 / GameConfig.PPM, new Vector2(0, 0), 0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        hazardBody.createFixture(fixtureDef).setUserData("spike1");
    }

    @Override
    public void update(float delta) {
        setPosition(hazardBody.getPosition().x - getWidth() / 2f,
                hazardBody.getPosition().y - getHeight() / 2f);
    }

    @Override
    public void playerHit(MainPlayer mainPlayer) {

    }

    @Override
    public void hazardHit(HazardsSprite hazardsSprite) {

    }

    @Override
    public void dispose() {
        polygonShape.dispose();
    }
}
