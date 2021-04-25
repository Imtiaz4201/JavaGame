package AssetsManager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Manager {

    public static final Manager managerInstance = new Manager();
    public AssetManager assetManager = new AssetManager(new InternalFileHandleResolver());

    // audio
    //=============
    public final String menuMusic = "Audio/Musics/menuMusic1.mp3";
    public final String button_click = "Audio/Sounds/click.ogg";
    public final String starting_music = "Audio/Musics/startingAppMusic.ogg";
    public final String dialogOpen = "Audio/Sounds/maximize.ogg";
    public final String dialogClose = "Audio/Sounds/minimize.ogg";
    public final String jungleSoundEffect = "Audio/GamePlayAudio/ForestNightSound.ogg";
    public final String runningSoundEffect = "Audio/GamePlayAudio/run.ogg";
    public final String arrowSound = "Audio/GamePlayAudio/arrow.ogg";
    public final String coinSound = "Audio/GamePlayAudio/coin.ogg";
    public final String springJumpSound = "Audio/GamePlayAudio/jumpSound.ogg";
    public final String jLevel0 = "Audio/GamePlayAudio/JL0EnvironMusic.ogg";
    public final String wolfAttackSound = "Audio/GamePlayAudio/wolfAttack.ogg";
    public final String snakeAttackSound = "";
    public final String snakeHissingSound = "Audio/GamePlayAudio/SnakeHissing.ogg";

    // images
    //================

    // for BG
    public final String BG2Json = "Backgrounds/BG2/BG.json";
    public final String BG3Json = "Backgrounds/BG2/jBG.json";
    public final String BG4Json = "Backgrounds/BG2/menuBG.json";
    public final String startingAppAnimation = "Backgrounds/startingAnim.png";
    // for level
    public final String levelUiSkin = "mainLevelItems/levelButton.json";
    public final String subLevelUiSkin = "subLevelItems/buttonSkin.json";
    public final String lifeSkin = "subLevelItems/lifeSkin.json";
    public final String settingsSkin = "settingSkinItems/skin/golden-ui-skin.json";
    public final String JL0img = "mainLevelItems/levelStartingImgs/JL0.png";
    public final String arrowAmmo = "subLevelItems/items/bowBeg.png";
    public final String GameOverText = "subLevelItems/items/GameOverText.png";
    public final String ControlInfo = "settingSkinItems/ControlInfo/ControlInfo.json";
    // for jungle levels
    public final String gameOverBG = "subLevelItems/items/jungleBG.png";

    // for GamePlay
    public final String playerPack = "GamePlayAssets/sprites/player1.atlas";
    public final String enemy1Pack = "GamePlayAssets/sprites/snake.atlas";
    public final String flyingEnemy = "GamePlayAssets/sprites/bat.atlas";
    public final String hazards = "GamePlayAssets/items/hazardsPack1.atlas";
    public final String coins = "GamePlayAssets/items/coins.atlas";
    public final String groundObjects = "GamePlayAssets/items/grounObject.atlas";
    public final String torchLight = "GamePlayAssets/items/torch.atlas";
    public final String playerArrow = "GamePlayAssets/sprites/playerArrow.atlas";
    public final String enemy2Pack = "GamePlayAssets/sprites/enemies.atlas";
    public final String animatedFire = "GamePlayAssets/items/fireAnimation.atlas";
    public final String woodLog = "GamePlayAssets/items/movingPlatform.atlas";
    public final String animatedWater = "GamePlayAssets/items/animatedWater.atlas";
    public final String scoreLogo = "subLevelItems/items/scoreLogo.png";
    public final String scoreFrame = "subLevelItems/items/clipB.png";
    public final String springJumper = "GamePlayAssets/items/springJumper.atlas";
    public final String cloud = "GamePlayAssets/items/clouds.atlas";
    public final String hiddenSnake = "GamePlayAssets/sprites/hiddenSnake.atlas";
    public final String AnimalsPack = "GamePlayAssets/sprites/AnimlasNPC.atlas";
    public final String animatedDuck = "GamePlayAssets/sprites/duck.atlas";
    public final String ButterfliesNPC = "GamePlayAssets/sprites/BFlies.atlas";
    public final String BirdNPC = "GamePlayAssets/sprites/birdNPC.atlas";

    public void load() {
        assetManager.load(menuMusic, Music.class);
        assetManager.load(button_click, Sound.class);
        assetManager.load(dialogOpen, Sound.class);
        assetManager.load(dialogClose, Sound.class);
        assetManager.load(jungleSoundEffect, Music.class);
        assetManager.load(runningSoundEffect, Sound.class);
        assetManager.load(jLevel0, Music.class);
        assetManager.load(coinSound, Sound.class);
        assetManager.load(arrowSound, Sound.class);
        assetManager.load(springJumpSound, Sound.class);
        assetManager.load(wolfAttackSound, Sound.class);
        assetManager.load(snakeHissingSound, Music.class);
        assetManager.load(starting_music, Music.class);
        //=========================================================
        assetManager.load(levelUiSkin, Skin.class, new SkinLoader.SkinParameter("mainLevelItems/itemsSkins/mainLevelSkinPack.atlas"));
        assetManager.load(BG2Json, Skin.class, new SkinLoader.SkinParameter("Backgrounds/BG2/BGSkin.pack"));
        assetManager.load(BG3Json, Skin.class, new SkinLoader.SkinParameter("Backgrounds/BG2/jungleBG.pack"));
        assetManager.load(BG4Json, Skin.class, new SkinLoader.SkinParameter("Backgrounds/BG2/mBG.pack"));
        assetManager.load(subLevelUiSkin, Skin.class, new SkinLoader.SkinParameter("subLevelItems/items/subLevelSkinPack.atlas"));
        assetManager.load(lifeSkin, Skin.class, new SkinLoader.SkinParameter("subLevelItems/items/life.atlas"));
        assetManager.load(settingsSkin, Skin.class, new SkinLoader.SkinParameter("settingSkinItems/skin/golden-ui-skin.atlas"));
        assetManager.load(ControlInfo, Skin.class, new SkinLoader.SkinParameter("settingSkinItems/ControlInfo/gamePlayExtra.atlas"));
        assetManager.load(playerPack, TextureAtlas.class);
        assetManager.load(enemy1Pack, TextureAtlas.class);
        assetManager.load(flyingEnemy, TextureAtlas.class);
        assetManager.load(hazards, TextureAtlas.class);
        assetManager.load(coins, TextureAtlas.class);
        assetManager.load(groundObjects, TextureAtlas.class);
        assetManager.load(torchLight, TextureAtlas.class);
        assetManager.load(playerArrow, TextureAtlas.class);
        assetManager.load(enemy2Pack, TextureAtlas.class);
        assetManager.load(woodLog, TextureAtlas.class);
        assetManager.load(animatedFire, TextureAtlas.class);
        assetManager.load(animatedWater, TextureAtlas.class);
        assetManager.load(springJumper, TextureAtlas.class);
        assetManager.load(JL0img, Texture.class);
        assetManager.load(arrowAmmo, Texture.class);
        assetManager.load(gameOverBG, Texture.class);
        assetManager.load(scoreLogo, Texture.class);
        assetManager.load(scoreFrame, Texture.class);
        assetManager.load(GameOverText, Texture.class);
        assetManager.load(cloud, TextureAtlas.class);
        assetManager.load(hiddenSnake, TextureAtlas.class);
        assetManager.load(AnimalsPack, TextureAtlas.class);
        assetManager.load(animatedDuck, TextureAtlas.class);
        assetManager.load(ButterfliesNPC, TextureAtlas.class);
        assetManager.load(BirdNPC, TextureAtlas.class);
        assetManager.load(startingAppAnimation, Texture.class);
    }

    public void dispose() {
        assetManager.dispose();
    }

}
