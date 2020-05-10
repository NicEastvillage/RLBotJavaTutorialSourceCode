package goblin.data;


import rlbot.flat.PlayerInfo;
import goblin.vector.Vec3;

/**
 * The car's orientation in space, a.k.a. what direction it's pointing.
 */
public class CarOrientation {

    /** The direction that the front of the car is facing */
    public final Vec3 forward;

    /** The direction the roof of the car is facing. (0, 0, 1) means the car is upright. */
    public final Vec3 up;

    /** The direction that the left side of the car is facing. */
    public final Vec3 left;

    public CarOrientation(Vec3 forward, Vec3 up) {

        this.forward = forward;
        this.up = up;
        this.left = forward.cross(up);
    }

    public static CarOrientation fromFlatbuffer(PlayerInfo playerInfo) {
        return convert(
                playerInfo.physics().rotation().pitch(),
                playerInfo.physics().rotation().yaw(),
                playerInfo.physics().rotation().roll());
    }

    /**
     * All params are in radians.
     */
    private static CarOrientation convert(double pitch, double yaw, double roll) {

        double cp = Math.cos(pitch);
        double sp = Math.sin(pitch);
        double cy = Math.cos(yaw);
        double sy = Math.sin(yaw);
        double cr = Math.cos(roll);
        double sr = Math.sin(roll);

        Vec3 forward = new Vec3(cp * cy, cp * sy, sp);
        Vec3 up = new Vec3(-cr * cy * sp - sr * sy, -cr * sy * sp + sr * cy, cp * cr);

        return new CarOrientation(forward, up);
    }
}
