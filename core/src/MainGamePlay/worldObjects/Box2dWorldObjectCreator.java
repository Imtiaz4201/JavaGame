package MainGamePlay.worldObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import GameConfiguration.GameConfig;
import MainGamePlay.AllSprites.CoinsClass.Coin1Definition;
import MainGamePlay.AllSprites.CoinsClass.DiamondDefinition;
import MainGamePlay.AllSprites.EnemyClass1.EnemyDefinition;
import MainGamePlay.AllSprites.EnemyClass1.Enemy2Definition;
import MainGamePlay.AllSprites.GroundAndPlatform.Ground;
import MainGamePlay.AllSprites.GroundAndPlatform.GroundObjectDefinition;
import MainGamePlay.AllSprites.GroundAndPlatform.Platform;
import MainGamePlay.AllSprites.GroundAndPlatform.TorchDefinition;
import MainGamePlay.AllSprites.Hazards.AnimatedHazardsDefinition;
import MainGamePlay.AllSprites.Hazards.SpikeType1;
import MainGamePlay.jungle.jungleLevel1;

public class Box2dWorldObjectCreator {
    private jungleLevel1 jungleLevel1;
    private World world;
    private Array<EnemyDefinition> enemy1;
    private Array<Enemy2Definition> flyingEnemies;
    private Array<AnimatedHazardsDefinition> animatedHazards;
    private Array<GroundObjectDefinition> groundObjectDefinitions;
    private Array<TorchDefinition> torchDefinitions;
    private Array<Coin1Definition> coin1Definitions;
    private Array<SpikeType1> spikeType1;
    private Array<DiamondDefinition> diamondDefinition;

    public Box2dWorldObjectCreator(jungleLevel1 jungleLevel1) {
        this.jungleLevel1 = jungleLevel1;
        world = jungleLevel1.getWorld();

        // ground object fixture/body
        for (MapObject object : jungleLevel1.getTiledMap().getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            new Ground(jungleLevel1, rectangle);
        }

        // platform object fixture/body
        for (MapObject object : jungleLevel1.getTiledMap().getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            new Platform(jungleLevel1, rectangle);
        }
        //for enemy 1
        enemy1 = new Array<>();
        for (MapObject object : jungleLevel1.getTiledMap().getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            enemy1.add(new EnemyDefinition(jungleLevel1, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

        // for flying enemies
        flyingEnemies = new Array<>();
        for (MapObject object : jungleLevel1.getTiledMap().getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            flyingEnemies.add(new Enemy2Definition(jungleLevel1, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }
        // for animated hazards
        animatedHazards = new Array<>();
        for (MapObject object : jungleLevel1.getTiledMap().getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            animatedHazards.add(new AnimatedHazardsDefinition(jungleLevel1, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }
        // for ground object
        groundObjectDefinitions = new Array<>();
        for (MapObject object : jungleLevel1.getTiledMap().getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            groundObjectDefinitions.add(new GroundObjectDefinition(jungleLevel1, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }
        // for torch
        torchDefinitions = new Array<>();
        for (MapObject object : jungleLevel1.getTiledMap().getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            torchDefinitions.add(new TorchDefinition(jungleLevel1, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

        // for coins
        coin1Definitions = new Array<>();
        for (MapObject object : jungleLevel1.getTiledMap().getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            coin1Definitions.add(new Coin1Definition(jungleLevel1, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }
        // for diamond
        diamondDefinition = new Array<>();
        for (MapObject object : jungleLevel1.getTiledMap().getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            diamondDefinition.add(new DiamondDefinition(jungleLevel1, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

        // for spike type 1
        spikeType1 = new Array<>();
        for (MapObject object : jungleLevel1.getTiledMap().getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            spikeType1.add(new SpikeType1(jungleLevel1, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

    }

    public Array<EnemyDefinition> getEnemy1() {
        return enemy1;
    }

    public Array<Enemy2Definition> getFlyingEnemies() {
        return flyingEnemies;
    }

    public Array<AnimatedHazardsDefinition> getAnimatedHazards() {
        return animatedHazards;
    }

    public Array<GroundObjectDefinition> getGroundObjectDefinitions() {
        return groundObjectDefinitions;
    }

    public Array<TorchDefinition> getTorchDefinitions() {
        return torchDefinitions;
    }

    public Array<Coin1Definition> getCoin1Definitions() {
        return coin1Definitions;
    }

    public Array<DiamondDefinition> getDiamondDefinition() {
        return diamondDefinition;
    }


    public Array<SpikeType1> getSpikeType1() {
        return spikeType1;
    }
}
