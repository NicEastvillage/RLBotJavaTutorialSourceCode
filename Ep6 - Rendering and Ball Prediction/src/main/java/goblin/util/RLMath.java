package goblin.util;

import goblin.data.CarOrientation;
import goblin.vector.Vec3;

/**
 * Various helper functions related to math and Rocket League
 */
public class RLMath {

    /**
     * Returns -1 or 1 depending on the sign of v
     */
    public static double sign(double v) {
        return v >= 0 ? 1 : -1;
    }

    /**
     * Transforms the target vector to local space relative to the from vector and the given orientation
     */
    public static Vec3 toLocal(Vec3 target, Vec3 from, CarOrientation orientation) {
        Vec3 carToTarget = target.minus(from);
        return new Vec3(
                carToTarget.dot(orientation.forward),
                carToTarget.dot(orientation.left),
                carToTarget.dot(orientation.up)
        );
    }

    /**
     * Clamps a value to be between a minimum and a maximum value
     */
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
