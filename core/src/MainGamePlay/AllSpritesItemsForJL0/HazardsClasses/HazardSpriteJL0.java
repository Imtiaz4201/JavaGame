package MainGamePlay.AllSpritesItemsForJL0.HazardsClasses;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import MainGamePlay.Player2.PlayerClass2;
import MainGamePlay.jungle.jungleLevel0;

public abstract class HazardSpriteJL0 extends Sprite {
    protected jungleLevel0 jungleLevel0;
    protected World world;
    protected Vector2 vector2;
    public Body hazardBody;

    public HazardSpriteJL0(jungleLevel0 jungleLevel0, float x, float y) {
        this.jungleLevel0 = jungleLevel0;
        world = jungleLevel0.getWorld();
        setPosition(x, y);
        defineHazards();
    }

    public abstract void defineHazards();

    public abstract void update(float delta);

    public abstract void playerHit(PlayerClass2 mainPlayer);

    public abstract void hazardHit(HazardSpriteJL0 hazardsSprite);

}
