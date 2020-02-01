package goblin.data;

import goblin.Goblin;
import goblin.boost.BoostManager;
import goblin.boost.BoostPad;
import goblin.vector.Vec3;
import rlbot.flat.GameTickPacket;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is here for your convenience, it is NOT part of the framework. You can change it as much
 * as you want, or delete it. The benefits of using this instead of rlbot.flat.GameTickPacket are:
 * 1. You end up with nice custom Vec3 objects that you can call methods on.
 * 2. If the framework changes its data format, you can just update the code here
 * and leave your bot logic alone.
 */
public class DataPacket {

    public final Goblin bot;

    /** Your own car, based on the playerIndex */
    public final CarData car;

    public final List<CarData> allCars;

    public final BallData ball;
    public final int team;

    /** The index of your player */
    public final int playerIndex;

    public final boolean isKickoff;
    public final double time;
    public final double timeRemaining;

    public final BoostPad closestFullBoost;
    public final double closestFullBoostDist;

    public DataPacket(Goblin bot, GameTickPacket packet) {

        this.bot = bot;
        this.playerIndex = bot.getIndex();
        this.ball = new BallData(packet.ball());

        allCars = new ArrayList<>();
        for (int i = 0; i < packet.playersLength(); i++) {
            allCars.add(new CarData(packet.players(i), packet.gameInfo().secondsElapsed()));
        }

        this.car = allCars.get(playerIndex);
        this.team = this.car.team;

        // Game info
        isKickoff = ball.position.flatten().isZero();
        time = packet.gameInfo().secondsElapsed();
        timeRemaining = packet.gameInfo().gameTimeRemaining();

        // Calculate closest boost pad
        BoostPad closestPad = null;
        double shortestDist = 99999999;
        for (BoostPad pad : BoostManager.getFullBoosts()) {
            double dist = pad.getLocation().dist(car.position);
            if (closestPad == null || (dist < shortestDist && pad.isActive())) {
                closestPad = pad;
                shortestDist = dist;
            }
        }

        closestFullBoost = closestPad;
        closestFullBoostDist = shortestDist;
    }
}
