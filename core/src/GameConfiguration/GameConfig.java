package GameConfiguration;

public class GameConfig {

    // Box2d Bits for jungle level 0
    public static final short Default_BitL0 = 1;
    public static final short Player_BitL0 = 2;
    public static final short Enemy_BitL0 = 64;
    public static final short Enemy_Hit_BitL0 = 2;
    public static final short Coin_BitL0 = 8;
    public static final short Star_BitL0 = 16;
    public static final short Ground_BitL0 = 128;
    public static final short PlatForm_BitL0 = 64;
    public static final short NOTHING_BITL0 = 0;
    public static final short Spring_BitL0 = 32;
    public static final short ENEMY_LEFT_SENSOR_BITL0 = 4;
    public static final short ENEMY_RIGHT_SENSOR_BITL0 = 256;
    public static final short ENEMY_WAKE_UP_BITL0 = 512;
    public static final short Ground_ENEMY_LEFT_SENSOR_BITL0 = 1024;
    public static final short Ground_ENEMY_RIGHT_SENSOR_BITL0 = 2048;
    public static final short Deer_NPC_LEFT_SENSOR_BITL0 = 5;
    public static final short Deer_NPC_RIGHT_SENSOR_BITL0 = 9;
    public static final short Fox_NPC_LEFT_SENSOR_BITL0 = 20;
    public static final short Fox_NPC_RIGHT_SENSOR_BITL0 = 12;
    public static final short NPC_Boundary_Left_BITL0 = 112;
    public static final short NPC_Boundary_Right_BITL0 = 114;
    public static final short Bird_BITL0 = 116;

    // Box2d Bits for jungle level 1
    public static final short Default_Bit = 1;
    public static final short Player_Bit = 2;
    public static final short Player_Hit_Bit = 4;
    public static final short Enemy_Bit = 64;
    public static final short Enemy_Hit_Bit = 2;
    public static final short Enemy_Destroyed_Bit = 16;
    public static final short Coin_Bit = 8;
    public static final short Object_Bit = 32;
    public static final short Ground_Bit = 128;
    public static final short PlatForm_Bit = 64;
    public static final short NOTHING_BIT = 0;

    // Game Settings
    public static final int WIDTH = 900;
    public static final int HEIGHT = 700;
    public static final String title = "I Hop";
    public static final int PPM = 100;
    public static final int V_WIDTH = WIDTH / 2;
    public static final int V_HEIGHT = HEIGHT / 2;

}
