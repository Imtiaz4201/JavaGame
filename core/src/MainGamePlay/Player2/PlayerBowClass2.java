package MainGamePlay.Player2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import GameConfiguration.GameConfig;
import MainGamePlay.jungle.jungleLevel0;

public class PlayerBowClass2 extends Sprite implements Disposable {

    private jungleLevel0 jungleLevel0;
    private World world;
    private float stateTime = 0;
    private TextureRegion region;
    private boolean destroyed = false;
    private boolean setToDestroy = false;
    private boolean shootRight;
    private float x, y;

    // box2d
    private Body body;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private CircleShape circleShape;

    private PlayerClass2 playerClass2;

    public PlayerBowClass2(jungleLevel0 jungleLevel0, float x, float y, boolean shootRight) {
        playerClass2 = new PlayerClass2();
        this.jungleLevel0 = jungleLevel0;
        world = jungleLevel0.getWorld();
        this.shootRight = shootRight;
        this.x = x;
        this.y = y;
        setSprite();
        defineWeapon();
    }

    public void setSprite() {
        region = new TextureRegion(jungleLevel0.getPlayerArrow().findRegion("arrow"), 0, 0, 120, 95);
        setBounds(x, y, (float) 20 / GameConfig.PPM, (float) 6 / GameConfig.PPM);
        setRegion(region);
    }

    public void defineWeapon() {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        circleShape = new CircleShape();
        bodyDef.position.set(getX() + 8f / GameConfig.PPM, getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        if (!world.isLocked()) {
            body = world.createBody(bodyDef);
            body.setActive(true);
        }
        circleShape.setRadius((float) .5 / GameConfig.PPM);
        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef).setUserData("bulletL0");
        body.setLinearVelocity(new Vector2(shootRight ? 5 : -5, 0.5f)); // bullet range

    }

    public void update(float delta) {
        stateTime += delta;
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2f);
        if (((stateTime > 1f || setToDestroy) && !destroyed) || (stateTime > 1f && !destroyed)) {
            world.destroyBody(body);
            destroyed = true;
        } else if (setToDestroy == true) {
            world.destroyBody(body);
            destroyed = true;
        }

        if (body.getLinearVelocity().y > 1f) {
            body.setLinearVelocity(body.getLinearVelocity().x, 3f);
        }
        if ((shootRight && body.getLinearVelocity().x < 0) || (!shootRight && body.getLinearVelocity().x > 0)) {
            setToDestroy();
        }

    }

    public void setToDestroy() {
        setToDestroy = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void dispose() {
        circleShape.dispose();
    }
}
