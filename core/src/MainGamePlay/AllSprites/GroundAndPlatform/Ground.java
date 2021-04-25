package MainGamePlay.AllSprites.GroundAndPlatform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import MainGamePlay.AllSprites.InterActiveTilesObject;
import MainGamePlay.jungle.jungleLevel1;

public class Ground extends InterActiveTilesObject {
    public Ground(jungleLevel1 jungleLevel1, Rectangle rectangle) {
        super(jungleLevel1, rectangle);
        fixture.setUserData("Platform");
    }

    @Override
    public void onTouch() {
    }

    @Override
    public void onHit() {

    }

}
