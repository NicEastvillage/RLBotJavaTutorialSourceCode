package goblin.state;

import goblin.data.CarData;
import goblin.data.ControlsOutput;
import goblin.data.DataPacket;
import goblin.maneuver.DodgeManeuver;
import goblin.util.RLMath;
import goblin.vector.Vec3;
import rlbot.manager.BotLoopRenderer;

import java.awt.*;

public class MoveToPointState extends State {

    public Vec3 target = Vec3.ZERO;
    public double targetSpeed = 1410;
    public boolean allowDodge = true;

    private DodgeManeuver dodge = null;
    private double lastDodgeEnd = 0;

    private static double TIME_BETWEEN_DODGES = 0.2;

    @Override
    public ControlsOutput exec(DataPacket data) {

        // Execute dodge if relevant
        if (dodge != null) {
            if (dodge.done()) {
                dodge = null;
                lastDodgeEnd = data.time;
            } else {
                dodge.target = target;
                return dodge.exec(data);
            }
        }

        ControlsOutput controls = new ControlsOutput();

        CarData myCar = data.car;
        Vec3 carPosition = myCar.position;

        // If we are on the wall, we choose a target that is closer to the ground.
        // This should make the bot go down the wall.
        Vec3 target = this.target;
        if (myCar.hasWheelContact && !myCar.isUpright) {
            target = carPosition.flatten().scaled(0.9);
        }

        data.bot.renderer.drawLine3d(Color.RED, carPosition, target);

        Vec3 carToTarget = target.minus(carPosition);
        Vec3 localTarget = RLMath.toLocal(target, myCar.position, myCar.orientation);

        double dist = carToTarget.mag();
        double forwardDotTarget = myCar.orientation.forward.dot(carToTarget.normalized());

        // Consider dodge
        if (allowDodge
                && myCar.hasWheelContact
                && myCar.isUpright
                && dist > 2200
                && forwardDotTarget > 0.9
                && myCar.velocity.mag() > 650
                && data.time > lastDodgeEnd + TIME_BETWEEN_DODGES) {
            dodge = new DodgeManeuver(data, target);
        }

        double currentSpeed = myCar.velocity.dot(carToTarget.normalized());
        if (currentSpeed < targetSpeed) {
            // We need to speed up
            controls.withThrottle(1.0);
            if (targetSpeed > 1410 && currentSpeed + 60 < targetSpeed && forwardDotTarget > 0.8) {
                controls.withBoost(true);
            }
        } else {
            // We are going too fast
            double extraSpeed = currentSpeed - targetSpeed;
            controls.withThrottle(0.3 - extraSpeed / 500);
        }

        controls.withSteer(-localTarget.normalized().y * 5);

        return controls;
    }
}
