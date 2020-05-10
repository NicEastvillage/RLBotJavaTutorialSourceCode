package goblin.maneuver;

import goblin.data.ControlsOutput;
import goblin.data.DataPacket;

public interface Maneuver {

    ControlsOutput exec(DataPacket data);

    boolean done();
}
