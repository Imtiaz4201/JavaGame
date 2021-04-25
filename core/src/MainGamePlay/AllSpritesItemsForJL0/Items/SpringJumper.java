package MainGamePlay.AllSpritesItemsForJL0.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;

import AssetsManager.Manager;
import GameConfiguration.GameConfig;
import MainGamePlay.Player2.PlayerClass2;
import MainGamePlay.jungle.jungleLevel0;
import Settings.SettingPref;

public class SpringJumper extends ItemsSprite implements Disposable {
    // Box2d
    private BodyDef bodyDef;
    private PolygonShape polygonShape;
    private FixtureDef fixtureDef;

    private Manager manager = Manager.managerInstance;
    private SettingPref settingPref = SettingPref.instance;
    private Timer timer;
    private float stateTime = 0;
    private boolean playerJumpOn = false;
    private Sound springSound;

    public enum SpringState {
        Idle, PushUp
    }

    public SpringState currentState;
    public SpringState pastState;

    private jungleLevel0 jungleLevel0;
    private Array<TextureRegion> frames;
    private Animation<TextureRegion> idel;
    private Animation<TextureRegion> pushup;

    public SpringJumper(jungleLevel0 jungleLevel0, float x, float y) {
        super(jungleLevel0, x, y);
        this.jungleLevel0 = jungleLevel0;
        itemBody.setActive(false);
        currentState = SpringState.Idle;
        pastState = SpringState.Idle;
        springSound = manager.assetManager.get(manager.springJumpSound, Sound.class);
        setSprite();
    }

    public void setSprite() {
        frames = new Array<>();
        for (int i = 0; i < 1; i++) {
            frames.add(new TextureRegion(jungleLevel0.getSprings().findRegion("spring"), i * 145, 0, 145, 77));
        }
        idel = new Animation<>(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 1; i++) {
            frames.add(new TextureRegion(jungleLevel0.getSprings().findRegion("spring_out"), i * 145, 0, 145, 110));
        }
        pushup = new Animation<>(0.1f, frames);
        frames.clear();
        setBounds(getX(), getY(), (float) 8 / GameConfig.PPM, (float) 9 / GameConfig.PPM);
    }


    @Override
    public void defineItems() {
        bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;
        itemBody = world.createBody(bodyDef);
        polygonShape = new PolygonShape();
        polygonShape.setAsBox((float) 2 / GameConfig.PPM, (float) 1 / GameConfig.PPM, new Vector2(0, -.03f), 0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.restitution = 1.2f;
        fixtureDef.filter.categoryBits = GameConfig.Spring_BitL0;
        fixtureDef.filter.maskBits = GameConfig.Player_BitL0;
        itemBody.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void update(float delta) {
        stateTime += delta;
        if (playerJumpOn){
            if (settingPref.sfx == true) {
                springSound.play(settingPref.sfxVolume);
            } else if (settingPref.sfx == false) {
                springSound.stop();
            }
        }
        setPosition(itemBody.getPosition().x - getWidth() / 2f,
                itemBody.getPosition().y - getHeight() / 1f);
        setRegion(getFrame(delta));
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case PushUp:
                region = pushup.getKeyFrame(stateTime, false);
                break;
            default:
                region = idel.getKeyFrame(stateTime, false);
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

    public SpringState getState() {
        if (playerJumpOn) {
            return SpringState.PushUp;
        } else {
            return SpringState.Idle;
        }
    }

    @Override
    public void playerHit(PlayerClass2 mainPlayer) {
        playerJumpOn = true;
    }

    @Override
    public void playerNotHit(PlayerClass2 mainPlayer) {
        playerJumpOn = false;
    }

    @Override
    public void enemyHit(PlayerClass2 mainPlayer) {

    }

    @Override
    public void destroyed() {

    }


    @Override
    public void dispose() {
        polygonShape.dispose();
        springSound.dispose();
    }
}
