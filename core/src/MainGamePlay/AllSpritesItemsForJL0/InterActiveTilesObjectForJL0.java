package MainGamePlay.AllSpritesItemsForJL0;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import GameConfiguration.GameConfig;
import MainGamePlay.jungle.jungleLevel0;

public abstract class InterActiveTilesObjectForJL0 {
    private World world;
    private TiledMap tiledMap;
    private TiledMapTile tiledMapTile;
    private Rectangle rectangle;
    private Body body;
    protected Fixture fixture;

    public InterActiveTilesObjectForJL0(jungleLevel0 jungleLevel0, Rectangle rectangle) {
        this.world = jungleLevel0.getWorld();
        this.tiledMap = jungleLevel0.getTiledMap();
        this.rectangle = rectangle;
        setObject();
    }

    public void setObject() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / GameConfig.PPM,
                (rectangle.getY() + rectangle.getHeight() / 2) / GameConfig.PPM);
        Body body;
        body = world.createBody(bodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox((rectangle.getWidth() / 2) / GameConfig.PPM, (rectangle.getHeight() / 2) / GameConfig.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixture = body.createFixture(fixtureDef); // take the fixture of the object and assign to fixture variable
    }

    public abstract void onTouch();

    public abstract void onHit();

} // inter active object physics detection class

