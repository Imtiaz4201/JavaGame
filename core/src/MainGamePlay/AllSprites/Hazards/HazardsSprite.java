package MainGamePlay.AllSprites.Hazards;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import MainGamePlay.Player.MainPlayer;
import MainGamePlay.jungle.jungleLevel1;

public abstract class HazardsSprite extends Sprite {
    private jungleLevel1 jungleLevel1;
    protected World world;
    protected Vector2 vector2;
    public Body hazardBody;
    public HazardsSprite(jungleLevel1 jungleLevel1, float x, float y) {
        this.jungleLevel1 = jungleLevel1;
        world = jungleLevel1.getWorld();
        setPosition(x, y);
        defineHazards();
    }

    public abstract void defineHazards();

    public abstract void update(float delta);

    public abstract void playerHit(MainPlayer mainPlayer);

    public abstract void hazardHit(HazardsSprite hazardsSprite);
}
