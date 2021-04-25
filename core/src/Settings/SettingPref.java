package Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

import AssetsManager.Manager;

public class SettingPref {
    public static final SettingPref instance = new SettingPref();
    public Preferences preferences;

    public boolean sound;
    public boolean music;
    public boolean sfx;
    public float soundVolume;
    public float musicVolume;
    public float sfxVolume;
    public int score;
    public int high_score, high_score1, high_score2, high_score3;
    public String user_name, user_name1, user_name2, user_name3;
    public boolean bn, bn1, bn2, bn3;
    public boolean block_user1, block_user2, block_user3, block_user4;
    public boolean changeStar1Color;

    public SettingPref() {
        preferences = Gdx.app.getPreferences("IHop.game.settings");
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void eraseScore() {
        this.score = 0;
    }

    public void saveData() {
        // data will be saved to IHop.game.settings
        preferences.putBoolean("sound", sound);
        preferences.putBoolean("music", music);
        preferences.putBoolean("sfx", sfx);
        preferences.putBoolean("bn", bn);
        preferences.putBoolean("bn1", bn1);
        preferences.putBoolean("bn2", bn2);
        preferences.putBoolean("bn3", bn3);
        preferences.putBoolean("block_user1", block_user1);
        preferences.putBoolean("block_user2", block_user2);
        preferences.putBoolean("block_user3", block_user3);
        preferences.putBoolean("block_user4", block_user4);
        preferences.putFloat("soundVolume", soundVolume);
        preferences.putFloat("musicVolume", musicVolume);
        preferences.putFloat("sfxVolume", sfxVolume);
        preferences.putInteger("score", score);
        preferences.putInteger("high_score", high_score);
        preferences.putInteger("high_score1", high_score1);
        preferences.putInteger("high_score2", high_score2);
        preferences.putInteger("high_score3", high_score3);
        preferences.putString("user_name", user_name);
        preferences.putString("user_name1", user_name1);
        preferences.putString("user_name2", user_name2);
        preferences.putString("user_name3", user_name3);
        preferences.putBoolean("changeStar1Color", changeStar1Color);

        preferences.flush(); //  to Make sure the preferences are persisted/changed time to time
    }

    public void loadData() {
        // data will be loaded from IHop.game.settings
        // if there is no data stored manually , the default value will be loaded
        sound = preferences.getBoolean("sound", true);
        music = preferences.getBoolean("music", true);
        bn = preferences.getBoolean("bn", false);
        bn1 = preferences.getBoolean("bn1", false);
        bn2 = preferences.getBoolean("bn2", false);
        bn3 = preferences.getBoolean("bn3", false);
        block_user1 = preferences.getBoolean("block_user1", false);
        block_user2 = preferences.getBoolean("block_user2", false);
        block_user3 = preferences.getBoolean("block_user3", false);
        block_user4 = preferences.getBoolean("block_user4", false);
        sfx = preferences.getBoolean("sfx", true);
        score = preferences.getInteger("score", 0);
        high_score = preferences.getInteger("high_score", 0);
        user_name = preferences.getString("user_name", "");
        high_score1 = preferences.getInteger("high_score1", 0);
        user_name1 = preferences.getString("user_name1", "");
        high_score2 = preferences.getInteger("high_score2", 0);
        user_name2 = preferences.getString("user_name2", "");
        high_score3 = preferences.getInteger("high_score3", 0);
        user_name3 = preferences.getString("user_name3", "");
        soundVolume = MathUtils.clamp(preferences.getFloat("soundVolume", 0.5f), 0.0f, 1.0f);
        musicVolume = MathUtils.clamp(preferences.getFloat("musicVolume", 0.5f), 0.0f, 1.0f);
        sfxVolume = MathUtils.clamp(preferences.getFloat("sfxVolume", 0.5f), 0.0f, 1.0f);
        changeStar1Color = preferences.getBoolean("changeStar1Color", false);
        // clamp math defines the range between min and max
    }

} // class to save data for setting
