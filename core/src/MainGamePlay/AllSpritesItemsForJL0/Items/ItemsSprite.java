package MainGamePlay.AllSpritesItemsForJL0.Items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import MainGamePlay.Player2.PlayerClass2;
import MainGamePlay.jungle.jungleLevel0;

public abstract class ItemsSprite extends Sprite {
    protected jungleLevel0 jungleLevel0;
    protected World world;
    public Body itemBody;
    public Vector2 vector2;
    public boolean destroyed;

    public ItemsSprite(jungleLevel0 jungleLevel0, float x, float y) {
        this.jungleLevel0 = jungleLevel0;
        world = jungleLevel0.getWorld();
        setPosition(x, y);
        defineItems();
    }

    public abstract void defineItems();

    public abstract void update(float delta);

    public abstract void playerHit(PlayerClass2 mainPlayer);

    public abstract void playerNotHit(PlayerClass2 mainPlayer);

    public abstract void enemyHit(PlayerClass2 mainPlayer);

    public abstract void destroyed();
}
