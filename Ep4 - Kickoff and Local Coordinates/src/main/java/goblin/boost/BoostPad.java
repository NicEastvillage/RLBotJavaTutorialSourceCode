package goblin.boost;


import goblin.vector.Vec3;

/**
 * Representation of one of the boost pads on the field.
 *
 * This class is here for your convenience, it is NOT part of the framework. You can change it as much
 * as you want, or delete it.
 */
public class BoostPad {

    private final Vec3 location;
    private final boolean isFullBoost;
    private boolean isActive;

    public BoostPad(Vec3 location, boolean isFullBoost) {
        this.location = location;
        this.isFullBoost = isFullBoost;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Vec3 getLocation() {
        return location;
    }

    public boolean isFullBoost() {
        return isFullBoost;
    }

    public boolean isActive() {
        return isActive;
    }
}
