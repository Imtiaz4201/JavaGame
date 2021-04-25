package MainGamePlay.worldObjects;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import GameConfiguration.GameConfig;
import MainGamePlay.AllSpritesItemsForJL0.CoinClasses.CoinSpriteJL0;
import MainGamePlay.AllSpritesItemsForJL0.EnemyClasses.FlyingEnemy;
import MainGamePlay.AllSpritesItemsForJL0.EnemyClasses.GroundEnemy;
import MainGamePlay.AllSpritesItemsForJL0.EnemyClasses.EnemySpritesJL0;
import MainGamePlay.AllSpritesItemsForJL0.EnemyClasses.HiddenSnake;
import MainGamePlay.AllSpritesItemsForJL0.Items.AnimatedBirdNPC;
import MainGamePlay.AllSpritesItemsForJL0.Items.AnimatedDeer;
import MainGamePlay.AllSpritesItemsForJL0.Items.AnimatedDuck;
import MainGamePlay.AllSpritesItemsForJL0.Items.SpringJumper;
import MainGamePlay.Player2.PlayerBowClass2;
import MainGamePlay.Player2.PlayerClass2;

public class WordObjectsListenerJL0 implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        //contact between two shapes/fixture
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        int collisionDef = fixtureA.getFilterData().categoryBits |
                fixtureB.getFilterData().categoryBits;

        // if bullet touch with ground , it will destroy with timing , class PlayerWeapon
        if (fixtureA.getUserData() == "bulletL0" || fixtureB.getUserData() == "bulletL0") {
            Fixture fixture = fixtureA.getUserData() == "bulletL0" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && PlayerBowClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerBowClass2) object.getUserData()).setToDestroy();
            }

        }

        // for spike type1, class  SpikeType1
        if (fixtureA.getUserData() == "spike1JL0" || fixtureB.getUserData() == "spike1JL0") {
            Fixture fixture = fixtureA.getUserData() == "spike1JL0" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).oneHitDeath();
            }
            if (object.getUserData() != null && PlayerBowClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerBowClass2) object.getUserData()).setToDestroy();
            }

        }

        // for spike type1, class  SpikeType1
        if (fixtureA.getUserData() == "spike1JL0" || fixtureB.getUserData() == "spike1JL0") {
            Fixture fixture = fixtureA.getUserData() == "spike1JL0" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && AnimatedDeer.class.isAssignableFrom(object.getUserData().getClass())) {
                ((AnimatedDeer) object.getUserData()).destroyed();
            }
        }
        // for spike type1, class  SpikeType1
        if (fixtureA.getUserData() == "spike1JL0" || fixtureB.getUserData() == "spike1JL0") {
            Fixture fixture = fixtureA.getUserData() == "spike1JL0" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && AnimatedDuck.class.isAssignableFrom(object.getUserData().getClass())) {
                ((AnimatedDuck) object.getUserData()).destroyed();
            }
        }

        // for wall blade, class  Blade
        if (fixtureA.getUserData() == "Blade" || fixtureB.getUserData() == "Blade") {
            Fixture fixture = fixtureA.getUserData() == "Blade" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).playerDeath();
            }
            if (object.getUserData() != null && PlayerBowClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerBowClass2) object.getUserData()).setToDestroy();
            }

        }


        // for spike type1, class  SpikeType1
        if (fixtureA.getUserData() == "spike1JL0" || fixtureB.getUserData() == "spike1JL0") {
            Fixture fixture = fixtureA.getUserData() == "spike1JL0" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).shakeCamera();
            }
        }

        // for wall blade, class  Blade
        if (fixtureA.getUserData() == "Blade" || fixtureB.getUserData() == "Blade") {
            Fixture fixture = fixtureA.getUserData() == "Blade" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).shakeCamera();
            }
        }

        // for player bullet , class playerBowClass2 // ground enemy
        if (fixtureA.getUserData() == "bulletL0" || fixtureB.getUserData() == "bulletL0") {
            Fixture fixture = fixtureA.getUserData() == "bulletL0" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && GroundEnemy.class.isAssignableFrom(object.getUserData().getClass())) {
                ((GroundEnemy) object.getUserData()).playerHit(new PlayerClass2());
            }
            if (object.getUserData() != null && PlayerBowClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerBowClass2) object.getUserData()).setToDestroy();
            }
        }

        // for player bullet , class playerBowClass2 // flying enemy
        if (fixtureA.getUserData() == "bulletL0" || fixtureB.getUserData() == "bulletL0") {
            Fixture fixture = fixtureA.getUserData() == "bulletL0" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && FlyingEnemy.class.isAssignableFrom(object.getUserData().getClass())) {
                ((FlyingEnemy) object.getUserData()).playerHit(new PlayerClass2());
            }
            if (object.getUserData() != null && PlayerBowClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerBowClass2) object.getUserData()).setToDestroy();
            }
        }

        // for player bullet , class playerBowClass2 // hidden snake
        if (fixtureA.getUserData() == "bulletL0" || fixtureB.getUserData() == "bulletL0") {
            Fixture fixture = fixtureA.getUserData() == "bulletL0" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && HiddenSnake.class.isAssignableFrom(object.getUserData().getClass())) {
                ((HiddenSnake) object.getUserData()).playerHit(new PlayerClass2());
            }
            if (object.getUserData() != null && PlayerBowClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerBowClass2) object.getUserData()).setToDestroy();
            }
        }

        // for ground enemy1, class GroundEnemy
        if (fixtureA.getUserData() == "SCORPION" || fixtureB.getUserData() == "SCORPION") {
            Fixture fixture = fixtureA.getUserData() == "SCORPION" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).push_back();
            }
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).recursiveDeath_call();
            }
        }
        // for ground enemy1, class HiddenSnake
        if (fixtureA.getUserData() == "HSAttack" || fixtureB.getUserData() == "HSAttack") {
            Fixture fixture = fixtureA.getUserData() == "HSAttack" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).shakeCamera();
            }
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).push_back();
            }
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).recursiveDeath_call();
            }
        }

        // for ground enemy1, class GroundEnemy
        if (fixtureA.getUserData() == "SCORPION" || fixtureB.getUserData() == "SCORPION") {
            Fixture fixture = fixtureA.getUserData() == "SCORPION" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).shakeCamera();
            }
        }

        // for flying enemy, class FlyingEnemy
        if (fixtureA.getUserData() == "flyingEnemy" || fixtureB.getUserData() == "flyingEnemy") {
            Fixture fixture = fixtureA.getUserData() == "flyingEnemy" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).playerDeath();
            }
        }

        // for flying enemy, class FlyingEnemy
        if (fixtureA.getUserData() == "flyingEnemy" || fixtureB.getUserData() == "flyingEnemy") {
            Fixture fixture = fixtureA.getUserData() == "flyingEnemy" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).shakeCamera();
            }
        }

        // for animated water, class AnimatedWater
        if (fixtureA.getUserData() == "water" || fixtureB.getUserData() == "water") {
            Fixture fixture = fixtureA.getUserData() == "water" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).oneHitDeath();
            }
        }

        // for animated water, class AnimatedWater
        if (fixtureA.getUserData() == "water" || fixtureB.getUserData() == "water") {
            Fixture fixture = fixtureA.getUserData() == "water" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).setPlayerOnWater();
            }
        }

        // for animated fire, class AnimatedFire
        if (fixtureA.getUserData() == "fire" || fixtureB.getUserData() == "fire") {
            Fixture fixture = fixtureA.getUserData() == "fire" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).oneHitDeath();
            }
        }

        // for woodLog, class WoodenLog // fixture1
        if (fixtureA.getUserData() == "wodLog1" || fixtureB.getUserData() == "wodLog1") {
            Fixture fixture = fixtureA.getUserData() == "wodLog1" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).notOnGroundLayer();
            }
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).playerOnGroundNow();
            }
        }
        // for woodLog, class WoodenLog // fixture2
        if (fixtureA.getUserData() == "wodLog2" || fixtureB.getUserData() == "wodLog2") {
            Fixture fixture = fixtureA.getUserData() == "wodLog2" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).notOnGroundLayer();
            }
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).playerOnGroundNow();
            }
        }
        // for woodLog, class WoodenLog // fixture3
        if (fixtureA.getUserData() == "wodLog3" || fixtureB.getUserData() == "wodLog3") {
            Fixture fixture = fixtureA.getUserData() == "wodLog3" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).notOnGroundLayer();
            }
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).playerOnGroundNow();
            }
        }


        // for specif object
        switch (collisionDef) {
            case GameConfig.Coin_BitL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Coin_BitL0) {
                    ((CoinSpriteJL0) fixtureA.getUserData()).playerHit((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((CoinSpriteJL0) fixtureB.getUserData()).playerHit((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.Enemy_BitL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Enemy_BitL0) {
                    ((EnemySpritesJL0) fixtureA.getUserData()).attachStaticBody((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((EnemySpritesJL0) fixtureB.getUserData()).attachStaticBody((PlayerClass2) fixtureA.getUserData());
                }
                if (fixtureA.getFilterData().categoryBits == GameConfig.Enemy_BitL0) {
                    ((EnemySpritesJL0) fixtureA.getUserData()).playAttackAnimation((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((EnemySpritesJL0) fixtureB.getUserData()).playAttackAnimation((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.Player_BitL0 | GameConfig.Ground_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Player_BitL0) {
                    ((PlayerClass2) fixtureA.getUserData()).playerOnGroundNow();
                } else {
                    ((PlayerClass2) fixtureB.getUserData()).playerOnGroundNow();
                }
                break;
            case GameConfig.Star_BitL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Star_BitL0) {
                    ((CoinSpriteJL0) fixtureA.getUserData()).playerHit((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((CoinSpriteJL0) fixtureB.getUserData()).playerHit((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.Spring_BitL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Spring_BitL0) {
                    ((SpringJumper) fixtureA.getUserData()).playerHit((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((SpringJumper) fixtureB.getUserData()).playerHit((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.ENEMY_WAKE_UP_BITL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.ENEMY_WAKE_UP_BITL0) {
                    ((HiddenSnake) fixtureA.getUserData()).playerDetectionON((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((HiddenSnake) fixtureB.getUserData()).playerDetectionON((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.ENEMY_LEFT_SENSOR_BITL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.ENEMY_LEFT_SENSOR_BITL0) {
                    ((HiddenSnake) fixtureA.getUserData()).playerOnLeft((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((HiddenSnake) fixtureB.getUserData()).playerOnLeft((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.ENEMY_RIGHT_SENSOR_BITL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.ENEMY_RIGHT_SENSOR_BITL0) {
                    ((HiddenSnake) fixtureA.getUserData()).playerOnRight((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((HiddenSnake) fixtureB.getUserData()).playerOnRight((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.Ground_ENEMY_LEFT_SENSOR_BITL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Ground_ENEMY_LEFT_SENSOR_BITL0) {
                    ((GroundEnemy) fixtureA.getUserData()).playerOnLeft((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((GroundEnemy) fixtureB.getUserData()).playerOnLeft((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.Ground_ENEMY_RIGHT_SENSOR_BITL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Ground_ENEMY_RIGHT_SENSOR_BITL0) {
                    ((GroundEnemy) fixtureA.getUserData()).playerOnRight((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((GroundEnemy) fixtureB.getUserData()).playerOnRight((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.Deer_NPC_LEFT_SENSOR_BITL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Deer_NPC_LEFT_SENSOR_BITL0) {
                    ((AnimatedDeer) fixtureA.getUserData()).playerHit((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((AnimatedDeer) fixtureB.getUserData()).playerHit((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.Deer_NPC_RIGHT_SENSOR_BITL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Deer_NPC_RIGHT_SENSOR_BITL0) {
                    ((AnimatedDeer) fixtureA.getUserData()).playerNotHit((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((AnimatedDeer) fixtureB.getUserData()).playerNotHit((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.Fox_NPC_LEFT_SENSOR_BITL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Fox_NPC_LEFT_SENSOR_BITL0) {
                    ((AnimatedDuck) fixtureA.getUserData()).playerHit((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((AnimatedDuck) fixtureB.getUserData()).playerHit((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.Fox_NPC_RIGHT_SENSOR_BITL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Fox_NPC_RIGHT_SENSOR_BITL0) {
                    ((AnimatedDuck) fixtureA.getUserData()).playerNotHit((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((AnimatedDuck) fixtureB.getUserData()).playerNotHit((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.Bird_BITL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Bird_BITL0) {
                    ((AnimatedBirdNPC) fixtureA.getUserData()).playerHit((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((AnimatedBirdNPC) fixtureB.getUserData()).playerHit((PlayerClass2) fixtureA.getUserData());
                }
                break;
        }

    }

    @Override
    public void endContact(Contact contact) {

        //contact between two shapes/fixture
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int collisionDef = fixtureA.getFilterData().categoryBits |
                fixtureB.getFilterData().categoryBits;

        // for spike type1, class SpikeType1
        // if not contacting with spike type1
        if (fixtureA.getUserData() == "spike1JL0" || fixtureB.getUserData() == "spike1JL0") {
            Fixture fixture = fixtureA.getUserData() == "spike1JL0" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).notTakingHit();

            }
        }

        if (fixtureA.getUserData() == "Blade" || fixtureB.getUserData() == "Blade") {
            Fixture fixture = fixtureA.getUserData() == "Blade" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).notTakingHit();

            }
        }

        // for ground enemy, class GroundEnemy
        if (fixtureA.getUserData() == "SCORPION" || fixtureB.getUserData() == "SCORPION") {
            Fixture fixture = fixtureA.getUserData() == "SCORPION" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).notTakingHit();
            }
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).dont_push_back();
            }
        }

        // for flying enemy, class FlyingEnemy
        if (fixtureA.getUserData() == "flyingEnemy" || fixtureB.getUserData() == "flyingEnemy") {
            Fixture fixture = fixtureA.getUserData() == "flyingEnemy" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).notTakingHit();
            }
        }

        // for flying enemy, class Hidden Snake
        if (fixtureA.getUserData() == "HSAttack" || fixtureB.getUserData() == "HSAttack") {
            Fixture fixture = fixtureA.getUserData() == "HSAttack" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).notTakingHit();
            }
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).stopShakeCamera();
            }
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).dont_push_back();
            }
        }

        // for woodLog, class WoodenLog // fixture1
        if (fixtureA.getUserData() == "wodLog1" || fixtureB.getUserData() == "wodLog1") {
            Fixture fixture = fixtureA.getUserData() == "wodLog1" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).onGroundLayer();
            }
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).playerInAir();
            }
        }
        // for woodLog, class WoodenLog // fixture2
        if (fixtureA.getUserData() == "wodLog2" || fixtureB.getUserData() == "wodLog2") {
            Fixture fixture = fixtureA.getUserData() == "wodLog2" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).onGroundLayer();
            }
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).playerInAir();
            }
        }
        // for woodLog, class WoodenLog // fixture3
        if (fixtureA.getUserData() == "wodLog3" || fixtureB.getUserData() == "wodLog3") {
            Fixture fixture = fixtureA.getUserData() == "wodLog3" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).onGroundLayer();
            }
            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).playerInAir();
            }
        }

        if (fixtureA.getUserData() == "spike1JL0" || fixtureB.getUserData() == "spike1JL0") {
            Fixture fixture = fixtureA.getUserData() == "spike1JL0" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).stopShakeCamera();
            }
        }

        if (fixtureA.getUserData() == "Blade" || fixtureB.getUserData() == "Blade") {
            Fixture fixture = fixtureA.getUserData() == "Blade" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).stopShakeCamera();
            }
        }

        if (fixtureA.getUserData() == "SCORPION" || fixtureB.getUserData() == "SCORPION") {
            Fixture fixture = fixtureA.getUserData() == "SCORPION" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).stopShakeCamera();
            }
        }
        if (fixtureA.getUserData() == "flyingEnemy" || fixtureB.getUserData() == "flyingEnemy") {
            Fixture fixture = fixtureA.getUserData() == "flyingEnemy" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && PlayerClass2.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerClass2) object.getUserData()).stopShakeCamera();
            }
        }


        // for specif object
        switch (collisionDef) {
            case GameConfig.Enemy_BitL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Enemy_BitL0) {
                    ((EnemySpritesJL0) fixtureA.getUserData()).detachStaticBody((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((EnemySpritesJL0) fixtureB.getUserData()).detachStaticBody((PlayerClass2) fixtureA.getUserData());
                }
                if (fixtureA.getFilterData().categoryBits == GameConfig.Enemy_BitL0) {
                    ((EnemySpritesJL0) fixtureA.getUserData()).dontPlayAttackAnimation((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((EnemySpritesJL0) fixtureB.getUserData()).dontPlayAttackAnimation((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.Player_BitL0 | GameConfig.Ground_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Player_BitL0) {
                    ((PlayerClass2) fixtureA.getUserData()).playerInAir();
                } else {
                    ((PlayerClass2) fixtureB.getUserData()).playerInAir();
                }
                break;
            case GameConfig.Spring_BitL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Spring_BitL0) {
                    ((SpringJumper) fixtureA.getUserData()).playerNotHit((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((SpringJumper) fixtureB.getUserData()).playerNotHit((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.ENEMY_WAKE_UP_BITL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.ENEMY_WAKE_UP_BITL0) {
                    ((HiddenSnake) fixtureA.getUserData()).playerDetectionOff((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((HiddenSnake) fixtureB.getUserData()).playerDetectionOff((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.Ground_ENEMY_LEFT_SENSOR_BITL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Ground_ENEMY_LEFT_SENSOR_BITL0) {
                    ((GroundEnemy) fixtureA.getUserData()).playerDetectionOff((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((GroundEnemy) fixtureB.getUserData()).playerDetectionOff((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.Ground_ENEMY_RIGHT_SENSOR_BITL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Ground_ENEMY_RIGHT_SENSOR_BITL0) {
                    ((GroundEnemy) fixtureA.getUserData()).playerDetectionOff((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((GroundEnemy) fixtureB.getUserData()).playerDetectionOff((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.Deer_NPC_LEFT_SENSOR_BITL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Deer_NPC_LEFT_SENSOR_BITL0) {
                    ((AnimatedDeer) fixtureA.getUserData()).enemyHit((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((AnimatedDeer) fixtureB.getUserData()).enemyHit((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.Deer_NPC_RIGHT_SENSOR_BITL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Deer_NPC_RIGHT_SENSOR_BITL0) {
                    ((AnimatedDeer) fixtureA.getUserData()).enemyHit((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((AnimatedDeer) fixtureB.getUserData()).enemyHit((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.Fox_NPC_LEFT_SENSOR_BITL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Fox_NPC_LEFT_SENSOR_BITL0) {
                    ((AnimatedDuck) fixtureA.getUserData()).enemyHit((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((AnimatedDuck) fixtureB.getUserData()).enemyHit((PlayerClass2) fixtureA.getUserData());
                }
                break;
            case GameConfig.Fox_NPC_RIGHT_SENSOR_BITL0 | GameConfig.Player_BitL0:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Fox_NPC_RIGHT_SENSOR_BITL0) {
                    ((AnimatedDuck) fixtureA.getUserData()).enemyHit((PlayerClass2) fixtureB.getUserData());
                } else {
                    ((AnimatedDuck) fixtureB.getUserData()).enemyHit((PlayerClass2) fixtureA.getUserData());
                }
                break;
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
