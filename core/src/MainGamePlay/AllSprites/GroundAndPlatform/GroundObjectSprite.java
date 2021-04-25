package MainGamePlay.AllSprites.GroundAndPlatform;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import MainGamePlay.AllSprites.EnemyClass1.EnemySprite;
import MainGamePlay.Player.MainPlayer;
import MainGamePlay.jungle.jungleLevel1;

public abstract class GroundObjectSprite extends Sprite {
    protected World world;
    protected jungleLevel1 jungleLevel1;
    public Body GobjectBody;

    public GroundObjectSprite(jungleLevel1 jungleLevel1, float x, float y) {
        this.jungleLevel1 = jungleLevel1;
        world = jungleLevel1.getWorld();
        setPosition(x, y);
        defineGroundObject();

    }

    public abstract void defineGroundObject();

    public abstract void update(float delta);

    public abstract void playerHit(MainPlayer mainPlayer);

    public abstract void enemyHit(EnemySprite enemySprite);
}
