package MainGamePlay.AllSprites.Hazards;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import GameConfiguration.GameConfig;
import MainGamePlay.Player.MainPlayer;
import MainGamePlay.jungle.jungleLevel1;

public class AnimatedHazardsDefinition extends HazardsSprite implements Disposable {
    private jungleLevel1 jungleLevel1;

    public enum hazardState {
        Rotating
    }

    private hazardState currentState;
    private hazardState pastState;

    private float stateTime = 0;
    private boolean isTurningRight = false;

    private Array<TextureRegion> frames;
    private Animation<TextureRegion> rotate;
    private Animation<TextureRegion> rotateL;

    // Box2d physics
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private CircleShape circleShape;
    private PolygonShape polygonShape;

    public AnimatedHazardsDefinition(jungleLevel1 jungleLevel1, float x, float y) {
        super(jungleLevel1, x, y);
        this.jungleLevel1 = jungleLevel1;
        currentState = hazardState.Rotating;
        pastState = hazardState.Rotating;
        hazardBody.setActive(true);
        setSprite();
    }

    public void setSprite() {

        frames = new Array<>();
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(jungleLevel1.getHazardsSprite().findRegion("blade"), i * 32, 0, 32, 32));
        }
        rotate = new Animation<>(0.1f, frames);
        frames.clear();

        setBounds(getX(), getY(), (float) 25 / GameConfig.PPM, (float) 25 / GameConfig.PPM);
    }

    @Override
    public void defineHazards() {
        bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;
        hazardBody = world.createBody(bodyDef);
        circleShape = new CircleShape();
        circleShape.setRadius((float) 8 / GameConfig.PPM);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        hazardBody.createFixture(fixtureDef).setUserData(this);

        // hit senor
        polygonShape = new PolygonShape();
        polygonShape.setAsBox((float) 10 / GameConfig.PPM, (float) 10 / GameConfig.PPM, new Vector2(0, 0), 0);
        fixtureDef.shape = polygonShape;
        hazardBody.createFixture(fixtureDef).setUserData("AniBlade");
    }

    @Override
    public void update(float delta) {
        stateTime += delta;

        setPosition(hazardBody.getPosition().x - getWidth() / 2f,
                hazardBody.getPosition().y - getHeight() / 1.8f);
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

    public hazardState getState() {

        return hazardState.Rotating;
    }

    @Override
    public void playerHit(MainPlayer mainPlayer) {

    }

    @Override
    public void hazardHit(HazardsSprite hazardsSprite) {

    }

    @Override
    public void dispose() {
        circleShape.dispose();
        polygonShape.dispose();
    }
}
