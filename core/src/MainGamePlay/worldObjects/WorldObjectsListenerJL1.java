package MainGamePlay.worldObjects;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import GameConfiguration.GameConfig;
import MainGamePlay.AllSprites.CoinsClass.CoinSprite;
import MainGamePlay.AllSprites.EnemyClass1.Enemy2Definition;
import MainGamePlay.AllSprites.EnemyClass1.EnemyDefinition;
import MainGamePlay.AllSprites.EnemyClass1.EnemySprite;
import MainGamePlay.AllSprites.Hazards.HazardsSprite;
import MainGamePlay.AllSpritesItemsForJL0.CoinClasses.CoinSpriteJL0;
import MainGamePlay.Player.MainPlayer;
import MainGamePlay.Player.PlayerWeapon;
import MainGamePlay.Player2.PlayerClass2;

public class WorldObjectsListenerJL1 implements ContactListener {

    // contact listener is called when two fixture/body get collied with each other
    @Override
    public void beginContact(Contact contact) {
        //contact between two shapes/fixture
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int collisionDef = fixtureA.getFilterData().categoryBits |
                fixtureB.getFilterData().categoryBits;

        // if enemy collide with pillar , will turn back , class EnemyDefinition
        if (fixtureA.getUserData() == "pillar" || fixtureB.getUserData() == "pillar") {
            Fixture fixture = fixtureA.getUserData() == "pillar" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && EnemySprite.class.isAssignableFrom(object.getUserData().getClass())) {
                ((EnemySprite) object.getUserData()).reversedEnemy1(true, false);
            }
        }
        // if bullet touch with ground , it will destroy with timing , class PlayerWeapon
        if (fixtureA.getUserData() == "bullet" || fixtureB.getUserData() == "bullet") {
            Fixture fixture = fixtureA.getUserData() == "bullet" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && PlayerWeapon.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerWeapon) object.getUserData()).setToDestroy();
            }

        }
        // for player bullet , class PlayerWeapon // ground enemy
        if (fixtureA.getUserData() == "bullet" || fixtureB.getUserData() == "bullet") {
            Fixture fixture = fixtureA.getUserData() == "bullet" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && EnemyDefinition.class.isAssignableFrom(object.getUserData().getClass())) {
                ((EnemyDefinition) object.getUserData()).playerHit(new MainPlayer());
            }
            if (object.getUserData() != null && PlayerWeapon.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerWeapon) object.getUserData()).setToDestroy();
            }

        }

        // for player bullet , class PlayerWeapon // flaying enemy (bat)
        if (fixtureA.getUserData() == "bullet" || fixtureB.getUserData() == "bullet") {
            Fixture fixture = fixtureA.getUserData() == "bullet" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && Enemy2Definition.class.isAssignableFrom(object.getUserData().getClass())) {
                ((Enemy2Definition) object.getUserData()).playerHit(new MainPlayer());
            }
            if (object.getUserData() != null && PlayerWeapon.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerWeapon) object.getUserData()).setToDestroy();
            }

        }

        // for animated blade, class AnimatedHazardsDefinition
        if (fixtureA.getUserData() == "AniBlade" || fixtureB.getUserData() == "AniBlade") {
            Fixture fixture = fixtureA.getUserData() == "AniBlade" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && MainPlayer.class.isAssignableFrom(object.getUserData().getClass())) {
                ((MainPlayer) object.getUserData()).playerDeath();

            }
            if (object.getUserData() != null && PlayerWeapon.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerWeapon) object.getUserData()).setToDestroy();
            }

        }

        // for spike type1, class  SpikeType1
        if (fixtureA.getUserData() == "spike1" || fixtureB.getUserData() == "spike1") {
            Fixture fixture = fixtureA.getUserData() == "spike1" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && MainPlayer.class.isAssignableFrom(object.getUserData().getClass())) {
                ((MainPlayer) object.getUserData()).playerDeath();

            }
            if (object.getUserData() != null && PlayerWeapon.class.isAssignableFrom(object.getUserData().getClass())) {
                ((PlayerWeapon) object.getUserData()).setToDestroy();
            }

        }

        // for ground enemy1, class EnemyDefinition
        if (fixtureA.getUserData() == "enemyG1" || fixtureB.getUserData() == "enemyG1") {
            Fixture fixture = fixtureA.getUserData() == "enemyG1" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && MainPlayer.class.isAssignableFrom(object.getUserData().getClass())) {
                ((MainPlayer) object.getUserData()).playerDeath();

            }
        }

        // for flying enemy (bat), class Enemy2Definition
        if (fixtureA.getUserData() == "flyingEnemy" || fixtureB.getUserData() == "flyingEnemy") {
            Fixture fixture = fixtureA.getUserData() == "flyingEnemy" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && MainPlayer.class.isAssignableFrom(object.getUserData().getClass())) {
                ((MainPlayer) object.getUserData()).playerDeath();
            }
        }

        // for specif object
        switch (collisionDef) {
            case GameConfig.Coin_Bit | GameConfig.Player_Bit:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Coin_Bit) {
                    ((CoinSprite) fixtureA.getUserData()).playerHit((MainPlayer) fixtureB.getUserData());
                } else {
                    ((CoinSprite) fixtureB.getUserData()).playerHit((MainPlayer) fixtureA.getUserData());
                }
                break;
            case GameConfig.Enemy_Bit | GameConfig.Player_Bit:
                if (fixtureA.getFilterData().categoryBits == GameConfig.Enemy_Bit) {
                    ((EnemySprite) fixtureA.getUserData()).playAttackAnimation((MainPlayer) fixtureB.getUserData());
                } else {
                    ((EnemySprite) fixtureB.getUserData()).playAttackAnimation((MainPlayer) fixtureA.getUserData());
                }
        }

    }

    @Override
    public void endContact(Contact contact) {
        //dis-contact between two shapes/fixture
        //contact between two shapes/fixture
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int collisionDef = fixtureA.getFilterData().categoryBits |
                fixtureB.getFilterData().categoryBits;


        // for animated blade, class AnimatedHazardDefinition
        // if not contacting with blade
        if (fixtureA.getUserData() == "AniBlade" || fixtureB.getUserData() == "AniBlade") {
            Fixture fixture = fixtureA.getUserData() == "AniBlade" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && MainPlayer.class.isAssignableFrom(object.getUserData().getClass())) {
                ((MainPlayer) object.getUserData()).notTakingHit();

            }

        }

        // for spike type1, class SpikeType1
        // if not contacting with spike type1
        if (fixtureA.getUserData() == "spike1" || fixtureB.getUserData() == "spike1") {
            Fixture fixture = fixtureA.getUserData() == "spike1" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && MainPlayer.class.isAssignableFrom(object.getUserData().getClass())) {
                ((MainPlayer) object.getUserData()).notTakingHit();

            }
        }

        // for ground enemy1, class EnemyDefinition
        // if not contacting with enemy
        if (fixtureA.getUserData() == "enemyG1" || fixtureB.getUserData() == "enemyG1") {
            Fixture fixture = fixtureA.getUserData() == "enemyG1" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && MainPlayer.class.isAssignableFrom(object.getUserData().getClass())) {
                ((MainPlayer) object.getUserData()).notTakingHit();

            }
        }

        // for flying enemy (bat), class Enemy2Definition
        // if not contacting with enemy
        if (fixtureA.getUserData() == "flyingEnemy" || fixtureB.getUserData() == "flyingEnemy") {
            Fixture fixture = fixtureA.getUserData() == "flyingEnemy" ? fixtureA : fixtureB;
            Fixture object = fixture == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && MainPlayer.class.isAssignableFrom(object.getUserData().getClass())) {
                ((MainPlayer) object.getUserData()).notTakingHit();

            }
        }


        // for specif object
        switch (collisionDef) {
            case GameConfig.Enemy_Bit | GameConfig.Player_Bit:
                // if not contacting with player
                if (fixtureA.getFilterData().categoryBits == GameConfig.Enemy_Bit) {
                    ((EnemySprite) fixtureA.getUserData()).dontPlayAttackAnimation((MainPlayer) fixtureB.getUserData());
                } else {
                    ((EnemySprite) fixtureB.getUserData()).dontPlayAttackAnimation((MainPlayer) fixtureA.getUserData());
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
