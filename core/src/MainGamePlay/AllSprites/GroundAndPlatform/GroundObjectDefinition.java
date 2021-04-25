package MainGamePlay.AllSprites.GroundAndPlatform;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Disposable;

import GameConfiguration.GameConfig;
import MainGamePlay.AllSprites.EnemyClass1.EnemySprite;
import MainGamePlay.Player.MainPlayer;
import MainGamePlay.jungle.jungleLevel1;

public class GroundObjectDefinition extends GroundObjectSprite implements Disposable {
    private jungleLevel1 jungleLevel1;
    private TextureRegion region;

    // Box2d
    private BodyDef bodyDef;
    private PolygonShape polygonShape;
    private FixtureDef fixtureDef;

    public GroundObjectDefinition(jungleLevel1 jungleLevel1, float x, float y) {
        super(jungleLevel1, x, y);
        this.jungleLevel1 = jungleLevel1;
        setSprite();
    }

    private void setSprite() {
        region = new TextureRegion(jungleLevel1.getGroundObjects().findRegion("ground1"), 0, 0, 54, 124);
        setBounds(getX(), getY(), (float) 22 / GameConfig.PPM, (float) 30 / GameConfig.PPM);
        setRegion(region);
    }

    @Override
    public void defineGroundObject() {
        bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;
        GobjectBody = world.createBody(bodyDef);
        polygonShape = new PolygonShape();
        polygonShape.setAsBox((float) 7 / GameConfig.PPM, (float) 15 / GameConfig.PPM, new Vector2(0, 0.04f), 0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        GobjectBody.createFixture(fixtureDef).setUserData("pillar");

    }

    @Override
    public void update(float delta) {
        setPosition(GobjectBody.getPosition().x - getWidth() / 2f,
                GobjectBody.getPosition().y - getHeight() / 2.6f);
    }

    @Override
    public void playerHit(MainPlayer mainPlayer) {

    }

    @Override
    public void enemyHit(EnemySprite enemySprite) {

    }

    @Override
    public void dispose() {
        polygonShape.dispose();
    }
}
