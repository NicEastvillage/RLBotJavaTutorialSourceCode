package goblin.util;

import goblin.data.CarOrientation;
import goblin.vector.Vec3;

public class RLMath {

    public static double sign(double v) {
        return v >= 0 ? 1 : -1;
    }

    public static Vec3 toLocal(Vec3 target, Vec3 from, CarOrientation orientation) {
        Vec3 carToTarget = target.minus(from);
        return new Vec3(
                carToTarget.dot(orientation.forward),
                carToTarget.dot(orientation.left),
                carToTarget.dot(orientation.up)
        );
    }
}
