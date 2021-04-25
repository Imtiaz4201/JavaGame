package MainGamePlay.AllSpritesItemsForJL0.CoinClasses;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import MainGamePlay.Player2.PlayerClass2;
import MainGamePlay.jungle.jungleLevel0;

public abstract class CoinSpriteJL0 extends Sprite {
    public World world;
    private jungleLevel0 jungleLevel0;
    public Vector2 vector2;
    public Body coinBody;
    public int totalCoinCollected = 0;
    public boolean setToDestroy = false;
    public boolean destroyed = false;
    public CoinSpriteJL0(jungleLevel0 jungleLevel0, float x, float y) {
        this.jungleLevel0 = jungleLevel0;
        world = jungleLevel0.getWorld();
        vector2 = new Vector2(0, 0);
        setPosition(x, y);
        defineCoin();
    }

    public abstract void defineCoin();

    public abstract void update(float delta);

    public abstract void playerHit(PlayerClass2 mainPlayer);

    public abstract void callGameOverScreen(PlayerClass2 mainPlayer);
}
