package goblin.prediction;

import goblin.data.DataPacket;
import goblin.vector.Vec3;
import rlbot.cppinterop.RLBotDll;
import rlbot.cppinterop.RLBotInterfaceException;
import rlbot.flat.BallPrediction;
import rlbot.flat.PredictionSlice;
import rlbot.render.Renderer;

import java.awt.*;

public class BallPredictionHelper {

    /**
     * Returns the next ball position that we are able to reach
     */
    public static PredictionData nextReachable(DataPacket data) {
        try {
            BallPrediction ballPrediction = RLBotDll.getBallPrediction();
            for (int i = 0; i < 6 * 60; i += 6) {
                PredictionSlice slice = ballPrediction.slices(i);

                Vec3 position = new Vec3(slice.physics().location());
                double time = i / 60.0;

                if (position.z > 100) continue;

                Vec3 carToBall = position.minus(data.car.position);
                Vec3 carToBallDir = carToBall.normalized();
                double dist = carToBall.mag();

                double speedTowardsBall = data.car.velocity.dot(carToBallDir);
                double averageSpeed = (speedTowardsBall + 2300) / 2.0;

                double travelTime = dist / averageSpeed;

                if (travelTime < time) {
                    return new PredictionData(position, time);
                }
            }

        } catch (RLBotInterfaceException ignored) {

        }

        return new PredictionData(data.ball.position, 0);
    }
}
