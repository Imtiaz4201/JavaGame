package MainGamePlay.AllSprites.GroundAndPlatform;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;

import GameConfiguration.GameConfig;
import MainGamePlay.AllSpritesItemsForJL0.InterActiveTilesObjectForJL0;
import MainGamePlay.jungle.jungleLevel0;

public class Ground2 extends InterActiveTilesObjectForJL0 {
    public Ground2(jungleLevel0 jungleLevel0, Rectangle rectangle) {
        super(jungleLevel0, rectangle);
        Filter filter = fixture.getFilterData();
        filter.categoryBits = GameConfig.Ground_BitL0;
        filter.maskBits = GameConfig.Player_BitL0 | GameConfig.Enemy_BitL0 |
                GameConfig.Deer_NPC_LEFT_SENSOR_BITL0 | GameConfig.Deer_NPC_RIGHT_SENSOR_BITL0 |
                GameConfig.Fox_NPC_RIGHT_SENSOR_BITL0 |
                GameConfig.Fox_NPC_LEFT_SENSOR_BITL0;
        fixture.setFriction(4f);
        fixture.setFilterData(filter);
        fixture.setUserData(this);
    }

    @Override
    public void onTouch() {
    }

    @Override
    public void onHit() {

    }

} // for jungle level 0
