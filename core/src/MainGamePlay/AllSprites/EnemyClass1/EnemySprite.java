package MainGamePlay.AllSprites.EnemyClass1;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import MainGamePlay.Player.MainPlayer;
import MainGamePlay.jungle.jungleLevel1;

public abstract class EnemySprite extends Sprite {
    protected jungleLevel1 jungleLevel1;
    protected World world;
    public Body enemyBody;
    public Vector2 vector2;
    public boolean destroyed = false;

    public EnemySprite(jungleLevel1 jungleLevel1, float x, float y) {
        this.jungleLevel1 = jungleLevel1;
        world = jungleLevel1.getWorld();
        vector2 = new Vector2(-0.5f, 0);
        setPosition(x, y);
        defineEnemy();
    }

    public abstract void defineEnemy();

    public abstract void update(float delta);

    public abstract void playerHit(MainPlayer mainPlayer);

    public abstract void playAttackAnimation(MainPlayer mainPlayer);

    public abstract void dontPlayAttackAnimation(MainPlayer mainPlayer);

    public abstract void enemyHit();

    public void reversedEnemy1(boolean x, boolean y) {
        if (x) {
            vector2.x = -vector2.x;
        }
        if (y) {
            vector2.y = -vector2.y;
        }

    }

}
