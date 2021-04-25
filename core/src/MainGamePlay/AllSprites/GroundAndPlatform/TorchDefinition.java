package MainGamePlay.AllSprites.GroundAndPlatform;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import GameConfiguration.GameConfig;
import MainGamePlay.AllSprites.EnemyClass1.EnemySprite;
import MainGamePlay.Player.MainPlayer;
import MainGamePlay.jungle.jungleLevel1;

public class TorchDefinition extends GroundObjectSprite implements Disposable {
    private jungleLevel1 jungleLevel1;

    private enum torchState {
        RotateLeft, RotateRight
    }

    private torchState currentState;
    private torchState pastState;
    private float stateTime = 0;
    private boolean isRotatingRight = false;

    private Array<TextureRegion> frames;
    private Animation<TextureRegion> rotateLeft;
    private Animation<TextureRegion> rotateRight;

    // Box2d
    private BodyDef bodyDef;
    private PolygonShape polygonShape;
    private FixtureDef fixtureDef;

    public TorchDefinition(MainGamePlay.jungle.jungleLevel1 jungleLevel1, float x, float y) {
        super(jungleLevel1, x, y);
        this.jungleLevel1 = jungleLevel1;
        setSprite();
    }

    public void setSprite() {
        frames = new Array<>();
        for (int i = 0; i < 3; i++) {
            frames.add(new TextureRegion(jungleLevel1.getTorchAtlas().findRegion("torchA"), i * 16, 0, 16, 32));
        }
        rotateLeft = new Animation<>(0.1f, frames);
        frames.clear();
        for (int i = 0; i < 3; i++) {
            frames.add(new TextureRegion(jungleLevel1.getTorchAtlas().findRegion("torchB"), i * 16, 0, 16, 32));
        }
        rotateRight = new Animation<>(0.1f, frames);
        frames.clear();

        setBounds(getX(), getY(), (float) 20 / GameConfig.PPM, (float) 22 / GameConfig.PPM);
    }

    @Override
    public void defineGroundObject() {
        bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;
        GobjectBody = world.createBody(bodyDef);
        polygonShape = new PolygonShape();
        polygonShape.setAsBox((float) 2 / GameConfig.PPM, (float) 2 / GameConfig.PPM, new Vector2(0.02f, -0.04f), 0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        GobjectBody.createFixture(fixtureDef);
    }

    @Override
    public void update(float delta) {
        stateTime += delta;
        setPosition(GobjectBody.getPosition().x - getWidth() / 2f,
                GobjectBody.getPosition().y - getHeight() / 1.4f);
        setRegion(getFrames(delta));

    }

    public TextureRegion getFrames(float delta) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case RotateRight:
                region = rotateRight.getKeyFrame(stateTime, true);
                break;
            default:
                region = rotateLeft.getKeyFrame(stateTime, true);
        }

        if (currentState == pastState)
            stateTime += delta;
        else
            stateTime = 0;

        //update previous state
        pastState = currentState;
        //return the final adjusted frame
        return region;
    }

    public torchState getState() {
        return torchState.RotateLeft;
    }

    @Override
    public void playerHit(MainPlayer mainPlayer) {

    }

    @Override
    public void enemyHit(EnemySprite enemySprite) {

    }

    @Override
    public void dispose() {

    }
}
