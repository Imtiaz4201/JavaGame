package MainGamePlay.AllSpritesItemsForJL0.EnemyClasses;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import MainGamePlay.Player2.PlayerClass2;
import MainGamePlay.jungle.jungleLevel0;


public abstract class EnemySpritesJL0 extends Sprite {
    protected jungleLevel0 jungleLevel0;
    protected World world;
    public Body enemyBody;
    public Vector2 vector2;
    public boolean destroyed = false;
    public boolean playerDetectionSensor = false;
    public float stateTimer =0f;

    public EnemySpritesJL0(jungleLevel0 jungleLevel0, float x, float y) {
        this.jungleLevel0 = jungleLevel0;
        world = jungleLevel0.getWorld();
        vector2 = new Vector2(-0.5f, 0);
        setPosition(x, y);
        defineEnemy();
    }

    public abstract void defineEnemy();

    public abstract void update(float delta);

    public abstract void playerHit(PlayerClass2 mainPlayer);

    public abstract void playAttackAnimation(PlayerClass2 mainPlayer);

    public abstract void dontPlayAttackAnimation(PlayerClass2 mainPlayer);

    public abstract void enemyHit();

    public abstract void attachStaticBody(PlayerClass2 mainPlayer);

    public abstract void detachStaticBody(PlayerClass2 mainPlayer);

    public abstract void playerDetectionON(PlayerClass2 mainPlayer);

    public abstract void playerDetectionOff(PlayerClass2 mainPlayer);

    public abstract void playerOnLeft(PlayerClass2 mainPlayer);

    public abstract void playerOnRight(PlayerClass2 mainPlayer);

    public void reversedEnemy1(boolean x, boolean y) {
        if (x) {
            vector2.x = -vector2.x;
        }
        if (y) {
            vector2.y = -vector2.y;
        }

    }

}
