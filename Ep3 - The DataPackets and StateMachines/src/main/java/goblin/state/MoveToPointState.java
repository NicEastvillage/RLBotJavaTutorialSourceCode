package goblin.state;

import goblin.data.CarData;
import goblin.data.ControlsOutput;
import goblin.data.DataPacket;
import goblin.vector.Vec3;

public class MoveToPointState extends State {

    public Vec3 target = Vec3.ZERO;
    public double targetSpeed = 1410;

    @Override
    public ControlsOutput exec(DataPacket data) {
        CarData myCar = data.car;
        Vec3 carPosition = myCar.position.flatten();

        // Subtract the two positions to get a vector pointing from the car to the ball.
        Vec3 carToTarget = target.minus(carPosition);

        double currentSpeed = myCar.velocity.dot(carToTarget.normalized());
        double throttle = 1;
        boolean boost = false;
        if (currentSpeed < targetSpeed) {
            // We need to speed up
            throttle = 1;
            if (targetSpeed > 1410 && currentSpeed + 60 < targetSpeed) {
                boost = true;
            }
        } else {
            // We are going too fast
            double extraSpeed = currentSpeed - targetSpeed;
            throttle = 0.3 - extraSpeed / 500;
        }

        double steer = -myCar.orientation.left.dot(carToTarget.normalized()) * 5;

        return new ControlsOutput()
                .withSteer(steer)
                .withThrottle(throttle)
                .withBoost(boost);
    }
}
