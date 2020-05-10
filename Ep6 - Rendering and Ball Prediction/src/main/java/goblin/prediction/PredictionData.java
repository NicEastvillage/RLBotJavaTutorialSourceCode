package goblin.prediction;

import goblin.vector.Vec3;

/**
 * A small class to hold results from ball prediction
 */
public class PredictionData {

    public Vec3 position;
    public double time;

    public PredictionData(Vec3 position, double time) {
        this.position = position;
        this.time = time;
    }
}
