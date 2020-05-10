package goblin.state;

import goblin.data.ControlsOutput;
import goblin.data.DataPacket;
import goblin.data.Goal;
import goblin.prediction.BallPredictionHelper;
import goblin.prediction.PredictionData;
import goblin.vector.Vec3;

import java.awt.*;

public class AtbaState extends MoveToPointState {

    @Override
    public ControlsOutput exec(DataPacket data) {

        PredictionData prediction = BallPredictionHelper.nextReachable(data);
        Vec3 ballToGoalDir = Goal.get(1 - data.team).minus(prediction.position).normalized();

        target = prediction.position.plus(ballToGoalDir.scaled(-50));
        targetSpeed = data.car.position.dist(prediction.position) / prediction.time;

        data.bot.renderer.drawCross(Color.green, target, 100);

        if (data.car.boost <= 10) {
            data.bot.state = new CollectBoostState();
        }

        return super.exec(data);
    }
}
