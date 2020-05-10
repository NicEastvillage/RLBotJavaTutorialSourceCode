package goblin.state;

import goblin.data.ControlsOutput;
import goblin.data.DataPacket;

public abstract class State {

    public abstract ControlsOutput exec(DataPacket data);
}
