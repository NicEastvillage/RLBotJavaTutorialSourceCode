package goblin.state;

import goblin.data.ControlsOutput;
import goblin.data.DataPacket;

public class CollectBoostState extends MoveToPointState {

    @Override
    public ControlsOutput exec(DataPacket data) {

        target = data.closestFullBoost.getLocation();
        targetSpeed = 2300;

        if (data.car.boost >= 60) {
            data.bot.state = new AtbaState();
        }

        return super.exec(data);
    }
}
