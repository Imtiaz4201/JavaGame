package MainGamePlay.AllSprites.CoinsClass;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import MainGamePlay.Player.MainPlayer;
import MainGamePlay.jungle.jungleLevel1;

public abstract class CoinSprite extends Sprite {
    public World world;
    private jungleLevel1 jungleLevel1;
    public Vector2 vector2;
    public Body coinBody;
    public int totalCoinCollected = 0;
    public boolean setToDestroy = false;
    public boolean destroyed = false;

    public CoinSprite(jungleLevel1 jungleLevel1, float x, float y) {
        this.jungleLevel1 = jungleLevel1;
        world = jungleLevel1.getWorld();
        vector2 = new Vector2(0, 0);
        setPosition(x, y);
        defineCoin();
    }

    public abstract void defineCoin();

    public abstract void update(float delta);

    public abstract void playerHit(MainPlayer mainPlayer);
}
