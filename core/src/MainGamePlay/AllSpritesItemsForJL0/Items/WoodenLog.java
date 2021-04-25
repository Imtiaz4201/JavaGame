package MainGamePlay.AllSpritesItemsForJL0.Items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;

import AssetsManager.Manager;
import GameConfiguration.GameConfig;
import MainGamePlay.Player2.PlayerClass2;
import MainGamePlay.gameHub;
import MainGamePlay.jungle.jungleLevel0;
import Settings.SettingPref;

public class WoodenLog extends ItemsSprite implements Disposable {
    // Box2d
    private BodyDef bodyDef;
    private FixtureDef fixtureDef1, fixtureDef2, fixtureDef3;
    protected PolygonShape polygonShape1, polygonShape2, polygonShape3;

    private Vector2 position = new Vector2();
    private Vector2 direction = new Vector2();
    private float distance = 0;
    private float maxDistance = 0;

    private TextureRegion region;
    private jungleLevel0 jungleLevel0;
    private boolean activate = false;

    public WoodenLog(jungleLevel0 jungleLevel0, float x, float y) {
        super(jungleLevel0, x, y);
        this.jungleLevel0 = jungleLevel0;
        itemBody.setActive(true);
        position.x = x;
        position.y = y;
        direction.x = -.3f;
        direction.y = 0;
        maxDistance = 4f;

        setSprite();
    }

    public void setSprite() {
        region = new TextureRegion(jungleLevel0.getWoodenLog().findRegion("movingP"), 0, 0, 177, 110);
        setBounds(getX(), getY() - 10, (float) 100 / GameConfig.PPM, (float) 60 / GameConfig.PPM);
        setRegion(region);
    }

    @Override
    public void defineItems() {
        bodyDef = new BodyDef();
        fixtureDef1 = new FixtureDef();
        fixtureDef2 = new FixtureDef();
        fixtureDef3 = new FixtureDef();
        polygonShape1 = new PolygonShape();
        polygonShape2 = new PolygonShape();
        polygonShape3 = new PolygonShape();

        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        itemBody = world.createBody(bodyDef);
        polygonShape1.setAsBox((float) 31 / GameConfig.PPM, (float) 1 / GameConfig.PPM, new Vector2(0, -.099f), 0);
        polygonShape2.setAsBox((float) 8 / GameConfig.PPM, (float) 1 / GameConfig.PPM, new Vector2(.3f, -.099f), 180);
        polygonShape3.setAsBox((float) 8 / GameConfig.PPM, (float) 1 / GameConfig.PPM, new Vector2(-.28f, -.098f), -180);
        fixtureDef1.shape = polygonShape1;
        fixtureDef2.shape = polygonShape2;
        fixtureDef3.shape = polygonShape3;
        fixtureDef1.friction = 7;
        fixtureDef1.restitution = 0;
        fixtureDef2.friction = 7;
        fixtureDef2.restitution = .5f;
        fixtureDef3.friction = 7;
        fixtureDef3.restitution = .5f;
        itemBody.createFixture(fixtureDef1).setUserData("wodLog1");
        itemBody.createFixture(fixtureDef2).setUserData("wodLog2");
        itemBody.createFixture(fixtureDef3).setUserData("wodLog3");
    }

    @Override
    public void update(float delta) {

        distance += direction.len() * delta;
        if (distance > maxDistance) {
            direction.scl(-1);
            distance = 0;
        }
        itemBody.setLinearVelocity(direction);


        setPosition(itemBody.getPosition().x - getWidth() / 2f,
                itemBody.getPosition().y - getHeight() / 2f);
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
        polygonShape1.dispose();
        polygonShape2.dispose();
        polygonShape3.dispose();
    }
}
