package goblin.prediction;

import goblin.vector.Vec3;
import rlbot.flat.BallPrediction;
import rlbot.flat.PredictionSlice;
import rlbot.render.Renderer;

import java.awt.*;

/**
 * This class can help you get started with ball prediction. Feel free to change it as much as you want,
 * this is part of your bot, not part of the framework!
 */
public class BallPredictionHelper {

    public static void drawTillMoment(BallPrediction ballPrediction, float gameSeconds, Color color, Renderer renderer) {
        Vec3 previousLocation = null;
        for (int i = 0; i < ballPrediction.slicesLength(); i += 4) {
            PredictionSlice slice = ballPrediction.slices(i);
            if (slice.gameSeconds() > gameSeconds) {
                break;
            }
            Vec3 location = new Vec3(slice.physics().location());
            if (previousLocation != null) {
                renderer.drawLine3d(color, previousLocation, location);
            }
            previousLocation = location;
        }
    }
}
