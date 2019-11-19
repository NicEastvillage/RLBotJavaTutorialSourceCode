package goblin.state;

import goblin.data.ControlsOutput;
import goblin.data.DataPacket;
import goblin.vector.Vec3;

public class KickoffState extends MoveToPointState {

    @Override
    public ControlsOutput exec(DataPacket data) {

        target = Vec3.ZERO;
        targetSpeed = 2300;

        if (!data.isKickoff) {
            data.bot.state = new AtbaState();
        }

        return super.exec(data);
    }
}
