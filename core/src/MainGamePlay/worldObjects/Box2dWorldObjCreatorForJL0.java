package MainGamePlay.worldObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import GameConfiguration.GameConfig;
import MainGamePlay.AllSprites.GroundAndPlatform.Ground2;
import MainGamePlay.AllSpritesItemsForJL0.CoinClasses.CoinDefinitionJL0;
import MainGamePlay.AllSpritesItemsForJL0.CoinClasses.StarDefinitionJL0;
import MainGamePlay.AllSpritesItemsForJL0.EnemyClasses.FlyingEnemy;
import MainGamePlay.AllSpritesItemsForJL0.EnemyClasses.GroundEnemy;
import MainGamePlay.AllSpritesItemsForJL0.EnemyClasses.HiddenSnake;
import MainGamePlay.AllSpritesItemsForJL0.HazardsClasses.SpikeType1JL0;
import MainGamePlay.AllSpritesItemsForJL0.HazardsClasses.wallHazard.Blade;
import MainGamePlay.AllSpritesItemsForJL0.Items.AnimatedBirdNPC;
import MainGamePlay.AllSpritesItemsForJL0.Items.AnimatedButterFlyStill;
import MainGamePlay.AllSpritesItemsForJL0.Items.AnimatedButterflyPurple;
import MainGamePlay.AllSpritesItemsForJL0.Items.AnimatedDeer;
import MainGamePlay.AllSpritesItemsForJL0.Items.AnimatedFire;
import MainGamePlay.AllSpritesItemsForJL0.Items.AnimatedDuck;
import MainGamePlay.AllSpritesItemsForJL0.Items.AnimatedWater;
import MainGamePlay.AllSpritesItemsForJL0.Items.SpringJumper;
import MainGamePlay.AllSpritesItemsForJL0.Items.WoodenLog;
import MainGamePlay.jungle.jungleLevel0;

public class Box2dWorldObjCreatorForJL0 {
    private jungleLevel0 jungleLevel0;
    private World world;
    private Array<CoinDefinitionJL0> coin1Definitions;
    private Array<SpikeType1JL0> spikeType1JL0;
    private Array<GroundEnemy> enemyClass1;
    private Array<WoodenLog> woodenLog;
    private Array<AnimatedWater> animatedWaters;
    private Array<AnimatedFire> animatedFires;
    private Array<StarDefinitionJL0> starDefinitionJL0s;
    private Array<FlyingEnemy> flyingEnemies;
    private Array<SpringJumper> springJumpers;
    private Array<Blade> blades;
    private Array<HiddenSnake> hiddenSnakes;
    private Array<AnimatedDeer> animatedDeers;
    private Array<AnimatedDuck> animatedRabbits;
    private Array<AnimatedButterflyPurple> animatedButterflyPurples;
    private Array<AnimatedButterFlyStill> animatedButterFlyStills;
    private Array<AnimatedBirdNPC> animatedBirdNPCS;

    public Box2dWorldObjCreatorForJL0(jungleLevel0 jungleLevel0) {
        this.jungleLevel0 = jungleLevel0;
        world = jungleLevel0.getWorld();

        // ground object fixture/body
        for (MapObject object : jungleLevel0.getTiledMap().getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            new Ground2(jungleLevel0, rectangle);
        }

        // for coins
        coin1Definitions = new Array<>();
        for (MapObject object : jungleLevel0.getTiledMap().getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            coin1Definitions.add(new CoinDefinitionJL0(jungleLevel0, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }
        // for diamond
        starDefinitionJL0s = new Array<>();
        for (MapObject object : jungleLevel0.getTiledMap().getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            starDefinitionJL0s.add(new StarDefinitionJL0(jungleLevel0, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

        // for spike type 1
        spikeType1JL0 = new Array<>();
        for (MapObject object : jungleLevel0.getTiledMap().getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            spikeType1JL0.add(new SpikeType1JL0(jungleLevel0, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

        // for ground enemy
        enemyClass1 = new Array<>();
        for (MapObject object : jungleLevel0.getTiledMap().getLayers().get(13).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            enemyClass1.add(new GroundEnemy(jungleLevel0, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

        // for flying enemy
        flyingEnemies = new Array<>();
        for (MapObject object : jungleLevel0.getTiledMap().getLayers().get(14).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            flyingEnemies.add(new FlyingEnemy(jungleLevel0, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

        // for moving wooden log
        woodenLog = new Array<>();
        for (MapObject object : jungleLevel0.getTiledMap().getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            woodenLog.add(new WoodenLog(jungleLevel0, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

        // for moving water
        animatedWaters = new Array<>();
        for (MapObject object : jungleLevel0.getTiledMap().getLayers().get(15).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            animatedWaters.add(new AnimatedWater(jungleLevel0, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

        // for animated fire
        animatedFires = new Array<>();
        for (MapObject object : jungleLevel0.getTiledMap().getLayers().get(16).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            animatedFires.add(new AnimatedFire(jungleLevel0, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

        // for animated springs jumper
        springJumpers = new Array<>();
        for (MapObject object : jungleLevel0.getTiledMap().getLayers().get(17).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            springJumpers.add(new SpringJumper(jungleLevel0, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

        // for animated wall blade
        blades = new Array<>();
        for (MapObject object : jungleLevel0.getTiledMap().getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            blades.add(new Blade(jungleLevel0, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

        // for hidden snake
        hiddenSnakes = new Array<>();
        for (MapObject object : jungleLevel0.getTiledMap().getLayers().get(18).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            hiddenSnakes.add(new HiddenSnake(jungleLevel0, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

        // for animated deer
        animatedDeers = new Array<>();
        for (MapObject object : jungleLevel0.getTiledMap().getLayers().get(19).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            animatedDeers.add(new AnimatedDeer(jungleLevel0, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

        // for animated duck
        animatedRabbits = new Array<>();
        for (MapObject object : jungleLevel0.getTiledMap().getLayers().get(20).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            animatedRabbits.add(new AnimatedDuck(jungleLevel0, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

        // for animated butterfly P
        animatedButterflyPurples = new Array<>();
        for (MapObject object : jungleLevel0.getTiledMap().getLayers().get(21).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            animatedButterflyPurples.add(new AnimatedButterflyPurple(jungleLevel0, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

        // for animated butterfly R
        animatedButterFlyStills = new Array<>();
        for (MapObject object : jungleLevel0.getTiledMap().getLayers().get(22).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            animatedButterFlyStills.add(new AnimatedButterFlyStill(jungleLevel0, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

        // for animated birds
        animatedBirdNPCS = new Array<>();
        for (MapObject object : jungleLevel0.getTiledMap().getLayers().get(23).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = new Rectangle(((RectangleMapObject) object).getRectangle());
            animatedBirdNPCS.add(new AnimatedBirdNPC(jungleLevel0, rectangle.getX() / GameConfig.PPM,
                    0.1f + rectangle.getY() / GameConfig.PPM));
        }

    }

    public Array<CoinDefinitionJL0> getCoin1Definitions() {
        return coin1Definitions;
    }

    public Array<SpikeType1JL0> getSpikeType1JL0() {
        return spikeType1JL0;
    }

    public Array<GroundEnemy> getEnemyClass1() {
        return enemyClass1;
    }

    public Array<WoodenLog> getWoodenLog() {
        return woodenLog;
    }

    public Array<AnimatedWater> getAnimatedWaters() {
        return animatedWaters;
    }

    public Array<AnimatedFire> getAnimatedFires() {
        return animatedFires;
    }

    public Array<StarDefinitionJL0> getStarDefinitionJL0s() {
        return starDefinitionJL0s;
    }

    public Array<FlyingEnemy> getFlyingEnemies() {
        return flyingEnemies;
    }

    public Array<SpringJumper> getSpringJumpers() {
        return springJumpers;
    }

    public Array<Blade> getBlades() {
        return blades;
    }

    public Array<HiddenSnake> getHiddenSnakes() {
        return hiddenSnakes;
    }

    public Array<AnimatedDeer> getAnimatedDeers() {
        return animatedDeers;
    }

    public Array<AnimatedDuck> getAnimatedRabbits() {
        return animatedRabbits;
    }

    public Array<AnimatedButterflyPurple> getAnimatedButterflyPurples() {
        return animatedButterflyPurples;
    }

    public Array<AnimatedButterFlyStill> getAnimatedButterFlyStills() {
        return animatedButterFlyStills;
    }

    public Array<AnimatedBirdNPC> getAnimatedBirdNPCS(){
        return animatedBirdNPCS;
    }

} // world object creator for jungle level 0
