package goblin.vector;

/**
 * A simple 3d vector class with the most essential operations.
 */
public class Vec3 extends rlbot.vector.Vector3 {

    public static final Vec3 UP = new Vec3(0, 0, 1);
    public static final Vec3 ZERO = new Vec3(0, 0, 0);

    public final double x, y, z;

    public Vec3(double x, double y, double z) {
        super((float) x, (float) y, (float) z);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3(rlbot.flat.Vector3 vec) {
        this(vec.x(), vec.y(), vec.z());
    }

    public Vec3 withX(double newX) {
        return new Vec3(newX, y, z);
    }

    public Vec3 withY(double newY) {
        return new Vec3(x, newY, z);
    }

    public Vec3 withZ(double newZ) {
        return new Vec3(x, y, newZ);
    }

    public Vec3 plus(Vec3 other) {
        return new Vec3(x + other.x, y + other.y, z + other.z);
    }

    public Vec3 minus(Vec3 other) {
        return new Vec3(x - other.x, y - other.y, z - other.z);
    }

    public Vec3 scaled(double scale) {
        return new Vec3(x * scale, y * scale, z * scale);
    }

    public Vec3 scaledToMag(double magnitude) {
        if (isZero()) {
            throw new IllegalStateException("Cannot scale up a vector with length zero!");
        }
        double scaleRequired = magnitude / mag();
        return scaled(scaleRequired);
    }

    public double dist(Vec3 other) {
        double xDiff = x - other.x;
        double yDiff = y - other.y;
        double zDiff = z - other.z;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }

    public double mag() {
        return Math.sqrt(magSqr());
    }

    public double magSqr() {
        return x * x + y * y + z * z;
    }

    public Vec3 normalized() {
        if (isZero()) {
            throw new IllegalStateException("Cannot normalize a vector with length zero!");
        }
        return this.scaled(1 / mag());
    }

    public double dot(Vec3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public boolean isZero() {
        return x == 0 && y == 0 && z == 0;
    }

    public Vec3 flatten() {
        return new Vec3(x, y, 0);
    }

    public double angle(Vec3 other) {
        double mag2 = magSqr();
        double vmag2 = other.magSqr();
        double dot = dot(other);
        return Math.acos(dot / Math.sqrt(mag2 * vmag2));
    }

    public Vec3 cross(Vec3 other) {
        double tx = y * other.z - z * other.y;
        double ty = z * other.x - x * other.z;
        double tz = x * other.y - y * other.x;
        return new Vec3(tx, ty, tz);
    }
}
