package MainGamePlay.AllSpritesItemsForJL0.HazardsClasses.wallHazard;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;

import AssetsManager.Manager;
import GameConfiguration.GameConfig;
import MainGamePlay.AllSpritesItemsForJL0.Items.ItemsSprite;
import MainGamePlay.Player2.PlayerClass2;
import MainGamePlay.jungle.jungleLevel0;
import Settings.SettingPref;

public class Blade extends ItemsSprite implements Disposable {
    // Box2d
    private BodyDef bodyDef;
    private PolygonShape polygonShape;
    private CircleShape circleShape;
    private FixtureDef fixtureDef, fixtureDef2;

    private Manager manager = Manager.managerInstance;
    private SettingPref settingPref = SettingPref.instance;
    private Timer timer;
    private float stateTime = 0;

    private enum bladeState {
        Rotating
    }

    private bladeState currentState;
    private bladeState pastState;

    private jungleLevel0 jungleLevel0;
    private Array<TextureRegion> frames;
    private Animation<TextureRegion> rotate;

    private Vector2 position = new Vector2();
    private Vector2 direction = new Vector2();
    private float distance = 0;
    private float maxDistance = 0;

    public Blade(jungleLevel0 jungleLevel0, float x, float y) {
        super(jungleLevel0, x, y);
        this.jungleLevel0 = jungleLevel0;
        itemBody.setActive(false);
        currentState = bladeState.Rotating;
        pastState = bladeState.Rotating;

        position.x = x;
        position.y = y;
        direction.x = 0;
        direction.y = -.3f;
        maxDistance = .85f;

        setSprite();
    }

    public void setSprite() {
        frames = new Array<>();
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(jungleLevel0.getHazardsSprite().findRegion("blade"), i * 32, 0, 32, 32));
        }
        rotate = new Animation<>(0.1f, frames);
        frames.clear();

        setBounds(getX(), getY(), (float) 25 / GameConfig.PPM, (float) 25 / GameConfig.PPM);
    }

    @Override
    public void defineItems() {
        bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        itemBody = world.createBody(bodyDef);
        circleShape = new CircleShape();
        circleShape.setRadius((float) 8 / GameConfig.PPM);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.isSensor = true;
        itemBody.createFixture(fixtureDef);

        // hit senor
        polygonShape = new PolygonShape();
        fixtureDef2 = new FixtureDef();
        polygonShape.setAsBox((float) 10 / GameConfig.PPM, (float) 10 / GameConfig.PPM, new Vector2(.01f, 0), 0);
        fixtureDef2.shape = polygonShape;
        fixtureDef2.isSensor = true;
        itemBody.createFixture(fixtureDef2).setUserData("Blade");
    }

    @Override
    public void update(float delta) {
        stateTime += delta;

        distance += direction.len() * delta;
        if (distance > maxDistance) {
            direction.scl(-1);
            distance = 0;
        }
        itemBody.setLinearVelocity(direction);

        setPosition(itemBody.getPosition().x - getWidth() / 2.5f,
                itemBody.getPosition().y - getHeight() / 1.8f);
        setRegion(getFrames(delta));
    }

    public TextureRegion getFrames(float delta) {
        currentState = getState();
        TextureRegion region;
        region = rotate.getKeyFrame(stateTime, true);

        if (currentState == pastState)
            stateTime += delta;
        else
            stateTime = 0;

        //update previous state
        pastState = currentState;
        //return the final adjusted frame
        return region;
    }

    public bladeState getState() {
        return bladeState.Rotating;
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

    }
}
