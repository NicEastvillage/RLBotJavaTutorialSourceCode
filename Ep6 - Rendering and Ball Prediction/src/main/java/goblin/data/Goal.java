package goblin.data;

import goblin.vector.Vec3;

public class Goal {

    public static Vec3 get(int team) {
        return team == 0 ? new Vec3(0, -5200, 0) : new Vec3(0, 5200, 0);
    }
}
