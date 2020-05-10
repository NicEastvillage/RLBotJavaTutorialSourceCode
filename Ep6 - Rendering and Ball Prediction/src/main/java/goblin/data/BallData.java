package goblin.data;


import goblin.vector.Vec3;
import rlbot.flat.BallInfo;

/**
 * Basic information about the ball.
 *
 * This class is here for your convenience, it is NOT part of the framework. You can change it as much
 * as you want, or delete it.
 */
public class BallData {
    public final Vec3 position;
    public final Vec3 velocity;
    public final Vec3 spin;

    public BallData(final BallInfo ball) {
        this.position = new Vec3(ball.physics().location());
        this.velocity = new Vec3(ball.physics().velocity());
        this.spin = new Vec3(ball.physics().angularVelocity());
    }
}
