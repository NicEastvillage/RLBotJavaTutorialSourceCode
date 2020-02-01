package goblin.state;

import goblin.data.ControlsOutput;
import goblin.data.DataPacket;
import goblin.maneuver.DodgeManeuver;
import goblin.vector.Vec3;

public class KickoffState extends MoveToPointState {

    DodgeManeuver dodge = null;

    @Override
    public ControlsOutput exec(DataPacket data) {

        target = Vec3.ZERO;
        targetSpeed = 2300;
        allowDodge = false;

        if (!data.isKickoff) {
            dodge = null;
            data.bot.state = new AtbaState();
        }

        if (data.car.position.dist(target) < 840) {
            if (dodge == null) dodge = new DodgeManeuver(data);
            dodge.target = data.ball.position;
            return dodge.exec(data);
        }

        return super.exec(data);
    }
}
