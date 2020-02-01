package goblin.state;

import goblin.data.ControlsOutput;
import goblin.data.DataPacket;

public class AtbaState extends MoveToPointState {

    @Override
    public ControlsOutput exec(DataPacket data) {

        target = data.ball.position;
        targetSpeed = 1800;

        if (data.car.boost <= 10) {
            data.bot.state = new CollectBoostState();
        }

        return super.exec(data);
    }
}
