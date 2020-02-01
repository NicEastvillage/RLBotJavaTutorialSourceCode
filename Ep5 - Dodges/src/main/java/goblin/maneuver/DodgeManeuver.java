package goblin.maneuver;

import goblin.data.ControlsOutput;
import goblin.data.DataPacket;
import goblin.util.RLMath;
import goblin.vector.Vec3;

public class DodgeManeuver implements Maneuver {

    public Vec3 target;

    boolean done = false;
    double startTime;

    double firstJumpDur = 0.09;
    double postFirstJumpPauseDur = 0.0;
    double aimDur = 0.08;
    double secondJumpDur = 0.28;
    double postSecondJumpPauseDur = 0.14;

    public DodgeManeuver(DataPacket data) {
        startTime = data.time;
    }

    public DodgeManeuver(DataPacket data, Vec3 target) {
        startTime = data.time;
        this.target = target;
    }

    @Override
    public ControlsOutput exec(DataPacket data) {

        double t_firstJumpEnd = firstJumpDur;
        double t_aimBegin =  t_firstJumpEnd + postFirstJumpPauseDur;
        double t_secondJumpBegin = t_aimBegin + aimDur;
        double t_secondJumpEnd = t_secondJumpBegin + secondJumpDur;
        double t_dodgeEnd = t_secondJumpEnd + postSecondJumpPauseDur;

        double currentTime = data.time - startTime;

        ControlsOutput controls = new ControlsOutput();
        controls.withThrottle(1.0);

        if (currentTime < t_firstJumpEnd) {
            controls.withJump(true);

        } else if (t_firstJumpEnd <= currentTime && currentTime < t_aimBegin) {
            // Wait

        } else if (t_aimBegin <= currentTime && currentTime < t_secondJumpEnd) {
            if (target == null) {
                controls.withPitch(-1.0);

            } else {
                Vec3 localTarget = RLMath.toLocal(target, data.car.position, data.car.orientation);
                Vec3 direction = localTarget.withZ(0).normalized();

                controls.withPitch(-direction.x);
                controls.withYaw(RLMath.sign(data.car.orientation.up.z) * direction.y);
            }

            if (currentTime >= t_secondJumpBegin) {
                controls.withJump(true);
            }

        } else if (t_secondJumpEnd <= currentTime && currentTime < t_dodgeEnd) {
            // Let car rotate

        } else if (currentTime >= t_dodgeEnd) {
            done = data.car.hasWheelContact;

        }

        return controls;
    }

    @Override
    public boolean done() {
        return done;
    }
}
