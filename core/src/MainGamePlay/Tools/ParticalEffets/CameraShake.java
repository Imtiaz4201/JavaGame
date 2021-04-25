package MainGamePlay.Tools.ParticalEffets;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public abstract class CameraShake {

    private static float time = 0;
    private static float currentTime = 0;
    private static float power = 0;
    private static float currentPower = 0;
    private static Random random;
    private static Vector2 pos = new Vector2();

    public static void shake(float shakePower, float shakeLength) {
        random = new Random();
        power = shakePower;
        time = shakeLength;
        currentTime = 0;
    }

    public static Vector2 tick(float delta) {
        if (currentTime <= time) {
            currentPower = power * ((time - currentTime) / time);

            pos.x = (random.nextFloat() - 0.5f) * 2 * currentPower;
            pos.y = (random.nextFloat() - 0.5f) * 2 * currentPower;

            currentTime += delta;
        } else {
            time = 0;
        }
        return pos;
    }

    public static float getRumbleTimeLeft() {
        return time;
    }

    public static Vector2 getPos() {
        return pos;
    }
}
