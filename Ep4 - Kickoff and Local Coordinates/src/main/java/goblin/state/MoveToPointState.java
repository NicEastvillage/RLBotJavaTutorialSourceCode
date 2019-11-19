package goblin.state;

import goblin.data.CarData;
import goblin.data.ControlsOutput;
import goblin.data.DataPacket;
import goblin.vector.Vec3;
import rlbot.manager.BotLoopRenderer;

import java.awt.*;

public class MoveToPointState extends State {

    public Vec3 target = Vec3.ZERO;
    public double targetSpeed = 1410;

    @Override
    public ControlsOutput exec(DataPacket data) {
        CarData myCar = data.car;
        Vec3 carPosition = myCar.position;

        // If we are on the wall, we choose a target that is closer to the ground.
        // This should make the bot go down the wall.
        Vec3 target = this.target;
        if (myCar.hasWheelContact && !myCar.isUpright) {
            target = carPosition.flatten().scaled(0.9);
        }

        BotLoopRenderer renderer = BotLoopRenderer.forBotLoop(data.bot);
        renderer.drawLine3d(Color.RED, carPosition, target);

        // Subtract the two positions to get a vector pointing from the car to the ball.
        Vec3 carToTarget = target.minus(carPosition);
        Vec3 localTarget = new Vec3(
                carToTarget.dot(myCar.orientation.forward),
                carToTarget.dot(myCar.orientation.left),
                carToTarget.dot(myCar.orientation.up)
        );

        double currentSpeed = myCar.velocity.dot(carToTarget.normalized());
        double throttle = 1;
        boolean boost = false;
        if (currentSpeed < targetSpeed) {
            // We need to speed up
            throttle = 1;
            if (targetSpeed > 1410 && currentSpeed + 60 < targetSpeed &&
                    myCar.orientation.forward.dot(carToTarget.normalized()) > 0.8) {
                boost = true;
            }
        } else {
            // We are going too fast
            double extraSpeed = currentSpeed - targetSpeed;
            throttle = 0.3 - extraSpeed / 500;
        }

        double steer = -localTarget.normalized().y * 5;

        return new ControlsOutput()
                .withSteer(steer)
                .withThrottle(throttle)
                .withBoost(boost);
    }
}
